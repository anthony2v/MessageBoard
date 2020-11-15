package com.fruitforloops.model;

import com.fruitforloops.model.dao.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.security.auth.message.AuthException;

public class MessageManager
{
	MessageDAO mdao = null;
	MessageAttachmentDAO madao = null;
	
	public MessageManager() {
		mdao = new MessageDAO();
		madao = new MessageAttachmentDAO();
	}

	public void createMessage(Message newMessage)
	{
		// create new message
		mdao.save(newMessage);
	}

	public void deleteMessage(long messageId)
	{
		// delete message with corresponding ID
		mdao.delete(messageId);
	}

	public void deleteMessageAttachment(long attachmentId)
	{
		// deletes specified attachment
		madao.delete(attachmentId);
	}

	public MessageAttachment getMessageAttachment(long attachmentId)
	{
		// returns requested attachment
		return madao.get(attachmentId);
	}
	
	public ArrayList<Message> getMessages(Date fromDate, Date toDate, String[] authors, String[] hashtags)
	{
		List<String> authorsList = null;
		List<String> hashtagsList = null;
		if (authors != null) {
			authorsList = Arrays.asList(authors);
		}
		if (hashtags != null) {
			hashtagsList = Arrays.asList(hashtags);
		}
		
		Properties appConfig = new Properties();
		int limit = 10;
		try
		{
			appConfig.load(getClass().getClassLoader().getResourceAsStream("/WEB-INF/app.config.properties"));
			limit = Integer.valueOf(appConfig.getProperty("messages.pagination").trim());
		}
		catch (IOException e)
		{
			System.err.println("could not read app config. Using default of 10 instead.");
		}
		
		return (ArrayList<Message>)mdao.getMessages(fromDate, toDate, authorsList, hashtagsList, limit);
	}

	public void updateMessage(String currentUser, Message message, long[] filesToDelete) throws AuthException
	{
		if (!userOwnsMessage(currentUser, message.getId()))
			throw new AuthException("The current user did not write this message.");
		// update the desired message
		if (mdao.update(message))
			// delete series of message attachments
			for (long id: filesToDelete)
				madao.delete(id);
	}
	
	public boolean userOwnsMessage(String currentUser, Long messageId) {
		Message message = mdao.get(messageId);
		
		if (message != null && message.getAuthor().equals(currentUser))
			return true;
		return false;
	}
}

