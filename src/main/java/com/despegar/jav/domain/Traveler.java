package com.despegar.jav.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.despegar.jav.example.TopRoutesReader;
import com.despegar.jav.jsonsearch.CheapPrice;
import com.despegar.jav.service.CheapestRentalGeneratorImpl;
import com.despegar.jav.service.CheapestTripGeneratorImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Traveler {

	private int wallet;
	@JsonIgnore
	private Collection<String> visitedCities;
	private ArrayList<Stop> destinations;
	@JsonIgnore
	private String hereCity;
	@JsonIgnore
	private String hereCountry;

	public ArrayList<Stop> getDestinations() {
		return destinations;
	}

	public String getHereCity() {
		return hereCity;
	}

	public String getHereCountry() {
		return hereCountry;
	}

	public void setDestinations(ArrayList<Stop> destinations) {
		this.destinations = destinations;
	}

	public void setHereCity(String hereCity) {
		this.hereCity = hereCity;
	}

	public void setHereCountry(String hereCountry) {
		this.hereCountry = hereCountry;
	}

	public Collection<String> getVisitedCities() {
		return visitedCities;
	}

	public int getWallet() {
		return wallet;
	}

	public void setVisitedCities(Collection<String> visitedCities) {
		this.visitedCities = visitedCities;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	public Traveler(int wallet, String here, World world) throws IllegalArgumentException {
		visitedCities = new ArrayList<String>();
		if (wallet >= 0) {
			this.wallet = wallet;
		} else {
			throw new IllegalArgumentException("The amount of money must be possitive");
		}
		for (Country country : world.getCountries()) {
			if (country.getCities().contains(here)) {
				this.hereCity = here.toUpperCase();
				this.hereCountry = world.getCountryOfACity(here);
			}
		}
		if (this.hereCity == null || this.hereCountry == null) {
			throw new IllegalArgumentException("City doesn't exists");
		}
		this.destinations = new ArrayList<Stop>();
		visitedCities.add(hereCity);
	}
}
