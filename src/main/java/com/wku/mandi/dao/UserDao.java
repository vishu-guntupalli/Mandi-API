package com.wku.mandi.dao;

import java.util.List;

import com.wku.mandi.db.User;

public interface UserDao {
	
	public User findUserById(String id);
	
	public List<User> findUsersWithNameLike(String nameLike);
	
	public void saveUser(User user);
	
	public void deleteUser(String id);
	
}
