package com.osu.ceti.REComponent.helpers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


/**
 * Custom comparator written for alternative way to generate the maximum matching success stories
 * or persuasion messages from the activity tags 
 * @author nhchdhr
 *
 */
public class TagComparison implements Comparator<TagComparison>, Comparable<TagComparison>{

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
		
		if(this.comparisionSize()<o.comparisionSize())
			return 1;
		else if(this.comparisionSize()>o.comparisionSize())
			return -1;
		else
		return 0;
	}
	
	
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