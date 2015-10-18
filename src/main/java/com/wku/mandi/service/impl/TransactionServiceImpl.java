package com.wku.mandi.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wku.mandi.dao.impl.TransactionDaoImpl;
import com.wku.mandi.dao.impl.UserDaoImpl;
import com.wku.mandi.db.Inventory;
import com.wku.mandi.db.MandiConstants.TransactionStatus;
import com.wku.mandi.db.Transaction;
import com.wku.mandi.db.User;
import com.wku.mandi.service.TransactionService;

@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionDaoImpl transactionDaoImpl;
	
	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Override
	public String buyItem(String sellerId, String buyerId, String inventoryId, int quantity) {
		
		Transaction transaction = createInitialTransactionObject(sellerId, buyerId, inventoryId, quantity);
		String transactionID = transaction.getTransactionId();
		
		boolean createInitialTransactionSuccessful = createInitialTransaction(transaction);
		if((createInitialTransactionSuccessful))
		{
			boolean addPendingTransactionToSellerSuccessful = addPendingTransactionToSeller(sellerId, transactionID);
			boolean addPendingTransactionToBuyerSuccessful = addPendingTransactionToBuyer(buyerId, transactionID);
			
			if(addPendingTransactionToSellerSuccessful && addPendingTransactionToBuyerSuccessful) {
				
				boolean deductFromSellerSuccessful = deductFromSeller(sellerId, inventoryId, quantity);
				if(deductFromSellerSuccessful) {
						
					boolean addToBuyerSucccessful = addToBuyer(buyerId, inventoryId);
					if(addToBuyerSucccessful) {
						
						removePendingTransactionFromSellerAndBuyer(sellerId, buyerId, transactionID);
						setTransactionStatusAsCompleted(transactionID);
						return transactionID;
					}
					else {
						
						rollBackSeller(sellerId, inventoryId, quantity);
						removePendingTransactionFromSellerAndBuyer(buyerId, sellerId, transactionID);
						setTransactionStatusAsFailed(transactionID);
						return null;
					}
				}
				else {
					
					removePendingTransactionFromSellerAndBuyer(buyerId, sellerId, transactionID);
					setTransactionStatusAsFailed(transactionID);
					return null;
				}
			}
			else {
				if(!addPendingTransactionToSellerSuccessful){
					transactionDaoImpl.removePendingTransactionFromUser(sellerId, transactionID);
				}
				else {
					transactionDaoImpl.removePendingTransactionFromUser(buyerId, transactionID);
				}
				setTransactionStatusAsFailed(transactionID);
				return null;
			}
		}
		else {
			return null;
		}
	}

	private void setTransactionStatusAsCompleted(String transactionID) {
		transactionDaoImpl.changeTransactionStatus(transactionID, TransactionStatus.COMPLETED);
	}

	private void setTransactionStatusAsFailed(String transactionID) {
		transactionDaoImpl.changeTransactionStatus(transactionID, TransactionStatus.FAILED);
	}

	private void removePendingTransactionFromSellerAndBuyer(String sellerId, String buyerId, String transactionID) {
		transactionDaoImpl.removePendingTransactionFromUser(sellerId, transactionID);
		transactionDaoImpl.removePendingTransactionFromUser(buyerId, transactionID);
	}

	private boolean addToBuyer(String buyerId, String inventoryId) {
		Inventory inventory = transactionDaoImpl.findInventoryInSeller(buyerId, inventoryId);
		if(inventory != null) {
		   return transactionDaoImpl.addToBuyer(buyerId, inventory);
		}
		else
			return false;
	}

	private boolean deductFromSeller(String sellerId, String inventoryId, int quantity) {
		return transactionDaoImpl.deductOrAddToSeller(sellerId, inventoryId, -quantity);
	}
	
	private boolean rollBackSeller(String sellerId, String inventoryId, int quantity) {
		return transactionDaoImpl.deductOrAddToSeller(sellerId, inventoryId, quantity);
	}

	private boolean addPendingTransactionToSeller(String sellerId, String transactionId) {
		if(noCurrentPendingTransactions(sellerId)) {
			return transactionDaoImpl.addPendingTransactionToUser(sellerId, transactionId );
		}
		else {
			return false;
		}
	}
	
	private boolean addPendingTransactionToBuyer(String buyerId, String transactionId) {
		if(noCurrentPendingTransactions(buyerId)) {
			return transactionDaoImpl.addPendingTransactionToUser(buyerId, transactionId );
		}
		else {
			return false;
		}
	}

	private boolean noCurrentPendingTransactions(String userId) {
		User user = userDaoImpl.findUserById(userId);
		if ((user.getPendingTransactions() != null)) {
			if((user.getPendingTransactions().size() == 0)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return true;
		}
	}
	
	private boolean createInitialTransaction(Transaction transaction) {
		return transactionDaoImpl.createInitialTransaction(transaction);
	}

	private Transaction createInitialTransactionObject(String sellerId, String buyerId, String inventoryId, int quantity) {
		Transaction transaction = new Transaction();
		transaction.setBuyerId(buyerId);
		transaction.setSellerId(sellerId);
		transaction.setInventoryId(inventoryId);
		transaction.setTransactionId(ObjectId.get().toString());
		transaction.setQuantity(quantity);
		transaction.setStatus(TransactionStatus.PENDING);
		
		return transaction;
	}

}
