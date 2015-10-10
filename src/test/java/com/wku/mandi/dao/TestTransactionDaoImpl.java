package com.wku.mandi.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
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
	
	@After
	public void tearDown() {
		super.tearDown();
	}
	
	@Test
	public void testCreateInitialTransaction() {
		Transaction transaction = createSampleTransaction();
		
		boolean result = transactionDaoImpl.createInitialTransaction(transaction);
		Assert.assertTrue(result);
		
		removeSampleTransaction(transaction);
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
	
	@Test
	public void testAddPendingTransactionToUser() {
		User user = userDaoImpl.findUserById(JOHN_DOE);
		
		Assert.assertNull(user.getPendingTransactions());
		
		boolean result = transactionDaoImpl.addPendingTransactionToUser(JOHN_DOE, "1234");
		Assert.assertTrue(result);
		
		user = userDaoImpl.findUserById(JOHN_DOE);
		
		Assert.assertNotNull(user.getPendingTransactions());
		Assert.assertEquals("1234", user.getPendingTransactions().get(0));
	}
	
	@Test
	public void testRemovePendingTransactionFromUser() {
		boolean result = transactionDaoImpl.addPendingTransactionToUser(JOHN_DOE, "1234");
		Assert.assertTrue(result);
		
		result = transactionDaoImpl.removePendingTransactionFromUser(JOHN_DOE, "1234");
		Assert.assertTrue(result);
		
		User user = userDaoImpl.findUserById(JOHN_DOE);
		Assert.assertEquals(0, user.getPendingTransactions().size());
	}
	
	@Test
	public void testSetInitialTransactionToComplete() {
		Transaction transaction = createSampleTransaction();
		
		boolean result = transactionDaoImpl.createInitialTransaction(transaction);
		Assert.assertTrue(result);
		
		result = transactionDaoImpl.setInitialTransactionToComplete(transaction.getTransactionId());
		Assert.assertTrue(result);
		
		List<Transaction> actualTransaction = mongoTemplate.find(Query.query(Criteria.where("_id").is(transaction.getTransactionId())), Transaction.class);
		Assert.assertNotNull(actualTransaction);
		Assert.assertEquals(1, actualTransaction.size());
		Assert.assertEquals(TransactionStatus.COMPLETED, actualTransaction.get(0).getStatus());
		
		removeSampleTransaction(transaction);
	}
	
	private Transaction createSampleTransaction() {
		Transaction transaction = new Transaction();
		transaction.setTransactionId(ObjectId.get().toString());
		transaction.setBuyerId("JOHN_DOE");
		transaction.setSellerId("JEFF_DOE");
		transaction.setQuantity(2);
		transaction.setInventoryId(ObjectId.get().toString());
		transaction.setStatus(TransactionStatus.PENDING);
		transaction.setTransactionDate(new Date());
		return transaction;
	}
	
	private void removeSampleTransaction(Transaction transaction) {
		mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(transaction.getTransactionId())), Transaction.class);
	}
	

}
