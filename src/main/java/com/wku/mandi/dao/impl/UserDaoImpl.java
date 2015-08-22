package com.wku.mandi.dao.impl;

import com.wku.mandi.dao.UserDao;
import com.wku.mandi.db.Address;
import com.wku.mandi.db.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class UserDaoImpl implements UserDao{
	
	Log log = LogFactory.getLog(UserDaoImpl.class);

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
		
		log.debug("Finding the user by userID "+ id);
		User user = (User) mongoTemplate.findOne(query, User.class);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.wku.mandi.dao.UserDao#saveUser(User)
	 * Saves a User to the database.
	 */
	@Override
	public void saveUser(User user) {
		log.debug("Saving the user "+user);
		mongoTemplate.save(user);
	}

	@Override
	public void updateUser(User user) {
		log.debug("Updating the user " + user);
		//mongoTemplate.modi(user);
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
		
		log.debug("Finding user with name like "+nameLike);
		ArrayList<User> users = (ArrayList<User>) mongoTemplate.find(query, User.class);
		return users;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wku.mandi.dao.UserDao#deleteUser(java.lang.String)
	 * Deletes a user, given the unique user ID
	 */
	@Override
	public User deleteUser(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		
		log.debug("Deleting the user with ID "+id);
		return mongoTemplate.findAndRemove(query,User.class);
	}

	@Override
	public List<User> getSearchResults(double[] loc, int distance) {
		Point location = new Point(loc[0],loc[1]);
		NearQuery query = NearQuery.near(location).maxDistance(new Distance(distance, Metrics.MILES));
		//GeoResults<User> results = mongoTemplate.geoNear(query, User.class);
		GeoResults<User> results = mongoTemplate.geoNear(query, User.class);
		List<User> response = new ArrayList<>();
		/*for(GeoResult<User>  eachUser : results.getContent()){
			response.add(eachUser.getContent());
		}*/
		return  response;
	}

	public MongoTemplate getMongoOperations() {
		return mongoTemplate;
	}

	public void setMongoOperations(MongoTemplate mongoOperations) {
		this.mongoTemplate = mongoOperations;
	}

}
