package com.despegar.jav.service;

import java.io.InputStream;
import java.util.List;

public interface TopRoutesFinder {
	public List<TopRoute> getTopRoutes(InputStream is);
}
