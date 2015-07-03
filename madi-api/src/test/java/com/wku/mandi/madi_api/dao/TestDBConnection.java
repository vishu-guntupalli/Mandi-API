package com.wku.mandi.madi_api.dao;

import java.net.UnknownHostException;
import junit.framework.Assert;
import org.junit.Test;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class TestDBConnection {
	
@Test
public void testConenction() throws UnknownHostException {
	MongoClient client = new MongoClient();
    DB database = client.getDB("mandi");
    
    String dbName = database.getName();
    
    Assert.assertEquals("mandi", dbName);
}

}
