package com.example.models;

import java.util.ArrayList;

public class Account {
	
	private int accountId;
	private int customerId;
	private double accountBalance;
	
	
	public Account() {
		
	}
	//public Account(int customerId) {
		//this.customerId = customerId;
	//}
	public Account(int accountId, int customerId, double balance) {
		this.accountId = accountId;
		this.customerId = customerId;
		this.accountBalance = balance;
	}
	public Account(double balance, int customerId ) {
		this.customerId = customerId;
		this.accountBalance = balance;
	}
	

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "Account: [accountId=" + accountId + ", customerId ="+ customerId + ", accountBalacne=" + accountBalance + "]";
	}

}


