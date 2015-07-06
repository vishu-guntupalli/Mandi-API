package com.wku.mandi.service.impl;

import com.wku.mandi.dao.ProfileDao;
import com.wku.mandi.dao.SequenceDao;
import com.wku.mandi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by srujangopu on 7/5/15.
 */

@Service("ProfileService")
public class ProfileServiceImpl implements ProfileService{

    private static final String PROFILE_SEQ_KEY = "profile";

    @Autowired
    @Qualifier("SequenceDAO")
    private SequenceDao sequenceDao;

    @Autowired
    @Qualifier("ProfileDAO")
    private ProfileDao profileDao;


}
