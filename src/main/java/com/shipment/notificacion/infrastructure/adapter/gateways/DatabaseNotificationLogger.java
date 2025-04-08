package com.shipment.notificacion.infrastructure.adapter.gateways;

import com.shipment.notificacion.application.usecase.ports.NotificationLogger;
import com.shipment.notificacion.domain.model.entity.NotificationLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Implement the NotificationLogger interface to save the notification using the repository.
 *
 * @author Diego Cortes
 */
@Component
public class DatabaseNotificationLogger implements NotificationLogger {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseNotificationLogger.class);

    private final SpringDataNotificationLogRepository springDataNotificationLogRepository;

    public DatabaseNotificationLogger(SpringDataNotificationLogRepository springDataNotificationLogRepository) {
        this.springDataNotificationLogRepository = springDataNotificationLogRepository;
    }

    @Override
    public Mono<Void> logNotification(String email, String diaHora, String locationName, String locationRegion, String country, String forecastDescription) {
        NotificationLog notificationLog = new NotificationLog();
        notificationLog.setEmail(email);
        notificationLog.setDiaHora(diaHora);
        notificationLog.setLocationName(locationName);
        notificationLog.setLocationRegion(locationRegion);
        notificationLog.setCountry(country);
        notificationLog.setForecastDescription(forecastDescription);
        return springDataNotificationLogRepository.save(notificationLog).then()
                .onErrorResume(throwable -> {
                    String errorMessage = String.format("Failed to log notification: %s", throwable.getMessage());
                    logger.error(errorMessage, throwable);
                return Mono.error(new RuntimeException(errorMessage));
        });
    }
}
