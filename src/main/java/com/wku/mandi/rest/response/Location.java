package com.wku.mandi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by srujangopu on 8/3/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location{

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lng")
    private String lng;

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}