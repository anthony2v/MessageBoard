package com.fruitforloops.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String author;
	
	@Column(name = "message_text")
	private String messageText;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "last_modified_date")
	private Date lastModifiedDate;
	
	@OneToMany(mappedBy = "message", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private Set<MessageAttachment> attachments;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(name = "message_hashtag", joinColumns = @JoinColumn(name = "message_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	private Set<HashTag> hashtags;
	
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

	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
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
	
	public Set<MessageAttachment> getAttachments()
	{
		return attachments;
	}
	public void setAttachments(Set<MessageAttachment> attachments)
	{
		this.attachments = attachments;
	}
	
	public Set<HashTag> getHashtags()
	{
		return hashtags;
	}
	public void setHashtags(Set<HashTag> hashtags)
	{
		this.hashtags = hashtags;
	}
	
	@Override
	public String toString()
	{
		return "PostMessage [id=" + id + ", author=" + author + ", messageText=" + messageText + ", createdDate=" 
				+ createdDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
}
