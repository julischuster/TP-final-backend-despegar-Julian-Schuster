package com.despegar.jav.example;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.NameValuePair;
import org.springframework.http.HttpEntity;

import com.despegar.jav.domain.Traveler;
import com.despegar.jav.json.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TopRoutesReader {

	public static void main(String[] args) {
		TopRoutesReader reader = new TopRoutesReader();
		Traveler traveler = new Traveler(500, "BUE", "AR");
		InputStream is = null;
		System.out.println(reader.getPossibleDestinations(traveler).size());
		
	}

	public List<TopRoute> getTopRoutes(InputStream is) {
		JsonFactory jsonFactory = new JsonFactory();
		InputStream input = TopRoutesReader.class.getResourceAsStream("top_routes.json");
		return jsonFactory.fromJson(new InputStreamReader(input), new TypeReference<List<TopRoute>>() {
		});
	}
	
	public List<String> getPossibleDestinations(Traveler traveler) {
		InputStream json = conectar(traveler);
		List<TopRoute> topRoutes = getTopRoutes(json);
		List<String> possibleDestinations = new ArrayList();
		for (TopRoute route : topRoutes) {
			if (route.getFrom().compareTo(traveler.getHereCity()) == 0) {
				possibleDestinations.add(route.getTo());
			}
		}
		System.out.println(possibleDestinations.size());
		return possibleDestinations;
	}

	public static InputStream conectar(Traveler viajero) {
		HttpClient httpclient = HttpClientBuilder.create().build();
		//TODO hacer url para aca que no tenemos el servicio
		HttpGet httpget = new HttpGet("top_routes.json");
		InputStream is = ejecutarConsulta(httpclient, httpget);
		return is;
	}

	public static InputStream ejecutarConsulta(HttpClient httpclient, HttpGet httpget) {
		try {
			InputStream is = null;
			is = httpclient.execute(httpget).getEntity().getContent();
			return is;
		} catch (UnsupportedEncodingException e) {
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}
	
//	public InputStream traerJson(){
//		Traveler traveler = new Traveler(500, new City("Buenos Aires", "BUE"));
//		conectar(traveler, new City(""));
//	}
}