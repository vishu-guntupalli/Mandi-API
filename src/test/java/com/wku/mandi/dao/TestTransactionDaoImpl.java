package com.wku.mandi.dao;

import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wku.mandi.SpringBoot;
import com.wku.mandi.dao.impl.TransactionDaoImpl;
import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.MandiConstants.TransactionStatus;
import com.wku.mandi.db.Transaction;
import com.wku.mandi.db.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBoot.class)
@WebAppConfiguration
public class TestTransactionDaoImpl extends CommonTestParent{
	
	@Autowired
	private TransactionDaoImpl transactionDaoImpl;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testCreateInitialTransaction() {
		Transaction transaction = new Transaction();
		transaction.setTransactionId(ObjectId.get().toString());
		transaction.setBuyerId("JOHN_DOE");
		transaction.setSellerId("JEFF_DOE");
		transaction.setQuantity(2);
		transaction.setInventoryId(ObjectId.get().toString());
		transaction.setStatus(TransactionStatus.PENDING);
		transaction.setTransactionDate(new Date());
		
		boolean result = transactionDaoImpl.createInitialTransaction(transaction);
		
		Assert.assertTrue(result);
		
		mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(transaction.getTransactionId())), Transaction.class);
	}
	
	@Test
	public void testDeductFromSeller(){
		boolean result = transactionDaoImpl.deductFromSeller(JOHN_DOE, fakeUser.getInventory().get(0).getInventoryId(), 5);
		
		Assert.assertTrue(result);
		
		User user = userDaoImpl.findUserById(JOHN_DOE);
		
		Assert.assertEquals(5, user.getInventory().get(0).getQuantity());
		
		compareAddressData(user.getAddresses().get(0));
		compareUserData(user);
	}
	
	@Test
	public void testAddToBuyer() {
		Inventory inventory = new Inventory();
		inventory.setInventoryId(new ObjectId().get().toString());
		inventory.setName("Potato");
		inventory.setBought(true);
        inventory.setDescription("Big potatoes");
        inventory.setExpiryDate(new Date());
        inventory.setQuantity(7);
        inventory.setUnit("Singles");
        
        boolean result = transactionDaoImpl.addToBuyer(JOHN_DOE, inventory);
        
        Assert.assertTrue(result);
        
        User user = userDaoImpl.findUserById(JOHN_DOE);
        
        Assert.assertEquals(2, user.getInventory().size());
	}
	

}
