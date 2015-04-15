package com.osu.ceti.REComponent.controllers;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.osu.ceti.REComponent.services.RecommenderService;


public class RecommenderController {

	public static void main(String[] args) throws Exception {
		
		//specify the file name and path here
		String transactionFile = "/Users/nhchdhr/Documents/workspace/REComponent/transactions.csv";
		
		//specify the number of neighbors to be considered
		int noOfNeighbours = 2;
		
		//specify the id of user for which the recommendations are to be generated
		int userId = 3;
		
		//specify the number of recommendations to be generated
		int noOfRecommendations = 1;
		
		File file = new File(transactionFile);
	    
	    List<RecommendedItem> recommendations = RecommenderService.getRecommendations(file, 
	    		noOfNeighbours, userId, noOfRecommendations);
	       
	    for (RecommendedItem recommendation : recommendations) {
	      System.out.println(recommendation);
	    }
	}
	
}
