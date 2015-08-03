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

}
