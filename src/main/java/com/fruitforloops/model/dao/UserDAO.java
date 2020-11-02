package com.fruitforloops.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.fruitforloops.model.User;

public class UserDAO implements IDAO<User>
{
	@Override
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

	@Override
	public User get(long id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(User object)
	{
		//
		return false;
	}

	@Override
	public void update(User object)
	{
		//
	}

	@Override
	public void delete(User object)
	{
		//
	}
}
