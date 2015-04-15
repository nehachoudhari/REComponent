package com.osu.ceti.REComponent.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import opennlp.tools.util.InvalidFormatException;

import com.osu.ceti.REComponent.model.PersuasionMessage;
import com.osu.ceti.REComponent.model.SuccessStory;

/**
 * This class will contain helper methods to match the array of tags 
 * with the tags of messages or success stories and return the match index/coefficient 
 * @author nhchdhr
 *
 */
public class MatchHelper {
	

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
	public static SuccessStory getSuccessStory(String userMsg, String tagString, List<SuccessStory> storyList) throws InvalidFormatException, IOException{
		
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
	public static String getSuccessStoryText(SuccessStory s) {
		
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
	
	
	/**
	 * An api that the consuming systems can use to get success story add on messages for the email invitations
	 * @param userMsg : a personalized message that the sender of invitation has added for the receiver 
	 * @param tags : a list of tags that of all the activities in the quest for which the invitation is being sent
	 * @return an object of type PersuasionMessage or SuccessStory
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public static String getPersuasionMessage(String userMsg, String tags, List<PersuasionMessage> messageList) throws InvalidFormatException, IOException {
		
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
		
		
		}
}
