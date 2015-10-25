package com.wku.mandi.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Document
public class Vault {
	
	@Id
	@JsonView(Vault.class)
	private String userId;
	
	@JsonView(Vault.class)
	private String password;
	
	@JsonView(Vault.class)
	private String emailId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}
