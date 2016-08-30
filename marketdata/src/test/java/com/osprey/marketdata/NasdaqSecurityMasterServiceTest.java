package com.osprey.marketdata;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.nasdaq.NasdaqSecurityMasterFtpService;
import com.osprey.securitymaster.Security;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test-marketdata")
@SpringApplicationConfiguration(classes = MarketDataTestConfiguration.class)
public class NasdaqSecurityMasterServiceTest {

	@Autowired
	private NasdaqSecurityMasterFtpService nasdaqSecurityMasterService;

	@Test
	public void nasdaqSecurityMasterTest() throws MarketDataIOException{

		Set<Security> securities = nasdaqSecurityMasterService.fetchSecurityMaster();
		Assert.assertFalse(securities.isEmpty());
		
	}

}
