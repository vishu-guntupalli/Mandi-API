package com.wku.mandi.dao;


import com.wku.mandi.exception.SequenceException;

public interface SequenceDao {

	long getNextSequenceId(String key) throws SequenceException;

}