package com.despegar.jav.example;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.despegar.jav.domain.Traveler;
import com.despegar.jav.domain.World;
import com.despegar.jav.json.JsonFactory;
import com.despegar.jav.service.TopRoutesReader;

import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class TopRoutesReaderTest {

	TopRoutesReader top;
	World world;
	JsonFactory jsonFactory;
	@Before
	public void setUp(){
		jsonFactory = mock(JsonFactory.class); 
		top = new TopRoutesReader(jsonFactory);
		world = new World(jsonFactory);
	}
	
	
}
