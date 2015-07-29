package com.wku.mandi.dao;

import java.util.List;
import com.wku.mandi.db.User;
import org.springframework.data.repository.Repository;

public interface UserDao extends Repository<User, String> {

	public User findUserById(String id);

	public List<User> findUsersWithNameLike(String nameLike);

	public void saveUser(User user);

	public User deleteUser(String id);

}