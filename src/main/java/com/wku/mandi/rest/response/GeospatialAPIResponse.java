package com.wku.mandi.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srujangopu on 8/3/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeospatialAPIResponse {

    @JsonProperty("results")
    private List<Result> results = new ArrayList<Result>();

    public List<Result> getResults() {
        return results;
    }
}




