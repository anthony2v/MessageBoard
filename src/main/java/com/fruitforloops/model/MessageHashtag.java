package com.fruitforloops.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message_hashtag")
public class MessageHashtag {

	@Id
	@Column(name = "message_id")
	private Long messageId;
	
	@Column(name = "hashtag_id")
	private Long hashtagId;
	
	public MessageHashtag() {
	}

	public MessageHashtag(Long hashId) {
		hashtagId = hashId;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getHashtagId() {
		return hashtagId;
	}

	public void setHashtagId(Long hashtagId) {
		this.hashtagId = hashtagId;
	}
}