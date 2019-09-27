package com.perkins.project0.interact;

import java.util.ArrayList;
import java.util.List;

import com.perkins.project0.models.Administrator;
import com.perkins.project0.service.DatabaseHelper;
import com.perkins.project0.service.InputValidator;

public class AdministratorLoginDisplay implements Displayable {

	@Override
	public void display() {
		while (true) {
			String username = InputValidator.textGet(4, 30, "Please enter your username:");
			String password = InputValidator.textGet(4, 15, "Please enter your password:");
			// ask database if username is present
			List<Administrator> administrators = new ArrayList<>();
			administrators = DatabaseHelper.selectAllAdministrators();
			for (Administrator c : administrators) {
				if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
					AdministratorDisplay.administratorOptions();
				}								
			}
			System.out.println("Wrong credentials. Please try again.");
		}
	}
}
