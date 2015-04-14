package com.osu.ceti.REComponent.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import snowballstemmer.PorterStemmer;


/**
 * A helper class for performing operations on Strings
 * Contains static methods for validating, tokenizing strings and removing stop words from strings
 * @author nhchdhr
 *
 */
public class StringHelper {
	
	private static HashSet<String> stopwords = new HashSet<String>();
	private static final String ENGLISH_STOP_WORDS[] = {
			"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", 
            "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", 
            "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", 
            "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", 
            "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt",
            "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else",
            "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", 
            "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", 
            "front", "full", "further", "get", "give", "go", "had", "has", "hasnt",
            "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", 
            "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", 
            "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", 
            "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", 
            "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", 
            "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", 
            "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps",
            "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she",
            "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", 
            "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", 
            "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", 
            "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", 
            "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", 
            "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
            "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", 
            "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet",
            "you", "your", "yours", "yourself", "yourselves","1","2","3","4","5","6","7","8","9","10","1.","2.","3.","4.","5.","6.","11",
            "7.","8.","9.","12","13","14","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "terms","CONDITIONS","conditions","values","interested.","care","sure",".","!","@","#","$","%","^","&","*","(",")","{","}","[","]",":",";",",","<",".",">","/","?","_","-","+","=",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
            "contact","grounds","buyers","tried","said,","plan","value","principle.","forces","sent:","is,","was","like",
            "discussion","tmus","diffrent.","layout","area.","thanks","thankyou","hello","bye","rise","fell","fall","psqft.","http://","km","miles"
			 };
	
	public StringHelper() {
		addToStopWords();
	}
	
	private static void addToStopWords(){
		for (int i=0; i< ENGLISH_STOP_WORDS.length;i++){
			stopwords.add(ENGLISH_STOP_WORDS[i]);
		}
	}

	/**
	 * A method to get a list of tags from a comma separated list of tags from database
	 * @param tags
	 * @return array of tags 
	 */
	public static String[] getTokens(String tags) {
		String[] strings = tags.split(",");
		return strings;
	}
	
	/**
	 * A method to remove unwanted characters from the string
	 * and to lower case the string content to treat upper and lower case as same 
	 * @param s
	 * @return a clean string
	 */
	public static String cleanString(String s){
		if(!isValid(s)) {
			return s;
		}
		s = s.trim();
		s = s.toLowerCase();
		return s;
	}
	
	
	/**
	 * Check if a string is non empty
	 * @param s
	 * @return true for non-empty string
	 */
	public static boolean isValid(String s) {
		if(s==null || s.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * A method to tokenize the string into words
	 * @param s
	 * @return array list of tokens excluding stop words
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static ArrayList<String> tokenize(String s) throws InvalidFormatException, IOException {
		//troubleshoot tip: this is absolute path, change this while running on other computers
		String filename = "/Users/nhchdhr/Documents/workspace/REComponent/src/main/resources/en-token.bin";
        File file = new File(filename);
        
		InputStream is = new FileInputStream(file);
	 
		TokenizerModel model = new TokenizerModel(is);
	 
		Tokenizer tokenizer = new TokenizerME(model);
	 
		String[] tokens = tokenizer.tokenize(cleanString(s));
		is.close();
		
		return removeStopWordsFromSentence(tokens);
	}
	
	/**
	 * A method to remove stop words from an array of words
	 * @param an array of string tokens
	 * @return array list of tokens excluding stop words
	 */
	public static ArrayList<String> removeStopWordsFromSentence(String[] tokens){
		addToStopWords();
		ArrayList<String> newTokens = new ArrayList<String>(Arrays.asList(tokens));
		ArrayList<String> finalTokens = new ArrayList<String>();
		for (String token: newTokens){
			if (!stopwords.contains(token)){
				finalTokens.add(token);
			}
		}
		return finalTokens;
	}
	
	/**
	 * A method to stem the words to their roots
	 * ie. to treat all form of a word as same
	 * for example running, ran, run all mean the root word 'run' in our context
	 * 
	 * @param tokens: an array list of strings
	 * @return an array list of stemmed strings
	 */
	public static ArrayList<String> stemTags(ArrayList<String> tokens) {
		//troubleshoot tip: this may not work if snowball-stemmer jar is not exported to the server before execution
		PorterStemmer stemmer = new PorterStemmer();
		ArrayList<String> stemmedTags = new ArrayList<String>();    
		
		for(String s: tokens) {
			s = StringHelper.cleanString(s);
			stemmer.setCurrent(s);
			if(stemmer.stem()){	
				stemmedTags.add(stemmer.getCurrent());
			}
		}
		
		return stemmedTags;
	}
	
}
