package com.example.dao;

import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.example.logging.Logging;
import com.example.models.Account;
import com.example.models.AccountDisplay;
import com.example.models.Transaction;
import com.example.models.User;
import com.example.utils.ConnectionUtil;

public class AccountDaoDB implements AccountDao{
	
	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
	

	
	
	//We use callable statements to call stored procedures and functions from java
	
	
	
	/*public void addAccount(Account a) {
		try {
			Connection con = conUtil.getConnection();
			//To use our functions/procedure we need to turn off autocommit
			con.setAutoCommit(false);
			String sql = "call create_account(?,?)";
			CallableStatement cs = con.prepareCall(sql);
			
			cs.setInt(1, a.getCustomerId());
			cs.setDouble(2, a.getAccountBalance());
	
			
			cs.execute();
			
			con.setAutoCommit(true);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}*/
	public Account checkBalance(int acc_id) {
		Account accounts = new Account();
		
		 try {
		Connection con = conUtil.getConnection();
		String sql = "SELECT SUM (acc_balance) AS total\r\n"
                + "FROM accounts\r\n"
                + "WHERE acc_id ='" + acc_id + "'";
		
		PreparedStatement ps = con.prepareCall(sql);
		
		Statement s = con.createStatement();
        ResultSet rs = s.executeQuery(sql);

		
		while(rs.next()) {
			accounts.setAccountBalance(rs.getInt(1));
		}
		return accounts;
		
	} catch(SQLException e) {
		e.printStackTrace();
		
	} return null;
	}

	@Override
	public List<AccountDisplay> getAllAccounts() {
		
		List<AccountDisplay> aList = new ArrayList<AccountDisplay>();
		
		try {
			Connection con = conUtil.getConnection();
			con.setAutoCommit(false);
			//Use this syntax to call a stored function
			String sql = "{?=call get_all_accounts()}";
			
			CallableStatement cs = con.prepareCall(sql);
			
			cs.registerOutParameter(1, Types.OTHER);
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			
			while(rs.next()) {
				AccountDisplay ad = new AccountDisplay(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4));
				aList.add(ad);
			}
			
			con.setAutoCommit(true);
			return aList;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public void viewAllTransactions() {
		// TODO Auto-generated method stub
		Transaction t = new Transaction();
		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM transactions";
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				t.setTransId(rs.getInt(1));
				t.setSenderId(rs.getInt(2));
				t.setReceiverId(rs.getInt(3));
				t.setAmount(rs.getInt(4));
				System.out.println("Transaction ID: " + t.getTransId() + " Sending Acc: " + t.getSenderId() + 
						" Recieving acc: " + t.getReceiverId() + " Transfer Amount: "+ t.getAmount());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getUserAccounts (User u) {
		List<Account> accounts = new ArrayList<Account>();
		try {
			Connection con = conUtil.getConnection();
			con.setAutoCommit(false);
			String sql = "{?=call get_user_accounts(?)}";
			
			CallableStatement cs = con.prepareCall(sql);
			
			cs.registerOutParameter(1, Types.OTHER);
			cs.setInt(2, u.getId());
			
			cs.execute();
			
			ResultSet rs = (ResultSet) cs.getObject(1);
			
			while(rs.next()) {
				Account a = new Account(rs.getInt(1), rs.getInt(2), rs.getDouble(4));
				accounts.add(a);
			}
			
			u.setAccounts(accounts);
			
			con.setAutoCommit(true);
			return u;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void createAccount(Account a) {
		// TODO Auto-generated method stub
		try {
			Connection con = conUtil.getConnection();
			
			String sql =  "INSERT INTO accounts(acc_id, user_id, acc_balance) values"
					+ "(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, a.getAccountId());
			ps.setInt(2, a.getCustomerId());
			ps.setDouble(3, a.getAccountBalance());
		
			ps.execute();
					
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override	
	public void makeDeposit(Account a) {
		try {
			Connection con = conUtil.getConnection();
			String sql = "update accounts set acc_balance = ?"
					+ "where acc_id = ? and user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, a.getAccountBalance());
			ps.setInt(2, a.getAccountId());
			ps.setInt(3, a.getCustomerId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void makeWithdraw(Account a) {
		try {
			Connection con = conUtil.getConnection();
			String sql = "UPDATE accounts SET acc_balance = ?"
					+ "WHERE acc_id =? and user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, a.getAccountBalance());
			ps.setInt(2, a.getAccountId());
			ps.setInt(3, a.getCustomerId());
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void deleteAccount(Account a) {
		try {	
			Connection con = conUtil.getConnection();
			String sql = "DELETE FROM accounts WHERE accounts.user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, a.getAccountId());
			
			ps.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Account getAccountbyId(int accountId) {
		// TODO Auto-generated method stub
		Account a = new Account();
		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT acc_id, user_id, acc_balance FROM accounts WHERE acc_id = " + accountId;
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			rs.next();
			a.setAccountId(rs.getInt(1));
			a.setCustomerId(rs.getInt(2));
			a.setAccountBalance(rs.getDouble(3));
			return a;
		}catch(SQLException e) {
			System.out.println("SQL error");
			e.printStackTrace();
		}
		System.out.println("Unable to find account with matching Account ID.");
		return null;
	}
	@Override
	public void recordTransaction(Account sender, Account reciever, double amount) {
		// TODO Auto-generated method stub
		try {
			Connection con = conUtil.getConnection();
			String sql = "INSERT INTO transactions(acc_rec_id,acc_sen_id,trans_amt) values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, sender.getAccountId());
			ps.setInt(2, reciever.getAccountId());
			ps.setDouble(3, amount);
			ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

		
	}
		
	



