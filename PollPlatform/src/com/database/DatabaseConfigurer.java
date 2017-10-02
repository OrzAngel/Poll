package com.database;

import org.apache.log4j.BasicConfigurator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * This class configures the derby database.
 * It uses the embeddedDriver for derby, and stores the data in the specified local directory.
 * When the program is run for the first time, three tables are created named poll, pollUser and filledPolls respectively.
 * After that, the tables will be availabe in the database, and the user just needs to use the tables to update data.
 * It uses the singleton design pattern, i.e. the program can only have one instance of jdbcTemplate.
 * @author Jinyun
 *
 */
public class DatabaseConfigurer {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
//	public static final String JDBC_URL = "jdbc:derby:zadb;create=true";
	
	public static final String JDBC_URL = "jdbc:derby:/Users/LuyiYang/Documents/HelloWorld/zadb;create=true";
	
	private static JdbcTemplate jdbcTemplate;
	
	/**
	 * This methods gets the instance of jdbcTemplate.
	 * If there is no instance of jdbcTemplate, create one. 
	 * If there is already one, return the isntance.
	 * @return 
	 * @throws ClassNotFoundException
	 */
	public static JdbcTemplate getInstance() throws ClassNotFoundException {
		if(jdbcTemplate == null) {
			createConnection();
		} 
		return jdbcTemplate;
	}
	
	/**
	 * This private method configures the database.
	 * It uses the embeddedDriver of derby.
	 * The database is stored in the specified local directory with the given user name and password.
	 * @throws ClassNotFoundException
	 */
	private static void createConnection() throws ClassNotFoundException {
		BasicConfigurator.configure();
		
		SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
		
		dataSource.setDriverClassName(DRIVER);
		dataSource.setUrl(JDBC_URL);
		dataSource.setUsername("za");
		dataSource.setPassword("");
			
		jdbcTemplate = new JdbcTemplate(dataSource);
		
//		jdbcTemplate.execute("DROP TABLE poll");
//		jdbcTemplate.execute("DROP TABLE pollUser");
//		jdbcTemplate.execute("DROP TABLE filledPolls");

//		jdbcTemplate.execute("create table pollUser (name varchar(20), password varchar(20), points integer)");	
		
//		jdbcTemplate.execute("create table poll (id integer primary key generated always as identity, "
//				+ "title varchar(20), content varchar(500), poster varchar(20), tag varchar(20), createdTime TIMESTAMP, "
//				+ "r1 integer, r2 integer, r3 integer, r4 integer, r5 integer)");
		
//		jdbcTemplate.execute("create table filledPolls (pollId integer, raterName varchar(20), score int)");
				
	}
}