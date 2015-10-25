package com.wku.mandi.service;

import com.wku.mandi.db.User;
import com.wku.mandi.db.Vault;
import com.wku.mandi.rest.response.ZipCodeResponse;

import java.util.List;

/**
 * Created by srujangopu on 7/5/15.
 */
public interface ProfileService {

    public User findUserById(String id);

    public List<User> findUsersWithNameLike(String nameLike);

    public void saveUser(User user);
    
    public boolean saveRegistrationInfo(Vault vault);

    public void updateUser(User user);

    public User deleteUser(String id);

    public ZipCodeResponse getAddressDetails(String zipCode);

    public List<User> searchResults(String zipCode, int distance);
}
