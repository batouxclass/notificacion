package com.shipment.notificacion.domain.repository;

import com.shipment.notificacion.domain.model.WeatherStatus;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 *  Interface for getting weather status from external system.
 *
 * @author Diego Cortes
 * */
public interface WeatherStatusRepository extends Serializable {

    /**
     * Returns the weather given the location according user's coordinates record from data source.
     */
    Mono<WeatherStatus> check(UserRequest location);
}
