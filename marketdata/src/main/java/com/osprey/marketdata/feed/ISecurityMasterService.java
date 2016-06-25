package com.osprey.marketdata.feed;

import java.util.Set;

import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.securitymaster.Security;

public interface ISecurityMasterService {

	public Set<Security> fetchSecurityMaster() throws MarketDataIOException;

}
