package com.shipment.notificacion.domain.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherStatusTest {

    @Test
    void createWeatherStatusTest() {
        Location location =  new Location(
                "Cali",
                "Valle del Cauca",
                "Colombia",
                6.1543519,
                -75.6076758,
                "txz_id",
                456587954,
                "localtme");
        Forecast forecast = new Forecast(Collections.emptyList());

        WeatherStatus weatherStatus = new WeatherStatus(location, forecast);

        assertNotNull(weatherStatus);
        assertEquals(location, weatherStatus.location());
        assertEquals(forecast, weatherStatus.forecast());
    }

}