package com.wku.mandi.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wku.mandi.dao.UserDao;
import com.wku.mandi.db.User;

@Component
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

	public MongoTemplate getMongoOperations() {
		return mongoTemplate;
	}

	public void setMongoOperations(MongoTemplate mongoOperations) {
		this.mongoTemplate = mongoOperations;
	}
	
}
