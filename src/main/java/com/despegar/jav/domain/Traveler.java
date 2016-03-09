package com.despegar.jav.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.despegar.jav.example.TopRoute;
import com.despegar.jav.example.TopRoutesReader;
import com.despegar.jav.jsonsearch.CheapPrice;
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

	public Traveler(int wallet, String here, String hereCountry) {
		visitedCities = new ArrayList<String>();
		this.wallet = wallet;
		this.hereCity = here;
		this.hereCountry = hereCountry;
		this.destinations = new ArrayList();
		visitedCities.add(hereCity);
	}

	public Stop getNextTrip(TopRoutesReader topRoutesReader, CheapestTripGeneratorImpl cheapestTripGeneratorImpl,
			World world) {
		List<String> possibleDestinations = topRoutesReader.getPossibleDestinations(this);
		CheapPrice cheaptrip = null;
		Integer cheapestprice = null;
		String variableCity = null;
		for (String cityTo : possibleDestinations) {
			if (!this.visitedCities.contains(cityTo)) {
				if (this.wallet >= cheapestTripGeneratorImpl.tripFinder(cityTo, this).getItems().get(0)
						.getPrice_detail().getTotal()) {
					if (cheaptrip == null) {
						cheaptrip = cheapestTripGeneratorImpl.tripFinder(cityTo, this);
						variableCity = cityTo;
						cheapestprice = cheaptrip.getItems().get(0).getPrice_detail().getTotal();
					} else if (cheaptrip.getItems().get(0).getPrice_detail().getTotal() > cheapestTripGeneratorImpl
							.tripFinder(cityTo, this).getItems().get(0).getPrice_detail().getTotal()) {
						cheaptrip = cheapestTripGeneratorImpl.tripFinder(cityTo, this);
						variableCity = cityTo;
						cheapestprice = cheaptrip.getItems().get(0).getPrice_detail().getTotal();
					}
				}
			}
		}

		Stop stop = null;
		if (cheaptrip != null) {
			Flight flight = new Flight(new BigDecimal(cheaptrip.getItems().get(0).getPrice_detail().getTotal()),
					cheaptrip.getItems().get(0).getAirline());
			stop = new Stop(variableCity, flight);			
		}
		return stop;
	}

	public void doTheTrip(Stop stop, World world) {
		this.wallet = this.wallet - stop.getFlight().getPriceInUsd().intValue();
		this.visitedCities.add(stop.getCitycode());
		this.setHereCity(stop.getCitycode());
		this.setHereCountry(world.getCountryOfACity(stop.getCitycode()));
		this.destinations.add(stop);
	}
}
