package com.fruitforloops.gson;

import java.lang.reflect.Type;

import com.fruitforloops.model.HashTag;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class HashTagSerializer implements JsonSerializer<HashTag> 
{
	@Override
	public JsonElement serialize(HashTag src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();
		
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("message_id", src.getTag() == null ? null : src.getTag());
        
        return jsonObject;
	}
}
