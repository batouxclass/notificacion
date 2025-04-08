package com.shipment.notificacion.application.services.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NotificationResponseDTOTest {

    @Test
    void createNotificationResponseDTOTest() {
        int forecastCode = 200;
        String forecastDescription = "Clear";
        boolean buyerNotification = true;
        String name = "Buga";
        String region = "Valle del Cauca";
        String country = "Colombia";

        NotificationResponseDTO dto = new NotificationResponseDTO(
                forecastCode, forecastDescription, buyerNotification, name, region, country);

        assertNotNull(dto);
        assertEquals(forecastCode, dto.getForecastCode());
        assertEquals(forecastDescription, dto.getForecastDescription());
        assertEquals(buyerNotification, dto.isBuyerNotification());
        assertEquals(name, dto.getName());
        assertEquals(region, dto.getRegion());
        assertEquals(country, dto.getCountry());
    }

    @Test
    void propertiesAreEqualsTest() {
        NotificationResponseDTO dto1 = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");
        NotificationResponseDTO dto2 = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void forecastCodeAreNotEqualTest() {
        NotificationResponseDTO dto1 = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");
        NotificationResponseDTO dto2 = new NotificationResponseDTO(
                400, "Clear", true, "Buga", "Valle", "CO");


        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void forecastDescriptionAreNotEqualTest() {
        NotificationResponseDTO dto1 = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");
        NotificationResponseDTO dto2 = new NotificationResponseDTO(
                200, "Rainy", true, "Buga", "Valle", "CO");

        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void differentBuyerNotificationAreNotEqualTest() {
        NotificationResponseDTO dto1 = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");
        NotificationResponseDTO dto2 = new NotificationResponseDTO(
                200, "Clear", false, "Buga", "Valle", "CO");

        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void nameAreNotEqualTest() {
        NotificationResponseDTO dto1 = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");
        NotificationResponseDTO dto2 = new NotificationResponseDTO(
                200, "Clear", true, "Cali", "Valle", "CO");

        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void objectsAreNotNullEqualTest() {
        NotificationResponseDTO dto = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");

        assertNotEquals(dto, null);
    }

    @Test
    void objetsAreNotEqualsTest() {
        NotificationResponseDTO dto = new NotificationResponseDTO(
                200, "Clear", true, "Buga", "Valle", "CO");
        Object other = new Object();

        assertNotEquals(dto, other);
    }
}