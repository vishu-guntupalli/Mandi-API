package com.wku.mandi.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.MongoException;
import com.wku.mandi.dao.TransactionDao;
import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.Transaction;
import com.wku.mandi.db.User;

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
	public boolean deductFromSeller(String sellerId, String inventoryId, String quantity) {
		Update update = new Update();
		update.
		Query query = new Query(Criteria.where("_id").is(sellerId).
				                andOperator(Criteria.where("_")));
		
		mongoTemplate.updateFirst(query, update, User.class);
		return false;
	}

	@Override
	public boolean addToBuyer(Inventory inventory) {
		return false;
	}

	
}
