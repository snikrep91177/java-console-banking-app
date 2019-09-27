package com.perkins.project0.models;

import java.util.ArrayList;
import java.util.List;

import com.perkins.project0.service.DatabaseHelper;

public class Customer implements User {
	
	private int customer_id;
	private String username;
	private String password;
	private int[] acct_num;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param customer_id
	 */
	public Customer(int customer_id, String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.customer_id = customer_id;
	}

	/**
	 * @return the customer_id
	 */
	public int getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
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
		return "Customer [customer_id = " + customer_id + ", username = " + username + "]";
	}
	
	public static boolean createAccount(String username, String password) {
		
		boolean wasSuccess = DatabaseHelper.insertUser(username, password, "customer");
		if(wasSuccess) {
			Customer temp = new Customer();
			temp = DatabaseHelper.selectOneCustomer(username);
			int custId = temp.getCustomer_id();
			DatabaseHelper.insertBankAccount(custId, 0.0, false);			
		}
		return wasSuccess;
	}
	
	public static boolean checkUsernameExists(String username) {
		
		List <Customer> customers = new ArrayList <>();
		customers = DatabaseHelper.selectAllCustomers();
		
		boolean nameExists = false;
		for(Customer c: customers) {
			if(c.username.equals(username)) {
				nameExists = true;
			}
		}
		return nameExists;
	}
	
}
