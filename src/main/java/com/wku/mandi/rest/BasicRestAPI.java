package com.wku.mandi.rest;

import org.springframework.web.client.RestTemplate;

/**
 * Created by srujangopu on 7/29/15.
 */
public class BasicRestAPI<T> {

    public T getRestAPIContent(String URL, Class<T> requestObject){

        RestTemplate restTemplate = new RestTemplate();
        T response = restTemplate.getForObject(URL, requestObject);

        return response;

    }
}
