package com.wku.mandi.controller;

import com.wku.mandi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by srujangopu on 7/5/15.
 */

@Controller
@Path("/profile")
public class ProfileController {

    @Autowired
    @Qualifier("ProfileService")
    private ProfileService profileService;

    @GET
    @Path("/hello")
    public String getHello()
    {
        return "Hello World";
    }

}
