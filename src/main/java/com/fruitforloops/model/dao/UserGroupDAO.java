package com.fruitforloops.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fruitforloops.Constants;
import com.fruitforloops.JSONUtil;
import com.fruitforloops.model.UserGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class UserGroupDAO implements IDAO<UserGroup>
{
	@Override
	public UserGroup get(Long id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserGroup> getAll()
	{
		JsonReader reader = null;
		try
		{
			reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource(Constants.USERS_DATASTORE_PATH).getPath()));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("'users.json' file is missing.\n" + e.getMessage());
			return new ArrayList<UserGroup>();
		}
		
		JsonObject jsonObj = JSONUtil.gson.fromJson(reader, JsonObject.class);
		
		JsonArray groupsJsonArray = jsonObj.get("groups").getAsJsonArray();
		UserGroup[] groups = JSONUtil.gson.fromJson(groupsJsonArray, UserGroup[].class);
		
		List<UserGroup> groupsList = new ArrayList<UserGroup>();
		for (UserGroup g : groups)
			groupsList.add(g);
		
		return groupsList;
	}

	@Override
	public boolean save(UserGroup object)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(UserGroup object)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
