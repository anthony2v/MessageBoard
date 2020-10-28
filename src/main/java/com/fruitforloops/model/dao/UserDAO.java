package com.fruitforloops.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.fruitforloops.model.User;

public class UserDAO
{
	public List<User> getAll()
	{
		List<User> messageList = new ArrayList<User>();
		try
		{
			// get Users from json file
		}
		catch (Exception e)
		{
			System.err.println("Unable to get list of users.\n" + e.getMessage());
		}
		
		return messageList;
	}
}
