package com.shipment.notificacion.application.services.dto;

/**
 *  Notification DTO to hold the value to inform the notification operation to caller.
 *
 * @author Diego Cortes
 */
public record NotificationOperationDTO(
        String diaHora,
        String locationName,
        String locationRegion,
        String country,
        String forecastDescription
){ }
