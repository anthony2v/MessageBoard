package com.fruitforloops;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JSONUtil
{
	public static Gson gson;

	static
	{
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}

	private JSONUtil()
	{
		// do not instantiate
	}
}
