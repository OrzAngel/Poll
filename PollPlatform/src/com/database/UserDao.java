package com.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.javatpoint.User;

/**
 * This class manages the data access to the pollUser table.
 * @author Jinyun
 *
 */
public class UserDao {
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * Constructor
	 * Initialize the instance variable
	 * @param jdbcTemplate
	 */
	public UserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * This method adds a new user to the database.
	 * @param newUser the given new user object
	 * @return return 1 if the user is added successfully; return 0 if no user is added.
	 */
	public int saveUser(User newUser) {		
		return jdbcTemplate.update("insert into pollUser (name, password, points) values (?, ?, ?)", 
				newUser.getName(), newUser.getPassword(), 50);
	}
	
	/**
	 * This method deletes the given user from the database.
	 * @param userName the given user name
	 * @return return 1 if the user is deleted successfully; return 0 if no user is affected
	 */
	public int deleteUser(String userName) {		
		return jdbcTemplate.update("delete from pollUser where name = ?", userName);
	}
	
	/**
	 * Given the user name, this method gets the data for the user out of the database.
	 * @param name the given user name
	 * @return return the user object if the user exists; return null otherwise
	 */
	public User getUser(String name) {
		String query = "select name, password, points from pollUser where name=?";
		
		RowMapper<User> mapper = new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
					String name = resultSet.getString("name");
					String password = resultSet.getString("password");
					int points = resultSet.getInt("points");					
					User user = new User(name, password);
					user.setPoints(points);
					return user;				
			}			
		};
		User res;
		try {
			res = (User)jdbcTemplate.queryForObject(query,new Object[] {name}, mapper); 
		} catch (Exception e) {
			res = null;
		}
		return res; 
	}
	
	/**
	 * Given a user name, this method checks whether the user exists in the database.
	 * @param userName the given user name
	 * @return return true if the user exists in the database; return false otherwise
	 */
	@SuppressWarnings("deprecation")
	public boolean hasUser(String userName) {
		String query = "select count(*) from pollUser where name = ?";
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(query, userName);
		} catch(Exception e) {			
		}
		
		if(count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method updates the user's points.
	 * @param userName the user name
	 * @param changedPoints the points that should be added
	 * @return return 1 if the points are added successfully; return 0 if no user is affected
	 */
	public int updatePoints(String userName, int changedPoints) {
		User curUser = getUser(userName);
		int newPoint = curUser.getPoints() + changedPoints;
		String updatePointQuery = "update pollUser set points = ? where name = ?";
		return this.jdbcTemplate.update(updatePointQuery, new Object[]{newPoint, userName});		
	}
}