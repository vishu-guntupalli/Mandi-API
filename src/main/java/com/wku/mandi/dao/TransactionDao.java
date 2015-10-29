package com.wku.mandi.dao;

import com.wku.mandi.MandiConstants;
import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.Transaction;

public interface TransactionDao {
	
	public boolean createInitialTransaction(Transaction transaction);
	
	public boolean addPendingTransactionToUser(String userId, String transactionId );
	
	public boolean deductOrAddToSeller(String sellerId, String inventoryId, int quantity);
	
	public Inventory findInventoryInSeller(String sellerId, String inventoryId);
	
	public boolean addToBuyer(String buyerId, Inventory inventory);
	
	public boolean removePendingTransactionFromUser(String userId, String transactionId);
	
	public boolean changeTransactionStatus(String transactionId, MandiConstants.TransactionStatus status );
}
