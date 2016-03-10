package com.despegar.jav.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import com.despegar.jav.domain.Country;
import com.despegar.jav.domain.Flight;
import com.despegar.jav.domain.Rental;
import com.despegar.jav.domain.Traveler;
import com.despegar.jav.domain.World;
import com.despegar.jav.example.TopRoute;
import com.despegar.jav.example.TopRoutesReader;
import com.despegar.jav.json.JsonFactory;
import com.despegar.jav.jsonsearch.CheapPrice;
import com.fasterxml.jackson.core.type.TypeReference;

public class CheapestRentalGeneratorImpl implements CheapestRentalFinder {

	@Override
	public List<Rental> rentalFinder(String city) throws Exception {
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

	public Rental cheapestRental(String city) throws Exception {
		List<Rental> rentals = rentalFinder(city);
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

	public static InputStream conectar(String city) throws IllegalArgumentException, IOException{
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet("http://rc.vr.despegar.it/v3/vr/home/ads/" + city);
		InputStream is;
		try {
			is = ejecutarConsulta(httpclient, httpget);
		} catch (IOException e) {
			throw e;
		}
		return is;
	}

	public static InputStream ejecutarConsulta(HttpClient httpclient, HttpGet httpget) throws 
	IOException {
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
