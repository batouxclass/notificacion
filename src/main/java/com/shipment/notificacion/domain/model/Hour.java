package com.shipment.notificacion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record to manage the Hour from forecast day.
 *
 * @author Diego Cortes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Hour(
        String time,
        Condition condition
) { }
