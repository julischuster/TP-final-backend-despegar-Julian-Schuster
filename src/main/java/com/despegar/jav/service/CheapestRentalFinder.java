package com.despegar.jav.service;

import java.util.List;

import com.despegar.jav.domain.Rental;

public interface CheapestRentalFinder {
	public List<Rental> rentalFinder(String city) throws Exception;

}
