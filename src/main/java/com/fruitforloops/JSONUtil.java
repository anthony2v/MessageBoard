package com.fruitforloops;

import com.fruitforloops.gson.HashTagDeserializer;
import com.fruitforloops.gson.HashTagSerializer;
import com.fruitforloops.gson.MessageAttachmentDeserializer;
import com.fruitforloops.gson.MessageAttachmentSerializer;
import com.fruitforloops.model.HashTag;
import com.fruitforloops.model.MessageAttachment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JSONUtil
{
	public static Gson gson;

	static
	{
		gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.registerTypeAdapter(MessageAttachment.class, new MessageAttachmentSerializer())
			    .registerTypeAdapter(MessageAttachment.class, new MessageAttachmentDeserializer())
			    .registerTypeAdapter(HashTag.class, new HashTagSerializer())
			    .registerTypeAdapter(HashTag.class, new HashTagDeserializer())
				.create();
	}

	private JSONUtil()
	{
		// do not instantiate
	}
}
