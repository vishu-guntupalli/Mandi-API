package com.wku.mandi.dao.impl;

import com.wku.mandi.dao.ProfileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by srujangopu on 7/5/15.
 */

@Repository("ProfileDAO")
public class ProfileDaoImpl implements ProfileDao{

    @Autowired
    private MongoOperations mongoOperation;


}
