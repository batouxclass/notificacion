package com.shipment.notificacion.infrastructure.adapter.frameworks.email;

import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.application.usecase.ports.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Implements IEmailService to send an email message notification.
 *
 * @author Diego Cortes
 */
@Service
public class EmailServiceImpl implements IEmailService {

    public static final String EMAIL_BODY =
            "Hola! Tenemos programada la entrega de tu paquete para mañana, en la dirección de entrega "  +
            "esperamos un dia con %s y por esta razón es posible que tengamos retrasos. Haremos todo a nuestro " +
            "alcance para cumplir con tu entrega.";

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public Mono<Void> sendNotificationEmail(String email, NotificationResponseDTO notificationResponseDTO) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Notificación estado entrega de tu paquete");
            message.setText(String.format(EMAIL_BODY, notificationResponseDTO.getForecastDescription()));
            mailSender.send(message);
        }).onErrorResume(throwable -> {
            String errorMessage = String.format("Failed to send notification email: %s", throwable.getMessage());
            logger.error(errorMessage, throwable);
            return Mono.error(new RuntimeException(errorMessage));
        }).subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic()).then();
    }
}
