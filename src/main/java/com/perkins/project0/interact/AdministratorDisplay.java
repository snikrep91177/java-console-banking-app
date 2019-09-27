package com.perkins.project0.interact;

import java.util.List;

import com.perkins.project0.models.BankAccount;
import com.perkins.project0.models.Customer;
import com.perkins.project0.service.DatabaseHelper;
import com.perkins.project0.service.InputValidator;

public class AdministratorDisplay implements Displayable {

	@Override
	public void display() {
		
		administratorOptions();
	}

	public static void administratorOptions() {
		while(true) {
			int option = InputValidator.integerGet(0, 3, "Please make a selection:", 
					"View Customers", "Approve account Requests", "Exit");
			
			switch(option) {
			case 1:
				viewCustomers();
				break;
			case 2:
				approveAccounts();
				break;
			case 3:
				IntroDisplay uc = new IntroDisplay();
				uc.display();
			}
			
		}
	}

	private static void approveAccounts() {
		AdministratorDisplay.viewCustomers();
		int input = InputValidator.integerGet(1, 1000, "Enter the account number of the account you "
				+ "would like to approve:");
		DatabaseHelper.approveAccount(input);
		System.out.println("Account " + input + " approved.");
		AdministratorDisplay.administratorOptions();
	}

	private static void viewCustomers() {
		List <Customer> customers = DatabaseHelper.selectAllCustomers();
		for(Customer c: customers) {			
			CustomerDisplay.viewAccounts(c);
		}
	}
}
