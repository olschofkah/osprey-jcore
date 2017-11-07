package com.osprey.securitymaster.constants;

public enum Exchange {
	NYSE_MKT("A"), NYSE("N"), NYSE_ARCA("P"), BATS("Z"), NASDAQ("Q");

	private String code;

	private Exchange(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static Exchange fromCode(String code) {
		for (Exchange e : values()) {
			if (e.getCode().equals(code)) {
				return e;
			}
		}
		
		return null;
	}

}
