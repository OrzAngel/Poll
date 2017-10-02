package com.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.javatpoint.Poll;

/**
 * This class manages the data access to the poll table.
 * @author Jinyun
 *
 */
public class PollDao {
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Constructor
	 * Initialize the instance variable
	 * @param jdbcTemplate
	 */
	public PollDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * This method adds a new entry to the poll table when a new poll is saved.
	 * It also adds the posted time in the entry.
	 * @param p the given new poll object
	 * @return return 1 if the poll is added successfully; return 0 if no poll is added
	 */
	public int savePoll(Poll p) {
		Calendar calendar = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
		 
		return jdbcTemplate.update("insert into poll (title, content, poster, tag, createdTime) values(?, ?, ?, ?, ?)", 
				p.getPollTitle(), p.getPollContent(), p.getPoster(), p.getTag(), timestamp);
	}
	
	/**
	 * This method deletes a certain poll from the database.
	 * @param pollId the given poll id
	 * @return return 1 if the poll is successfully deleted; return 0 if no poll is affected
	 */
	public int deletePoll(int pollId) {
		return jdbcTemplate.update("delete from poll where id = ?", pollId);
	}
	
	/**
	 * Given the poll id, this method gets the poll out of the database.
	 * @param pollId the given poll id
	 * @return return the poll object
	 */
	public Poll getPoll(int pollId) {
		String query = "select id, title, content, poster, tag, createdTime, r1, r2, r3, r4, r5 from poll where id=?";
		Poll poll;
		try {
			poll = (Poll) this.jdbcTemplate.queryForObject(query,new Object[] {pollId}, new PollMapper());
		} catch(Exception e) {
			poll = null;
		}
		return poll; 
	}
	
	/**
	 * This method gets all the polls posted by the given user.
	 * @param posterName the given user name
	 * @return a list of polls the user has posted
	 */
	public List<Poll> findAllPollsForUser(String posterName) {		
		String query = "select id, title, content, poster, tag, createdTime, r1, r2, r3, r4, r5 from poll where poster = ?";	
		try {
			return this.jdbcTemplate.query(query, new Object[] {posterName}, new PollMapper());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * This method gets all the polls in the database.
	 * @return the list of all the polls
	 */
	public List<Poll> findAllPolls() {
		String query = "select id, title, content, poster, tag, createdTime, r1, r2, r3, r4, r5 from poll";
		try {
			return this.jdbcTemplate.query(query, new PollMapper());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * This method updates the poll results when a poll is rated.
	 * @param pollId the given poll id
	 * @param score the rating for the poll
	 * @return return 1 if the results are updated successfully; return 0 if no poll is affected
	 */
	public int rated(int pollId, int score) {
		String query = "";
		Poll ratedPoll = getPoll(pollId);
		int count = 0;
		
		switch(score) {
		case 1:
			query = "update poll set r1=? where id = ?";
			count = ratedPoll.getPollResults()[0] + 1;
			break;
		case 2:
			query = "update poll set r2=? where id = ?";
			count = ratedPoll.getPollResults()[1] + 1;
			break;
		case 3: 
			query = "update poll set r3=? where id = ?";
			count = ratedPoll.getPollResults()[2] + 1;
			break;
		case 4:
			query = "update poll set r4=? where id = ?";
			count = ratedPoll.getPollResults()[3] + 1;
			break;
		case 5:
			query = "update poll set r5=? where id = ?";
			count = ratedPoll.getPollResults()[4] + 1;
			break;
		}		
		return this.jdbcTemplate.update(query, new Object[]{count, pollId});
	}
	
	/**
	 * This method gets the list of polls with the given tag.
	 * @param tag the given tag
	 * @return the list of polls with the tag
	 */
	public List<Poll> findTaggedPolls(String tag) {
		String query = "select id, title, content, poster, tag, createdTime, r1, r2, r3, r4, r5 from poll where tag = ?";	
		try {
			return this.jdbcTemplate.query(query, new Object[] {tag}, new PollMapper());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * This private class implements RowMapper interface.
	 * It maps an entry of the poll table to the poll object.
	 * @author Jinyun
	 *
	 */
	private static final class PollMapper implements RowMapper<Poll> {
		@Override
		public Poll mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Poll poll = new Poll();
			poll.setPollId(resultSet.getInt("id"));
			poll.setPollTitle(resultSet.getString("title"));
			poll.setPollContent(resultSet.getString("content"));
			poll.setPoster(resultSet.getString("poster"));
			int[] pollResults = new int[5];
			pollResults[0] = resultSet.getInt("r1");
			pollResults[1] = resultSet.getInt("r2");
			pollResults[2] = resultSet.getInt("r3");
			pollResults[3] = resultSet.getInt("r4");
			pollResults[4] = resultSet.getInt("r5");
			poll.setTag(resultSet.getString("tag"));
			poll.setPollResults(pollResults);
			poll.setCreatedDate(resultSet.getTimestamp("createdTime"));
			return poll;
		}			
	}	
}