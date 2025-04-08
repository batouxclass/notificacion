package com.shipment.notificacion.common.exception;

/**
 * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
 *
 * @author Diego Cortes
 */
public class NotificationException extends IllegalArgumentException{
    /**
     * Instantiates a new Notification Exception.
     *
     * @param errorMessage the error message
     */
    public NotificationException(String errorMessage) {
        super(errorMessage);
    }
}
