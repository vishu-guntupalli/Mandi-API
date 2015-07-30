package com.wku.mandi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by srujangopu on 7/29/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Places {

    @JsonProperty("place name")
    private String place_name;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("state")
    private String state;

    @JsonProperty("state abbreviation")
    private String state_abbreviation;

    @JsonProperty("latitude")
    private String latitude;

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_abbreviation() {
        return state_abbreviation;
    }

    public void setState_abbreviation(String state_abbreviation) {
        this.state_abbreviation = state_abbreviation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
