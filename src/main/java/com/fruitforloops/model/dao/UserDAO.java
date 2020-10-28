package com.fruitforloops.model.dao;

import com.fruitforloops.model.User;

public class UserDAO
{
	public List<User> getAll()
	{
		List<Message> messageList = new ArrayList<Message>();
		try
		{
			// get Users from json file
		}
		catch (IOException e)
		{
			System.err.println("Unable to get list of users.\n" + e.getMessage());
		}
		
		return messageList;
	}
}
