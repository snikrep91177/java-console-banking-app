package com.perkins.project0.interact;

import java.util.ArrayList;
import java.util.List;

import com.perkins.project0.models.BankAccount;
import com.perkins.project0.models.Customer;
import com.perkins.project0.service.DatabaseHelper;
import com.perkins.project0.service.InputValidator;

public class CustomerDisplay implements Displayable {

	@Override
	public void display() {

		getCustomerType();

	}

	private static void getCustomerType() {

		while (true) {
			int option = InputValidator.integerGet(1, 3, "Please make a selection:", "Existing Customer",
					"New Customer", "Exit");
			if(option < 0 || option > 3) {
				System.out.println("Invalid input. Please try again.");				
			}
			switch (option) {
			case 1:
				CustomerLoginDisplay ld = new CustomerLoginDisplay();
				ld.display();
				break;
			case 2:
				createNewCustomer();
				break;
			case 3:
				IntroDisplay uc = new IntroDisplay();
				uc.display();
			}

		}

	}

	private static void createNewCustomer() {

		String username = null;
		boolean done = false;
		while (!done) {
			username = InputValidator.textGet(4, 30, "Choose a username:");
			boolean nameExists = Customer.checkUsernameExists(username);
			if (nameExists) {
				System.out.println("Username already exists. Please try again.");
			} else {
				done = true;
			}
		}

		String password = InputValidator.textGet(4, 15, "Please enter a password");

		boolean wasSuccess = Customer.createAccount(username, password);
		if (wasSuccess) {
			System.out.println("\nAccount created Successfully!!!");
		}
	}

	public static void getCustomerOption(Customer cust) {

		while (true) {
			int option = InputValidator.integerGet(0, 7, "Please make a selection:", "Deposit", "Withdraw", "Transfer",
					"Request Account", "View Account", "Exit");
			
			switch (option) {
			case 1:
				deposit(cust);
				break;
			case 2:
				// withdraw
				withdraw(cust);
				break;
			case 3:
				transfer(cust);
				break;
			case 4:
				requestAccount(cust);
				break;
			case 5:
				// view accounts
				viewAccounts(cust);
				break;
			case 6:
				IntroDisplay uc = new IntroDisplay();
				uc.display();
				break;
			}
		}

	}
	
	private static void requestAccount(Customer cust) {
		int status = DatabaseHelper.insertBankAccount(cust.getCustomer_id(), 0.0, false);
		if(status == 1) {
			System.out.println("Your request has been initiated and is pending approval.");
			CustomerDisplay.getCustomerOption(cust);
		}
	}

	private static void transfer(Customer cust) {
		
		List<BankAccount> accounts = DatabaseHelper.selectAccounts(cust.getCustomer_id());
		
		if(accounts.size() <= 1) {
			System.out.println("You must have more than one account to transfer funds.");
			CustomerDisplay.getCustomerOption(cust);
		}
		
		CustomerDisplay.viewAccounts(cust);
		int transferFrom = InputValidator.integerGet(0, 1000, "Enter account number to transfer from:");
		int transferTo = InputValidator.integerGet(0, 1000, "Enter account number to transfer to:");
		double amount = InputValidator.decimalGet(0, 10000, "Enter transfer amount:");
		
		if(amount > 10000) {
			System.out.println("Maximum transfer amount is $10,000.00.");
			CustomerDisplay.transfer(cust);
		}
		BankAccount from = new BankAccount();
		BankAccount to = new BankAccount();
		
		for(BankAccount b: accounts) {
			if(b.getAcct_num() == transferFrom) {
				from = b;
			}else if(b.getAcct_num() == transferTo){
				to = b;
			}else {
				continue;
			}
		}	
		
		double newFromBal = 0;
		double newToBal = 0;
		if(amount > from.getAcctBalance()) {
			System.out.println("Transfer amount to large. Insufficient funds.\n");
			CustomerDisplay.transfer(cust);
		}else if(amount <= 0) {
			System.out.println("Cannot transfer negative amounts.\n");
			CustomerDisplay.transfer(cust);
		}else {
			newFromBal = from.getAcctBalance() - amount;
			newToBal = to.getAcctBalance() + amount;
		}
		
		DatabaseHelper.updateBalance(transferFrom, newFromBal);
		DatabaseHelper.updateBalance(transferTo, newToBal);
		
		System.out.println("Transfer success.");
		CustomerDisplay.viewAccounts(cust);
		CustomerDisplay.getCustomerOption(cust);		
	}

