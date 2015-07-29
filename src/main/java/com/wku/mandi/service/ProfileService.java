package com.wku.mandi.service;

import com.wku.mandi.db.User;

import java.util.List;

/**
 * Created by srujangopu on 7/5/15.
 */
public interface ProfileService {

    public User findUserById(String id);

    public List<User> findUsersWithNameLike(String nameLike);

    public void saveUser(User user);

    public User deleteUser(String id);
}
