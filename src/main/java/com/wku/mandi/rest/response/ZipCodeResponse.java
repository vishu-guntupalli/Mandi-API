package com.wku.mandi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srujangopu on 7/29/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipCodeResponse {

    @JsonProperty("post code")
    private String post_code;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country abbreviation")
    private String country_abbreviation;

    @JsonProperty("places")
    private List<Places> places = new ArrayList<Places>();


    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_abbreviation() {
        return country_abbreviation;
    }

    public void setCountry_abbreviation(String country_abbreviation) {
        this.country_abbreviation = country_abbreviation;
    }

    public List<Places> getPlaces() {
        return places;
    }

    public void setPlaces(List<Places> places) {
        this.places = places;
    }
}
