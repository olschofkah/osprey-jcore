package com.osprey.securitymaster.repository;

import java.time.LocalDate;

public interface ISecurityMasterRepository {

	
	public double fetchClosingPrice(String ticker, LocalDate date);
}
