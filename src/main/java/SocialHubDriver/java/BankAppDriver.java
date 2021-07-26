package SocialHubDriver.java;

import java.sql.SQLException;
import java.util.*;

import com.example.models.User;

import com.example.dao.AccountDao;
import com.example.dao.AccountDaoDB;
import com.example.dao.UserDao;
import com.example.dao.UserDaoDB;
import com.example.exceptions.InvalidCredentialsException;
import com.example.models.Account;
import com.example.models.AccountDisplay;
import com.example.models.User;
import com.example.services.AccountService;
import com.example.services.UserService;

public class BankAppDriver {
	

	public static UserDao uDao = new UserDaoDB();
	public static AccountDao aDao = new AccountDaoDB();
	public static UserService uServ = new UserService(uDao);
	public static AccountService aServ = new AccountService(aDao);
	
	public static void main(String[] args) throws SQLException {
		
		
		Scanner in = new Scanner(System.in);
		
		//This will be used to control our loop
		boolean done = false;
		
		User u = null;
		
		while(!done) {
			//	SIGN UP OR SIGN IN
			if(u == null) {
				System.out.println("Welcome to the bank!" + "\n"
						+ "Login or Signup? Press 1 to Login, Press 2 to Signup");
				int choice = Integer.parseInt(in.nextLine());
				//	SIGN IN 
				if(choice == 1) {
					System.out.print("Please enter your username: ");
					String username = in.nextLine();
					System.out.print("Please enter your password: ");
					String password = in.nextLine();
					try {
						u = uServ.signIn(username, password);
						System.out.println("Welcome " + u.getFirstName());
					} catch(Exception e) {
						System.out.println("Username or password was incorect. Goodbye");
						done = true;
					}
				//	SIGN UP
				} else {
					System.out.print("Please enter you first name: ");
					String first = in.nextLine();
					System.out.print("Please enter your last name: ");
					String last = in.nextLine();
					System.out.print("Please enter your email: ");
					String email = in.nextLine();
					System.out.print("Please enter a password: ");
					String password = in.nextLine();
					try {
						u = uServ.signUp(first, last, email, password);
						System.out.println("You may now log in with the username: " + u.getUsername());
					} catch (Exception e) {
						System.out.println("Sorry, we could not process your request");
						System.out.println("Please try again later");
						done = true;
					}
					break;
				}
			// 	END SIGN UP AND SIGN IN
			} else {
				System.out.println("To view balance press 1." +"\n"
						+ "To start an account press 2." +"\n"
						+ "To deposit funds into your account press 3." + "\n"
						+ "To withdraw funds from your account press 4." + "\n"
						+ "To transfer funds press 5." + "\n"
						+ "To loggout or quit press 6.");
				int choice = in.nextInt();
				switch(choice) {
					//	VIEW BALANCE
					case 1:
						Account a = aDao.checkBalance(u.getId());
						System.out.println("Your current balance: $" + a.getAccountBalance());
						break;
					// CREATE NEW ACCOUNT
					case 2:
						System.out.println("Your account & user ID are :" + u.getId());
						System.out.println("Enter your account ID:");
						int id = in.nextInt();
						System.out.println("Enter your user ID:");
						int u_id = in.nextInt();
						System.out.println("Let's create your account!" + "\n"
								+ "How much would you like to deposit?");
						double deposit_0 = in.nextDouble();
						a = aServ.createNewAccount(id, u_id, deposit_0);
						aDao.createAccount(a);
						System.out.println("Your account details::" + a);
						// might need to catch exception (SQL)
						break;
					//	MAKE DEPOSIT
					case 3:
						Account ac = aDao.getAccountbyId(u.getId());
						System.out.println("test :" + ac); //returning ad.getBalance() = 1.0
						System.out.println("Please tell me how much you would like to deposit: ");
						double deposit = in.nextDouble();
						Account ad = aServ.depositAmount(deposit, ac); 
						aDao.makeDeposit(ad);
						break;
					//	MAKE WITHDRAW
					case 4:
						Account acc = aDao.getAccountbyId(u.getId());
						System.out.println("test :" + acc);
						System.out.println("How much would you like to withdraw?");
						double withdrawal = in.nextDouble();
						a = aServ.withdrawAmount(withdrawal, acc);
						aDao.makeWithdraw(a);
						//System.out.println("$" + withdrawal +", has been withdrawn from your account.");
						break;
					// TRANSFER BETWEEN ACCOUNTS
					case 5:
						Account sender = aDao.getAccountbyId(u.getId());
						System.out.println("Please enter the Account ID you'd like to transfer to:");
						int receiver_id = in.nextInt();
						Account receiver = aDao.getAccountbyId(receiver_id);
						System.out.println("How much would you like to send?");
						double transfer = in.nextDouble();
						// a = aServ.transferAmount(u.getId(), receiver_id, transfer);
						Account as = aServ.withdrawAmount(transfer, sender);
						aDao.makeWithdraw(as);
						Account ar = aServ.depositAmount(transfer, receiver);
						aDao.makeDeposit(ar);
						aDao.recordTransaction(sender, receiver, transfer);
						
					// QUIT
					case 6:
						done = true;
						break;
					default:
						System.out.println("Please review the choices for valid inputs! :)");
						
					
						
						
				
		System.out.println("Goodbye :)");
		in.close();
	}
	}
	} 
	}
	}
	
			
