package com.fruitforloops.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;

import com.fruitforloops.JSONUtil;
import com.google.gson.stream.JsonReader;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public boolean authenticate()
	{
		JsonReader reader = null;
		try
		{
			reader = new JsonReader(new FileReader(getClass().getClassLoader().getResource("/WEB-INF/users.json").getPath()));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("'users.json' file is missing.\n" + e.getMessage());
			return false;
		}
		User[] userList = JSONUtil.gson.fromJson(reader, User[].class);
		
		for (User userEntry : userList)
		{
			if (userEntry.username.equals(username))
			{
				if (userEntry.password.equals(password))
					return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() 
	{
		return "{username:\"" + username + "\"}";
	}
}
