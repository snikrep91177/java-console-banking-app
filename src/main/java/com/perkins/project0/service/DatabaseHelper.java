package com.perkins.project0.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.perkins.project0.models.Administrator;
import com.perkins.project0.models.BankAccount;
import com.perkins.project0.models.Customer;
import com.perkins.project0.models.Employee;

public class DatabaseHelper {

	private static String url = "jdbc:oracle:thin:@revaturedb.c39ybozzntjs.us-east-2.rds.amazonaws.com:1521:ORCL";
	private static String username = "mgperkins1";
	private static String password = "p4ssw0rd";

	// callable statement
	public static boolean insertUser(String usrnme, String psswrd, String table) {
		boolean wasSuccess = false;
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = null;
			switch (table) {
			case "customer":
				sql = "{ call insert_customer_null_id(?,?) }";
				break;
			case "employee":
				sql = "{ call insert_employee_null_id(?,?) }";
				break;
			}

			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, usrnme);
			cs.setString(2, psswrd);

			int status = cs.executeUpdate();

			if (status == 1) {
				wasSuccess = true;
			}
			// System.out.println("Callable statement returns: " + status);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wasSuccess;
	}

	public static int insertBankAccount(int customerId, double acctBalance, boolean approved) {
		int status = 0;
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "{ call insert_bank_account_null_id(?,?,?) }";

			CallableStatement cs = conn.prepareCall(sql);
			cs.setInt(1, customerId);
			cs.setDouble(2, acctBalance);
			cs.setBoolean(3, approved);

			status = cs.executeUpdate();
			//System.out.println("Callable statement returns: " + status);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	// prepared statement
	public static void preparedStatementExample(String f_name, String f_recipe) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// prepared statement guards against sql injection
			// because it's pre-compiled (which also makes it faster)
			String sql = "INSERT INTO food(food_name, recipe) " + "VALUES(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, f_name);
			ps.setString(2, f_recipe);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// prepared statement
	public static void updateBalance(int acct_num, double amount) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// prepared statement guards against sql injection
			// because it's pre-compiled (which also makes it faster)
			String sql = "UPDATE bank_account SET acctBalance=? WHERE acct_num=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, amount);
			ps.setInt(2, acct_num);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void approveAccount(int acct_num) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// prepared statement guards against sql injection
			// because it's pre-compiled (which also makes it faster)
			String sql = "UPDATE bank_account SET approved=? WHERE acct_num=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "TRUE");
			ps.setInt(2, acct_num);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// THIS IS AN EXAMPLE of how to query using JDBC
	public static List<Customer> selectAllCustomers() {
		// creating an array to hold the records from
		// the future query
		List<Customer> customers = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// query everything in the food table
			String sql = "SELECT * FROM customer";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// This while loop is responsible for loading up the
			// arrayList we created with the values we pulled from
			// from the query.
			// "rs.next()" is a method used to access each record
			// inside of a result set

			// users.add(new T());

			while (rs.next()) {
				customers.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3)));

				// we use "recipe" here instead of "rs.getString(3)"
				// in short, we use "recipe", aka the column name,
				// instead of getting the "3rd column" in the table
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public static Customer selectOneCustomer(String usrname) {
		// creating an array to hold the records from
		// the future query
		Customer customer = new Customer();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// query everything in the food table
			String sql = "SELECT * FROM customer WHERE username='" + usrname + "'";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// This while loop is responsible for loading up the
			// arrayList we created with the values we pulled from
			// from the query.
			// "rs.next()" is a method used to access each record
			// inside of a result set

			// users.add(new T());

			while (rs.next()) {
				customer.setCustomer_id(rs.getInt(1));
				customer.setUsername(rs.getString(2));
				customer.setPassword(rs.getString(3));
				// we use "recipe" here instead of "rs.getString(3)"
				// in short, we use "recipe", aka the column name,
				// instead of getting the "3rd column" in the table
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public static List<BankAccount> selectAccounts(int custId) {
		// creating an array to hold the records from
		// the future query
		List<BankAccount> accounts = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// query everything in the food table
			String sql = "SELECT * FROM bank_account WHERE acctID=" + custId;

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// This while loop is responsible for loading up the
			// arrayList we created with the values we pulled from
			// from the query.
			// "rs.next()" is a method used to access each record
			// inside of a result set

			// users.add(new T());

			while (rs.next()) {
				accounts.add(new BankAccount(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getBoolean(4)));
				// we use "recipe" here instead of "rs.getString(3)"
				// in short, we use "recipe", aka the column name,
				// instead of getting the "3rd column" in the table
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	public static List<Employee> selectAllEmployees() {
		// creating an array to hold the records from
		// the future query
		List<Employee> employees = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// query everything in the food table
			String sql = "SELECT * FROM employee";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// This while loop is responsible for loading up the
			// arrayList we created with the values we pulled from
			// from the query.
			// "rs.next()" is a method used to access each record
			// inside of a result set

			// users.add(new T());

			while (rs.next()) {
				employees.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)));

				// we use "recipe" here instead of "rs.getString(3)"
				// in short, we use "recipe", aka the column name,
				// instead of getting the "3rd column" in the table
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	public static List<Administrator> selectAllAdministrators() {
		// creating an array to hold the records from
		// the future query
		List<Administrator> administrators = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// query everything in the food table
			String sql = "SELECT * FROM administrator";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// This while loop is responsible for loading up the
			// arrayList we created with the values we pulled from
			// from the query.
			// "rs.next()" is a method used to access each record
			// inside of a result set

			// users.add(new T());

			while (rs.next()) {
				administrators.add(new Administrator(rs.getInt(1), rs.getString(2), rs.getString(3)));

				// we use "recipe" here instead of "rs.getString(3)"
				// in short, we use "recipe", aka the column name,
				// instead of getting the "3rd column" in the table
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administrators;
	}

	public static List<BankAccount> selectAllAccounts() {
		// creating an array to hold the records from
		// the future query
		List<BankAccount> accounts = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// query everything in the food table
			String sql = "SELECT * FROM bank_account";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			// This while loop is responsible for loading up the
			// arrayList we created with the values we pulled from
			// from the query.
			// "rs.next()" is a method used to access each record
			// inside of a result set

			// users.add(new T());

			while (rs.next()) {
				accounts.add(new BankAccount(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getBoolean(4)));

				// we use "recipe" here instead of "rs.getString(3)"
				// in short, we use "recipe", aka the column name,
				// instead of getting the "3rd column" in the table
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	// THIS IS AN EXAMPLE of how to query using JDBC
//	public static User getCredentials(String username, String password, String table) {
//		// creating an array to hold the records from
//		// the future query
//		User user = new User();
//		String sql = null;
//
//		try (Connection conn = DriverManager.getConnection(url, username, password)) {
//			// query everything in the food table
//			switch (table) {
//			case "customer":
//				sql = "SELECT * FROM customer";
//				break;
//			case "employee":
//				sql = "SELECT * FROM employee";
//				break;
//			case "administator":
//				sql = "SELECT * FROM administrator";
//				break;
//			}
//
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
//
//			// This while loop is responsible for loading up the
//			// arrayList we created with the values we pulled from
//			// from the query.
//			// "rs.next()" is a method used to access each record
//			// inside of a result set
//			while (rs.next()) {
//				switch (table) {
//				case "customer":
//					users.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3)));
//					break;
//				case "employee":
//					users.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)));
//					break;
//				case "administator":
//					users.add(new Administrator(rs.getInt(1), rs.getString(2), rs.getString("recipe")));
//					break;
//				}
//
//				// we use "recipe" here instead of "rs.getString(3)"
//				// in short, we use "recipe", aka the column name,
//				// instead of getting the "3rd column" in the table
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return users;
//	}

}
