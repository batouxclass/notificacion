package com.shipment.notificacion.infrastructure.adapter.gateways;

import com.shipment.notificacion.application.usecase.ports.NotificationLogRepository;
import com.shipment.notificacion.domain.model.entity.NotificationLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;


/**
 * Repository interface to store the notification log.
 *
 * @author Diego Cortes
 */
@Component
public interface SpringDataNotificationLogRepository extends NotificationLogRepository {
}
