package com.despegar.jav.service;

import java.io.Serializable;


@SuppressWarnings("serial")
public class TopRoute implements Serializable {

	private String from;
	private String to;

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
