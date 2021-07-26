package com.example.dao;


import java.util.List;

import com.example.models.Account;
import com.example.models.AccountDisplay;
import com.example.models.User;

public interface AccountDao {
	
	public void createAccount(Account a);
	
	public void deleteAccount(Account a);
	
	public List<AccountDisplay> getAllAccounts();
	
	User getUserAccounts(User u);
	
	public Account getAccountbyId(int accountId);
	
	public void recordTransaction(Account sender, Account reciever, double amount);

	//void addAccount(Account a);

	void makeDeposit(Account a);

	void makeWithdraw(Account a);

	Account checkBalance(int acc_id);


}
