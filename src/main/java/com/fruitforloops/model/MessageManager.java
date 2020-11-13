package com.fruitforloops.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import com.fruitforloops.model.dao.*;

public class MessageManager
{
	MessageDAO mdao = new MessageDAO();
	public List<Message> getMessages(Date fromDate, Date toDate)
	{
		ArrayList<Message> messageList = new ArrayList<Message>();
		
		// populate messageList with most recent messages
		
		return messageList;
	}

	public void createMessage(String user, String messageText)
	{
		// create new message
		Message newMessage = new Message(user, messageText);
		mdao.save(newMessage);
	}
}

