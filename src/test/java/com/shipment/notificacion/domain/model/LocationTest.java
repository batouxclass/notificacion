package com.shipment.notificacion.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void createLocationTest() {
        String name = "Test Name";
        String region = "Test Region";
        String country = "Test Country";
        double lat = 10.0;
        double lon = 20.0;
        String tzId = "Test/Zone";
        long localtimeEpoch = 1234567890L;
        String localtime = "2025-04-08 00:00";

        Location location = new Location(name, region, country, lat, lon, tzId, localtimeEpoch, localtime);

        assertNotNull(location);
        assertEquals(name, location.getName());
        assertEquals(region, location.getRegion());
        assertEquals(country, location.getCountry());
        assertEquals(lat, location.getLat());
        assertEquals(lon, location.getLon());
        assertEquals(tzId, location.getTzId());
        assertEquals(localtimeEpoch, location.getLocaltimeEpoch());
        assertEquals(localtime, location.getLocaltime());
    }

    @Test
    void locationAreEqualTest() {
        Location location1 = new Location(
                "Test", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");
        Location location2 = new Location(
                "Test", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");

        assertEquals(location1, location2);
        assertEquals(location1.hashCode(), location2.hashCode());
    }

    @Test
    void locationAreNotEqualTest() {
        Location location1 = new Location(
                "Test1", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");
        Location location2 = new Location(
                "Test2", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");

        assertNotEquals(location1, location2);
        assertNotEquals(location1.hashCode(), location2.hashCode());
    }

    @Test
    void latitudeAreEqualTest() {
        Location location1 = new Location(
                "Test", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");
        Location location2 = new Location(
                "Test", "Region", "Country", 1.1, 2.0, "TZ", 100L, "Time");

        assertNotEquals(location1, location2);
        assertNotEquals(location1.hashCode(), location2.hashCode());
    }

    @Test
    void latitudeAreNotEqualTest() {
        Location location1 = new Location(
                "Test", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");
        Location location2 = new Location(
                "Test", "Region", "Country", 1.0, 2.0, "TZ", 101L, "Time");

        assertNotEquals(location1, location2);
        assertNotEquals(location1.hashCode(), location2.hashCode());
    }

    @Test
    void locationAreNotNullTest() {
        Location location = new Location(
                "Test", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");

        assertNotEquals(location, null);
    }

    @Test
    void locationObjectsAreNotEqualsTest() {
        Location location = new Location(
                "Test", "Region", "Country", 1.0, 2.0, "TZ", 100L, "Time");
        Object other = new Object();

        assertNotEquals(location, other);
    }
}