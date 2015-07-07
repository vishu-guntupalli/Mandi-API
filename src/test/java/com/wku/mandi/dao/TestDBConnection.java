package com.wku.mandi.dao;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import junit.framework.Assert;
import org.junit.Test;

import java.net.UnknownHostException;

public class TestDBConnection {

    @Test
    public void testConenction() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("mandi");

        String dbName = database.getName();

        Assert.assertEquals("mandi", dbName);
    }

}
