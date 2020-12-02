package com.fruitforloops.model;

import java.io.Serializable;
import java.util.List;

import com.fruitforloops.model.dao.UserDAO;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private UserGroup[] groups;
	private String username;
	private String password;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public UserGroup[] getGroups()
	{
		return groups;
	}
	public void setGroups(UserGroup[] groups)
	{
		this.groups = groups;
	}
	
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
		UserDAO userDAO = new UserDAO();
		
		List<User> userList = userDAO.getAll();
		
		for (User userEntry : userList)
		{
			if (userEntry.username.equals(username))
			{
				if (userEntry.password.equals(password))
				{
					// user credentials match
					// load user data
					groups = userEntry.getGroups();
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() 
	{
		return "{username:\"" + username + " : " + (groups==null?"":groups[0].getId()) + "\"}";
	}
}
