package com.shipment.notificacion.infrastructure.adapter.controller;

import com.shipment.notificacion.application.services.dto.NotificationOperationDTO;
import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.application.usecase.GetNotificationOperationUseCase;
import com.shipment.notificacion.application.usecase.GetShipmentStatusUseCase;
import com.shipment.notificacion.domain.repository.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiRestControllerTest {
    @Mock
    private GetShipmentStatusUseCase getShipmentStatusUseCase;

    @Mock
    private GetNotificationOperationUseCase getNotificationOperationUseCase;

    @InjectMocks
    private ApiRestController apiRestController;

    @Test
    void getForecastNotificationResponseTest() {
        UserRequest userRequest = new UserRequest("dfer.cortes@gmial.com", "10.0", "20.0");
        NotificationResponseDTO expectedResponse = new NotificationResponseDTO(
                200,
                "Sunny",
                true,
                "Test City",
                "Test Region",
                "Test Country"
        );
        when(getShipmentStatusUseCase.execute(userRequest)).thenReturn(Mono.just(expectedResponse));

        Mono<NotificationResponseDTO> result = apiRestController.getForecast(userRequest);

        StepVerifier.create(result).expectNext(expectedResponse).verifyComplete();
    }

    @Test
    void getForecastNotificationResponseErrorTest() {
        UserRequest userRequest = new UserRequest("dfer.cortes()gmial.com", "10.0", "20.0");
        RuntimeException error = new RuntimeException("Invalid input");
        when(getShipmentStatusUseCase.execute(userRequest)).thenReturn(Mono.error(error));

        Mono<NotificationResponseDTO> result = apiRestController.getForecast(userRequest);

        StepVerifier.create(result).expectErrorMatches( throwable -> throwable.equals(error)).verify();
    }

    @Test
    void getForecastNotificationResponsesTest() {
        String email = "dfer.cortes@gmial.com";
        List<NotificationOperationDTO> expectedNotifications = Arrays.asList(
                new NotificationOperationDTO(
                        "2025-04-08 10:00:00", "Cali", "Valle del Cauca", "Colombia", "ABC"),
                new NotificationOperationDTO(
                        "2025-04-08 11:00:00", "Cali", "Valle del Cauca", "Colombia", "XYZ")
        );
        when(getNotificationOperationUseCase.execute(email)).thenReturn(Flux.fromIterable(expectedNotifications));

        Flux<NotificationOperationDTO> result = apiRestController.getNotification(email);

        StepVerifier.create(result).expectNextSequence(expectedNotifications).verifyComplete();
    }

    @Test
    void getForecastNotificationEmptyResponseTest() {
        String email = "dfer.cortes@gmial.com";
        when(getNotificationOperationUseCase.execute(email)).thenReturn(Flux.empty());

        Flux<NotificationOperationDTO> result = apiRestController.getNotification(email);

        StepVerifier.create(result).expectComplete();
    }

    @Test
    void getNotificationUseCaseReturnsErrorStatusTest() {
        String email = "dfer.cortes@gmial.com";
        RuntimeException error = new RuntimeException("Database error");
        when(getNotificationOperationUseCase.execute(email)).thenReturn(Flux.error(error));

        Flux<NotificationOperationDTO> result = apiRestController.getNotification(email);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.equals(error))
                .verify();

    }
}
