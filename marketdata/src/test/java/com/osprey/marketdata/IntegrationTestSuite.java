package com.osprey.marketdata;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.osprey.screen.LiveMarketDataCalcTest;
import com.osprey.screen.LiveMarketDataScreenTest;

@RunWith(Suite.class)
@SuiteClasses({ LiveMarketDataCalcTest.class, LiveMarketDataScreenTest.class, YahooQuoteTest.class,
		NasdaqSecurityMasterServiceTest.class })
public class IntegrationTestSuite {

}
