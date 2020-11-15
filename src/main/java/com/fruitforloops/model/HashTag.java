package com.fruitforloops.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="hashtag")
public class HashTag
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String tag;
	
	@ManyToMany(mappedBy = "hashtags", fetch = FetchType.EAGER)
	private Set<Message> messages;
	
	//default constructor
	public HashTag() {
	}
	
	//single parameter constructor
	public HashTag(String tag) {
		this.tag = tag;
	}
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTag()
	{
		return tag;
	}
	public void setTag(String tag)
	{
		this.tag = tag;
	}
	
	public Set<Message> getMessages()
	{
		return messages;
	}
	public void setMessages(Set<Message> messages)
	{
		this.messages = messages;
	}
	
	@Override
	public String toString()
	{
		return "HashTag [id=" + id + ", tag=" + tag + "]";
	}
}