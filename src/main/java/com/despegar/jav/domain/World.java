package com.despegar.jav.domain;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.despegar.jav.example.TopRoute;
import com.despegar.jav.example.TopRoutesReader;
import com.despegar.jav.json.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

public class World{
	private List<Country> countries;
	private JsonFactory jsonFactory;
	
	public World(JsonFactory jsonFactory){
		this.jsonFactory = jsonFactory;
		InputStream input = World.class.getResourceAsStream("Paises.json");		
		String json = new Scanner(input,"UTF-8").useDelimiter("\\A").next();
		this.countries = getCountries(json);
	}
	
	private List<Country> getCountries(String json) {
		JSONArray array = new JSONArray(json);
		List<Country> countries = new ArrayList<Country>();
		for (int i = 0; i < array.length(); i++)		
		{
			Country country = new Country();
			JSONObject jsono = array.getJSONObject(i);
			country.setCode(jsono.getString("code"));
			JSONArray jsonarray2 = jsono.getJSONArray("cities");
			Collection<String> cities = new ArrayList<String>();
			for (int j = 0; j < jsonarray2.length(); j++) {
				cities.add(jsonarray2.getJSONObject(j).getString("code"));
			}
			country.setCities(cities);
			countries.add(country);
		}
		return countries;
	}

	public String getCountryOfACity(String city){
		Country country = null;
		int i =0;
		while (i<countries.size() && country == null) {
			int j = 0;
			while (j<countries.get(i).getCities().size() && country == null) {
				if (countries.get(i).getCities().contains(city)) {
					country = countries.get(i);
				}
				j++;
			}
			i++;
		}
		return country.getCode();
	}
}
