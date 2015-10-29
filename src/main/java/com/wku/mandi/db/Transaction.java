package com.wku.mandi.db;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.wku.mandi.MandiConstants.TransactionStatus;


public class Transaction {
	
	@Id
	private String transactionId;
	private String sellerId;
	private String buyerId;
	private String inventoryId;
	private Date transactionDate;
	private int quantity;
	private TransactionStatus status;

	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public TransactionStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
