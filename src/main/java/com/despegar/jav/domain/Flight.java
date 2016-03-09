package com.despegar.jav.domain;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Flight implements Priceable {
	
	private BigDecimal amount;
	private String airline;
	
	
	public BigDecimal getAmount() {
		return amount;
	}

	public String getAirline() {
		return airline;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public Flight(BigDecimal amount, String airline) {
		this.amount = amount;
		this.airline = airline;
	}

	@Override
	public String toString() {
		return "Flight{amount=" + amount + ", airline="+ airline + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		return true;
	}

	@JsonIgnore
	@Override
	public BigDecimal getPriceInUsd() {
		return amount;
	}
}
