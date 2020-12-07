package com.fruitforloops.usermanagement;

import com.fruitforloops.model.User;

public class UserManager implements IUserManager {

	public User authenticate(String userName, String password) {
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);

		if (user.authenticate() == true) {

			return user;
		} else {
			return null;
		}
	}
}
