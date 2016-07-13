package com.osprey.securitymaster;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	public void testInsertSecurity() {

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
	
	@Test
	public void testInsertSecurityAndHistory() {

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
		
		HistoricalQuote hq = new HistoricalQuote(symbol, LocalDate.now());
		hq.setAdjClose(15);
		hq.setClose(20);
		hq.setHigh(25);
		hq.setLow(10);
		hq.setOpen(23);
		hq.setVolume(235235423);
		
		List<HistoricalQuote> histList = new ArrayList<HistoricalQuote>(2);
		histList.add(hq);
		
		SecurityQuoteContainer sqc = new SecurityQuoteContainer(s.getKey());
		sqc.setSecurity(s);
		sqc.setHistoricalQuotes(histList);

		repo.persist(sqc);

	}

}
