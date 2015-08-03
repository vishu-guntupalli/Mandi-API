package com.wku.mandi.rest;

import com.wku.mandi.db.Address;
import com.wku.mandi.rest.response.GeospatialAPIResponse;
import org.springframework.stereotype.Component;

/**
 * Created by srujangopu on 8/3/15.
 */
@Component
public class GeographicalAPI extends BasicRestAPI<GeospatialAPIResponse> {

    public static final String GEOSPATIAL_URL = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyARg4Dq9ZsMbGv1K9PYkIsvf-JvNk_xE1Y&address=";

    public GeospatialAPIResponse getGeospatialAPIResponse(String address){

        return getRestAPIContent(GEOSPATIAL_URL+address ,GeospatialAPIResponse.class);
    }
}
