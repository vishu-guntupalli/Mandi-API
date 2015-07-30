package com.wku.mandi.rest;

import com.wku.mandi.rest.response.ZipCodeResponse;
import org.springframework.stereotype.Component;

/**
 * Created by srujangopu on 7/29/15.
 */
@Component
public class ZipcodeRestAPI extends BasicRestAPI<ZipCodeResponse> {

    public static final String ZIPCODE_URL = "http://api.zippopotam.us/us/";

    public ZipCodeResponse getAddressDetails(String zipCode){
        return getRestAPIContent(ZIPCODE_URL+zipCode ,ZipCodeResponse.class);
    }
}
