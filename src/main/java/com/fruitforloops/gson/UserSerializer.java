package com.fruitforloops.gson;

import java.lang.reflect.Type;

import com.fruitforloops.model.User;
import com.fruitforloops.model.UserGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserSerializer implements JsonSerializer<User>
{
	@Override
	public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();
		
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("username", src.getUsername());
        jsonObject.addProperty("password", src.getPassword());
        
        JsonArray groupMembershipArray = new JsonArray();
        for (UserGroup group : src.getGroups())
        	groupMembershipArray.add(group.getId());
        jsonObject.add("groupMembership", groupMembershipArray);
        
        return jsonObject;
	}
}
