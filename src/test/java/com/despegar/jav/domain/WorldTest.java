package com.despegar.jav.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.despegar.jav.json.JsonFactory;

public class WorldTest {

	World world;
	@Before
	public void setUp(){
		world = new World(new JsonFactory());
	}
	@Test
	public void CountryFromACityTest() throws Exception {
		Country country = new Country();
		String city = "BUE";
		country.setCode(world.getCountryOfACity(city));
		assertEquals("AR", country.getCode());
	}
	@Test
	public void CountryFromACityTestError() throws Exception {
		Country country = new Country();
		String city = "MIA";
		country.setCode(world.getCountryOfACity(city));
		assertNotEquals("MX", country.getCode());
	}
}
