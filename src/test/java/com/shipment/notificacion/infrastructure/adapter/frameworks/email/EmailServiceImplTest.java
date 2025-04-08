package com.shipment.notificacion.infrastructure.adapter.frameworks.email;

import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private Logger logger;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void sendNotificationEmailSuccessTest() {
        String email = "dfer.cortes@gmail.com";
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO(
                100,
                "Fuertes LLuvias",
                true,
                "Cali",
                "Valle del Cauca",
                "Colombia"
        );
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        doNothing().when(mailSender).send(messageCaptor.capture());

        Mono<Void> result = emailService.sendNotificationEmail(email, notificationResponseDTO);

        StepVerifier.create(result)
                .verifyComplete();

        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals(email, sentMessage.getTo()[0]);
        assertEquals("Notificación estado entrega de tu paquete", sentMessage.getSubject());
        assertEquals(String.format(EmailServiceImpl.EMAIL_BODY, "Fuertes LLuvias"), sentMessage.getText());
    }

    @Test
    void sendNotificationEmailFailureTest() {
        String email = "dfer.cortes@gmail.com";
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO(
                100,
                "Parcialmente nublado",
                true,
                "Cali",
                "Valle del Cauca",
                "Colombia"
        );
        MailException mailException = new MailException("Connection refused") {};
        doThrow(mailException).when(mailSender).send(any(SimpleMailMessage.class));

        Mono<Void> result = emailService.sendNotificationEmail(email, notificationResponseDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Failed to send notification email: Connection refused"))
                .verify();

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}