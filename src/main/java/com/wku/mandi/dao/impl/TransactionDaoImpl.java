package com.wku.mandi.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoException;
import com.wku.mandi.dao.TransactionDao;
import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.Transaction;
import com.wku.mandi.db.User;

@Repository
public class TransactionDaoImpl implements TransactionDao{
	
	Log log = LogFactory.getLog(TransactionDaoImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public boolean createInitialTransaction(Transaction transaction) {
		try {
			mongoTemplate.save(transaction);
		}
		catch(MongoException exception) {
			log.error("An exception occured ", exception);
			return false;
		}
		return true;
	}

	@Override
	public boolean deductFromSeller(String sellerId, String inventoryId, int quantity) {
		Query query = new Query(new Criteria().andOperator(Criteria.where("inventory._id").is(new ObjectId(inventoryId)), Criteria.where("_id").is(sellerId)));
		try{
			Update update = new Update();
			update.inc("inventory.$.quantity", -quantity);
			mongoTemplate.findAndModify(query, update, User.class);
		}
		catch(MongoException exception) {
			log.error("An exception occured ", exception);
			return false;
		}
		return true;
	}
 
	@Override
	public boolean addToBuyer(String buyerId, Inventory inventory) {
		Update update = new Update();
		update.addToSet("inventory", inventory);
		
		Query query = new Query(Criteria.where("_id").is(buyerId));
		
		try{
			mongoTemplate.updateFirst(query, update, User.class);
		}
		catch(MongoException exception) {
			log.error("An exception occured ", exception);
			return false;
		}
		return true;
	}

	
}
