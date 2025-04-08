package com.shipment.notificacion.application.services;

import com.shipment.notificacion.application.services.dto.NotificationOperationDTO;
import com.shipment.notificacion.application.usecase.ports.NotificationLogRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

/**
 * Servicio que consulta las notificaciones que fueron enviadas a un comprador.
 *
 * @author Diego Cortes
 */
@Service
public class NotificationOperationService {

    private final NotificationLogRepository notificationLogRepository;

    public NotificationOperationService(NotificationLogRepository notificationLogRepository) {
        this.notificationLogRepository = notificationLogRepository;
    }

    public Flux<NotificationOperationDTO> getOperation(String email) {{
        if (!EmailValidator.getInstance().isValid(email)) {
            return Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email con formato incorrecto"));
        }else {
            return notificationLogRepository.findByEmail(email);
        }
    }}
}
