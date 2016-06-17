package com.osprey.marketdata;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.feed.nasdaq.NasdaqSecurityMasterFtpService;
import com.osprey.securitymaster.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketDataDevConfiguration.class)
public class NasdaqSecurityMasterServiceTest {

	@Autowired
	private NasdaqSecurityMasterFtpService nasdaqSecurityMasterService;

	@Test
	private void nasdaqSecurityMasterTest() {

		Set<Security> securities = nasdaqSecurityMasterService.fetchSecurityMaster();
		
	}

}
