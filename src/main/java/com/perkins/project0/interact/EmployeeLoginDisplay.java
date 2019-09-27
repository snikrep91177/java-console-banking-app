package com.perkins.project0.interact;

import java.util.ArrayList;
import java.util.List;

import com.perkins.project0.models.Employee;
import com.perkins.project0.service.DatabaseHelper;
import com.perkins.project0.service.InputValidator;

public class EmployeeLoginDisplay implements Displayable {

	@Override
	public void display() {

		while (true) {
			String username = InputValidator.textGet(4, 30, "Please enter your username:");
			String password = InputValidator.textGet(4, 15, "Please enter your password:");
			// ask database if username is present
			List<Employee> employees = new ArrayList<>();
			employees = DatabaseHelper.selectAllEmployees();
			for (Employee e : employees) {
				if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
					EmployeeDisplay.employeeOptions();
				}				
			}
			System.out.println("Wrong credentials. Please try again.");
		}
	}
}
