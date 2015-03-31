package com.osu.ceti.REComponent.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osu.ceti.REComponent.dao.SuccessStoryDao;
import com.osu.ceti.REComponent.model.SuccessStory;
import com.osu.ceti.REComponent.services.SuccessStoryService;

@Service
public class SuccessStoryServiceImpl implements SuccessStoryService {

	@Autowired
	private SuccessStoryDao ssDao;
	
	@Transactional
	public void add(SuccessStory s) {
		ssDao.add(s);
	}

	@Transactional
	public void edit(SuccessStory s) {
		ssDao.edit(s);
	}

	@Transactional
	public void delete(int storyId) {
		ssDao.delete(storyId);
	}

	@Transactional
	public SuccessStory getSuccesStory(int msgId) {
		return ssDao.getSuccesStory(msgId);
	}

	@Transactional
	public List<SuccessStory> getAllSuccessStories() {
		return ssDao.getAllSuccessStories();
	}

}
