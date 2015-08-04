package com.wku.mandi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by srujangopu on 8/3/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry{

    @JsonProperty("location")
    private Location location;

    public Location getLocation() {
        return location;
    }
}