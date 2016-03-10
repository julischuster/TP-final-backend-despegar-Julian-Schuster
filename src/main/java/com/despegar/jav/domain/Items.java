package com.despegar.jav.domain;

public class Items {
		private String id;
		private String airline;
		private Integer stops;
		private String departure_date;
		private String return_date;
		private Price_detail price_detail;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAirline() {
			return airline;
		}

		public void setAirline(String airline) {
			this.airline = airline;
		}

		public Integer getStops() {
			return stops;
		}

		public void setStops(Integer stop) {
			this.stops = stop;
		}

		public String getDeparture_date() {
			return departure_date;
		}

		public void setDeparture_date(String departure_date) {
			this.departure_date = departure_date;
		}

		public String getReturn_date() {
			return return_date;
		}

		public void setReturn_date(String return_date) {
			this.return_date = return_date;
		}

		public Price_detail getPrice_detail() {
			return price_detail;
		}

		public void setPrice_detail(Price_detail pricedetail) {
			this.price_detail = pricedetail;
		}

}
