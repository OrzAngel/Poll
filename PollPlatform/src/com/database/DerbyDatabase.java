package com.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import org.springframework.jdbc.core.JdbcTemplate;

import com.javatpoint.Poll;
import com.javatpoint.User;

/**
 * This class represents a derbyDatabase.
 * It implements the PollPlatformDatabase interface.
 * @author Jinyun
 *
 */
public class DerbyDatabase implements PollPlatformDatabase {
	private final String[] tagArray = {"business", "science", "health", "sports", "arts", "entertainment", "life", "others"};
	
	private static DerbyDatabase derbyDatabase;
	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private PollDao pollDao;
	private FilledPollsDao filledPollsDao;
	
	/**
	 * Constructor
	 * Configure the database
	 * create the dao objects for keeping the records
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */
//	public DerbyDatabase() throws ClassNotFoundException, SQLException {
	private DerbyDatabase() throws ClassNotFoundException, SQLException {
		this.jdbcTemplate = DatabaseConfigurer.getInstance();
		this.userDao = new UserDao(jdbcTemplate);
		this.pollDao = new PollDao(jdbcTemplate);
		this.filledPollsDao = new FilledPollsDao(jdbcTemplate);
	}
	
	public static DerbyDatabase getInstance() throws ClassNotFoundException, SQLException {
		if(derbyDatabase == null) {
			derbyDatabase = new DerbyDatabase();
		} 
		return derbyDatabase;
	}
	
	/**
	 * This method adds a user to the database.
	 */
	@Override
	public int addUser(User u) {
		boolean hasUser = userDao.hasUser(u.getName());
		if(hasUser == true) {
			return -1;
		}
		return userDao.saveUser(u);
	}

	/**
	 * This method updates the records for poll table, and pollUser table when a user posts a poll.
	 */
	@Override
	public int postAPoll(String poster, Poll p) {
		if(userDao.hasUser(poster) == false) {
			return -1;
		}
		userDao.updatePoints(poster, -2);
		return pollDao.savePoll(p);
	}

	/**
	 * Given a user name, this method gets the user object from the database.
	 */
	@Override
	public User getUser(String userName) {		
		return userDao.getUser(userName);
	}

	/**
	 * Given a poll's id, this method gets the poll object from the database.
	 */
	@Override
	public Poll getPoll(int pollId) {
		return pollDao.getPoll(pollId);
	}

	/**
	 * This methods deletes the poll with the given id.
	 */
	@Override
	public int deletePoll(int pollId) {
		return pollDao.deletePoll(pollId);
	}

	/**
	 * This method deletes the user with the given user name.
	 */
	@Override
	public int deleteUser(String userName) {
		return userDao.deleteUser(userName);
	}

	/**
	 * This method updates the records for poll table, pollUser table, and filledPolls table when a user fills a poll.
	 */
	@Override
	public int fillPoll(String raterName, int pollId, int rating) {
		if(filledPollsDao.hasFilled(raterName, pollId)) {
			return -1;
		}
		pollDao.rated(pollId, rating);
		userDao.updatePoints(raterName, 1);
		return filledPollsDao.fillAPoll(pollId, raterName, rating);
	}

	/**
	 * This method gets all the polls the given user has posted.
	 */
	@Override
	public List<Poll> getPollsForUser(String userName) {
		return pollDao.findAllPollsForUser(userName);
	}

	/**
	 * This method gets all the polls in the database.
	 */
	@Override
	public List<Poll> getAllPolls() {
		return pollDao.findAllPolls();
	}

	/**
	 * This method gets all the polls with the given tag.
	 */
	@Override
	public List<Poll> getTaggedPolls(String tag) {
		return pollDao.findTaggedPolls(tag);
	}
	
	/**
	 * This method gets the recent polls.
	 * The number of returned polls is specified by the parameter - numNeeded.
	 * If there is no enough polls in the database, return all the polls in the database in the order of their posted dates.
	 */
	@Override
	public ArrayList<Poll> getRecentPolls(int numNeeded) {
		List<Poll> allPolls = getAllPolls();
		PriorityQueue<Poll> maxHeap = new PriorityQueue<Poll>(new Comparator<Poll>() {
			public int compare(Poll o1, Poll o2) {			
				return o2.getCreatedDate().compareTo(o1.getCreatedDate());
			}
		});
		for(Poll p : allPolls) {
			maxHeap.add(p);
		}
		
		ArrayList<Poll> res = new ArrayList<Poll>();
		//if the size of maxHeap is smaller than numNeeded, return all the elements in maxHeap
		int size = Math.min(numNeeded, maxHeap.size());
		for(int i = 0; i < size; i++) {
			Poll curPoll = maxHeap.poll();
			res.add(curPoll);		
		}		
		return res;
	}

