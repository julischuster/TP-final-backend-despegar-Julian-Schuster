package com.despegar.jav.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.despegar.jav.domain.CheapPrice;
import com.despegar.jav.domain.Flight;
import com.despegar.jav.domain.Rental;
import com.despegar.jav.domain.Stop;
import com.despegar.jav.domain.Traveler;
import com.despegar.jav.domain.World;

public class Methods {

	private static final Logger LOGGER = LoggerFactory.getLogger(Methods.class);
	
	private CheapestTripFinder cheapestTripGeneratorImpl;
	private CheapestRentalFinder cheapestRentalGeneratorImpl;
	private World world;
	private TopRoutesFinder topRoutesReader;
	
	public Methods(CheapestTripFinder cheapestTripGeneratorImpl, CheapestRentalFinder cheapestRentalGeneratorImpl,
			World world, TopRoutesFinder topRoutesReader) {
		this.cheapestTripGeneratorImpl = cheapestTripGeneratorImpl;
		this.cheapestRentalGeneratorImpl = cheapestRentalGeneratorImpl;
		this.world = world;
		this.topRoutesReader = topRoutesReader;
	}

	public static InputStream conectar(URI url) throws IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet(url);
		InputStream is;
		try {
			is = ejecutarConsulta(httpclient, httpget);
		} catch (IOException e) {
			throw e;
		}
		return is;
	}

	public static InputStream ejecutarConsulta(HttpClient httpclient, HttpGet httpget) throws IOException {
		try {
			InputStream is = null;
			is = httpclient.execute(httpget).getEntity().getContent();
			return is;
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}

	public Stop getNextTrip(Traveler traveler) throws Exception {
		List<String> possibleDestinations = getPossibleDestinations(traveler);
		CheapPrice cheaptrip = null;
		@SuppressWarnings("unused")
		Integer cheapestprice = null;
		String variableCity = null;
		Rental rental = null;
		Stop stop = null;
		try {
			for (String cityTo : possibleDestinations) {
				LOGGER.info(cityTo + " es la posible ciudad ciudad");
				if (!traveler.getVisitedCities().contains(cityTo)) {
					LOGGER.info("el usuario todavia no visito esta ciudad");
					if (cheapestRental(cityTo) != null) {
						LOGGER.info("existen hoteles en esta ciudad");
						LOGGER.info((cheapestTripGeneratorImpl.tripFinder(cityTo, traveler).getItems().get(0)
								.getPrice_detail().getTotal() + "es el total del vuelo mas barato"));
						LOGGER.info(cheapestRental(cityTo).getAmount()
								+ "es el precio del rental");
						if (traveler.getWallet() >= (cheapestTripGeneratorImpl.tripFinder(cityTo, traveler).getItems()
								.get(0).getPrice_detail().getTotal()
								+ cheapestRental(cityTo).getAmount())) {
							if (cheaptrip == null) {
								cheaptrip = cheapestTripGeneratorImpl.tripFinder(cityTo, traveler);
								variableCity = cityTo;
								cheapestprice = cheaptrip.getItems().get(0).getPrice_detail().getTotal();
								rental = cheapestRental(cityTo);
								Flight flight = new Flight(new BigDecimal(cheaptrip.getItems().get(0).getPrice_detail().getTotal()),
										cheaptrip.getItems().get(0).getAirline());
								stop = new Stop(variableCity, flight, rental);
							} else if ((cheaptrip.getItems().get(0).getPrice_detail().getTotal()
									+ rental.getAmount()) > (cheapestTripGeneratorImpl.tripFinder(cityTo, traveler)
											.getItems().get(0).getPrice_detail().getTotal()
											+ cheapestRental(cityTo).getAmount())) {
								cheaptrip = cheapestTripGeneratorImpl.tripFinder(cityTo, traveler);
								variableCity = cityTo;
								cheapestprice = cheaptrip.getItems().get(0).getPrice_detail().getTotal();
								rental = cheapestRental(cityTo);
								Flight flight = new Flight(new BigDecimal(cheaptrip.getItems().get(0).getPrice_detail().getTotal()),
										cheaptrip.getItems().get(0).getAirline());
								stop = new Stop(variableCity, flight, rental);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return stop;
	}

	public Traveler doTheTrip(Stop stop, Traveler traveler) {

		traveler.setWallet(traveler.getWallet() - stop.getFlight().getPriceInUsd().intValue() - 
				stop.getRental().getAmount());
		traveler.getVisitedCities().add(stop.getCitycode());
		traveler.setHereCity(stop.getCitycode());
		traveler.setHereCountry(world.getCountryOfACity(stop.getCitycode()));
		traveler.getDestinations().add(stop);
		return traveler;
	}

	public Rental cheapestRental(String city) throws Exception {
		List<Rental> rentals = cheapestRentalGeneratorImpl.rentalFinder(city);
		Rental finalRental = null;
		for (Rental rental : rentals) {
			if (finalRental == null) {
				finalRental = rental;
			} else {
				if (rental.getAmount() < finalRental.getAmount()) {
					finalRental = rental;
				}
			}
		}
		return finalRental;
	}
	
	public List<String> getPossibleDestinations(Traveler traveler) throws NullPointerException {
		InputStream json = null;
		List<TopRoute> topRoutes;
		try {
			topRoutes = topRoutesReader.getTopRoutes(json);
			
		} catch (RuntimeException e) {
			throw e;
		}

		List<String> possibleDestinations = new ArrayList<String>();
		for (TopRoute route : topRoutes) {
			if (route.getFrom().compareTo(traveler.getHereCity()) == 0) {
				possibleDestinations.add(route.getTo());
			}
		}
		return possibleDestinations;
	}
}