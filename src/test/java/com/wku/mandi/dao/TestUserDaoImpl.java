package com.wku.mandi.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wku.mandi.SpringBoot;
import com.wku.mandi.db.Address;
import com.wku.mandi.db.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBoot.class)
@WebAppConfiguration
public class TestUserDaoImpl extends CommonTestParent {
	
	@Before
	public void setUp(){
		super.setUp();
	}

	@After
	public void tearDown() {
		super.tearDown();
	}
	
	@Test
	public void testSaveUserandRetrieve() {
		User actualuser = userDaoImpl.findUserById(JOHN_DOE);
		
		compareUserData(actualuser);
		
		Address actualAddress = actualuser.getAddresses().get(0);
		
		compareAddressData(actualAddress);
	}

	@Test
	public void testFindUsersNameLike() {
		List<User> userList = userDaoImpl.findUsersWithNameLike("john");
		
		Assert.assertNotNull(userList);
		Assert.assertEquals(1, userList.size());
		
		User actualUser = userList.get(0);
		
		Assert.assertEquals(fakeUser.getFirstName(), actualUser.getFirstName());
		Assert.assertEquals(fakeUser.getLastName(), actualUser.getLastName());
		Assert.assertEquals(fakeUser.getSex(), actualUser.getSex());
		Assert.assertEquals(fakeUser.getUserId(), actualUser.getUserId());
	}
	
	@Test
	public void testGetSearchNear() {
		double loc[] = {-86.8556036, 35.9462032};
		
		List<User> searchResults = userDaoImpl.getSearchResults(loc, 10);
		Assert.assertNotNull(searchResults);
		Assert.assertEquals(1, searchResults.size());
		
		User actualUser = searchResults.get(0);
        compareUserData(actualUser);
		
		Address actualAddress = actualUser.getAddresses().get(0);
		
		compareAddressData(actualAddress);
	}
	
}

