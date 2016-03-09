package com.despegar.jav.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

	public static void main(String args[]){
		Traveler traveler = new Traveler(500, "BUE", "AR");
		CheapPrice cheapprice;
		CheapestTripGeneratorImpl asd = new CheapestTripGeneratorImpl();
		cheapprice = asd.tripFinder("BRC", traveler);
		System.out.println(cheapprice.getItems().get(0).getAirline());		
	}

	@Override
	public CheapPrice tripFinder(String city, Traveler traveler) {
		JsonFactory jsonFactory = new JsonFactory();
//		InputStream inputStream = CheapestTripGeneratorImpl.class.getResourceAsStream("http://backoffice.despegar.com/"
//				+ "v3/flights/search-stats/cheapest-itineraries?channel=site&cheapest_criteria=total&"
//				+ "search_type=roundtrip&offset=0&limit=1&currency=USD&country="+traveler.getHere().getCountry()+
//				"&from="+traveler.getHere()+"&to="+city);
		InputStream is = conectar(traveler, city);
		System.out.println(is);
		return jsonFactory.fromJson(new InputStreamReader(is), new TypeReference<CheapPrice>() {
		});
	}
	
	public static InputStream conectar(Traveler viajero, String to) {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpget = new HttpGet("http://backoffice.despegar.com/v3/flights/search-stats/cheapest-itineraries?"
				+ "channel=site&cheapest_criteria=total&search_type=roundtrip&offset=0&limit=1&currency=USD"
				+ "&country=" + viajero.getHereCountry() + "&from=" + viajero.getHereCity() + "&to=" + to);
		InputStream is = ejecutarConsulta(httpclient, httpget);
		return is;
	}

	public static InputStream ejecutarConsulta(HttpClient httpclient, HttpGet httpget) {
		try {
			System.out.println(httpget.toString());
			InputStream is = null;
			is = httpclient.execute(httpget).getEntity().getContent();
			return is;
		} catch (UnsupportedEncodingException e) {
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}
}
