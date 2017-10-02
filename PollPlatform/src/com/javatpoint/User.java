package com.javatpoint;
/**
 * This is a user class, tracks its id, name, points and password.
 * @author LuyiYang
 *
 */
public class User {  
	private int id;  
	private String name;  
	private int points;  
	private String password;  

	/**
	 * creates a new user object with its name and password.
	 * @param name
	 * @param password
	 */
	public User(String name, String password) {   
		this.name = name; 
		this.password = password;  
	}  

	public String getPassword() {
		return password;
	}

	public int getId() {  
		return id;  
	}  
	public void setId(int id) {  
		this.id = id;  
	}  
	/**
	 * get the name of the user, each user has its unique name
	 * @return the name of the user
	 */
	public String getName() {  
		return name;  
	}  
 
	public int getPoints() {  
		return  points;  
	}  
	public void setPoints(int points) {  
		this.points = points;  
	}  
}  