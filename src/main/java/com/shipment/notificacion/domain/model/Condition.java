package com.shipment.notificacion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Record to manage the condition information received from external API.
 *
 * @author Diego Cortes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Condition(
        String text,
        String icon,
        int code
) { }
