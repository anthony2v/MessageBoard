package com.fruitforloops.usermanagement;

public class UserManagerFactory
{
	private UserManagerFactory instance;
	
	private UserManagerFactory() 
	{
		//
	}
	
	public UserManagerFactory getInstance()
	{
		if (instance == null)
			instance = new UserManagerFactory();
		
		return instance;
	}
}
