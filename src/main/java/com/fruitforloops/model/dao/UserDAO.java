package com.fruitforloops.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fruitforloops.Constants;
import com.fruitforloops.JSONUtil;
import com.fruitforloops.model.User;
import com.fruitforloops.model.UserGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class UserDAO implements IDAO<User>
{
	@Override
	public List<User> getAll()
	{
		return getAll(Constants.USERS_DATASTORE_PATH);
	}
	
	public List<User> getAll(String datastorePath)
	{
		JsonReader reader = null;
		try
		{
			reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource(datastorePath).getPath()));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("'users.json' file is missing.\n" + e.getMessage());
			return new ArrayList<User>();
		}
		
		JsonObject jsonObj = JSONUtil.gson.fromJson(reader, JsonObject.class);
		
		JsonArray usersJsonArray = jsonObj.get("users").getAsJsonArray();
		User[] users = JSONUtil.gson.fromJson(usersJsonArray, User[].class);
		
		JsonArray groupsJsonArray = jsonObj.get("groups").getAsJsonArray();
		UserGroup[] groups = JSONUtil.gson.fromJson(groupsJsonArray, UserGroup[].class);
		
		if (groups != null)
		{
			for (int x = 0; x < users.length; ++x)
			{
				Map<Long, UserGroup> groupMembershipMap = new HashMap<Long, UserGroup>();
				for (int i = 0; i < groups.length; ++i)
				{
					groupMembershipMap.putIfAbsent(groups[i].getId(), groups[i]);
				}
				
				for (int i = 0; i < users[x].getGroups().length; ++i)
				{
					if (groupMembershipMap.containsKey(users[x].getGroups()[i].getId()))
						users[x].getGroups()[i] = groupMembershipMap.get(users[x].getGroups()[i].getId());
				}
			}
		}
		
		return Arrays.asList(users);
	}

	@Override
	public User get(Long id)
	{
		//
		return null;
	}

	@Override
	public boolean save(User object)
	{
		//
		return false;
	}

	@Override
	public boolean update(User object)
	{
		//
		return false;
	}

	@Override
	public boolean delete(Long id)
	{
		//
		return false;
	}
}
