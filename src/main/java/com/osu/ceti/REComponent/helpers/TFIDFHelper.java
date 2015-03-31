package com.osu.ceti.REComponent.helpers;

import java.util.ArrayList;
import java.util.List;

public class TFIDFHelper {
	
	 /**
     * A method to calculate the term frequency (TF) of a string token
     * @param tokens : array list of all the tokens after removing stop words
     * @param termToCheck : a current token of which TF is to be calculated.
     * @return term frequency of the current token
     */
    public double tfCalculator(ArrayList<String> tokens, String token) 
    {
        double count = 0;  
        for (String s : tokens) 
        {
            if (s.equalsIgnoreCase(token))
                count++;
        }
        return count / tokens.size();
    }
     
    /**
     * A method to calculate the inverse document frequency (IDF) of a string token
     * @param allTokens : all the tokens of all messages
     * @param token : a current token of which IDF value is to be calculated.
     * @return inverse document frequency value of the token
     */
    public double idfCalculator(ArrayList<ArrayList<String>> allTokens, String token) 
    {
        double count = 0;
        for (ArrayList<String> ss : allTokens)
        {
            for (String s : ss) 
            {
                if (s.equalsIgnoreCase(token))
                {
                    count++;
                    break;
                }
            }
        }
        return Math.log(allTokens.size() / count);
    }  
	
}
