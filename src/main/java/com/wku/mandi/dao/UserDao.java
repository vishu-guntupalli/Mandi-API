package com.wku.mandi.dao;

import com.wku.mandi.db.User;

public interface UserDao {
	
	public User findUserById(String id);
	
	public void saveUser(User user);
	
}
