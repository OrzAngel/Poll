package com.database;

import org.apache.log4j.BasicConfigurator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class Reset {

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub		
		
		JdbcTemplate jdbcTemplate = DatabaseConfigurer.getInstance();

		jdbcTemplate.execute("DROP TABLE poll");
		jdbcTemplate.execute("DROP TABLE pollUser");
		jdbcTemplate.execute("DROP TABLE filledPolls");

//		jdbcTemplate.execute("create table pollUser (name varchar(20), password varchar(20), points integer)");	
//
//		jdbcTemplate.execute("create table poll (id integer primary key generated always as identity, "
//				+ "title varchar(20), content varchar(500), poster varchar(20), tag varchar(20), createdTime TIMESTAMP, "
//				+ "r1 integer, r2 integer, r3 integer, r4 integer, r5 integer)");
//
//		jdbcTemplate.execute("create table filledPolls (pollId integer, raterName varchar(20), score int)");


	}

}
