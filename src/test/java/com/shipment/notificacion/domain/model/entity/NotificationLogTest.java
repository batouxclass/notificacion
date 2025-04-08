package com.shipment.notificacion.domain.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationLogTest {

    @Test
    void createNotificationLogWithConstructorTest() {
        Long id = 1L;
        String email = "dfer.cortes@gmail.com";
        String diaHora = "2025-04-08 00:00:00";
        String locationName = "Buga";
        String locationRegion = "Valle del Cauca";
        String country = "Colombia";
        String forecastDescription = "Soleado";

        NotificationLog log = new NotificationLog(id, email, diaHora, locationName, locationRegion, country, forecastDescription);

        assertNotNull(log);
        assertEquals(id, log.getId());
        assertEquals(email, log.getEmail());
        assertEquals(diaHora, log.getDiaHora());
        assertEquals(locationName, log.getLocationName());
        assertEquals(locationRegion, log.getLocationRegion());
        assertEquals(country, log.getCountry());
        assertEquals(forecastDescription, log.getForecastDescription());
    }

    @Test
    void createNotificationLogWithNoArgsConstructorTest() {
        NotificationLog log = new NotificationLog();

        assertNotNull(log);
        assertNull(log.getId());
        assertNull(log.getEmail());
        assertNull(log.getDiaHora());
        assertNull(log.getLocationName());
        assertNull(log.getLocationRegion());
        assertNull(log.getCountry());
        assertNull(log.getForecastDescription());
    }

    @Test
    void setAndGetPropertiesTest() {
        NotificationLog log = new NotificationLog();
        Long id = 2L;
        String email = "diego@icloud.com";
        String diaHora = "2025-04-08 01:00:00";
        String locationName = "Cali";
        String locationRegion = "Valle del Cauca";
        String country = "Colombia";
        String forecastDescription = "Parcialmente nubleado";

        log.setId(id);
        log.setEmail(email);
        log.setDiaHora(diaHora);
        log.setLocationName(locationName);
        log.setLocationRegion(locationRegion);
        log.setCountry(country);
        log.setForecastDescription(forecastDescription);

        assertEquals(id, log.getId());
        assertEquals(email, log.getEmail());
        assertEquals(diaHora, log.getDiaHora());
        assertEquals(locationName, log.getLocationName());
        assertEquals(locationRegion, log.getLocationRegion());
        assertEquals(country, log.getCountry());
        assertEquals(forecastDescription, log.getForecastDescription());
    }

    @Test
    void propertiesAreEqualsTest() {
        NotificationLog log1 = new NotificationLog(1L, "dfer.cortes@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");
        NotificationLog log2 = new NotificationLog(1L, "dfer.cortes@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");

        assertEquals(log1, log2);
        assertEquals(log1.hashCode(), log2.hashCode());
    }

    @Test
    void propertiesAreNotEqualsTest() {
        NotificationLog log1 = new NotificationLog(1L, "dfer.cortes@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");
        NotificationLog log2 = new NotificationLog(2L, "dfer.cortes@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");

        assertNotEquals(log1, log2);
        assertNotEquals(log1.hashCode(), log2.hashCode());
    }

    @Test
    void emailAreNotEqualTest() {
        NotificationLog log1 = new NotificationLog(1L, "dfer.cortes@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");
        NotificationLog log2 = new NotificationLog(1L, "dfer.cortes2@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");

        assertNotEquals(log1, log2);
        assertNotEquals(log1.hashCode(), log2.hashCode());
    }

    @Test
    void nullObjectIsNotEqualTest() {
        NotificationLog log = new NotificationLog(1L, "dfer.cortes@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");

        assertNotEquals(log, null);
    }

    @Test
    void objetosAreNotEqualsTest() {
        NotificationLog log = new NotificationLog(1L, "dfer.cortes@gmail.com", "2025-04-08 00:00:00", "Buga", "Valle", "CO", "Clear");
        Object other = new Object();

        assertNotEquals(log, other);
    }

    @Test
    void toStringVerificatioTestn() {
        Long id = 3L;
        String email = "diego@test.com";
        String diaHora = "2025-04-08 02:00:00";
        String locationName = "Medellín";
        String locationRegion = "Antioquia";
        String country = "Colombia";
        String forecastDescription = "Luvioso";
        NotificationLog log = new NotificationLog(id, email, diaHora, locationName, locationRegion, country, forecastDescription);
        String expectedString = "NotificationLog{id=3, email='diego@test.com', diaHora=2025-04-08 02:00:00, locationName='Medellín', locationRegion='Antioquia', country='Colombia', forecastDescription='Luvioso'}";

        String actualString = log.toString();

        assertEquals(expectedString, actualString);
    }
}