	public static void viewAccounts(Customer cust) {
		List<BankAccount> accounts = DatabaseHelper.selectAccounts(cust.getCustomer_id());
		String format = "%1$-20s%2$-20s%3$-20s%4$-20s%5$-20s\n";
		System.out.format(format, "USERNAME", "CUSTOMER ID",  "ACCOUNT NUMBER", "ACCOUNT BALANCE", "APPROVED");
		for(BankAccount b: accounts) {			
			System.out.format(format, cust.getUsername(), cust.getCustomer_id(), b.getAcct_num(), 
					String.format("$%.2f", b.getAcctBalance()), b.isApproved());			
		}
	}

	private static void withdraw(Customer cust) {
		
		List<BankAccount> accounts = DatabaseHelper.selectAccounts(cust.getCustomer_id());
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Withdraw from:");
		for(int i = 0; i < accounts.size(); i++) {
			temp.add(accounts.get(i).toString());
		}
		temp.add("Exit");
		String[] prompt =new String[temp.size()];
		for(int i = 0; i < prompt.length; i++) {
			prompt[i] = temp.get(i);
		}	
		
		
		
		while(true) {
			int input = InputValidator.integerGet(0, prompt.length, prompt);
			if(input < 0 || input > prompt.length) {
				System.out.println("Invalid input. Please try again.");
				CustomerDisplay.getCustomerOption(cust);
			}
			if(input == prompt.length - 1) {
				CustomerDisplay.getCustomerOption(cust);
			}
			if(accounts.get(input - 1).isApproved() == false) {
				System.out.println("This account has not yet been approved");
				getCustomerOption(cust);
			}
			if(input <= 0 || input > prompt.length) {
				continue;
			}
			double amount = InputValidator.decimalGet(0, 5000,
					"Amount: ($5,000.00 daily withdraw limit.)");
			if(amount > 5000) {
				System.out.println("$5,000.00 daily withdraw limit.");
				continue;
			}			
			if(amount <= accounts.get(input - 1).getAcctBalance()){
				double newBal = accounts.get(input - 1).getAcctBalance() - amount;
				int acctNum = accounts.get(input - 1).getAcct_num();
				DatabaseHelper.updateBalance(acctNum, newBal);
				System.out.printf("The new balance is: $%.2f", newBal);
				getCustomerOption(cust);
			}else {
				System.out.println("Insufficient funds. Withdraw amount exeeds current balance");		
			}
			
		}
	}

	private static void deposit(Customer cust) {
		
		List<BankAccount> accounts = DatabaseHelper.selectAccounts(cust.getCustomer_id());
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Deposit into:");
		for(int i = 0; i < accounts.size(); i++) {
			temp.add(accounts.get(i).toString());			
		}
		temp.add("Exit");
		String[] prompt =new String[temp.size()];
		for(int i = 0; i < prompt.length; i++) {
			prompt[i] = temp.get(i);
		}		
		
		while(true) {
			int input = InputValidator.integerGet(0, prompt.length, prompt);
			if(input < 0 || input > prompt.length) {
				System.out.println("Invalid input. Please try again.");
				CustomerDisplay.getCustomerOption(cust);
			}
			if(input == prompt.length - 1) {
				CustomerDisplay.getCustomerOption(cust);
			}
			if(accounts.get(input - 1).isApproved() == false) {
				System.out.println("This account has not yet been approved");
				getCustomerOption(cust);
			}
			double amount = InputValidator.decimalGet(0, 10000,
					"Amount: ($10,000.00 daily deposit limit)");
			if(amount > 10000) {
				System.out.println("$10,000.00 daily deposit limit");
				continue;
			}
			if(amount <= 0) {
				System.out.println("Cannot deposit $0.00 or a negative amount.");
				continue;
			}			
			double newBal = accounts.get(input - 1).getAcctBalance() + amount;
			int acctNum = accounts.get(input - 1).getAcct_num();
			DatabaseHelper.updateBalance(acctNum, newBal);
			System.out.printf("The new balance is: $%.2f", newBal);
			getCustomerOption(cust);
		}
		
	}	

}
