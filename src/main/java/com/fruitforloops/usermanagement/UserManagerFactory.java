package com.fruitforloops.usermanagement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;


import org.apache.tomcat.util.json.ParseException ;
import org.apache.tomcat.util.json.JSONParser;


public class UserManagerFactory
{
	private UserManager instance;
	//private static final String path = "com.fruitforloops.usermanagement.UserManager";

	
	private UserManagerFactory() 
	{
	 //
	}


	 // Returns a UserManager instance based on the filename  and it will avoid making a new user each time
	public UserManager getInstance(){    
		try {
			if (instance == null) {
				Class<?> cl = Class.forName("com.fruitforloops.usermanagement.UserManager"); //The file path to userManager
				Constructor<?> cons = cl.getConstructor();
				instance = (UserManager) cons.newInstance();
			}

			return instance;
		} 
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	
//    private static String getpath() throws IOException, ParseException {
//        InputStream inputStream = UserManagerFactory.class.getClassLoader().getResourceAsStream("users.json"); // Get resource
//        Object objectFile = new InputStreamReader(inputStream); // Read file
//        
//
//        return objectFile.toString();
//    }
}