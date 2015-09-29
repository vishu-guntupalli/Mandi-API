package com.wku.mandi.dao;

import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.Transaction;

public interface TransactionDao {
	
	public boolean createInitialTransaction(Transaction transaction);
	public boolean deductFromSeller(String sellerId, String inventoryId, String quantity);
	public boolean addToBuyer(Inventory inventory);
}
