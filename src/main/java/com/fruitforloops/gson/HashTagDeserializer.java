package com.fruitforloops.gson;

import java.lang.reflect.Type;

import com.fruitforloops.model.HashTag;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class HashTagDeserializer implements JsonDeserializer<HashTag>
{

	@Override
	public HashTag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		HashTag hashtag = new HashTag();
		
		JsonElement id = json.getAsJsonObject().get("id");
		hashtag.setId(id == null ? null : id.getAsLong());
		
		JsonElement tag = json.getAsJsonObject().get("tag");
		hashtag.setTag(tag == null ? null : tag.getAsString());
		
        return hashtag;
	}

}
