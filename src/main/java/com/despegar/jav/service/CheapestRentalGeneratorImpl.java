package com.despegar.jav.service;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.despegar.jav.domain.Rental;
import com.despegar.jav.json.JsonFactory;

public class CheapestRentalGeneratorImpl implements CheapestRentalFinder {

	@SuppressWarnings("resource")
	@Override
	public List<Rental> rentalFinder(String city) throws Exception {
		@SuppressWarnings("unused")
		JsonFactory jsonFactory = new JsonFactory();
		URI uri;
		try {
			uri = new URI(generateUri(city));
		} catch (URISyntaxException e) {
			throw e;
		} 
		InputStream input = Methods.conectar(uri);
		String json = new Scanner(input, "UTF-8").useDelimiter("\\A").next();
		List<Rental> rentals = getRentals(json);
		return rentals;
	}

	private String generateUri(String city) {
		return "http://rc.vr.despegar.it/v3/vr/home/ads/" + city;
	}
	
	private List<Rental> getRentals(String json) {
		JSONArray array = new JSONArray(json);
		List<Rental> rentals = new ArrayList<Rental>();
		for (int i = 0; i < array.length(); i++)		
		{
			JSONObject jsono = array.getJSONObject(i);
			String title = jsono.getString("title");
			int amountInUsd = jsono.getJSONObject("prices").getInt("USD");
			Rental rental = new Rental(amountInUsd, title);
			rentals.add(rental);
		}
		return rentals;
	}
}
