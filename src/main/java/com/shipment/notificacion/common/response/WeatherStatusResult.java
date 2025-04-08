package com.shipment.notificacion.common.response;

/**
 * The Helper class to return result or an IllegalArgumentException.
 *
 *  @author Diego Cortes
 *  @param <T> the generic type
 */
public class WeatherStatusResult<T> {
    /**
     * Check result given a data pojo and error message.
     *
     * @param data the data
     * @return the wrapper data
     */
    public T checkResult(T data) {
        T value = null;
        if (data != null) {
            value = data;
        } else {
            throw new IllegalArgumentException();
        }
        return value;
    }
}
