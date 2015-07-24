package com.wku.mandi.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wku.mandi.dao.impl.UserDaoImpl;
import com.wku.mandi.db.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestUserDaoImpl {

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	private User fakeUser;
	
	private static final String JOHN_DOE = "JohnDoe";
	
	@Before
	public void setUp() {
		fakeUser = new User();
		fakeUser.setFirstName("John");
		fakeUser.setLastName("Doe");
		fakeUser.setSex("M");
		fakeUser.setUserId(JOHN_DOE);
		
		userDaoImpl.saveUser(fakeUser);
	}
	
	@After
	public void tearDown() {
		userDaoImpl.deleteUser(JOHN_DOE);
	}
	
	@Test
	public void testSaveUserandRetrieve() {
		User actualuser = userDaoImpl.findUserById(JOHN_DOE);
		
		Assert.assertNotNull(actualuser);
		Assert.assertEquals(fakeUser.getFirstName(), actualuser.getFirstName());
		Assert.assertEquals(fakeUser.getLastName(), actualuser.getLastName());
		Assert.assertEquals(fakeUser.getSex(), actualuser.getSex());
		Assert.assertEquals(fakeUser.getUserId(), actualuser.getUserId());
	}
	
	@Test
	public void testFindUsersNameLike() {
		List<User> userList = userDaoImpl.findUsersWithNameLike("John");
		
		Assert.assertNotNull(userList);
		Assert.assertEquals(1, userList.size());
	}
	

}
