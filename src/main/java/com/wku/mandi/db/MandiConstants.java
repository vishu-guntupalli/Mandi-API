package com.wku.mandi.db;

/**
 * Created by srujangopu on 8/9/15.
 */
public interface MandiConstants {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    
    public enum TransactionStatus {
    	INITIATED,
    	PENDING,
    	COMPLETED;
    }
}
