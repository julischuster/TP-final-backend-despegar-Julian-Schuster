package com.despegar.jav.domain;

public class Stop {

	private String citycode;
	private Flight flight;
	private Rental rental;
	public Stop(String city, Flight flight, Rental rental) {
		super();
		this.citycode = city;
		this.flight = flight;
		this.rental = rental;
	}
	public String getCitycode() {
		return citycode;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setCitycode(String city) {
		this.citycode = city;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Rental getRental() {
		return rental;
	}
	public void setRental(Rental rental) {
		this.rental = rental;
	}
		
}
