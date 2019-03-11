/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Tweet tweet1 = new Tweet(4, "waleed", "@HAMNA @noor @maria @mit.edu", d1);
    private static final Tweet tweet2 = new Tweet(4, "hamza", "@bushra @yusra", d1);
    private static final Tweet tweet3 = new Tweet(4, "zaeem", "@poki ", d1);
	
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        HashSet<String> h1 = new HashSet<String>();
        HashSet<String> h2 = new HashSet<String>();
        HashSet<String> h3 = new HashSet<String>();
        h1.add("@obaj");
        h1.add("@tazhar");
        h1.add("@zhabib");
        h2.add("@obaj");
        h2.add("@tazhar");
        h3.add("@tazhar");
        
        followsGraph.put("@madham", h1);
        followsGraph.put("@ymasood", h2);
        followsGraph.put("@ubashir", h3);
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals(influencers.get(0), h1);
        assertEquals(influencers.get(2), h3);
        
    }
    
    @Test
    public void testGuessFollowsGraphOne() {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(tweet1);
//        tweets.add(tweet2);
//        tweets.add(tweet3);
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        Set<String> set = new HashSet<String>();
        set.add("@HAMNA");
        set.add("@maria");
        set.add("@noor");
        
        assertEquals(followsGraph.get("waleed"), set);
    }
    
    @Test
    public void testGuessFollowsGraphMultiple() {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        Set<String> set = new HashSet<String>();
        set.add("@poki");
        
        assertEquals(followsGraph.get("zaeem"), set);
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
