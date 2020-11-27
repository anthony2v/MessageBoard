package com.fruitforloops.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fruitforloops.JSONUtil;
import com.fruitforloops.model.User;
import com.fruitforloops.model.UserGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class UserDAO implements IDAO<User>
{
	public static final String usersDataStorePath = "/WEB-INF/users.json";
	
	@Override
	public List<User> getAll()
	{
		JsonReader reader = null;
		try
		{
			reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource(usersDataStorePath).getPath()));
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
			for (User u : users)
			{
				List<UserGroup> groupMembershipList = new ArrayList<UserGroup>();
				for (int i = 0; i < groups.length; ++i)
				{
					groupMembershipList.add(groups[i]);
				}
				
				UserGroup[] groupMembershipArray = new UserGroup[groupMembershipList.size()];
				for (int i = 0; i < groupMembershipList.size(); ++i)
					groupMembershipArray[i] = groupMembershipList.get(i);
				u.setGroups(groupMembershipArray);
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
