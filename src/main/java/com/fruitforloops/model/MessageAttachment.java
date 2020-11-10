package com.fruitforloops.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message_attachment")
public class MessageAttachment implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String filename;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Message message;
	
	@Lob
	@Column(columnDefinition = "BLOB")
	private byte[] data;
	
	public MessageAttachment() {}
	
	public MessageAttachment(String filename, byte[] data)
	{
		this.filename = filename;
		this.data = data;
	}
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getFilename()
	{
		return filename;
	}
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	
	public Message getMessage()
	{
		return message;
	}
	public void setMessage(Message message)
	{
		this.message = message;
	}
	
	public byte[] getData()
	{
		return data;
	}
	public void setData(byte[] data)
	{
		this.data = data;
	}
}
