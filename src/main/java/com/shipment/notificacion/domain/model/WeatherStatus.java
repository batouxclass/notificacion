package com.shipment.notificacion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record to manage the weather status information received from external API.
 *
 * @author Diego Cortes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherStatus (
        Location location,
        Forecast forecast
) { }
