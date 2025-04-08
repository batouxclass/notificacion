package com.shipment.notificacion.application.usecase.ports;

import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * Contract with the information received to call the weather status to be store
 * in the database.
 *
 * @author Diego Cortes
 */
public interface NotificationLogger {
    Mono<Void> logNotification(
            String email,
            String diaHora,
            String locationName,
            String locationRegion,
            String country,
            String forecastDescription);

}
