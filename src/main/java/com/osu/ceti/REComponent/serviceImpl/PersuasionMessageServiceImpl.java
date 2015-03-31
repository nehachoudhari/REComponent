package com.osu.ceti.REComponent.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osu.ceti.REComponent.dao.PersuasionMessageDao;
import com.osu.ceti.REComponent.model.PersuasionMessage;
import com.osu.ceti.REComponent.services.PersuasionMessageService;

@Service
public class PersuasionMessageServiceImpl implements PersuasionMessageService {

	@Autowired
	private PersuasionMessageDao pmDao;
	
	
	@Transactional
	public void add(PersuasionMessage msg) {
		pmDao.add(msg);
	}

	@Transactional
	public void edit(PersuasionMessage msg) {
		pmDao.edit(msg);

	}

	@Transactional
	public void delete(int msgId) {
		pmDao.delete(msgId);

	}

	@Transactional
	public PersuasionMessage getPersuasionMessage(int msgId) {
		return pmDao.getPersuasionMessage(msgId);
	}

	@Transactional
	public List<PersuasionMessage> getAllPersuasionMessages() {
		return pmDao.getAllPersuasionMessages();
	}

}
