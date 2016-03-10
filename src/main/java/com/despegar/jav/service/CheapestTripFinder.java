package com.despegar.jav.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.despegar.jav.domain.Flight;
import com.despegar.jav.domain.Traveler;
import com.despegar.jav.jsonsearch.CheapPrice;

public interface CheapestTripFinder {
	public CheapPrice tripFinder(String city, Traveler traveler) throws URISyntaxException, IOException;

}
