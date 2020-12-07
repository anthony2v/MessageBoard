package com.fruitforloops.usermanagement;

import java.util.List;

import com.fruitforloops.model.User;
import com.fruitforloops.model.dao.UserDAO;

public class UserManager implements IUserManager
{
	private String usersDataStorePath;
	
	public UserManager(String usersDataStorePath)
	{
		this.usersDataStorePath = usersDataStorePath;
	}
	
	public User authenticate(String userName, String password)
	{
		User user = new User();
		user.setUsername(userName);
		user.setPassword(password);

		UserDAO userDAO = new UserDAO();

		List<User> userList = userDAO.getAll(usersDataStorePath);

		for (User userEntry : userList)
		{
			if (userEntry.getUsername().equals(user.getUsername()))
			{
				if (userEntry.getPassword().equals(user.getPassword()))
				{
					// user credentials match
					// load user data

					user.setGroups(userEntry.getGroups());
					return user;

				}
			}
		}
		return null;

	}
}
