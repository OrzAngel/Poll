package com.database;

import org.springframework.jdbc.core.JdbcTemplate;


/**
 * This class manages the data access to the filledPolls table.
 * It can add an entry to the table when a user fills a poll.
 * It can also check whether a user has filled a certain poll. 
 * @author Jinyun
 *
 */
public class FilledPollsDao {
	private JdbcTemplate jdbcTemplate;

	/**
	 * Constructor
	 * initialize the instance variable
	 * @param jdbcTemplate
	 */
	public FilledPollsDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * This method adds an entry to the filledPolls table when a user fills a certain poll.
	 * @param pollId the poll id
	 * @param userName the given user name
	 * @param score the user's rating for the poll
	 * @return return 1 if the entry is successfully added
	 */
	public int fillAPoll(int pollId, String userName, int score) {	
		return this.jdbcTemplate.update("insert into filledPolls (pollId, raterName, score) values (?, ?, ?)", 
				pollId, userName, score);
	}
	
	/**
	 * This method checks whether the user has filled the given poll.
	 * @param userName
	 * @param pollId
	 * @return return true if the poll has been filled by the user; return false otherwise
	 */
	@SuppressWarnings("deprecation")
	public boolean hasFilled(String userName, int pollId) {		
		String query = "select count(*) from filledPolls where pollId = ? and raterName = ?";
		int count = 0;
		try {
			count = this.jdbcTemplate.queryForInt(query, pollId, userName);
		} catch(Exception e) {
		}
		if(count > 0) {
			return true;
		} 
		return false;
	}
}