package com.database;

import java.util.List;

import com.javatpoint.Poll;
import com.javatpoint.User;

/**
 * This interface works as a facade for the poll platform database.
 * It defines the major functions of the database.
 * It wrappes up the classes that deal with the data in the database.
 * Users interact with the database by invoking the methods defined in the interface.
 * @author Jinyun
 *
 */
public interface PollPlatformDatabase {
	
	/**
	 * This method adds a new user to the database.
	 * If the user's name already exists, the user will not add the user to the database. 
	 * The method will return null.
	 * @param u the user object
	 * @return return 1 if a new user is added; return -1 if the user's name already exists;
	 */
	public int addUser(User u);
	
	/**
	 * This method allows a user to post a poll.
	 * 2 points are deducted from the user's points when he/she posts a poll.
	 * The poll is added to the poll table of the database.
	 * @param poster the name of the user who posts the poll
	 * @param p the new poll
	 * @return return 1 if the poll is successfully added; return -1 otherwise
	 */
	public int postAPoll(String poster, Poll p);
	
	/**
	 * Given the user's name, this method gets the user object from the database.
	 * @param userName the given name
	 * @return the user object; return null if there is no such user
	 */
	public User getUser(String userName);
	
	
	/**
	 * Given the poll's id, this method gets the poll object from the database.
	 * @param pollId the given poll id
	 * @return the poll object; return null if there is no such poll
	 */
	public Poll getPoll(int pollId);
	
	/**
	 * This method deletes a poll from the database
	 * @param pollId the given poll id
	 * @return return 1 if the poll is deleted; return 0 if no poll is deleted
	 */
	public int deletePoll(int pollId);
	
	/**
	 * This method deletes a user from the database
	 * @param userName the given user name
	 * @return return 1 if the user is deleted; return 0 if no user is deleted
	 */
	public int deleteUser(String userName);
	
	/**
	 * This method allows a use to fill a poll.
	 * The user will get a point for filling a poll.
	 * The poll's results will be updated as well.
	 * If the user has filled a poll before, nothing changes in the database and the method returns -1.
	 * @param raterName 
	 * @param pollId
	 * @param score
	 * @return
	 */
	public int fillPoll(String raterName, int pollId, int rating);
	
	/**
	 * This method gets all the polls a certain user has posted.
	 * @param userName the given user name
	 * @return a list of polls the user has posted
	 */
	public List<Poll> getPollsForUser(String userName);
	
	/**
	 * This method gets all the polls in the database.
	 * @return a list of all the polls in the database
	 */
	public List<Poll> getAllPolls();
	
	/**
	 * This method gets all the polls with the specified tag.
	 * @param tag the given tag
	 * @return a list of the polls with the tag
	 */
	public List<Poll> getTaggedPolls(String tag);
	
	/**
	 * This method gets the recent polls.
	 * @param numNeeded the number of polls needed
	 * @return a list of recent polls
	 */
	public List<Poll> getRecentPolls(int numNeeded);
	
	/**
	 * This method gets the most popular polls.
	 * @param numNeeded the number of polls needed
	 * @return a list of recent polls
	 */
	public List<Poll> getPopularPolls(List<Poll> polls, int numNeeded);
	
	/**
	 * This method gets the most highly rated polls.
	 * @param numNeeded the number of polls needed
	 * @return a list of recent polls
	 */
	public List<Poll> getHighlyRatedPolls(int numNeeded);
	
	/**
	 * This method gets recommended polls for a certain user.
	 * @param numNeeded the number of recommendations needed
	 * @return a list ofrecommended polls
	 */
	public List<Poll> getRecommendations(String userName, int numNeeded);
	
}
