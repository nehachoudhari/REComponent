package com.osu.ceti.REComponent.dao;

import java.util.List;

import com.osu.ceti.REComponent.model.SuccessStory;

public interface SuccessStoryDao {

	public void add(SuccessStory s);
	public void edit(SuccessStory s);
	public void delete(int storyId);
	public SuccessStory getSuccesStory(int msgId);
	public List<SuccessStory> getAllSuccessStories();
}
