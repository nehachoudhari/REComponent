package com.osu.ceti.REComponent.services;

import java.util.List;

import com.osu.ceti.REComponent.model.PersuasionMessage;

public interface PersuasionMessageService {
	
	public void add(PersuasionMessage msg);
	public void edit(PersuasionMessage msg);
	public void delete(int msgId);
	public PersuasionMessage getPersuasionMessage(int msgId);
	public List<PersuasionMessage> getAllPersuasionMessages();

}
