package com.wku.mandi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by srujangopu on 8/3/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("formatted_address")
    private String formatted_address;

    @JsonProperty("geometry")
    private Geometry geometry;

    public String getFormatted_address() {
        return formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
