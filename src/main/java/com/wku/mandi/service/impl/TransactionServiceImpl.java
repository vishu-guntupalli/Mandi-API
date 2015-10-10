package com.wku.mandi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.wku.mandi.dao.impl.TransactionDaoImpl;
import com.wku.mandi.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionDaoImpl transactionDaoImpl;
	
	@Override
	public String buyItem(String sellerId, String buyerId, String inventoryId) {
		
		
		return null;
	}

}
