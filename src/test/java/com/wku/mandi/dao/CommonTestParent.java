package com.wku.mandi.dao;

import java.util.Arrays;
import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.wku.mandi.dao.impl.UserDaoImpl;
import com.wku.mandi.db.Address;
import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.User;

public class CommonTestParent {
	
	protected User fakeUser = new User();
	protected Address homeAddress = new Address();
	
	protected static final String JOHN_DOE = "JohnDoe";
	
	@Autowired
	protected UserDaoImpl userDaoImpl;
	
	@Before
	public void setUp() {
		fakeUser.setFirstName("John");
		fakeUser.setLastName("Doe");
		fakeUser.setSex("M");
		fakeUser.setUserId(JOHN_DOE);
		
		injectHomeAddress();
		injectInventory();
		
		userDaoImpl.saveUser(fakeUser);
	}
	
	@After
	public void tearDown() {
		userDaoImpl.deleteUser(JOHN_DOE);
	}
	
	private void injectHomeAddress() {
		double loc[] = {-86.8556036, 35.9462032};
		
		homeAddress.setAddressLine1("123 Some Street");
		homeAddress.setAddressLine2("Apartment 1");
		homeAddress.setCity("Nashville");
		homeAddress.setState("TN");
		homeAddress.setZipCode("37027");
		homeAddress.setLocation(loc);
		homeAddress.setType("Home");
		
		fakeUser.setAddresses(Arrays.asList(homeAddress));
	}
	
	private void injectInventory() {
		Inventory inventory = new Inventory();
		
		inventory.setInventoryId(new ObjectId().toString());		
		inventory.setBought(false);
		inventory.setName("Tomato");
		inventory.setDescription("Big Red Tomatoes");
		inventory.setExpiryDate(new Date());
		inventory.setQuantity(10);
		inventory.setUnit("Singles");
		
		fakeUser.setInventory(Arrays.asList(inventory));
	}
	
	protected void compareAddressData(Address actualAddress) {
		Assert.assertEquals(homeAddress.getAddressLine1(), actualAddress.getAddressLine1());
		Assert.assertEquals(homeAddress.getAddressLine2(), actualAddress.getAddressLine2());
		Assert.assertEquals(homeAddress.getCity(), actualAddress.getCity());
		Assert.assertEquals(homeAddress.getState(), actualAddress.getState());
		Assert.assertEquals(homeAddress.getZipCode(), actualAddress.getZipCode());
		Assert.assertEquals(homeAddress.getType(), actualAddress.getType());
	}

	protected void compareUserData(User actualuser) {
		Assert.assertNotNull(actualuser);
		Assert.assertEquals(fakeUser.getFirstName(), actualuser.getFirstName());
		Assert.assertEquals(fakeUser.getLastName(), actualuser.getLastName());
		Assert.assertEquals(fakeUser.getSex(), actualuser.getSex());
		Assert.assertEquals(fakeUser.getUserId(), actualuser.getUserId());
		Assert.assertNotNull(actualuser.getAddresses());
		Assert.assertEquals(1, actualuser.getAddresses().size());
	}

}
