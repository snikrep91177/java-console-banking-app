package com.perkins.project0.models;

public class BankAccount {
	
	private int acct_num;
	private int customer_ID;
	private double acctBalance;
	private boolean approved;
	
	public BankAccount() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param acct_num
	 * @param acctID
	 * @param acctBalance
	 * @param approved
	 */
	public BankAccount(int acct_num, int customer_id, double acctBalance, boolean approved) {
		super();
		this.acct_num = acct_num;
		this.customer_ID = customer_id;
		this.acctBalance = acctBalance;
		this.approved = approved;
	}

	/**
	 * @return the acct_num
	 */
	public int getAcct_num() {
		return acct_num;
	}

	/**
	 * @param acct_num the acct_num to set
	 */
	public void setAcct_num(int acct_num) {
		this.acct_num = acct_num;
	}

	/**
	 * @return the acctID
	 */
	public int getAcctID() {
		return customer_ID;
	}

	/**
	 * @param acctID the acctID to set
	 */
	public void setAcctID(int acctID) {
		this.customer_ID = acctID;
	}

	/**
	 * @return the acctBalance
	 */
	public double getAcctBalance() {
		return acctBalance;
	}

	/**
	 * @param acctBalance the acctBalance to set
	 */
	public void setAcctBalance(double acctBalance) {
		this.acctBalance = acctBalance;
	}

	/**
	 * @return the approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
//		return "BankAccount [acct_num=" + acct_num + ", acctID=" + customer_ID + ", acctBalance=" + acctBalance
//				+ ", approved=" + approved + "]";
//		String formattedDbl = 
		String[] formatted = {"ACCOUNT NUMBER", "ACCOUNT BALANCE", "APPROVED", "   " +
				Integer.toString(acct_num), String.format("$%.2f", acctBalance), 
				Boolean.toString(approved)};
		return String.format("%1$-17s%2$-20s%3$-20s\n%4$-20s%5$-20s%6$-20s", (Object[])formatted);
	}		
}