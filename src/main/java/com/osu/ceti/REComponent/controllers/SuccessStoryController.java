package com.osu.ceti.REComponent.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.osu.ceti.REComponent.model.SuccessStory;
import com.osu.ceti.REComponent.services.SuccessStoryService;

public class SuccessStoryController {
	@Autowired 
	private SuccessStoryService ssService;
	
	@RequestMapping("/story")
	public String setupForm(Map<String, Object> map){
		SuccessStory story = new SuccessStory();
		map.put("story", story);
		map.put("storyList", ssService.getAllSuccessStories());
		return "story";
	}
	
	@RequestMapping(value="/story.do", method=RequestMethod.POST)
	public String doActions(@ModelAttribute SuccessStory story, BindingResult result,
			@RequestParam String action, Map<String, Object> map){
		SuccessStory storyResult = new SuccessStory();
		switch(action.toLowerCase()){	
		case "add":
			ssService.add(story);
			storyResult = story;
			break;
		case "edit":
			ssService.edit(story);
			storyResult = story;
			break;
		case "delete":
			ssService.delete(story.getId());
			storyResult = new SuccessStory();
			break;
		case "search":
			SuccessStory searchedStory = ssService.getSuccesStory(story.getId());
			storyResult = searchedStory!=null ? searchedStory : new SuccessStory();
			break;
		}
		map.put("story", storyResult);
		map.put("storyList", ssService.getAllSuccessStories());
		return "story";
	}
}
