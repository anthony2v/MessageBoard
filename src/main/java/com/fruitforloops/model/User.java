package com.fruitforloops.model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private UserGroup[] groups;
	private String username;
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserGroup[] getGroups() {
		return groups;
	}

	public void setGroups(UserGroup[] groups) {
		this.groups = groups;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "{username:\"" + username + " : " + (groups == null ? "" : groups[0].getId()) + "\"}";
	}
}
