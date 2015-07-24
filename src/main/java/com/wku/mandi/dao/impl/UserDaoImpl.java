package com.wku.mandi.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.wku.mandi.dao.UserDao;
import com.wku.mandi.db.User;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private MongoTemplate mongoTemplate;
    
	/*
	 * (non-Javadoc)
	 * @see com.wku.mandi.dao.UserDao#findUserById(java.lang.String)
	 * Finds a single user given the unique user ID for that user.
	 */
	@Override
	public User findUserById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		
		User user = (User) mongoTemplate.findOne(query, User.class);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.wku.mandi.dao.UserDao#saveUser(com.wku.mandi.db.User)
	 * Saves a User to the database.
	 */
	@Override
	public void saveUser(User user) {
		mongoTemplate.save(user);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wku.mandi.dao.UserDao#findUsersWithNameLike(java.lang.String)
	 * Retrieve a list of users depending on firstName or lastName like, case insensitive
	 */
	@Override
	public List<User> findUsersWithNameLike(String nameLike) {
		Pattern namePattern = Pattern.compile("\\w*"+nameLike+"\\w*", Pattern.CASE_INSENSITIVE);
		Criteria criteria = new Criteria().orOperator(Criteria.where("firstName").regex(namePattern),
				                                      Criteria.where("lastName").regex(namePattern));
		Query query = new Query(criteria);
		
		ArrayList<User> users = (ArrayList<User>) mongoTemplate.find(query, User.class);
		return users;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wku.mandi.dao.UserDao#deleteUser(java.lang.String)
	 * Deletes a user, given the unique user ID
	 */
	@Override
	public void deleteUser(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		
		mongoTemplate.remove(query, User.class);
	}

	public MongoTemplate getMongoOperations() {
		return mongoTemplate;
	}

	public void setMongoOperations(MongoTemplate mongoOperations) {
		this.mongoTemplate = mongoOperations;
	}

}
