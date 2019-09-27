package com.perkins.project0.models;

public class Employee implements User {
	
	private int employee_id;
	private String username;
	private String password;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param employee_id
	 * @param username
	 * @param password
	 */
	public Employee(int employee_id, String username, String password) {
		super();
		this.employee_id = employee_id;
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the employee_id
	 */
	public int getEmployee_id() {
		return employee_id;
	}

	/**
	 * @param employee_id the employee_id to set
	 */
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
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
		return "Employee [employee_id=" + employee_id + ", username=" + username + "]";
	}
	
	

}
