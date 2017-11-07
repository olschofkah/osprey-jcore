package com.osprey.marketdata.service;

public class SecurityMasterLoadDateTime {

	private String dateString;

	public SecurityMasterLoadDateTime() {

	}

	public SecurityMasterLoadDateTime(String dateString) {
		this.setDateString(dateString);
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

}