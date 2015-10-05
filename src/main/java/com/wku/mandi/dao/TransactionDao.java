package com.wku.mandi.dao;

import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.Transaction;

public interface TransactionDao {
	
	public boolean createInitialTransaction(Transaction transaction);
	public boolean deductFromSeller(String sellerId, String inventoryId, int quantity);
	public boolean addToBuyer(String buyerId, Inventory inventory);
}
