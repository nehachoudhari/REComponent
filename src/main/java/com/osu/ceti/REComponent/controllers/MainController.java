package com.osu.ceti.REComponent.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import opennlp.tools.util.InvalidFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.osu.ceti.REComponent.helpers.MatchHelper;
import com.osu.ceti.REComponent.model.PersuasionMessage;
import com.osu.ceti.REComponent.model.SuccessStory;
import com.osu.ceti.REComponent.services.PersuasionMessageService;
import com.osu.ceti.REComponent.services.SuccessStoryService;

/**
 * @author nhchdhr
 *
 */
@Controller
public class MainController {

	@Autowired 
	private PersuasionMessageService pmService;
	@Autowired 
	private SuccessStoryService ssService;
	
	
	//main landing page
	@RequestMapping(value = "/index")
	public String landingPage(Map<String, Object> map){
			PersuasionMessage message = new PersuasionMessage();
			map.put("message", message);
			map.put("messageList", pmService.getAllPersuasionMessages());
			map.put("story", new SuccessStory());
			map.put("storyList", ssService.getAllSuccessStories());
		return "index";
	}
	
	//landing page for managing persuasion message repository
	@RequestMapping("/message")
	public String setupForm(Map<String, Object> map){
		PersuasionMessage message = new PersuasionMessage();
		map.put("message", message);
		map.put("messageList", pmService.getAllPersuasionMessages());
		return "message";
	}
	
	//controller for CRUD operations on the PersuasionMessage table
	@RequestMapping(value="/message.do", method=RequestMethod.POST)
	public String doActions(@ModelAttribute  PersuasionMessage message, BindingResult result,
			@RequestParam String action, Map<String, Object> map){
		PersuasionMessage messageResult = new PersuasionMessage();
		switch(action.toLowerCase()){
		case "add":
			pmService.add(message);
			messageResult = message;
			break;
		case "edit":
			pmService.edit(message);
			messageResult = message;
			break;
		case "delete":
			pmService.delete(message.getMsgId());
			messageResult = new PersuasionMessage();
			break;
		case "search":
			PersuasionMessage searhcedMessage = pmService.getPersuasionMessage(message.getMsgId());
			messageResult = searhcedMessage!=null ? searhcedMessage : new PersuasionMessage();
			break;
		}
		map.put("message", messageResult);
		map.put("messageList", pmService.getAllPersuasionMessages());
		return "message";
	}
	
	
	//redirect to managing the persuasion message repository
	@RequestMapping(value = "/redirect2", method = RequestMethod.GET)
	   public String redirect2() {
	     
	      return "redirect:message";
	   }
	   
	//redirect to managing the success stories repository
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	   public String redirect() {
	     
	      return "redirect:story";
	   }
	
	
	//landing page for managing success stories repository
	@RequestMapping("/story")
	public String setupForm2(Map<String, Object> map){
		SuccessStory story = new SuccessStory();
		map.put("story", story);
		map.put("storyList", ssService.getAllSuccessStories());
		return "story";
	}
	
	
	//controller for CRUD operations on the SuccessStory table
	@RequestMapping(value="/story.do", method=RequestMethod.POST)
	public String doActions2(@ModelAttribute SuccessStory story, BindingResult result,
			@RequestParam String action, Map<String, Object> map) throws InvalidFormatException, IOException{
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
	
	
	
	/**
	 * An api that the consuming systems can use to get Persuasion add on messages for the email invitations
	 * the actual method called is 'getPersuasionMessage'
	 * @param message
	 * @param result
	 * @param action
	 * @param map
	 * @return Add-on persuasion message
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	@RequestMapping(value="/getMessage", method=RequestMethod.POST)
	public String getMessage(@ModelAttribute PersuasionMessage message, BindingResult result,
			@RequestParam String action, Map<String, Object> map) throws InvalidFormatException, IOException{
	
		String userMsg = message.getText();
		String tags = message.getTags();
		
		// get a list of all persuasion messages from database
		List<PersuasionMessage> messageList = pmService.getAllPersuasionMessages();
		
		String p = MatchHelper.getPersuasionMessage(userMsg, tags, messageList);
		
		map.put("message", new PersuasionMessage());
		map.put("message2", p);
		return "index"; //display message on the same page for testing
	}
	
	
	/**
	 * An api that the consuming systems can use to get success stories add on messages for the email invitations
	 * the actual method called is 'getSuccessStory'
	 * 
	 * @param message
	 * @param result
	 * @param action
	 * @param map
	 * @return an html text of success story
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@RequestMapping(value="/getStory", method=RequestMethod.POST)
	public String getStory(@ModelAttribute PersuasionMessage message, BindingResult result,
			@RequestParam String action, Map<String, Object> map) throws InvalidFormatException, IOException{
	
		String userMsg = message.getText();
		String tags = message.getTags();
		
		//get a list of all stories from database
		List<SuccessStory> storyList = ssService.getAllSuccessStories();
		
		SuccessStory s = MatchHelper.getSuccessStory(userMsg, tags, storyList);
		String storyString = MatchHelper.getSuccessStoryText(s);
		
		map.put("message", new PersuasionMessage());
		map.put("storyText", storyString);
		return "index";
	}

}
