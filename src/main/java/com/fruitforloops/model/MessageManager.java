package com.fruitforloops.model;

import com.fruitforloops.model.dao.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.security.auth.message.AuthException;

public class MessageManager
{
	MessageDAO mdao = null;
	MessageAttachmentDAO madao = null;

	public MessageManager()
	{
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
		if (authors != null)
		{
			authorsList = Arrays.asList(authors);
		}
		if (hashtags != null)
		{
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

		return (ArrayList<Message>) mdao.getMessages(fromDate, toDate, authorsList, hashtagsList, limit);
	}

	public void updateMessage(String currentUser, Message message, long[] filesToDelete) throws AuthException
	{
		if (!userOwnsMessage(currentUser, message.getId()))
			throw new AuthException("The current user did not write this message.");
		// update the desired message
		if (mdao.update(message))
			// delete series of message attachments
			for (long id : filesToDelete)
			madao.delete(id);
	}

	public boolean userOwnsMessage(String currentUser, Long messageId)
	{
		Message message = mdao.get(messageId);

		if (message != null && message.getAuthor().equals(currentUser)) return true;

		return false;
	}

	public Boolean userCanView(Message message, User user)
	{
		boolean viewable = false;
		
		if (user != null && message != null)
		{
			UserGroupDAO ugDAO = new UserGroupDAO();
			List<UserGroup> temp = ugDAO.getAll();
			Map<Long, UserGroup> groupsMap = new HashMap<Long, UserGroup>();
			for (UserGroup g : temp)
				groupsMap.putIfAbsent(g.getId(), g);
			
			UserGroup[] groups = user.getGroups();
			if (groups == null) groups = new UserGroup[0];
			
			for (UserGroup g : groups)
			{
				if (g.getName().equals("admin") || g.getId() == message.getGroupId() || message.getGroupId() == -1)
				{
					viewable = true;
					break;
				}
				else
				{
					Long itr = g.getId();
					while (groupsMap.get(itr) != null)
					{
						if (groupsMap.get(itr).getId() == message.getGroupId())
						{
							viewable = true;
							break;
						}
						
						itr = groupsMap.get(itr).getParentId();
					}
					
					if (viewable) break;
				}
			}
		}
		
		return viewable;
	}

	public boolean userCanView(Long messageId, User user)
	{
		Message message = mdao.get(messageId);
		if (message == null)
			return false;
		else
			return userCanView(message, user);
	}

	public Boolean userCanEdit(Message message, User user)
	{
		boolean editable = false;
		
		if (user != null && message != null)
		{
			UserGroupDAO ugDAO = new UserGroupDAO();
			List<UserGroup> temp = ugDAO.getAll();
			Map<Long, UserGroup> groupsMap = new HashMap<Long, UserGroup>();
			for (UserGroup g : temp)
				groupsMap.putIfAbsent(g.getId(), g);
			
			UserGroup[] groups = user.getGroups();
			if (groups == null) groups = new UserGroup[0];
			
			for (UserGroup g : groups)
			{
				if (g.getName().equals("admin") || user.getUsername().equals(message.getAuthor()))
				{
					editable = true;
					break;
				}
			}
		}
		
		return editable;
	}
	
	public Boolean userCanEdit(Long messageId, User user)
	{
		Message message = mdao.get(messageId);
		if (message == null)
			return false;
		else
			return userCanEdit(message, user);
	}
}
