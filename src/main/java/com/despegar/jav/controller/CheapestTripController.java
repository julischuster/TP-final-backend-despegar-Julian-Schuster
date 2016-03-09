package com.despegar.jav.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.despegar.jav.service.CheapestTripGeneratorImpl;

@Controller
@RequestMapping("/despegar-it-jav")
public class CheapestTripController {
	
	
	private TopRoutesReader topRoutesReader;
	private CheapestTripGeneratorImpl cheapestTripGeneratorImpl;
	private World world;
	private JsonFactory jsonFactory;
	
	@Autowired
	public CheapestTripController(TopRoutesReader topRoutesReader, CheapestTripGeneratorImpl cheapestTripGeneratorImpl,
			World world, JsonFactory jsonFactory){
		this.topRoutesReader = topRoutesReader;
		this.cheapestTripGeneratorImpl = cheapestTripGeneratorImpl;
		this.world = world;
		this.jsonFactory = jsonFactory;
	}

	@RequestMapping(value = "/best-trip", method = {RequestMethod.GET, RequestMethod.GET})
	@ResponseBody
	public Traveler hello(@RequestParam(value = "from", required = true) String from,
			@RequestParam(value = "money", required = true) int money) {
		if (StringUtils.isNotBlank(from)) {
			String country = world.getCountryOfACity(from);
			Traveler traveler = new Traveler(money, from, country);
			while (traveler.getNextTrip(topRoutesReader, cheapestTripGeneratorImpl, world) != null) {
				traveler.doTheTrip(traveler.getNextTrip(topRoutesReader, cheapestTripGeneratorImpl, world), world);
			}
			return traveler;
			
		}
		return null;
	}
	@RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String helloWitName(@PathVariable String name,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "age", required = true) String age) {
		if (StringUtils.isNotBlank(lastName)) {
			return "Hello " + name + " " + lastName + " . Edad: " + age;
		}
		return "Hello " + name;
	}

}
