package com.wku.mandi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wku.mandi.MandiConstants;
import com.wku.mandi.db.Role;
import com.wku.mandi.db.User;
import com.wku.mandi.db.Vault;
import com.wku.mandi.exception.UserNotFoundException;
import com.wku.mandi.service.ProfileService;

/**
 * Created by srujangopu on 7/5/15.
 */

@RestController
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    @RequestMapping(method=RequestMethod.GET, value = MandiConstants.PROFILE_FETCH_URL, consumes= MediaType.APPLICATION_JSON_VALUE,produces={"application/json"})
    public @ResponseBody User getProfile(@PathVariable String id) {

        User user = profileService.findUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, value = MandiConstants.NEW_USER_SAVE_URL, produces={"application/json"} )
    public @ResponseBody boolean saveRegistrationInfo(@RequestBody Vault vault) {
        return profileService.saveRegistrationInfo(vault);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = MandiConstants.PROFILE_SAVE_URL, consumes= MediaType.APPLICATION_JSON_VALUE, produces={"application/json"})
    public @ResponseBody User save(@RequestBody User user) {
        if (user != null) {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(MandiConstants.USER));
            user.setRoles(roles);
            profileService.saveUser(user);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = MandiConstants.PROFILE_DELETE_URL, consumes= MediaType.APPLICATION_JSON_VALUE, produces={"application/json"})
    public @ResponseBody User delete(@PathVariable String id) {

        User user = profileService.deleteUser(id);
        return user;
    }

    @RequestMapping(method=RequestMethod.GET, value = MandiConstants.SEARCH_PROFILE_URL, consumes= MediaType.APPLICATION_JSON_VALUE, produces={"application/json"})
    public @ResponseBody
    List<User> getSearchResults(@RequestParam(value="zipCode") String zipCode,@RequestParam(value="distance") String distance) {
        return this.profileService.searchResults(zipCode,Integer.parseInt(distance));
    }
}
