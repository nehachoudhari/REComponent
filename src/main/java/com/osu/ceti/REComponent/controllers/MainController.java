package com.osu.ceti.REComponent.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import opennlp.tools.util.InvalidFormatException;

import com.mysql.jdbc.NotImplemented;
import com.osu.ceti.REComponent.helpers.StringHelper;
import com.osu.ceti.REComponent.model.PersuasionMessage;
import com.osu.ceti.REComponent.model.SuccessStory;
import com.osu.ceti.REComponent.services.PersuasionMessageService;

@Controller
@RequestMapping("/recomponent")
public class MainController {

	@Autowired 
	private static PersuasionMessageService pmService;
	
	
	public static void main(String[] args) throws InvalidFormatException, IOException {
//		String s = "This is a pleasant day"; 
//		ArrayList<String> tokens = StringHelper.tokenize(s);
//		for(String s2 : tokens) {
//			System.out.println(s2);
//		}
		
		PersuasionMessage msg = new PersuasionMessage();
		msg.setTags("running, fitness");
		msg.setText("a test msg to check db connectivity");
		
		pmService.add(msg);
		
		System.out.println("Done");
		
	}
	
	
	
	/**
	 * An api that the consuming systems can use to get add on messages for the email invitations
	 * @param userMsg : a personalized message that the sender of invitation has added for the receiver 
	 * @param tags : a list of tags that of all the activities in the quest for which the invitation is being sent
	 * @return an object of type PersuasionMessage or SuccessStory
	 */
	public Object getAddOnMessage(String userMsg, ArrayList<String> tags) {
		return null;
	}
	
	
	public PersuasionMessage getPersuasionMessage(String userMsg, ArrayList<String> tags) {
		ArrayList<String> al1;
		ArrayList<String> al2;
		
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
		return null;
	}
	
	public SuccessStory getSuccessStory(String userMsg, ArrayList<String> tags){
		return null;
	}
	
	public String getSuccessStoryText(SuccessStory s) {
		return null;
	}
}
