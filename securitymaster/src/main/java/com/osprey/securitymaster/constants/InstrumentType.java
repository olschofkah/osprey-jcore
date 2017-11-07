package com.osprey.securitymaster.constants;

public enum InstrumentType {
	STOCK(0,"",""), 
	PREFERRED(1,"p","-"), 
	PREFERRED_CLASS_A(2,"pA","-A"),
	PREFERRED_CLASS_B(3,"pB","-B"),
	STOCK_CLASS_A(4,"/A",".A"),
	STOCK_CLASS_B(5,"/B",".B"),
	PREFERRED_WHEN_DISTRIBUTED(6,"p/WD","-$"),
	WHEN_DISTRIBUTED(7,"/WD","$"),
	WARRANT(8,"/WS","+"),
	WARRANT_CLASS_A(9,"/WS/A","+A"),
	WARRANT_CLASS_B(10,"/WS/B","+B"),
	CALLED(11,"/CL","*"),
	CLASS_A_CALLED(12,"/A/CL",".A*"),
	PREFERRED_CALLED(13,"p/CL","-*"),
	PREFERRED_A_CALLED(14,"pA/CL","-A*"),
	PREFERRED_A_WHEN_ISSUED(15,"pAw","-A#"),
	EMERGING_MARKET(16,"/EC","!"),
	PARTIAL_PAID(17,"/PP","@"),
	CONVERTIBLE(18,"/CV","%"),
	CONVERTIBLE_CALLED(19,"/CV/CL","%*"),
	CLASS_CONVERTABLE(20,"/A/CV",".A%"),
	PREFERRED_CLASS_A_CONVERTIBLE(21,"pA/CV","-A%"),
	PREFERRED_CLASS_A_WHEN_DISTRIBUTED(22,"pA/WD","-A$"),
	RIGHTS(23,"r","^"),
	UNITS(24,"/U","="),
	WHEN_ISSUED(25,"w","#"),
	RIGHTS_WHEN_ISSUED(26,"rw","^#"),
	PREFERRED_WHEN_ISSUED(27,"pw","-#"),
	CLASS_A_WHEN_ISSUED(28,"/Aw",".A#"),
	WARRANT_WHEN_ISSUED(29,"/WSw","+#"),
	TEST(30,"/TEST","~"),
	ADR(31,"",""), 
	OPTION(32,"",""), 
	ETF(33,"",""), 
	ETN(34,"",""), 
	MUTUALFUND(35,"",""), 
	BOND(36,"",""), 
	COMMODITY(37,"",""), 
	FUTURE(38,"",""),
	NEXTSHARES(39,"","");
	
	public static final InstrumentType[] SPECIAL_CLASS_STOCKS = new InstrumentType[] {	
			PREFERRED, 
			PREFERRED_CLASS_A,
			PREFERRED_CLASS_B,
			STOCK_CLASS_A,
			STOCK_CLASS_B,
			PREFERRED_WHEN_DISTRIBUTED,
			WHEN_DISTRIBUTED,
			WARRANT,
			WARRANT_CLASS_A,
			WARRANT_CLASS_B,
			CALLED,
			CLASS_A_CALLED,
			PREFERRED_CALLED,
			PREFERRED_A_CALLED,
			PREFERRED_A_WHEN_ISSUED,
			EMERGING_MARKET,
			PARTIAL_PAID,
			CONVERTIBLE,
			CONVERTIBLE_CALLED,
			CLASS_CONVERTABLE,
			PREFERRED_CLASS_A_CONVERTIBLE,
			PREFERRED_CLASS_A_WHEN_DISTRIBUTED,
			RIGHTS,
			UNITS,
			WHEN_ISSUED,
			RIGHTS_WHEN_ISSUED,
			PREFERRED_WHEN_ISSUED,
			CLASS_A_WHEN_ISSUED,
			WARRANT_WHEN_ISSUED,
			TEST
		};
	
	private int id;
	private String cqsSuffix;
	private String nasdaqSuffix;

	private InstrumentType(int id, String cqsSuffix, String nasdaqSuffix) {
		this.id = id;
		this.cqsSuffix = cqsSuffix;
		this.nasdaqSuffix = nasdaqSuffix;
	}

	public int getId() {
		return id;
	}

	public String getCqsSuffix() {
		return cqsSuffix;
	}

	public String getNasdaqSuffix() {
		return nasdaqSuffix;
	}

	/**
	 * Only valid for equities. 
	 * 
	 * @param symbol
	 * @return
	 */
	public static InstrumentType fromNasdaqSuffix(String symbol) {
		for (InstrumentType instrumentType : SPECIAL_CLASS_STOCKS) {
			if (symbol.endsWith(instrumentType.getNasdaqSuffix())) {
				return instrumentType; 
			}
		}
		return InstrumentType.STOCK;
	}

	/**
	 * Only valid for equities.
	 * 
	 * @param symbol
	 * @return
	 */
	public static InstrumentType fromCqsSuffix(String symbol) {
		for (InstrumentType instrumentType : SPECIAL_CLASS_STOCKS) {
			if (symbol.endsWith(instrumentType.getCqsSuffix())) {
				return instrumentType;
			}
		}
		return InstrumentType.STOCK;
	}

	public static InstrumentType fromId(int id) {
		for (InstrumentType instrumentType : values()) {
			if (instrumentType.id == id) {
				return instrumentType;
			}
		}
		return null;
	}
}
