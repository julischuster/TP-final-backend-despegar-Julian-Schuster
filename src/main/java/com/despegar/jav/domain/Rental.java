package com.despegar.jav.domain;

import java.io.Serializable;

public class Rental implements Serializable{
	private int amount;
    private String title;
    
	public Rental(int amount, String title) {
		this.amount = amount;
		this.title = title;
	}
	public int getAmount() {
		return amount;
	}
	public String getTitle() {
		return title;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setTitle(String title) {
		this.title = title;
	}

    
}
