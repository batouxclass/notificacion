package com.shipment.notificacion.domain.repository;

/**
 * Record to manage the user request data.
 *
 * @author Diego Cortes
 */
public record UserRequest(String email, String latitude, String longitude
) { }
