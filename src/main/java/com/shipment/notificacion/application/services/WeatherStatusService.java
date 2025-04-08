package com.shipment.notificacion.application.services;

import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.domain.repository.UserRequest;
import com.shipment.notificacion.domain.repository.WeatherStatusRepository;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Servicio encargado de obtener el estado del tiempo o información relevante
 * para un "envío" (simulado en este contexto) basado en la ubicación proporcionada por el usuario.
 *
 * <p>Este servicio interactúa con un {@link WeatherStatusRepository} para consultar la información
 * del tiempo y procesarla para generar una {@link NotificationResponseDTO}.</p>
 *
 * <p>El servicio consulta la información del tiempo para la ubicación dada y extrae información
 * específica del pronóstico para el día siguiente (saltando el primer día) y una hora específica
 * (saltando las primeras 8 horas) para construir la {@link NotificationResponseDTO}.</p>
 *
 * <p>La notificación se genera si se encuentra información del pronóstico para el día y hora especificados.<</p>
 */
@Service
public class WeatherStatusService {

    private final WeatherStatusRepository weatherStatusRepository;

    private final Set<Integer> codeToBeNotified = new HashSet<>();

    public WeatherStatusService(WeatherStatusRepository weatherStatusRepository) {
        this.weatherStatusRepository = weatherStatusRepository;
        codeToBeNotified.addAll(List.of(123, 456, 789));
    }

    public Mono<NotificationResponseDTO> getShipmentStatus(UserRequest userRequest) {
        if (!EmailValidator.getInstance().isValid(userRequest.email())) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email con formato incorrecto"));
        }
        DoubleValidator validator = DoubleValidator.getInstance();
        Double latitude = validator.validate(userRequest.latitude());
        Double longitude = validator.validate(userRequest.longitude());

        if (latitude == null || latitude < -90 || latitude > 90) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitud no valida. Debe ser un número entre -90 y 90."));
        }
        if (longitude == null || longitude < -180 || longitude > 180) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Longitud no valida. Debe ser un número entre -180 y 180."));
        }

        return weatherStatusRepository.check(userRequest)
                .flatMap( weatherStatus -> Mono.fromSupplier(() -> weatherStatus.forecast().forecastday().stream()
                    .skip(1)
                    .findFirst()
                    .flatMap(forecastday -> forecastday.getHour().stream()
                            .skip(8)
                            .findFirst()
                            .map(hour -> new NotificationResponseDTO(
                                    hour.condition().code(),
                                    hour.condition().text(),
                                    codeToBeNotified.contains(hour.condition().code()),
                                    weatherStatus.location().getName(),
                                    weatherStatus.location().getRegion(),
                                    weatherStatus.location().getCountry())
                            )
                    ).orElse(null)
                )).filter(Objects::nonNull);
    }
}
