package com.fruitforloops.gson;

import java.lang.reflect.Type;

import com.fruitforloops.model.User;
import com.fruitforloops.model.UserGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class UserDeserializer implements JsonDeserializer<User[]>
{
	@Override
	public User[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		JsonArray usersJsonArray = json.getAsJsonArray();
		User[] users = new User[usersJsonArray.size()];
		
		for (int i = 0; i < usersJsonArray.size(); ++i)
		{
			User user = new User();
			
			JsonElement id = usersJsonArray.get(i).getAsJsonObject().get("id");
			user.setId(id == null ? null : id.getAsLong());
			
			JsonElement username = usersJsonArray.get(i).getAsJsonObject().get("username");
			user.setUsername(username == null ? null : username.getAsString());
			
			JsonElement password = usersJsonArray.get(i).getAsJsonObject().get("password");
			user.setPassword(password == null ? null : password.getAsString());
			
			JsonElement groupMembership = usersJsonArray.get(i).getAsJsonObject().get("groupMembership");
			JsonArray groupMembershipJsonArray = groupMembership.getAsJsonArray();
			UserGroup[] groups = new UserGroup[groupMembershipJsonArray.size()];
			for (int j = 0; j < groupMembershipJsonArray.size(); ++j)
			{
				UserGroup group = new UserGroup();
				JsonElement groupId = groupMembershipJsonArray.get(j);
				group.setId(groupId == null ? null : groupId.getAsLong());
				groups[j] = group;
			}
			user.setGroups(groups);
			
			users[i] = user;
		}
		
		return users;
	}
}
