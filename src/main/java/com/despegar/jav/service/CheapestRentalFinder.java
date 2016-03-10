package com.despegar.jav.service;

import java.util.List;

import com.despegar.jav.domain.Flight;
import com.despegar.jav.domain.Rental;
import com.despegar.jav.domain.Traveler;
import com.despegar.jav.jsonsearch.CheapPrice;

public interface CheapestRentalFinder {
	public List<Rental> rentalFinder(String city) throws Exception;

}
