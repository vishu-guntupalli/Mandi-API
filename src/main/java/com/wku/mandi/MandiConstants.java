package com.wku.mandi;

/**
 * Created by srujangopu on 8/9/15.
 */
public class MandiConstants {
	
    /* public urls */
    public static final String PUBLIC_URL = "/public";
    public static final String TEST_URL = PUBLIC_URL+"/hello";
    public static final String NEW_USER_SAVE_URL = PUBLIC_URL+"/saveregistrationinfo";
    public static final String PROFILE_SAVE_URL = PUBLIC_URL+"/newuserprofile/save";
    public static final String ADDRESS_DETAILS_URL = PUBLIC_URL+"/address/{zipCode}";


    /* profile related urls */
    public static final String PROFILE_URL = "/profile";
    public static final String PROFILE_UPDATE_URL = PROFILE_URL+"/update";
    public static final String PROFILE_DELETE_URL = PROFILE_URL+"/{id}";
    public static final String PROFILE_FETCH_URL = PROFILE_URL+"/{id}";
    public static final String SEARCH_PROFILE_URL = PROFILE_URL+"/search";
    
    /* Transaction related URL's*/
    public static final String TRANSACTION_URL = "/transaction";
    public static final String BUY_ITEM_URL = TRANSACTION_URL+"/buyitem";

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    
    public enum TransactionStatus {
    	INITIATED,
    	PENDING,
    	COMPLETED,
    	FAILED;
    }
}
