package com.shipment.notificacion.application.services;

import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.application.usecase.ports.IEmailService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio que permite realizar el llamado al proceso de envios de correos.
 *
 * @author Diego Cortes
 */
@Service
public class EmailService  {

    private final IEmailService emailService;

    public EmailService(IEmailService emailService) {
        this.emailService = emailService;
    }

    public Mono<Void> sendNotificationEmail(
            String email,
            NotificationResponseDTO notificationResponseDTO) {

        return emailService.sendNotificationEmail(email, notificationResponseDTO).then();

    }
}
