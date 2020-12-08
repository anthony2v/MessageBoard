package com.fruitforloops.usermanagement;

import com.fruitforloops.model.User;

public interface IUserManager
{
	public User authenticate(String userName, String password);
}
