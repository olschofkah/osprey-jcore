package com.osprey.screen.criteria.constants;

public enum CrossDirection {
	FROM_ABOVE_TO_BELOW(1), FROM_BELOW_TO_ABOVE(-1);

	private int code;

	private CrossDirection(int d) {
		this.code = d;
	}

	public int getCode() {
		return code;
	}

}
