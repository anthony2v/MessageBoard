package com.fruitforloops.model.beans;

import java.io.Serializable;
import java.util.Date;

public class PostMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String user;
	private String messageText;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public PostMessage()
	{
	}

	public PostMessage(String user, String messageText) { this(user, messageText, new Date()); }
	public PostMessage(String user, String messageText, Date createdDate) { this(user, messageText, createdDate, createdDate); }
	public PostMessage(String user, String messageText, Date createdDate, Date lastModifiedDate)
	{
		this.setUser(user);
		this.setMessageText(messageText);
		this.setCreatedDate(createdDate);
		this.setLastModifiedDate(lastModifiedDate);
	}

	public String getUser()
	{
		return user;
	}
	public void setUser(String user)
	{
		this.user = user;
	}

	public String getMessageText()
	{
		return messageText;
	}
	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate()
	{
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate)
	{
		this.lastModifiedDate = lastModifiedDate;
	}
}
