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
		FileReader reader = null;
		try
		{
			reader = new FileReader(getClass().getClassLoader().getResource(datastorePath).getPath());
		}
		catch (FileNotFoundException e)
		{
			System.err.println("'users.json' file is missing.\n" + e.getMessage());
			return new ArrayList<User>();
		}
		
		return getAll(reader);
	}
	
	public List<User> getAll(FileReader usersFileReader)
	{
		JsonReader reader = new JsonReader(usersFileReader);
		
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
		
		if (noErrors(users, groups))
			return Arrays.asList(users);
		return new ArrayList<User>();
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
	
	public boolean noErrors(User[] users, UserGroup[] groups)
	{		
		Map<Long, UserGroup> groupMap = new HashMap<Long, UserGroup>();
		for (int i = 0; i < groups.length; i++)
			groupMap.put(groups[i].getId(), groups[i]);

		// Check for undefined groups in users
		for (User user : users)
			for (UserGroup group : user.getGroups())
				if (!groupMap.containsKey(group.getId()))
					return false;
		
		// Check for a group definition with a non-existing parent
		for (UserGroup group : groups) {
			// Also check if group ID is -1, reserved for public
			if (group.getId() == -1)
				return false;
			if (group.getParentId() != null && !groupMap.containsKey(group.getParentId()))
				return false;
		}
		
		// Check for circular parent-child definitions
		for (UserGroup group : groups)
		{
			ArrayList<Long> descendants = new ArrayList<Long>();
			Long currentId = group.getId();
			while (currentId != null)
			{
				if (descendants.contains(currentId))
					return false;
				else
				{
					descendants.add(currentId);
					currentId = groupMap.get(currentId).getParentId();
				}
			}
		}
		
		return true;
	}
}
