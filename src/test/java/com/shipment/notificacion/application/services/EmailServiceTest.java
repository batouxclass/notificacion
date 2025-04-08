package com.shipment.notificacion.application.services;

import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.application.usecase.ports.IEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private IEmailService iEmailService;

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendNotificationEmailTest() {
        String email = "dfer.cortes@gmail.com";
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO(
                100,
                "Sunny",
                false,
                "Test City",
                "Test Region",
                "Test Country"
        );
        when(iEmailService.sendNotificationEmail(email, notificationResponseDTO)).thenReturn(Mono.empty());

        Mono<Void> result = emailService.sendNotificationEmail(email, notificationResponseDTO);

        StepVerifier.create(result)
                .verifyComplete();
        verify(iEmailService).sendNotificationEmail(email, notificationResponseDTO);
    }

    @Test
    void sendNotificationEmailErrorTest() {
        String email = "dfer.cortes@gmail.com";
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO(
                100,
                "Sunny",
                false,
                "Test City",
                "Test Region",
                "Test Country"
        );
        Throwable expectedError = new RuntimeException("Failed to send email");
        when(iEmailService.sendNotificationEmail(email, notificationResponseDTO)).thenReturn(Mono.error(expectedError));

        Mono<Void> result = emailService.sendNotificationEmail(email, notificationResponseDTO);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.equals(expectedError))
                .verify();
        verify(iEmailService).sendNotificationEmail(email, notificationResponseDTO);
    }
}
