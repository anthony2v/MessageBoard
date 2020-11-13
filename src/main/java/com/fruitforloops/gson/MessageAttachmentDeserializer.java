package com.fruitforloops.gson;

import com.fruitforloops.model.Message;
import com.fruitforloops.model.MessageAttachment;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class MessageAttachmentDeserializer implements JsonDeserializer<MessageAttachment>
{

	@Override
	public MessageAttachment deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		MessageAttachment attachment = new MessageAttachment();
		
		JsonElement id = json.getAsJsonObject().get("id");
		attachment.setId(id == null ? -1 : id.getAsLong());
		
		JsonElement filename = json.getAsJsonObject().get("filename");
		attachment.setFilename(filename == null ? null : filename.getAsString());
		
		JsonElement messageId = json.getAsJsonObject().get("messageId");
		attachment.setMessage(new Message());
		attachment.getMessage().setId(messageId == null ? -1 : messageId.getAsLong());
		
        return attachment;
	}
	
}
