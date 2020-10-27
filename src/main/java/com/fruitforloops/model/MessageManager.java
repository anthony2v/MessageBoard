package com.fruitforloops.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MessageManager
{
	private TreeMap<Date, Message> messageList;

	public MessageManager()
	{
		messageList = new TreeMap<Date, Message>();
	}

	// GetMessage
	public ArrayList<Message> listMessages(Date fromDate, Date toDate)
	{
		fromDate = fromDate == null ? new Date(0) : fromDate;
		toDate = toDate == null ? new Date(Long.MAX_VALUE) : toDate;
		
		ArrayList<Message> messageRangeToReturn = new ArrayList<Message>();
		if (fromDate.compareTo(toDate) <= 0)
		{
			SortedMap<Date, Message> messageRange = messageList.subMap(fromDate, toDate);
			
			for (Message message : messageRange.values())
				messageRangeToReturn.add(message);
		}
		
		return messageRangeToReturn;
	}

	// PostMessage
	public void postMessage(String user, String messageText)
	{
		Date currentDate = new Date();
		messageList.put(currentDate, new Message(user, messageText, currentDate));
	}

	// DeleteMessage
	public void clearChat(Date fromDate, Date toDate)
	{
		fromDate = fromDate == null ? new Date(0) : fromDate;
		toDate = toDate == null ? new Date(Long.MAX_VALUE) : toDate;
		
		if (fromDate.compareTo(toDate) <= 0)
		{
			SortedMap<Date, Message> messageRange = messageList.subMap(fromDate, toDate);
			
			List<Date> keysToRemove = new ArrayList<Date>();
			for (Message message : messageRange.values())
				keysToRemove.add(message.getCreatedDate());
			
			for (Date d : keysToRemove)
				messageList.remove(d);
		}
	}
}

