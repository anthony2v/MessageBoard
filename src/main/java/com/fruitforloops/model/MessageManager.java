package com.fruitforloops.model;

import java.util.ArrayList;
import java.util.Date;
import com.fruitforloops.model.dao.*;

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
		// populate messageList with message between fromDate and toDate, and includes the given authors and hashtags
		ArrayList<Message> messageList = (ArrayList<Message>)mdao.getAll();
		messageList.removeIf((message) -> {
			if (message.getCreatedDate().after(fromDate) && message.getCreatedDate().before(toDate))
				for (String author: authors)
					if (message.getAuthor().equals(author))
						for (String hashtag: hashtags)
							if (message.getHashtags().contains(hashtag))
								return false;
			return true;
		});
		
		return messageList;
	}
	
	public void updateMessage(Message message, long[] filesToDelete)
	{
		// update the desired message
		mdao.update(message);
		
		// delete series of message attachments
		for (long id: filesToDelete)
			madao.delete(id);
	}
	
}

