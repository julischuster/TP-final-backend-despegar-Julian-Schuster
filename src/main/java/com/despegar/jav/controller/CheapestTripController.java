package com.despegar.jav.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.despegar.jav.domain.Traveler;
import com.despegar.jav.domain.World;
import com.despegar.jav.example.TopRoutesReader;
import com.despegar.jav.json.JsonFactory;
import com.despegar.jav.service.CheapestRentalFinder;
import com.despegar.jav.service.CheapestRentalGeneratorImpl;
import com.despegar.jav.service.CheapestTripGeneratorImpl;
import com.despegar.jav.service.Methods;

@Controller
@RequestMapping("/despegar-it-jav")
public class CheapestTripController {
	public static final Logger LOGGER = LoggerFactory.getLogger(CheapestTripController.class);
	
	private TopRoutesReader topRoutesReader;
	private CheapestTripGeneratorImpl cheapestTripGeneratorImpl;
	private World world;
	private JsonFactory jsonFactory;
	private CheapestRentalGeneratorImpl cheapestRentalGeneratorImpl;
	private Methods methods;
	
	@Autowired
	public CheapestTripController(TopRoutesReader topRoutesReader, CheapestTripGeneratorImpl cheapestTripGeneratorImpl,
			World world, JsonFactory jsonFactory, CheapestRentalGeneratorImpl cheapestRentalGeneratorImpl, 
			Methods methods){
		this.topRoutesReader = topRoutesReader;
		this.cheapestTripGeneratorImpl = cheapestTripGeneratorImpl;
		this.world = world;
		this.jsonFactory = jsonFactory;
		this.cheapestRentalGeneratorImpl = cheapestRentalGeneratorImpl;
		this.methods = methods;
	}

	@RequestMapping(value = "/best-trip", method = {RequestMethod.GET, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<String> hello(@RequestParam(value = "from", required = true) String from,
			@RequestParam(value = "money", required = true) int money) {
		if (StringUtils.isNotBlank(from)) {
			from = from.toUpperCase();
			Traveler traveler;
			try {
				traveler = new Traveler(money, from, world);
				while (methods.getNextTrip(topRoutesReader, cheapestTripGeneratorImpl, world, 
						cheapestRentalGeneratorImpl, traveler) != null) {
					traveler = methods.doTheTrip(methods.getNextTrip(topRoutesReader, cheapestTripGeneratorImpl, world,
							cheapestRentalGeneratorImpl, traveler), world, traveler);
				}
				return new ResponseEntity(traveler, HttpStatus.OK);
			} catch (IllegalArgumentException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
			} catch (Exception e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
			}			
		}
		return new ResponseEntity<>("You have to put a city and an amount of moeny", HttpStatus.OK);
	}
}