	/**
	 * This method make recommendations for a certain user.
	 * It is based on the types of polls the user has posted. 
	 * If the user hasn't posted any poll, it will give the most popular polls.
	 * Otherwise it will look at the type of polls the user has made most posts and make recommendations accordingly.
	 */
	@Override
	public List<Poll> getRecommendations(String userName, int numNeeded) {
		List<Poll> userPolls = getPollsForUser(userName);
		
		if(userPolls == null || userPolls.size() == 0) {
			List<Poll> allPolls = getAllPolls();
			return getPopularPolls(allPolls, numNeeded);
		}
		
		int[] tagCounts = new int[8];
		for (Poll p : userPolls) {
			String tag = p.getTag();
			if (tag == null) {
				continue;
			}
			if(tag.equals(tagArray[0])) {
				tagCounts[0]++;
			} else if(tag.equals(tagArray[1])) {
				tagCounts[1]++;
			} else if(tag.equals(tagArray[2])) {
				tagCounts[2]++;
			} else if(tag.equals(tagArray[3])) {
				tagCounts[3]++;
			} else if(tag.equals(tagArray[4])) {
				tagCounts[4]++;
			} else if(tag.equals(tagArray[5])) {
				tagCounts[5]++;
			} else if(tag.equals(tagArray[6])) {
				tagCounts[6]++;
			} else {
				tagCounts[7]++;
			}
		}
		HashMap<String, Integer> tagCountMap = new HashMap<String, Integer>();
		for(int i = 0; i < 8; i++) {
			tagCountMap.put(tagArray[i], tagCounts[i]);
		}
		
		int maxCount = 0;
		String maxTag ="";
		for(String tag : tagCountMap.keySet()) {
			if(tagCountMap.get(tag) > maxCount) {
				maxCount = tagCountMap.get(tag);
				maxTag = tag;
			}
		}		
		List<Poll> taggedPolls = getTaggedPolls(maxTag);		
		return getPopularPolls(taggedPolls, numNeeded);
	}
	
	/**
	 * This method gets the most popular polls, i.e. the ones that have been rated by most people.
	 */
	@Override
	public List<Poll> getPopularPolls(List<Poll> allPolls, int numNeeded) {		
		PriorityQueue<Poll> maxHeap = new PriorityQueue<Poll>(new Comparator<Poll>() {
			public int compare(Poll o1, Poll o2) {
				int totalResult1 = 0;
				int totalResult2 = 0;
				
				for(int i : o1.getPollResults()) {
					totalResult1 += i;
				}
				for(int i : o2.getPollResults()) {
					totalResult2 += i;
				}
				return totalResult2 - totalResult1;
			}
		});
		for(Poll p : allPolls) {
			maxHeap.add(p);
		}
		
		ArrayList<Poll> result = new ArrayList<Poll>();
		//if the size of maxHeap is smaller than numNeeded, return all the elements in maxHeap
		int size = Math.min(numNeeded, maxHeap.size());
		for(int i = 0; i < size; i++) {
			Poll curPoll = maxHeap.poll();
			result.add(curPoll);		
		}		
		return result;
	}
	
	/**
	 * This method gets the most highly rated polls.
	 */
	@Override
	public List<Poll> getHighlyRatedPolls(int numNeeded) {
		List<Poll> allPolls = getAllPolls();
		PriorityQueue<Poll> maxHeap = new PriorityQueue<Poll>(new Comparator<Poll>() {
			public int compare(Poll o1, Poll o2) {
				int totalResult1 = 0;
				int totalResult2 = 0;
				int[] result1 = o1.getPollResults();
				for(int i = 0; i < 5; i++) {
					totalResult1 += ((i+1) * result1[i]);
				}
				int[] result2 = o2.getPollResults();
				
				for(int i = 0; i < 5; i++) {
					totalResult2 += ((i+1) * result2[i]);
				}
				return totalResult2 - totalResult1;
			}
		});
		for(Poll p : allPolls) {
			maxHeap.add(p);
		}
		
		ArrayList<Poll> result = new ArrayList<Poll>();
		//if the size of maxHeap is smaller than numNeeded, return all the elements in maxHeap
		int size = Math.min(numNeeded, maxHeap.size());
		for(int i = 0; i < size; i++) {
			Poll curPoll = maxHeap.poll();
			result.add(curPoll);		
		}		
		return result;
	}
}
