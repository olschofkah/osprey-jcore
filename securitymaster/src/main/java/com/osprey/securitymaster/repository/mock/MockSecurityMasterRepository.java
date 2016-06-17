package com.osprey.securitymaster.repository.mock;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomUtils;

import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class MockSecurityMasterRepository implements ISecurityMasterRepository {

	public double fetchClosingPrice(String ticker, LocalDate date) {
		return RandomUtils.nextDouble(6, 120);
	}

}
