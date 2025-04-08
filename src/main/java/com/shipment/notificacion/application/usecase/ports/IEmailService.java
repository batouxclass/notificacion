package com.shipment.notificacion.application.usecase.ports;

import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import reactor.core.publisher.Mono;

/**
 * EmailService interface with the information to send the email message.
 *
 * @author Diego Cortes
 */
public interface IEmailService {
    Mono<Void> sendNotificationEmail(String toEmail, NotificationResponseDTO notification);
}
