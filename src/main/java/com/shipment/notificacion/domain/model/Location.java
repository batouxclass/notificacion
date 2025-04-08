package com.shipment.notificacion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Class Location to manage the location information from external Api.
 *
 * @author Diego Cortes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Location {
    private final String name;
    private final String region;
    private final String country;
    private final double lat;
    private final double lon;
    @JsonProperty("tz_id")
    private final String tzId;
    @JsonProperty("localtime_epoch")
    private final long localtimeEpoch;
    private final String localtime;

    public Location(String name, String region, String country, double lat, double lon, String tzId, long localtimeEpoch, String localtime) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.tzId = tzId;
        this.localtimeEpoch = localtimeEpoch;
        this.localtime = localtime;
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

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getTzId() {
        return tzId;
    }

    public long getLocaltimeEpoch() {
        return localtimeEpoch;
    }

    public String getLocaltime() {
        return localtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(lat, location.lat) == 0 && Double.compare(lon, location.lon) == 0 && localtimeEpoch == location.localtimeEpoch && Objects.equals(name, location.name) && Objects.equals(region, location.region) && Objects.equals(country, location.country) && Objects.equals(tzId, location.tzId) && Objects.equals(localtime, location.localtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, region, country, lat, lon, tzId, localtimeEpoch, localtime);
    }
}
