package com.despegar.jav.domain;

import java.io.Serializable;
import java.util.Collection;

public class Country implements Serializable{
	private String code;
	private Collection<String> cities;

	public String getCode() {
		return code;
	}

	public Collection<String> getCities() {
		return cities;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCities(Collection<String> cities) {
		this.cities = cities;
	}		
}
