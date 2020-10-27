package com.fruitforloops.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String author;
	@Column(name = "message_text")
	private String messageText;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "last_modified_date")
	private Date lastModifiedDate;
	
	public Message(){ this(null, null); }
	public Message(String author, String messageText) { this(-1, author, messageText); }
	public Message(long id, String author, String messageText) { this(id, author, messageText, new Date()); }
	public Message(String author, String messageText, Date createdDate) { this(-1, author, messageText, createdDate); }
	public Message(long id, String author, String messageText, Date createdDate) { this(id, author, messageText, createdDate, createdDate); }
	public Message(long id, String author, String messageText, Date createdDate, Date lastModifiedDate)
	{
		this.id = id;
		this.setAuthor(author);
		this.setMessageText(messageText);
		this.setCreatedDate(createdDate);
		this.setLastModifiedDate(lastModifiedDate);
	}

	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
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
	
	@Override
	public String toString()
	{
		return "PostMessage [id=" + id + ", author=" + author + ", messageText=" + messageText + ", createdDate=" 
				+ createdDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
}
