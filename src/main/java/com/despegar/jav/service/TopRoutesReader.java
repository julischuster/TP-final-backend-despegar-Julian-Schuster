package com.despegar.jav.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.despegar.jav.json.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class TopRoutesReader implements TopRoutesFinder {
	JsonFactory jsonFactory;	

	public TopRoutesReader(JsonFactory jsonFactory) {
		this.jsonFactory = jsonFactory;
	}

	public List<TopRoute> getTopRoutes(InputStream is){
		InputStream input = null;
		try {
			input = TopRoutesReader.class.getResourceAsStream("top_routes.json");
		} catch (NullPointerException e) {
			throw e;
		}
		try {
			return jsonFactory.fromJson(new InputStreamReader(input), new TypeReference<List<TopRoute>>() {
			});
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}

	}
}
