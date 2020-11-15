package com.fruitforloops.model;

import com.fruitforloops.model.dao.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.security.auth.message.AuthException;

public class MessageManager
{
	MessageDAO mdao = new MessageDAO();
	MessageAttachmentDAO madao = new MessageAttachmentDAO();

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
		ArrayList<Message> messageList = (ArrayList<Message>)mdao.getAll();
		if (authors == null || hashtags == null)
			return messageList;
			
		// populate messageList with message between fromDate and toDate, and includes the given authors and hashtags
		messageList.removeIf((message) -> {
			if (message.getCreatedDate().after(fromDate) && message.getCreatedDate().before(toDate))
				for (String author: authors)
					if (message.getAuthor().equals(author))
						for (String hashtag: hashtags)
							if (message.getHashtags().contains(hashtag))
								return false;
			return true;
		});

//		Properties appConfig = new Properties();
//		appConfig.load(getClass().getClassLoader().getResourceAsStream("/WEB-INF/app.config.properties"));
//		int limit = Integer.valueOf(appConfig.getProperty("messages.pagination").trim());
//		ArrayList<Message> messageList = mdao.getAll(limit);
		
		return messageList;
	}

	public void updateMessage(String currentUser, Message message, long[] filesToDelete) throws AuthException
	{
		if (!userOwnsMessage(currentUser, message))
			throw new AuthException("The current user did not write this message.");
		// update the desired message
		if (mdao.update(message))
			// delete series of message attachments
			for (long id: filesToDelete)
				madao.delete(id);
	}
	
	public boolean userOwnsMessage(String currentUser, Message message) {
		if (message.getAuthor().equals(currentUser))
			return true;
		return false;
	}
}

