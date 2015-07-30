package com.wku.mandi.controller;

import com.wku.mandi.db.User;
import com.wku.mandi.rest.response.ZipCodeResponse;
import com.wku.mandi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by srujangopu on 7/5/15.
 */

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getHello()
    {
        return "Hello World";
    }

    @RequestMapping(method=RequestMethod.GET, value = "/{id}")
    public @ResponseBody User getProfile(@PathVariable String id) {

        return profileService.findUserById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user) {
        if (user != null) {
            profileService.saveUser(user);
        }
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@PathVariable String id) {

        User user = profileService.deleteUser(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.GET, value = "/address/{zipCode}")
    public @ResponseBody ZipCodeResponse getAddressDetails(@PathVariable String zipCode) {

        return profileService.getAddressDetails(zipCode);
    }

}
