package com.despegar.jav.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.despegar.jav.domain.Flight;
import com.despegar.jav.domain.Traveler;
import com.despegar.jav.example.TopRoute;
import com.despegar.jav.example.TopRoutesReader;
import com.despegar.jav.json.JsonFactory;
import com.despegar.jav.jsonsearch.CheapPrice;
import com.fasterxml.jackson.core.type.TypeReference;

public class CheapestTripGeneratorImpl implements CheapestTripFinder{
	@Override
	public CheapPrice tripFinder(String city, Traveler traveler) throws URISyntaxException, IOException {
		JsonFactory jsonFactory = new JsonFactory();
		URI uri;
		try {
			uri = new URI(generateUri(traveler, city));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			throw e;
		} 
		InputStream is = Methods.conectar(uri);
		return jsonFactory.fromJson(new InputStreamReader(is), new TypeReference<CheapPrice>() {
		});
	}
	
	public String generateUri(Traveler traveler, String to){
		return "http://backoffice.despegar.com/v3/flights/search-stats/cheapest-itineraries?"
				+ "channel=site&cheapest_criteria=total&search_type=roundtrip&offset=0&limit=1&currency=USD"
				+ "&country=" + traveler.getHereCountry() + "&from=" + traveler.getHereCity() + "&to=" + to;
	}
}
