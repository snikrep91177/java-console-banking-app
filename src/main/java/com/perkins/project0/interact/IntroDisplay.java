package com.perkins.project0.interact;

import com.perkins.project0.service.InputValidator;

public class IntroDisplay implements Displayable {

	@Override
	public void display() {

		getUserType();
	}

	private void getUserType() {

		int userType = InputValidator.integerGet(0, 4, "Please make a selection:", "Customer", "Employee",
				"Administrator", "Exit");
		if(userType < 0 || userType > 4) {
			System.out.println("Invalid input. Please try again.");
			getUserType();
		}

		switch (userType) {
		case 1:
			CustomerDisplay c = new CustomerDisplay();
			c.display();
			break;
		case 2:
			EmployeeLoginDisplay eld = new EmployeeLoginDisplay();
			eld.display();
			break;
		case 3:
			AdministratorLoginDisplay ald = new AdministratorLoginDisplay();
			ald.display();
			break;
		case 4:
			System.out.println("Goodbye!!!");
			System.exit(0);
		}

	}

}
