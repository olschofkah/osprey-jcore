package com.osprey.marketdata.feed;

import java.util.Set;

import com.osprey.securitymaster.Security;

public interface ISecurityMasterService {

	public Set<Security> fetchSecurityMaster();

}
