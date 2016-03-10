package com.despegar.jav.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.despegar.jav.domain.Rental;
import com.despegar.jav.domain.Stop;
import com.despegar.jav.domain.Traveler;
import com.despegar.jav.domain.World;
import com.despegar.jav.example.TopRoute;
import com.despegar.jav.example.TopRoutesReader;
import com.despegar.jav.json.JsonFactory;
import com.despegar.jav.jsonsearch.CheapPrice;
import com.despegar.jav.jsonsearch.Items;
import com.despegar.jav.jsonsearch.Price_detail;

public class MethodsTest {

	Methods methods;
	private CheapestTripGeneratorImpl cheapestTripGeneratorImpl;
	private TopRoutesReader topRoutesReader;
	private CheapestRentalGeneratorImpl cheapestRentalGeneratorImpl;
	private World world;
	private JsonFactory jsonFactory;
	@Before
	public void setUp(){
		methods = new Methods();
		cheapestTripGeneratorImpl = mock(CheapestTripGeneratorImpl.class);
		cheapestRentalGeneratorImpl = mock(CheapestRentalGeneratorImpl.class);
		topRoutesReader = mock(TopRoutesReader.class);
		jsonFactory = new JsonFactory();
		world = new World(jsonFactory);
	}
	
	@Test
	public void NextTripTest() throws Exception {
		Traveler traveler = new Traveler(5000, "BUE", world);
		TopRoute route1 = new TopRoute();
		route1.setFrom("BUE");
		route1.setTo("MIA");
		CheapPrice cheapprice1 = new CheapPrice();
		Items items = new Items();
		items.setAirline("JJ");
		items.setDeparture_date(null);
		items.setId("adfasdf");
		Price_detail pricedetail = new Price_detail();
		pricedetail.setAdult_base(200);
		pricedetail.setCurrency("adf");
		pricedetail.setTotal(300);
		items.setPrice_detail(pricedetail);
		items.setReturn_date(null);
		items.setStops(0);
		cheapprice1.setItems(Arrays.asList(items));
		Rental rental = new Rental(30, "depto");
		when(topRoutesReader.getPossibleDestinations(traveler)).thenReturn(Arrays.asList(route1.getTo()));
		when(cheapestTripGeneratorImpl.tripFinder("MIA", traveler)).thenReturn(cheapprice1);
		when(cheapestRentalGeneratorImpl.cheapestRental("MIA")).thenReturn(rental);
		
		Stop stop = methods.getNextTrip(topRoutesReader, cheapestTripGeneratorImpl, world, cheapestRentalGeneratorImpl, 
				traveler);
		
		assertEquals(rental, stop.getRental());
		
	}
	
}
