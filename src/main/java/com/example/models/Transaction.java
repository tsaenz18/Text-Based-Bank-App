package com.example.models;

public class Transaction {
	
	private int transactionId;
	private int senderId;
	private int receiverId;
	private double amount;
	//sender new balance
	//receiver new balance
	
	public Transaction() {
		
	}
	
	public Transaction (int transactionId, int senderId, int receiverId, double amount) {
		this.transactionId =transactionId;
		this.senderId =senderId;
		this.receiverId =receiverId;
		this.amount =amount;
	}
	public int getTransId() {
		return transactionId;
	}
	public void setTransId(int transactionId) {
		this.transactionId =transactionId;
	}
	
	public int getSenderId() {
		return senderId;
	}
	
	public void setSenderId(int senderId) {
		this.senderId =senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId =receiverId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount =amount;
	}
	
	@Override
	public String toString() {
		return ("Transaction ID =" + transactionId + ", Sender ID =" + senderId + ", Receiver ID =" + receiverId + ", Amount Transfered:" + amount);
		
	}
	
}
