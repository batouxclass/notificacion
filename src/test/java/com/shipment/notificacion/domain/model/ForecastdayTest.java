package com.shipment.notificacion.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForecastdayTest {

    @Test
    void createForecastdayTest() {
        String date = "2025-04-08";
        String dataEpoch = "1712534400";
        List<Hour> hours = Collections.singletonList(
                new Hour("00:00", new Condition("Clear", "//icon", 1000)));

        Forecastday forecastday = new Forecastday(date, dataEpoch, hours);

        assertNotNull(forecastday);
        assertEquals(date, forecastday.getDate());
        assertEquals(dataEpoch, forecastday.getDataEpoch());
        assertEquals(hours, forecastday.getHour());
    }


    @Test
    void foecastAreEqualTest() {
        List<Hour> hours1 = Collections.singletonList(
                new Hour("00:00", new Condition("Clear", "//icon", 1000)));

        List<Hour> hours2 = Collections.singletonList(
                new Hour("00:00", new Condition("Clear", "//icon", 1000)));

        Forecastday forecastday1 = new Forecastday("2025-04-08", "1712534400", hours1);
        Forecastday forecastday2 = new Forecastday("2025-04-08", "1712534400", hours2);

        assertEquals(forecastday1, forecastday2);
        assertEquals(forecastday1.hashCode(), forecastday2.hashCode());
    }

    @Test
    void foecastAreNotEqualTest() {
        List<Hour> hours = Collections.singletonList(
                new Hour("00:00", new Condition("Clear", "//icon", 1000)));

        Forecastday forecastday1 = new Forecastday("2025-04-08", "1712534400", hours);
        Forecastday forecastday2 = new Forecastday("2025-04-09", "1712534400", hours);

        assertNotEquals(forecastday1, forecastday2);
        assertNotEquals(forecastday1.hashCode(), forecastday2.hashCode());
    }

    @Test
    void hourListAreNotEqualTest() {
        List<Hour> hours1 = Collections.singletonList(
                new Hour("00:00", new Condition("Clear", "//icon", 1000)));

        List<Hour> hours2 = Collections.singletonList(
                new Hour("01:00", new Condition("Cloudy", "//icon2", 1001)));

        Forecastday forecastday1 = new Forecastday("2025-04-08", "1712534400", hours1);
        Forecastday forecastday2 = new Forecastday("2025-04-08", "1712534400", hours2);

        assertNotEquals(forecastday1, forecastday2);
        assertNotEquals(forecastday1.hashCode(), forecastday2.hashCode());
    }

    @Test
    void forecastIsNotNullEqualsTest() {
        Forecastday forecastday = new Forecastday("2025-04-08", "1712534400", Collections.emptyList());

        assertNotEquals(forecastday, null);
    }

    @Test
    void forecastDayObjectsAreNotEquals() {
        Forecastday forecastday = new Forecastday("2025-04-08", "1712534400", Collections.emptyList());
        Object other = new Object();

        assertNotEquals(forecastday, other);
    }
}