package com.wku.mandi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wku.mandi.db.MandiConstants;
import com.wku.mandi.db.Role;
import com.wku.mandi.db.User;
import com.wku.mandi.db.Vault;
import com.wku.mandi.exception.UserNotFoundException;
import com.wku.mandi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    
    @RequestMapping(method=RequestMethod.GET, value = AbstractController.PROFILE_FETCH_URL, consumes= org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces={"application/json"})
    public @ResponseBody User getProfile(@PathVariable String id) {

        User user = profileService.findUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @JsonView(Vault.class)
    @RequestMapping(method = RequestMethod.POST, value = AbstractController.NEW_USER_SAVE_URL, produces={"application/json"} )
    public @ResponseBody boolean saveRegistrationInfo(@RequestBody Vault vault) {
        return profileService.saveRegistrationInfo(vault);
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

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public Response handleFileUpload(@RequestParam("userId") String userId,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {

            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            profileService.uploadProfileImage(userId, inputStream);
            return Response.status(200).build();
        }
        return Response.status(422).build();
    }

    @RequestMapping(method=RequestMethod.GET, value = AbstractController.SEARCH_PROFILE_URL, produces={"application/json"})
    public @ResponseBody
    List<User> getSearchResults(@RequestParam(value="zipCode") String zipCode,@RequestParam(value="distance") String distance) {
        return this.profileService.searchResults(zipCode,Integer.parseInt(distance));
    }
}
