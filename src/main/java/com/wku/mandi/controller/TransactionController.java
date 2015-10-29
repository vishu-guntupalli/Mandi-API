package com.wku.mandi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wku.mandi.MandiConstants;
import com.wku.mandi.dao.impl.TransactionDaoImpl;
import com.wku.mandi.service.TransactionService;


@RestController
public class TransactionController {
   
	Log log = LogFactory.getLog(TransactionDaoImpl.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(method = RequestMethod.POST, value = MandiConstants.BUY_ITEM_URL, produces={"application/json"})
	public @ResponseBody String buyItem( @RequestBody String sellerId, @RequestBody String buyerId, @RequestBody String inventoryId, @RequestBody int quantity ) {
		log.debug("POST call for buying an item called with seller ID "+ sellerId +" buyer ID "+ buyerId + " inventory ID "+inventoryId +" quantity "+ quantity);
		
		String transactionId = transactionService.buyItem(sellerId, buyerId, inventoryId, quantity);
		if(transactionId != null){
			return transactionId;
		}
		else
			return null;
	}
}
