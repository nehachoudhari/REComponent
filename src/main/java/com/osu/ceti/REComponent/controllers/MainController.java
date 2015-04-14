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
		String p = getPersuasionMessage(userMsg, tags);
		
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
		
		SuccessStory s = getSuccessStory(userMsg, tags);
		String storyString = getSuccessStoryText(s);
		
		map.put("message", new PersuasionMessage());
		map.put("storyText", storyString);
		return "index";
	}


	/**
	 * A private class for tags comparisons
	 * @author nhchdhr
	 *
	 */
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
	
	
	/**
	 * An api that the consuming systems can use to get success story add on messages for the email invitations
	 * @param userMsg : a personalized message that the sender of invitation has added for the receiver 
	 * @param tags : a list of tags that of all the activities in the quest for which the invitation is being sent
	 * @return an object of type PersuasionMessage or SuccessStory
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public String getPersuasionMessage(String userMsg, String tags) throws InvalidFormatException, IOException {
		
		//tokenize the tags into an array list
		ArrayList<String> list = StringHelper.tokenize(tags);

		// we will use this only if there are no tags
		ArrayList<String> listTokens = StringHelper.tokenize(userMsg);

		if (!StringHelper.isValid(tags)) {
			list = listTokens; // use tokens in user message if the tags are empty
		}

		// stem the list of tags for better matching
		list = StringHelper.stemTags(list);

		// maintain the max length of intersection
		int lengthOfIntersection = 0;

		// maintain a list of messages with number of matching tags
		ArrayList<PersuasionMessage> matchingMessages = new ArrayList<PersuasionMessage>();

		// get a list of all persuasion messages from database
		List<PersuasionMessage> messageList = pmService.getAllPersuasionMessages();

		// iterate over each story and calculate matching score
		for (PersuasionMessage p : messageList) {

			// tokenize and stem the tags for the persuasion message p
			ArrayList<String> msgTags = StringHelper.tokenize(p.getTags());
			msgTags = StringHelper.stemTags(msgTags);

			// calculate the matching number of tags
			Set<String> intersection = new HashSet<String>(list);
			intersection.retainAll(msgTags);

			if (lengthOfIntersection == intersection.size()) {
				// if same number of matching tags add to list of matching messages
				matchingMessages.add(p);

			} else if (lengthOfIntersection < intersection.size()) {
				// otherwise if the length is more, update the max length of intersection
				lengthOfIntersection = intersection.size();

				// and clear the list of matching stories
				matchingMessages = new ArrayList<PersuasionMessage>();

				// add this message to the list of matching messages
				matchingMessages.add(p);
			}
		}

		PersuasionMessage result = null;
		if (matchingMessages.size() > 0) {
			// get a random from matching messages with same matching score
			int r = (int) (Math.random() * (matchingMessages.size() - 1));
			result = matchingMessages.get(r);
		} else {
			// get a random persuasion message from all the messages in database
			int r = (int) (Math.random() * (messageList.size() - 1));
			result = messageList.get(r);
		}

		return result.getText();
		
		/*HashSet<String> tagsSet = new HashSet<String>(tags);
		
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
		*/
		}
	
	
	/**
	 * This method takes tags of the activities in quest invitation
	 * and a message from user as input and 
	 * calculates a success story that is most suitable as an add on message to 
	 * the current quest invitation 
	 * 
	 * @param userMsg : String, a personalized message that the sender of invitation has added for the receiver 
	 * @param tagString : String, a comma separated list of tags of all the activities in the quest for which the invitation is being sent
	 * @return a success story with maximum matching
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public SuccessStory getSuccessStory(String userMsg, String tagString) throws InvalidFormatException, IOException{
		
		//tokenize the tags into an array list
		ArrayList<String> list = StringHelper.tokenize(tagString);
		
		//we will use this only if there are no tags
		ArrayList<String> listTokens = StringHelper.tokenize(userMsg);
		
		if(!StringHelper.isValid(tagString)) {
			list = listTokens; //use tokens in user message if the tags are empty
		}
		
		//stem the list of tags for better matching
		list = StringHelper.stemTags(list); 
		
		
		//maintain the max length of intersection
		int lengthOfIntersection = 0;
		
		//maintain a list of stories with number of matching tags
		ArrayList<SuccessStory> matchingStories = new ArrayList<SuccessStory>();
		
		//get a list of all stories from database
		List<SuccessStory> storyList = ssService.getAllSuccessStories();
		
		//iterate over each story and calculate matching score
		for(SuccessStory s: storyList) {

			//tokenize and stem the tags for the story
			ArrayList<String> storyTags = StringHelper.tokenize(s.getTags());			
			storyTags = StringHelper.stemTags(storyTags);
			
			//calculate the matching number of tags
			Set<String> intersection = new HashSet<String>(list);
			intersection.retainAll(storyTags);
			
		
			if(lengthOfIntersection==intersection.size()) {
				//if same number of matching tags add to list of matching stories
				matchingStories.add(s); 
				
			} else if(lengthOfIntersection < intersection.size()) {
				//otherwise if the length is more, update the max length of intersection
				lengthOfIntersection = intersection.size(); 
				
				//and clear the list of matching stories
				matchingStories = new ArrayList<SuccessStory>(); 
				
				//add this story to the list of matching stories
				matchingStories.add(s); 
			}
		}
		
		SuccessStory result = null;
		if(matchingStories.size()>0) {
			//get a random from matching stories with same matching score
			int r = (int) (Math.random() * (matchingStories.size()-1)); 
			result = matchingStories.get(r);
		} else {
			//get a random success story from all the stories in database
			int r = (int) (Math.random() * (storyList.size()-1));
			result = storyList.get(r);
		}
		
		return result;
	}
	
	/**
	 * This method takes a success story object and returns
	 * a string with a suitable display-able html text of the success story 
	 * @param s : SuccessStory
	 * @return string
	 */
	public String getSuccessStoryText(SuccessStory s) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ");
		sb.append(s.getUsername());
		sb.append("<br />");
		sb.append("Age: ");
		sb.append(s.getAge() + " years");
		sb.append("<br />");
		sb.append("Weight Before: ");
		sb.append(s.getWeightbefore() + " lbs");
		sb.append("<br />");
		sb.append("Weight After: ");
		sb.append(s.getWeightafter() + " lbs");
		sb.append("<br />");
		sb.append("How she did it: ");
		sb.append(s.getWork());
		
		return sb.toString();
	}
	
	
}
