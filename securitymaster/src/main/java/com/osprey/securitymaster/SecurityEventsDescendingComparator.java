package com.osprey.securitymaster;

import java.util.Comparator;

public class SecurityEventsDescendingComparator implements Comparator<SecurityEvent> {
	@Override
	public int compare(SecurityEvent o1, SecurityEvent o2) {
		return o2.getDate().compareTo(o1.getDate());
	}
}
