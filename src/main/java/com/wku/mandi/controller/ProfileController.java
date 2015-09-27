package com.wku.mandi.controller;

import com.wku.mandi.db.Role;
import com.wku.mandi.db.MandiConstants;
import com.wku.mandi.db.User;
import com.wku.mandi.exception.UserNotFoundException;
import com.wku.mandi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(method=RequestMethod.GET, value = AbstractController.PROFILE_FETCH_URL, produces={"application/json"})
    public @ResponseBody User getProfile(@PathVariable String id) {

        User user = profileService.findUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, value = AbstractController.PROFILE_SAVE_URL, produces={"application/json"})
    public @ResponseBody User save(@RequestBody User user) {
        if (user != null) {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role(MandiConstants.USER));
            user.setRoles(roles);
            profileService.saveUser(user);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = AbstractController.PROFILE_DELETE_URL, produces={"application/json"})
    public @ResponseBody User delete(@PathVariable String id) {

        User user = profileService.deleteUser(id);
        return user;
    }

    @RequestMapping(method=RequestMethod.GET, value = AbstractController.SEARCH_PROFILE_URL, produces={"application/json"})
    public @ResponseBody
    List<User> getSearchResults(@RequestParam(value="zipCode") String zipCode,@RequestParam(value="distance") String distance) {
        return this.profileService.searchResults(zipCode,Integer.parseInt(distance));
    }
}
