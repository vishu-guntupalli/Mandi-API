package com.wku.mandi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.wku.mandi.rest.response.BooleanResult;
import com.wku.mandi.service.ProfileService;

/**
 * Created by srujangopu on 7/5/15.
 */

@RestController
public class ProfileController {
	
	Log log = LogFactory.getLog(ProfileController.class);

    private final ProfileService profileService;

    @Autowired
    ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    @RequestMapping(method=RequestMethod.GET, value = MandiConstants.PROFILE_FETCH_URL, consumes= MediaType.APPLICATION_JSON_VALUE,produces={"application/json"})
    public @ResponseBody User getProfile(@PathVariable String id) {
        log.debug("GET call for fetching the profile called with profile id "+id);
    	
        User user = profileService.findUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, value = MandiConstants.NEW_USER_SAVE_URL, produces={"application/json"} )
    public @ResponseBody BooleanResult saveRegistrationInfo(@RequestBody Vault vault) {
    	log.debug("POST call for saving registration info called with details "+ vault);
    	
    	boolean result = profileService.saveRegistrationInfo(vault);
    	
        return new BooleanResult(result);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = MandiConstants.PROFILE_SAVE_URL, consumes= MediaType.APPLICATION_JSON_VALUE, produces={"application/json"})
    public @ResponseBody User save(@RequestBody User user) {
        if (user != null) {
        	log.debug("POST call for saving a new user called "+ user );
        	
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(MandiConstants.USER));
            user.setRoles(roles);
            profileService.saveUser(user);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = MandiConstants.PROFILE_DELETE_URL, consumes= MediaType.APPLICATION_JSON_VALUE, produces={"application/json"})
    public @ResponseBody User delete(@PathVariable String id) {
    	log.debug("DELETE call for deleting a user called with ID "+ id);
    	
        User user = profileService.deleteUser(id);
        return user;
    }

    @RequestMapping(method=RequestMethod.GET, value = MandiConstants.SEARCH_PROFILE_URL, consumes= MediaType.APPLICATION_JSON_VALUE, produces={"application/json"})
    public @ResponseBody
    List<User> getSearchResults(@RequestParam(value="zipCode") String zipCode,@RequestParam(value="distance") String distance) {
    	log.debug("GET call for getting search results called with params "+ zipCode + " and" + distance);
    	
        return this.profileService.searchResults(zipCode,Integer.parseInt(distance));
    }
}
