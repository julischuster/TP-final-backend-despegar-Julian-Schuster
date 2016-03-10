package com.despegar.jav.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.despegar.jav.domain.Traveler;
import com.fasterxml.jackson.core.type.TypeReference;

public interface TopRoutesFinder {
	public List<TopRoute> getTopRoutes(InputStream is);

	public List<String> getPossibleDestinations(Traveler traveler) throws NullPointerException;
}
