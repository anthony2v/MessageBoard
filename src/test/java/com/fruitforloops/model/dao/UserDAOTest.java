package com.fruitforloops.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserDAOTest {

	@Test
	void circularDependencyTest() {
		assertEquals(0, new UserDAO().getAll("src/test/webapp/WEB-INF/usersCircular.json").size());
	}
	
	@Test
	void goodTest() {
		assertEquals(8, new UserDAO().getAll("src/test/webapp/WEB-INF/users.json").size());
	}
	
	@Test
	void undefinedGroupTest() {
		assertEquals(0, new UserDAO().getAll("src/test/webapp/WEB-INF/usersBadGroup.json").size());
	}
	
	@Test
	void badParentTest() {
		assertEquals(0, new UserDAO().getAll("src/test/webapp/WEB-INF/usersBadParent.json").size());
	}

}
