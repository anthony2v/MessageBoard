package com.fruitforloops.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fruitforloops.JSONUtil;
import com.fruitforloops.model.User;
import com.google.gson.stream.JsonReader;

public class UserDAO implements IDAO<User> {
	@Override
	public List<User> getAll() {
		JsonReader reader = null;
		try {
			reader = new JsonReader(
					new FileReader(getClass().getClassLoader().getResource("/WEB-INF/users.json").getPath()));
		} catch (FileNotFoundException e) {
			System.err.println("'users.json' file is missing.\n" + e.getMessage());
			return new ArrayList<User>();
		}

		User[] userArray = JSONUtil.gson.fromJson(reader, User[].class);

		return Arrays.asList(userArray);
	}

	@Override
	public User get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(User object) {
		//
		return false;
	}

	@Override
	public boolean update(User object) {
		//
		return false;
	}

	@Override
	public boolean delete(long id) {
		//
		return false;
	}
}
