package com.wku.mandi.controller;

import com.wku.mandi.MandiConstants;
import com.wku.mandi.rest.response.ZipCodeResponse;
import com.wku.mandi.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Created by srujangopu on 8/12/15.
 */

@RestController
public class AddressController implements ParentController{

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

        return profileService.getAddressDetails(zipCode);
    }
}
