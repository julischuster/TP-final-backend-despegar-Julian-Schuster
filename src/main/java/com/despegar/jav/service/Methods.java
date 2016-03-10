package com.despegar.jav.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
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
	
	
	public Stop getNextTrip(TopRoutesFinder topRoutesReader, CheapestTripFinder cheapestTripGeneratorImpl,
			World world, CheapestRentalFinder cheapestRentalGeneratorImpl, Traveler traveler) throws Exception {
		List<String> possibleDestinations = topRoutesReader.getPossibleDestinations(traveler);
		CheapPrice cheaptrip = null;
		Integer cheapestprice = null;
		String variableCity = null;
		Rental rental = null;
		try {
			for (String cityTo : possibleDestinations) {
				LOGGER.info(cityTo + " es la posible ciudad ciudad");
				if (!traveler.getVisitedCities().contains(cityTo)) {
					LOGGER.info("el usuario todavia no visito esta ciudad");
					if (cheapestRentalGeneratorImpl.cheapestRental(cityTo) != null) {
						LOGGER.info("existen hoteles en esta ciudad");
						LOGGER.info((cheapestTripGeneratorImpl.tripFinder(cityTo, traveler).getItems()
								.get(0).getPrice_detail().getTotal() + "es el total del vuelo mas barato"));
						LOGGER.info(cheapestRentalGeneratorImpl.cheapestRental(cityTo)
								.getAmount() + "es el precio del rental");
						if (traveler.getWallet() >= (cheapestTripGeneratorImpl.tripFinder(cityTo, traveler).getItems()
								.get(0).getPrice_detail().getTotal() + cheapestRentalGeneratorImpl
								.cheapestRental(cityTo).getAmount())) {
							if (cheaptrip == null) {
								cheaptrip = cheapestTripGeneratorImpl.tripFinder(cityTo, traveler);
								variableCity = cityTo;
								cheapestprice = cheaptrip.getItems().get(0).getPrice_detail().getTotal();
								rental = cheapestRentalGeneratorImpl.cheapestRental(cityTo);
							} else if ((cheaptrip.getItems().get(0).getPrice_detail().getTotal()
									+ rental.getAmount()) > (cheapestTripGeneratorImpl.tripFinder(cityTo, traveler)
											.getItems().get(0).getPrice_detail().getTotal()
											+ cheapestRentalGeneratorImpl.cheapestRental(cityTo).getAmount())) {
								cheaptrip = cheapestTripGeneratorImpl.tripFinder(cityTo, traveler);
								variableCity = cityTo;
								cheapestprice = cheaptrip.getItems().get(0).getPrice_detail().getTotal();
								rental = cheapestRentalGeneratorImpl.cheapestRental(cityTo);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}

		Stop stop = null;
		if (cheaptrip != null) {
			Flight flight = new Flight(new BigDecimal(cheaptrip.getItems().get(0).getPrice_detail().getTotal()),
					cheaptrip.getItems().get(0).getAirline());
			stop = new Stop(variableCity, flight, rental);
		}
		return stop;
	}

	public Traveler doTheTrip(Stop stop, World world, Traveler traveler) {
		traveler.setWallet(traveler.getWallet() - stop.getFlight().getPriceInUsd().intValue() - stop.getRental()
				.getAmount());
		traveler.getVisitedCities().add(stop.getCitycode());
		traveler.setHereCity(stop.getCitycode());
		traveler.setHereCountry(world.getCountryOfACity(stop.getCitycode()));
		traveler.getDestinations().add(stop);
		return traveler;
	}
}
