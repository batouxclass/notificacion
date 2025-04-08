package com.shipment.notificacion.application.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Response DTO to hold the value for user's notification status
 *
 * @author Diego Cortes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class NotificationResponseDTO {
    @JsonProperty("forecast_code")
    private final int forecastCode;

    @JsonProperty("forecast_description")
    private final  String forecastDescription;

    @JsonProperty("buyer_notification")
    private final boolean buyerNotification;

    @JsonIgnore
    private final String name;

    @JsonIgnore
    private final String region;

    @JsonIgnore
    private final String country;

    public NotificationResponseDTO(
            int forecastCode,
            String forecastDescription,
            boolean buyerNotification,
            String name,
            String region,
            String country) {
        this.forecastCode = forecastCode;
        this.forecastDescription = forecastDescription;
        this.buyerNotification = buyerNotification;
        this.name = name;
        this.region = region;
        this.country = country;
    }

    public int getForecastCode() {
        return forecastCode;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    public boolean isBuyerNotification() {
        return buyerNotification;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationResponseDTO that = (NotificationResponseDTO) o;
        return forecastCode == that.forecastCode
                && buyerNotification == that.buyerNotification
                && Objects.equals(name, that.name)
                && Objects.equals(forecastDescription, that.forecastDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forecastCode, forecastDescription, buyerNotification, name);
    }

}
