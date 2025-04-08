package com.shipment.notificacion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * Record to manage the forecast day information received from external API.
 *
 * @author Diego Cortes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Forecastday {
    private final String date;
    @JsonProperty("date_epoch")
    private final String dataEpoch;
    private final List<Hour> hour;

    public Forecastday(String date, String dataEpoch, List<Hour> hour) {
        this.date = date;
        this.dataEpoch = dataEpoch;
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public String getDataEpoch() {
        return dataEpoch;
    }

    public List<Hour> getHour() {
        return hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecastday that = (Forecastday) o;
        return Objects.equals(date, that.date) && Objects.equals(dataEpoch, that.dataEpoch) && Objects.equals(hour, that.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, dataEpoch, hour);
    }
}