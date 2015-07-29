package com.wku.mandi.service.impl;

import com.wku.mandi.dao.UserDao;
import com.wku.mandi.db.User;
import com.wku.mandi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by srujangopu on 7/5/15.
 */

@Service("ProfileService")
public class ProfileServiceImpl implements ProfileService{

    private final UserDao userDao;

    @Autowired
    public ProfileServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserById(String id) {
        return userDao.findUserById(id);
    }

    @Override
    public List<User> findUsersWithNameLike(String nameLike) {
        return userDao.findUsersWithNameLike(nameLike);
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User deleteUser(String id) {
        return userDao.deleteUser(id);
    }
}
