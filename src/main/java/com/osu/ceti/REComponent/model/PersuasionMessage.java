package com.osu.ceti.REComponent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PersuasionMessage {

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int msgId;
	
	@Column
	private String text;
	
	@Column
	private String tags;
	
	public PersuasionMessage() {
		
	}
	
	public PersuasionMessage(int msgId, String text, String tags) {
		super();
		this.msgId = msgId;
		this.text = text;
		this.tags = tags;
	}
	
	
	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
}
