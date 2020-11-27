package com.fruitforloops.gson;

import java.lang.reflect.Type;

import com.fruitforloops.model.User;
import com.fruitforloops.model.UserGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class UserDeserializer implements JsonDeserializer<User>
{
	@Override
	public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		User user = new User();
		
		JsonElement id = json.getAsJsonObject().get("id");
		user.setId(id == null ? null : id.getAsLong());
		
		JsonElement username = json.getAsJsonObject().get("username");
		user.setUsername(username == null ? null : username.getAsString());
		
		JsonElement password = json.getAsJsonObject().get("password");
		user.setPassword(password == null ? null : password.getAsString());
		
		JsonElement groupMembership = json.getAsJsonObject().get("groupMembership");
		JsonArray groupMembershipJsonArray = groupMembership.getAsJsonArray();
		UserGroup[] groups = new UserGroup[groupMembershipJsonArray.size()];
		for (int i = 0; i < groupMembershipJsonArray.size(); ++i)
		{	
			UserGroup group = new UserGroup();
			JsonElement groupId = groupMembershipJsonArray.get(i);
			group.setId(groupId == null ? null : groupId.getAsLong());
			groups[i] = group;
		}
			
			
		user.setGroups(groups);
		
		return user;
	}
}
