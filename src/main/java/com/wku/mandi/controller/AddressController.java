package com.wku.mandi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wku.mandi.MandiConstants;
import com.wku.mandi.rest.response.ZipCodeResponse;
import com.wku.mandi.service.ProfileService;

/**
 * Created by srujangopu on 8/12/15.
 */

@RestController
public class AddressController {
	
	Log log = LogFactory.getLog(AddressController.class);

    private final ProfileService profileService;

    @Autowired
    AddressController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @RequestMapping(method = RequestMethod.GET, value = MandiConstants.TEST_URL, produces={"application/json"})
    public String getHello()
    {
        return "Hello World";
    }

    @RequestMapping(method=RequestMethod.GET, value = MandiConstants.ADDRESS_DETAILS_URL, produces={"application/json"})
    public @ResponseBody
    ZipCodeResponse getAddressDetails(@PathVariable String zipCode) {
        log.debug("GET call for address details triggered with zip code "+zipCode);
        return profileService.getAddressDetails(zipCode);
    }
}
