package com.shipment.notificacion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Record to manage the current and the next forecast day received from external API.
 *
 * @author Diego Cortes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Forecast(
        List<Forecastday> forecastday
) { }
