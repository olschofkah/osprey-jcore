package com.osprey.securitymaster.constants;

public enum SecurityEventType {
	EARNINGS_EST("EE"), EARNINGS_ACT("EA"), DIV("DV"), EX_DIV("ED"), REVENUE("RV");

	private String code;

	private SecurityEventType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static SecurityEventType fromCode(String code) {
		for (SecurityEventType t : values()) {
			if (t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}
}
