package com.osprey.marketdata.service;

import java.time.ZonedDateTime;

public class SecurityMasterLoadDateTime {
	
	private long millsSinceEpoch;

	public SecurityMasterLoadDateTime() {

	}

	public SecurityMasterLoadDateTime(ZonedDateTime millsSinceEpoch) {
		this.millsSinceEpoch = millsSinceEpoch.toInstant().toEpochMilli();
	}

	public long getMillsSinceEpoch() {
		return millsSinceEpoch;
	}

	public void setMillsSinceEpoch(long millsSinceEpoch) {
		this.millsSinceEpoch = millsSinceEpoch;
	}


}