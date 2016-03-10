package com.despegar.jav.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class CheapPrice implements Serializable {

	private List<Items> items;

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}
}