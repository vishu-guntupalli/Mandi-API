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
}
