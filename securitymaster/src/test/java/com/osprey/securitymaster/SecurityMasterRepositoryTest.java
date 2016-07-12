package com.osprey.securitymaster;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.securitymaster.constants.Exchange;
import com.osprey.securitymaster.constants.InstrumentType;
import com.osprey.securitymaster.repository.jdbctemplate.SecurityMasterJdbcRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SecurityMasterTestConfiguration.class)
@ActiveProfiles("test")
public class SecurityMasterRepositoryTest {

	@Autowired
	private SecurityMasterJdbcRepository repo;

	@Test
	public void testInsert() {

		String symbol = RandomStringUtils.randomAlphabetic(5);

		Security s = new Security(new SecurityKey(symbol, null));
		s.setCompanyDescription("Test Compnay Description");
		s.setCompanyName("Test Company Name");
		s.setCountry("US");
		s.setCurrency("USD");
		s.setEmployeeCount(3);
		s.setExchange(Exchange.NASDAQ);
		s.setIndustry("Financial Services");
		s.setInstrumentType(InstrumentType.TEST);
		s.setLotSize(100);
		s.setPreviousClose(69.0);
		s.setSector("Unknown");
		s.setState("MD");

		repo.persist(s);

	}

}
