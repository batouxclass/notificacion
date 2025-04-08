package com.shipment.notificacion.application.services;

import com.shipment.notificacion.application.services.dto.NotificationOperationDTO;
import com.shipment.notificacion.application.usecase.ports.NotificationLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationOperationServiceTest {

    @Mock
    private NotificationLogRepository notificationLogRepository;

    @InjectMocks
    private NotificationOperationService notificationOperationService;

    @Test
    void getOperationTest() {
        String validEmail = "dfer.cortes@gmail.com";
        List<NotificationOperationDTO> mockNotifications = Arrays.asList(
                new NotificationOperationDTO(
                         "2025-04-01 10:00:00",
                        "Cali",
                        "Valle del Cauca",
                        "Colombia",
                        "Descripcion 123"),
                new NotificationOperationDTO(
                        "2025-04-02 11:30:00",
                        "Buga",
                        "Valle del Cauca",
                        "Colombia",
                        "Descripcion 456")
        );
        when(notificationLogRepository.findByEmail(validEmail)).thenReturn(Flux.fromIterable(mockNotifications));

        Flux<NotificationOperationDTO> result = notificationOperationService.getOperation(validEmail);

        StepVerifier.create(result)
                .expectNextSequence(mockNotifications)
                .verifyComplete();
    }


    @Test
    void getOperationIncorrectEmailBadRequestErrorTest() {

        String invalidEmail = "invalid-email";

        Flux<NotificationOperationDTO> result = notificationOperationService.getOperation(invalidEmail);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST &&
                        throwable.getMessage().contains("Email con formato incorrecto"))
                .verify();
    }

    @Test
    void getOperationReturnsEmptyTest() {
        String validEmail = "dfer.cortes@gmail.com";
        when(notificationLogRepository.findByEmail(validEmail)).thenReturn(Flux.empty());

        Flux<NotificationOperationDTO> result = notificationOperationService.getOperation(validEmail);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }
}