package com.despegar.jav.service;

import java.util.List;

import com.despegar.jav.domain.CheapPrice;
import com.despegar.jav.domain.Flight;
import com.despegar.jav.domain.Rental;
import com.despegar.jav.domain.Traveler;

public interface CheapestRentalFinder {
	public List<Rental> rentalFinder(String city) throws Exception;

	public Rental cheapestRental(String cityTo) throws Exception;

}
