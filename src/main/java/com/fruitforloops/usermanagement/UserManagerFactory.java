package com.fruitforloops.usermanagement;

import java.lang.reflect.InvocationTargetException;

import com.fruitforloops.Constants;

public class UserManagerFactory
{
	private static UserManagerFactory instance;

	private UserManagerFactory()
	{
		//
	}

	public static UserManagerFactory getInstance()
	{
		if (instance == null)
		{
			instance = new UserManagerFactory();
		}
		
		return instance;
	}
	
	public IUserManager getUserManager(String usermanagerClazz)
	{
		IUserManager userManager = null;
		try
		{
			Class<?> clazz = Class.forName(usermanagerClazz);
			userManager = (IUserManager) clazz.getDeclaredConstructor(String.class).newInstance(Constants.USERS_DATASTORE_PATH);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e)
		{
			System.err.println("Error trying to create UserManager using reflection.");
			System.err.println(e.getMessage());
		}
		
		return userManager;
	}
}
