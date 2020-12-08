package com.fruitforloops.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.jupiter.api.Test;

class UserDAOTest
{

	@Test
	void circularDependencyTest()
	{
		try
		{
			// Group 2 is a parent of 4, and group 4 is a parent of 2, which is a circular dependency and is not allowed
			assertEquals(0, new UserDAO().getAll(new FileReader("src/test/webapp/WEB-INF/usersCircular.json")).size());
		}
		catch (FileNotFoundException e)
		{
			fail("The file 'src/test/webapp/WEB-INF/usersCircular.json' is missing.");
		}
	}

	@Test
	void goodTest()
	{
		try
		{
			// This is the file that our application uses
			assertEquals(8, new UserDAO().getAll(new FileReader("src/test/webapp/WEB-INF/users.json")).size());
		}
		catch (FileNotFoundException e)
		{
			fail("The file 'src/test/webapp/WEB-INF/users.json' is missing.");
		}
	}

	@Test
	void undefinedGroupTest()
	{
		try
		{
			// User ID 7, Jack123, is a member of group 8, which doesn't exist
			assertEquals(0, new UserDAO().getAll(new FileReader("src/test/webapp/WEB-INF/usersBadGroup.json")).size());
		}
		catch (FileNotFoundException e)
		{
			fail("The file 'src/test/webapp/WEB-INF/usersBadGroup.json' is missing.");
		}
	}

	@Test
	void badParentTest()
	{
		try
		{
			// Group ID 5, comp, has a parent of group 9, which doesn't exist
			assertEquals(0, new UserDAO().getAll(new FileReader("src/test/webapp/WEB-INF/usersBadParent.json")).size());
		}
		catch (FileNotFoundException e)
		{
			fail("The file 'src/test/webapp/WEB-INF/usersBadParent.json' is missing.");
		}
	}

}
