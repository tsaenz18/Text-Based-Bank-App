package com.example.services;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.AccountDao;
import com.example.logging.Logging;
import com.example.models.Account;
import com.example.models.AccountDisplay;
import com.example.models.User;

public class AccountService {
		private String name;
		private ArrayList<User> users;
		private ArrayList<Account> accounts;
		private static AccountDao aDao;
	  	
		
		public AccountService(AccountDao a) {
				AccountService.aDao = a;
	}
		
		
	public Account createNewAccount(int customerId, int accountId, double deposit_0) throws SQLException {
		Account a = new Account(customerId, accountId, deposit_0);
		aDao.createAccount(a);
		Logging.logger.info("aServ: New account # " + accountId + " has been created.");

        return a;
	}
	public Account transferAmount(int sender_id, int receiver_id, double amount) {
		try {
			
			System.out.println("Your $" + amount + "successfully transfered to account #:" + receiver_id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Account viewAccount(int account_id) {
		Account a = aDao.getAccountbyId(account_id);
		return a;
	}
	public Account withdrawAmount(double withdrawAmount, Account a) {
		double balance = a.getAccountBalance();
		System.out.println("old bal ="+ balance + ", withdraw amount=" + withdrawAmount);
		if(balance >= withdrawAmount) {
			System.out.println("new balance ="+ balance);
			System.out.println("ACCid ="+ a.getAccountId() + ", CUSTid =" + a.getCustomerId() + ", ACCbal (old)=" + a.getAccountBalance());
			double new_balance = a.getAccountBalance() - withdrawAmount;
			Account withdrawn = new Account( a.getAccountId(), a.getCustomerId(), new_balance);
			System.out.println("ACCid ="+ withdrawn.getAccountId() + ", CUSTid =" + withdrawn.getCustomerId() + ", ACCbal (new)=" + withdrawn.getAccountBalance());
			aDao.makeWithdraw(withdrawn);
			System.out.println("$" + withdrawAmount + ", has been removed from your account.");
			return withdrawn;
		}else {
			System.out.println("Insufficient funds, unable to complete request.");
			return null;
		}
		
	}
	public Account depositAmount( double depositAmount, Account a) {
		double balance = a.getAccountBalance() + depositAmount;
		//a.setAccountBalance(balance);
		System.out.println("new balance ="+ balance);
		System.out.println("ACCid ="+ a.getAccountId() + ", CUSTid =" + a.getCustomerId() + ", ACCbal (old)=" + a.getAccountBalance());
		Account add = new Account( a.getAccountId(), a.getCustomerId(), balance);
		System.out.println("ACCid ="+ add.getAccountId() + ", CUSTid =" + add.getCustomerId() + ", ACCbal (new)=" + add.getAccountBalance());
		//System.out.println(add);
		aDao.makeDeposit(add);
		System.out.println("$" + depositAmount+ " has been deposited into your account.");
		return add;

	}
	//might remove transactionId
	public void addAccount(int customerId, int accountId, double balance) {
		Account a = new Account(customerId, accountId, balance);
		aDao.createAccount(a);
	}
	
	public List<AccountDisplay> getAllAccounts(){
		return aDao.getAllAccounts();
	}
	

}