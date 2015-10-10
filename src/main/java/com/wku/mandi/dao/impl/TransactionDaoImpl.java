package com.wku.mandi.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
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
import com.wku.mandi.db.MandiConstants;
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
	public boolean deductOrAddToSeller(String sellerId, String inventoryId, int quantity) {
		Query query = new Query(new Criteria().andOperator(Criteria.where("inventory._id").is(new ObjectId(inventoryId)), Criteria.where("_id").is(sellerId)));
		try{
			Update update = new Update();
			update.inc("inventory.$.quantity", quantity);
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

	@Override
	public boolean addPendingTransactionToUser(String userId, String transactionId) {
		Update update = new Update();
		update.push("pendingTransactions", transactionId);
		
		Query query = new Query(Criteria.where("_id").is(userId));
		
		try {
			mongoTemplate.updateFirst(query, update, User.class);
		}
		catch(MongoException exception) {
			log.error("An exception occured ", exception);
			return false;
		}
		return true;
	}

	@Override
	public boolean removePendingTransactionFromUser(String userId, String transactionId) {
		Update update = new Update();
		update.pull("pendingTransactions", transactionId);
        
		Query query = new Query(Criteria.where("_id").is(userId));
		
		try {
			mongoTemplate.findAndModify(query, update, User.class);
		}
		catch(MongoException exception) {
			log.error("An exception occured ", exception);
			return false;
		}
		return true;
	}

	@Override
	public boolean changeTransactionStatus(String transactionId, MandiConstants.TransactionStatus status) {
		Update update = new Update();
		update.set("status", status);
		
		Query query = new Query(Criteria.where("_id").is(transactionId));
		
		try {
			mongoTemplate.findAndModify(query, update, Transaction.class);
		}
		catch(MongoException exception) {
			log.error("An exception occured ", exception);
			return false;
		}
		return true;
	}

	@Override
	public Inventory findInventoryInSeller(String sellerId, String inventoryId) {
		Query query = new Query(new Criteria().andOperator(Criteria.where("inventory._id").is(new ObjectId(inventoryId)), Criteria.where("_id").is(sellerId)));
		
		try {
			User user = mongoTemplate.findOne(query, User.class);
			
			Inventory inventory = (Inventory) CollectionUtils.find(user.getInventory(), new Predicate() {
				@Override
				public boolean evaluate(Object arg) {
					Inventory inv = (Inventory) arg;
					if(inv.getInventoryId().equals(inventoryId))
					   return true;
					else
					   return false;
				}
			});
			return inventory;
		}
		catch(MongoException exception) {
			log.error("An exception occured ", exception);
		}
		return null;
	}

	
}
