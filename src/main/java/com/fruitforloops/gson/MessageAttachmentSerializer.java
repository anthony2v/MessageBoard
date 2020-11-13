package com.fruitforloops.gson;

import java.lang.reflect.Type;

import com.fruitforloops.model.MessageAttachment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MessageAttachmentSerializer implements JsonSerializer<MessageAttachment>
{

	@Override
	public JsonElement serialize(MessageAttachment src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();
		
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("message_id", src.getMessage() == null ? -1 : src.getMessage().getId());
        jsonObject.addProperty("filename", src.getFilename());
        
        return jsonObject;
	}

}
