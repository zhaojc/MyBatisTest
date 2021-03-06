package it.bls.mybatistest;

import it.bls.mybatistest.domain.User;
import it.bls.mybatistest.service.UserService;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserServiceTest {

	private static UserService userService;

	@BeforeClass
	public static void setup() {
		userService = new UserService();
	}

	@AfterClass
	public static void teardown() {
		userService = null;
	}
	
	@Test
	public void testInsertUser() {
		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setEmail("test_email_" + System.currentTimeMillis() + "@gmail.com");
			user.setPassword("secret");
			user.setFirstName("TestFirstName");
			user.setLastName("TestLastName");
			userService.insertUser(user);
			Assert.assertTrue(user.getUserId() != 0);
			User createdUser = userService.getUserById(user.getUserId());
			Assert.assertNotNull(createdUser);
			Assert.assertEquals(user.getEmail(), createdUser.getEmail());
			Assert.assertEquals(user.getPassword(), createdUser.getPassword());
			Assert.assertEquals(user.getFirstName(), createdUser.getFirstName());
			Assert.assertEquals(user.getLastName(), createdUser.getLastName());
		}
	}	

	@Test
	public void testGetUserById() {
		User user = userService.getUserById(1);
		Assert.assertNotNull(user);
		System.out.println(user);
		Assert.assertNotNull(user.getBlog());
		System.out.println(user.getBlog());
	}

	@Test
	public void testGetAllUsers() {
		List<User> users = userService.getAllUsers();
		Assert.assertNotNull(users);
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testGetUSerByEmail() {
		List<User> users = userService.getAllUsers();
		Assert.assertNotNull(users);
		String email = users.get(0).getEmail();
		User user = userService.getUserByEmail(email);
		Assert.assertNotNull(user);
		System.out.println(user);
	}

	@Test
	public void testUpdateUser() {
		long timestamp = System.currentTimeMillis();
		User user = userService.getUserById(2);
		user.setFirstName("TestFirstName" + timestamp);
		user.setLastName("TestLastName" + timestamp);
		userService.updateUser(user);
		User updatedUser = userService.getUserById(2);
		Assert.assertEquals(user.getFirstName(), updatedUser.getFirstName());
		Assert.assertEquals(user.getLastName(), updatedUser.getLastName());
	}

	@Test
	public void testDeleteUser() {
		User user = userService.getUserById(1);
		userService.deleteUser(user.getUserId());
		User deletedUser = userService.getUserById(4);
		Assert.assertNull(deletedUser);
	}

}
