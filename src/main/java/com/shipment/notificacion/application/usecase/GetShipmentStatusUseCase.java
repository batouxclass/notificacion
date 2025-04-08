package com.shipment.notificacion.application.usecase;

import com.shipment.notificacion.application.services.EmailService;
import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.application.services.WeatherStatusService;
import com.shipment.notificacion.application.usecase.ports.NotificationLogger;
import com.shipment.notificacion.domain.repository.UserRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servicio encargado de obtener el estado de un envío para un usuario específico,
 * enviar una notificación por correo electrónico y registrar la actividad en un log.
 *
 * <p>Este caso de uso orquesta las siguientes acciones:</p>
 * <ul>
 * <li>Consulta el estado del envío utilizando el servicio {@link WeatherStatusService}.</li>
 * <li>Envía una notificación por correo electrónico al usuario con la información del estado del envío
 * a través del servicio {@link EmailService}.</li>
 * <li>Registra la información relevante de la notificación, incluyendo el correo del usuario,
 * la fecha y hora actual en la zona horaria de Bogotá, el nombre, región, país y la descripción
 * del pronóstico, utilizando el servicio {@link NotificationLogger}.</li>
 * </ul>
 *
 * <p>El método {@link #execute(UserRequest)} recibe una {@link UserRequest} que contiene la información
 * necesaria para consultar el estado del envío y enviar la notificación.</p>
 *
 * <p>La operación se realiza de forma reactiva utilizando {@link Mono}, lo que permite un manejo eficiente
 * de las operaciones asíncronas y la composición de las diferentes etapas del proceso.</p>
 */
@Service
public class GetShipmentStatusUseCase {

    private final WeatherStatusService service;
    private final NotificationLogger notificationLogger;
    private final EmailService emailService;

    public GetShipmentStatusUseCase(WeatherStatusService service, NotificationLogger notificationLogger, EmailService emailService) {
        this.service = service;
        this.notificationLogger = notificationLogger;
        this.emailService = emailService;
    }

    public Mono<NotificationResponseDTO> execute(UserRequest userRequest) {

        ZoneId bogotaZone = ZoneId.of("America/Bogota");
        ZonedDateTime nowBogota = ZonedDateTime.of(LocalDateTime.now(), bogotaZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z Z");
        String formatterString = nowBogota.format(formatter);

        return service.getShipmentStatus(userRequest)
                .filter(NotificationResponseDTO::isBuyerNotification)
                .flatMap(notificationResponse -> {
                    return emailService.sendNotificationEmail(
                            userRequest.email(), notificationResponse)
                                .then(Mono.just(notificationResponse));
                })
                .flatMap(notificationResponse -> {
                    return notificationLogger.logNotification(
                            userRequest.email(),
                            formatterString,
                            notificationResponse.getName(),
                            notificationResponse.getRegion(),
                            notificationResponse.getCountry(),
                            notificationResponse.getForecastDescription()
                    ).thenReturn(notificationResponse);
                })
                .switchIfEmpty(service.getShipmentStatus(userRequest));
        // si isBuyerNotification es falso Mono no emite valor, seria vacio asi que tenemos
        // que suscribir la respuesta para que se envie el json al client.
    }
}
