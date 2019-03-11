/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
	 public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
	        
	        Map<String, Set<String>> followGraph = new HashMap<String, Set<String>>();
	        
	        for (Tweet tweet: tweets) {
	           if(followGraph.containsKey(tweet.getAuthor())) {
	               Set<String> set = Extract.getMentionedUsers(Arrays.asList(tweet));
	               Set<String> preset = followGraph.get(tweet.getAuthor());
	               for (String c: set) {
	                   preset.add(c);
	               }
	               followGraph.put(tweet.getAuthor(), preset);
	               
	           }else {
	               Set<String> set = Extract.getMentionedUsers(Arrays.asList(tweet));
	               followGraph.put(tweet.getAuthor(), set);
	           }

	        }
	        
	        return followGraph;
	    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        //throw new RuntimeException("not implemented");
    	
    	List influencers = new ArrayList<String>();
    	HashMap<String, Integer> influencer = new HashMap<String, Integer>();
    	
    	for (String key:followsGraph.keySet()) {
    		influencer.put(key,followsGraph.get(key).size());    		
    	}
    	
    	Map<String, Integer> hm1 = sortByValue(influencer);
    	
    	for (String key:hm1.keySet()) {
    		influencers.add(followsGraph.get(key));    		
    	}
    	
    	return influencers;

    }
    
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }
    
    

}
