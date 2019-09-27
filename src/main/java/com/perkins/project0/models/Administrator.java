package com.perkins.project0.models;

public class Administrator implements User {
	
	private int admin_id;
	private String username;
	private String password;
	
	public Administrator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param admin_id
	 * @param username
	 * @param password
	 */
	public Administrator(int admin_id, String username, String password) {
		super();
		this.admin_id = admin_id;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the admin_id
	 */
	public int getAdmin_id() {
		return admin_id;
	}

	/**
	 * @param admin_id the admin_id to set
	 */
	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Administrator [admin_id=" + admin_id + ", username=" + username + "]";
	}
	
	

}
