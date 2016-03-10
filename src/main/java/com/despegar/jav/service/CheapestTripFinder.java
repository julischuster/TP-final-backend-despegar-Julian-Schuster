package com.despegar.jav.service;

import java.io.IOException;
import java.net.URISyntaxException;

import com.despegar.jav.domain.CheapPrice;
import com.despegar.jav.domain.Traveler;

public interface CheapestTripFinder {
	public CheapPrice tripFinder(String city, Traveler traveler) throws URISyntaxException, IOException;

}
