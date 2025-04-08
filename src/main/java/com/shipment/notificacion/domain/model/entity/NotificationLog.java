package com.shipment.notificacion.domain.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

/**
 * Entity class that describe the information to be store to database.
 *
 * @author Diego Cortes
 */
@Table(name= "notification_log")
public class NotificationLog {
    @Id
    private Long id;
    private String email;
    private String diaHora;
    private String locationName;
    private String locationRegion;
    private String country;
    private String forecastDescription;

    public NotificationLog(Long id, String email, String diaHora, String locationName, String locationRegion, String country, String forecastDescription) {
        this.id = id;
        this.email = email;
        this.diaHora = diaHora;
        this.locationName = locationName;
        this.locationRegion = locationRegion;
        this.country = country;
        this.forecastDescription = forecastDescription;
    }

    public NotificationLog() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaHora() {
        return diaHora;
    }

    public void setDiaHora(String diaHora) {
        this.diaHora = diaHora;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationRegion() {
        return locationRegion;
    }

    public void setLocationRegion(String locationRegion) {
        this.locationRegion = locationRegion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    public void setForecastDescription(String forecastDescription) {
        this.forecastDescription = forecastDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationLog that = (NotificationLog) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(diaHora, that.diaHora) && Objects.equals(locationName, that.locationName) && Objects.equals(locationRegion, that.locationRegion) && Objects.equals(country, that.country) && Objects.equals(forecastDescription, that.forecastDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, diaHora, locationName, locationRegion, country, forecastDescription);
    }

    @Override
    public String toString() {
        return "NotificationLog{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", diaHora=" + diaHora +
                ", locationName='" + locationName + '\'' +
                ", locationRegion='" + locationRegion + '\'' +
                ", country='" + country + '\'' +
                ", forecastDescription='" + forecastDescription + '\'' +
                '}';
    }
}
