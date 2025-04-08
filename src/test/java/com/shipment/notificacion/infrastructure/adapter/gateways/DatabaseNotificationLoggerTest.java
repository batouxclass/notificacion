package com.shipment.notificacion.infrastructure.adapter.gateways;

import com.shipment.notificacion.domain.model.entity.NotificationLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessResourceFailureException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseNotificationLoggerTest {

    @Mock
    private SpringDataNotificationLogRepository springDataNotificationLogRepository;

    @Mock
    private Logger logger;
    @InjectMocks
    private DatabaseNotificationLogger databaseNotificationLogger;

    @Test
    void logNotificationSuccessTest() {
        String email = "dfer.cortes@gmail.com";
        String diaHora = "2025-04-08 00:00:00";
        String locationName = "Buga";
        String locationRegion = "Valle del Cauca";
        String country = "Colombia";
        String forecastDescription = "Clear";
        NotificationLog savedLog = new NotificationLog();
        savedLog.setId(1L);
        savedLog.setEmail(email);
        savedLog.setDiaHora(diaHora);
        savedLog.setLocationName(locationName);
        savedLog.setLocationRegion(locationRegion);
        savedLog.setCountry(country);
        savedLog.setForecastDescription(forecastDescription);

        when(springDataNotificationLogRepository.save(any(NotificationLog.class))).thenReturn(Mono.just(savedLog));

        Mono<Void> result = databaseNotificationLogger.logNotification(email, diaHora, locationName, locationRegion, country, forecastDescription);

        StepVerifier.create(result)
                .verifyComplete();

        verify(springDataNotificationLogRepository).save(any(NotificationLog.class));
    }

    @Test
    void logNotificationFailureTest() {
        String email = "dfer.cortes@gmail.com";
        String diaHora = "2025-04-08 00:00:00";
        String locationName = "Buga";
        String locationRegion = "Valle del Cauca";
        String country = "Colombia";
        String forecastDescription = "Clear";
        DataAccessResourceFailureException databaseException = new DataAccessResourceFailureException("Failed to connect to database");
        when(springDataNotificationLogRepository.save(any(NotificationLog.class))).thenReturn(Mono.error(databaseException));

        Mono<Void> result = databaseNotificationLogger.logNotification(email, diaHora, locationName, locationRegion, country, forecastDescription);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().contains("Failed to log notification: Failed to connect to database"))
                .verify();

        verify(springDataNotificationLogRepository).save(any(NotificationLog.class));
    }
}