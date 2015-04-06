package com.osu.ceti.REComponent.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import opennlp.tools.util.InvalidFormatException;
import antlr.StringUtils;

import com.osu.ceti.REComponent.helpers.StringHelper;
import com.osu.ceti.REComponent.model.PersuasionMessage;
import com.osu.ceti.REComponent.model.SuccessStory;
import com.osu.ceti.REComponent.services.PersuasionMessageService;
import com.osu.ceti.REComponent.services.SuccessStoryService;

@Controller
public class MainController {

	@Autowired 
	private PersuasionMessageService pmService;
	@Autowired 
	private SuccessStoryService ssService;
	
	@RequestMapping("/message")
	public String setupForm(Map<String, Object> map){
		PersuasionMessage message = new PersuasionMessage();
		map.put("message", message);
		map.put("messageList", pmService.getAllPersuasionMessages());
		return "message";
	}
	
	@RequestMapping(value="/message.do", method=RequestMethod.POST)
	public String doActions(@ModelAttribute  PersuasionMessage message, BindingResult result,
			@RequestParam String action, Map<String, Object> map){
		PersuasionMessage messageResult = new PersuasionMessage();
		switch(action.toLowerCase()){	//only in Java7 you can put String in switch
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
	
	
//	@RequestMapping(value="/redirect", method=RequestMethod.POST)
//	public String redirect(@ModelAttribute  PersuasionMessage message, BindingResult result,
//			@RequestParam String action, Map<String, Object> map){
//		
//		String returnS = "";
//		switch(action.toLowerCase()){	//only in Java7 you can put String in switch
//		case "redirect":
//			returnS = "story";
//			break;
//		}
//		return returnS;
//	}
	
	@RequestMapping(value = "/index")
	   public String landingPage(Map<String, Object> map){
			PersuasionMessage message = new PersuasionMessage();
			map.put("message", message);
			map.put("messageList", pmService.getAllPersuasionMessages());
		return "index";
	}
	
//	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
//	   public String redirect(@ModelAttribute String input, BindingResult result, @RequestParam String action) {
//		String returnS="story";
//		switch(action.toLowerCase()){	
//		case "story":
//			returnS= "redirect:story";
//			
//		case "message":
//			returnS= "redirect:message";
//		
//		}
//	    return returnS;  
//	   }
	
	@RequestMapping(value = "/redirect2", method = RequestMethod.GET)
	   public String redirect2() {
	     
	      return "redirect:message";
	   }
	   
	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	   public String redirect() {
	     
	      return "redirect:story";
	   }
	
	@RequestMapping("/story")
	public String setupForm2(Map<String, Object> map){
		SuccessStory story = new SuccessStory();
		map.put("story", story);
		map.put("storyList", ssService.getAllSuccessStories());
		return "story";
	}
	
	@RequestMapping(value="/story.do", method=RequestMethod.POST)
	public String doActions2(@ModelAttribute SuccessStory story, BindingResult result,
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
		
		ArrayList<String> lis = new ArrayList<String>();
		lis.add("walk");
		lis.add("miles");
		
		getPersuasionMessage("I am going to work out",lis);
		
		return "story";
	}
	
	
	@RequestMapping(value="/getMessage", method=RequestMethod.POST)
	public String getMessage(@ModelAttribute PersuasionMessage message, BindingResult result,
			@RequestParam String action, Map<String, Object> map){
	
		String userMsg = message.getText();
		String tags = message.getTags();
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(tags.split(",")));
		String p = getPersuasionMessage(userMsg, list);
		
		map.put("message", new PersuasionMessage());
		map.put("message2", p);
		return "index";
	}
	
	

//	
//	
//	/**
//	 * An api that the consuming systems can use to get add on messages for the email invitations
//	 * @param userMsg : a personalized message that the sender of invitation has added for the receiver 
//	 * @param tags : a list of tags that of all the activities in the quest for which the invitation is being sent
//	 * @return an object of type PersuasionMessage or SuccessStory
//	 */
//	public Object getAddOnMessage(String userMsg, ArrayList<String> tags) {
//		return null;
//	}
//	
//
	private class TagComparison implements Comparator<TagComparison>, Comparable<TagComparison>{

		public String[] tags;
		public HashSet<String> set;
		public HashSet<String> givenSet;
		
		public TagComparison(String[] tags, HashSet<String> givenSet){
			this.tags = tags;
			set = new HashSet<String>(Arrays.asList(tags));
			this.givenSet = givenSet;
		}
		
		public int comparisionSize(){
			
			Set<String> intersection = new HashSet<String>(set);
			intersection.retainAll(givenSet);
			return intersection.size();
			
		}
		
		//Note need to change the return values here to reflect a max priority queue
		
		@Override
		public int compare(TagComparison o1, TagComparison o2) {
			// TODO Auto-generated method stub
			if(o1.comparisionSize()<o2.comparisionSize()){
				return 1;
			}
			else if(o1.comparisionSize()>o2.comparisionSize()){
				return -1;
			}
			else{
				return 0;
			}
		}

		@Override
		public int compareTo(TagComparison o) {
			// TODO Auto-generated method stub
			
			if(this.comparisionSize()<o.comparisionSize())
				return 1;
			else if(this.comparisionSize()>o.comparisionSize())
				return -1;
			else
			return 0;
		}
		
	}
	
	public String getPersuasionMessage(String userMsg, ArrayList<String> tags) {
		
		HashSet<String> tagsSet = new HashSet<String>(tags);
		
		HashMap<String,String> msgMap = new HashMap<String,String>();
		
		List<PersuasionMessage> msgList;
		
		msgList = pmService.getAllPersuasionMessages();
	
		ArrayList<ArrayList<String>> tagStringList = new ArrayList<ArrayList<String>>();
		
		for(PersuasionMessage msg:msgList){
			
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(msg.getTags().split(",")));
			
			ArrayList<String> list2 = new ArrayList<String>();
			
			for(String item:list){
				item = StringHelper.cleanString(item).replaceAll("\\s+", "");
				list2.add(item);
			}
			
			//list2 = StringHelper.stemTags(list2);
				
			tagStringList.add(list2);
			
			msgMap.put(msg.getTags(), msg.getText());
		}
		
		//now find most similar tag list, convert it to a string and use msgMap to get the message
		
		//store in the max priority queue, check if this queue is indeed max priority queue 
		
		PriorityQueue<TagComparison> queue = new PriorityQueue<TagComparison>();
		
		for(ArrayList<String> tagsList:tagStringList){
			queue.add(new TagComparison(tagsList.toArray(new String[tagsList.size()]),tagsSet));
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(String item: queue.peek().tags){
			sb.append(item);
			sb.append(", ");
		}
		
		sb.setLength(sb.length()-2);
		
		return msgMap.get(new String(sb));
		
		////return the above message
		
		
		
		
		/*
		 * Set<Type> union = new HashSet<Type>(s1);
		union.addAll(s2);

Set<Type> intersection = new HashSet<Type>(s1);
intersection.retainAll(s2);

Set<Type> difference = new HashSet<Type>(s1);
difference.removeAll(s2);
		 */
		
		/*
		 * 
		 * check if user msg empty
		 * if not do msg matching add to array list 1
		 * do tag matching add to array list 2
		 * take intersection 
		 * if empty take union 
		 * select random msg from union if not empty
		 * if empty take random msg from db
		 */
	
	}
//	
//	public SuccessStory getSuccessStory(String userMsg, ArrayList<String> tags){
//		return null;
//	}
//	
//	public String getSuccessStoryText(SuccessStory s) {
//		return null;
//	}
//	
//	
}
