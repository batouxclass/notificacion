package com.shipment.notificacion.application.usecase;

import com.shipment.notificacion.application.services.NotificationOperationService;
import com.shipment.notificacion.application.services.dto.NotificationOperationDTO;
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
class GetNotificationOperationUseCaseTest {

    public static final String MAIL = "dfer.cortes@gmail.com";

    @Mock
    private NotificationOperationService service;

    @InjectMocks
    private GetNotificationOperationUseCase useCase;

    @Test
    void executeCorrectEmailTest() {
        String validEmail = MAIL;
        List<NotificationOperationDTO> expectedNotifications = Arrays.asList(
                new NotificationOperationDTO(
                        "2025-04-07 00:00",
                        "Cali",
                        "Valle del Cauca",
                        "Colombia",
                        "Lluvias muy fuertes"),

                new NotificationOperationDTO(
                        "2025-04-08 11:00:00",
                        "Cali",
                        "Valle del Cauca",
                        "Colombia",
                        "Cielo nublado")
        );
        when(service.getOperation(validEmail)).thenReturn(Flux.fromIterable(expectedNotifications));

        Flux<NotificationOperationDTO> result = useCase.execute(validEmail);

        StepVerifier.create(result)
                .expectNextSequence(expectedNotifications)
                .verifyComplete();
    }

    @Test
    void executeIinvalidEmailErrorTest() {
        String invalidEmail = "dfer.cortes()gmail.com";
        when(service.getOperation(invalidEmail))
                .thenReturn(Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email con formato incorrecto")));

        Flux<NotificationOperationDTO> result = useCase.execute(invalidEmail);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST &&
                        throwable.getMessage().contains("Email con formato incorrecto"))
                .verify();
    }

    @Test
    void executeInvalidEmailReturnsEmptyTest() {
        String validEmail = MAIL;
        when(service.getOperation(validEmail)).thenReturn(Flux.empty());

        Flux<NotificationOperationDTO> result = useCase.execute(validEmail);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void executeServiceWithExceptionPropagateTest() {
        String validEmail = MAIL;
        RuntimeException serviceError = new RuntimeException("Database error");
        when(service.getOperation(validEmail)).thenReturn(Flux.error(serviceError));

        Flux<NotificationOperationDTO> result = useCase.execute(validEmail);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.equals(serviceError))
                .verify();
    }
}