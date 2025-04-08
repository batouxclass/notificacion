package com.shipment.notificacion.application.usecase;


import com.shipment.notificacion.application.services.EmailService;
import com.shipment.notificacion.application.services.WeatherStatusService;
import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.application.usecase.ports.NotificationLogger;
import com.shipment.notificacion.domain.repository.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetShipmentStatusUseCaseTest {

    @Mock
    private WeatherStatusService weatherStatusService;

    @Mock
    private NotificationLogger notificationLogger;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private GetShipmentStatusUseCase getShipmentStatusUseCase;

    @Test
    void buyerNotificationTrueSendsEmailAndLogsNotificationTest() {
        UserRequest userRequest = new UserRequest("dfer.cortes@gmail.com", "10.0", "20.0");
        NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO(
                123,
                "Lluvia muy fuerte",
                true,
                "Cali",
                "Valle del Cauca",
                "Colombia"
        );
        when(weatherStatusService.getShipmentStatus(userRequest)).thenReturn(Mono.just(notificationResponseDTO));
        when(emailService.sendNotificationEmail(eq("dfer.cortes@gmail.com"), eq(notificationResponseDTO))).thenReturn(Mono.empty());
        when(notificationLogger.logNotification(any(), any(), any(), any(), any(), any())).thenReturn(Mono.empty());

        ZoneId bogotaZone = ZoneId.of("America/Bogota");
        ZonedDateTime nowBogota = ZonedDateTime.now(bogotaZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z Z");
        String formattedDate = nowBogota.format(formatter);

        Mono<NotificationResponseDTO> result = getShipmentStatusUseCase.execute(userRequest);

        StepVerifier.create(result)
                .expectNext(notificationResponseDTO)
                .verifyComplete();

        verify(emailService, times(1)).sendNotificationEmail("dfer.cortes@gmail.com", notificationResponseDTO);
        verify(notificationLogger, times(1)).logNotification(
                "dfer.cortes@gmail.com",
                formattedDate,
                "Cali",
                "Valle del Cauca",
                "Colombia",
                "Lluvia muy fuerte"
        );
        verify(weatherStatusService, times(2)).getShipmentStatus(userRequest);
    }

    @Test
    void buyerNotificationFalseDoesNotSendEmailAndLogNotificationTest() {
        UserRequest userRequest = new UserRequest("dfer.cortes@gmail.com", "10.0", "20.0");
        NotificationResponseDTO initialResponse = new NotificationResponseDTO(
                100,
                "Cielo cubierto",
                false,
                "Cali",
                "Valle del Cauca",
                "Colombia"
        );
        when(weatherStatusService.getShipmentStatus(userRequest))
                .thenReturn(Mono.just(initialResponse));
        Mono<NotificationResponseDTO> result = getShipmentStatusUseCase.execute(userRequest);

        StepVerifier.create(result)
                .expectNext(initialResponse)
                .verifyComplete();

        verify(emailService, times(0)).sendNotificationEmail(any(), any());
        verify(notificationLogger, times(0)).logNotification(any(), any(), any(), any(), any(), any());
        verify(weatherStatusService, times(2)).getShipmentStatus(userRequest);
    }

    @Test
    void getShipmentStatusErrorTest() {
        UserRequest userRequest = new UserRequest("dfer.cortes@gmail.com", "10.0", "20.0");
        RuntimeException expectedError = new RuntimeException("Failed to get shipment status");
        when(weatherStatusService.getShipmentStatus(userRequest)).thenReturn(Mono.error(expectedError));

        Mono<NotificationResponseDTO> result = getShipmentStatusUseCase.execute(userRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable.equals(expectedError))
                .verify();

        verify(weatherStatusService, times(2)).getShipmentStatus(userRequest);
        verify(emailService, times(0)).sendNotificationEmail(any(), any());
        verify(notificationLogger, times(0)).logNotification(any(), any(), any(), any(), any(), any());
    }
}