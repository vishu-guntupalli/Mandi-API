package com.wku.mandi.db;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Inventory {
	
	@Id
	private ObjectId inventoryId;
	private String name;
	private String description;
	private int quantity;
	private String unit;
	private Date expiryDate;
	private boolean isBought;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public boolean isBought() {
		return isBought;
	}
	public void setBought(boolean isBought) {
		this.isBought = isBought;
	}
	public ObjectId getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(ObjectId inventoryId) {
		this.inventoryId = inventoryId;
	}

}
