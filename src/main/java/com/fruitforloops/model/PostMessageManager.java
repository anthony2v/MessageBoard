package com.fruitforloops.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.fruitforloops.model.beans.PostMessage;

public class PostMessageManager
{
	private TreeMap<Date, PostMessage> messageList;

	public PostMessageManager()
	{
		messageList = new TreeMap<Date, PostMessage>();
	}

	// GetMessage
	public ArrayList<PostMessage> listMessages(Date fromDate, Date toDate)
	{
		fromDate = fromDate == null ? new Date(0) : fromDate;
		toDate = toDate == null ? new Date(Long.MAX_VALUE) : toDate;
		
		ArrayList<PostMessage> messageRangeToReturn = new ArrayList<PostMessage>();
		if (fromDate.compareTo(toDate) <= 0)
		{
			SortedMap<Date, PostMessage> messageRange = messageList.subMap(fromDate, toDate);
			
			for (PostMessage message : messageRange.values())
				messageRangeToReturn.add(message);
		}
		
		return messageRangeToReturn;
	}

	// PostMessage
	public void postMessage(String user, String messageText)
	{
		Date currentDate = new Date();
		messageList.put(currentDate, new PostMessage(user, messageText, currentDate));
	}

	// DeleteMessage
	public void clearChat(Date fromDate, Date toDate)
	{
		fromDate = fromDate == null ? new Date(0) : fromDate;
		toDate = toDate == null ? new Date(Long.MAX_VALUE) : toDate;
		
		if (fromDate.compareTo(toDate) <= 0)
		{
			SortedMap<Date, PostMessage> messageRange = messageList.subMap(fromDate, toDate);
			
			List<Date> keysToRemove = new ArrayList<Date>();
			for (PostMessage message : messageRange.values())
				keysToRemove.add(message.getCreatedDate());
			
			for (Date d : keysToRemove)
				messageList.remove(d);
		}
	}
}

