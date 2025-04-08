package com.shipment.notificacion.application.usecase.ports;

import com.shipment.notificacion.application.services.dto.NotificationOperationDTO;
import com.shipment.notificacion.domain.model.entity.NotificationLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * Interface with the contract for the repository.
 *
 * @author Diego Cortes
 */
public interface NotificationLogRepository extends ReactiveCrudRepository<NotificationLog, Integer> {

    /**
     * Find all notification given an email
     *
     * @param email The email to search
     * @return a Flux with the NotificationOperationDTO response.
     */
    Flux<NotificationOperationDTO> findByEmail(String email);
}
