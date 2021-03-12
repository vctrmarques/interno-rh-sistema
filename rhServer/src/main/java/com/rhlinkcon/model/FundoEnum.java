package com.rhlinkcon.model;

public enum FundoEnum {
	FUNFIN("FUNFIN"), FUNPREV("FUNPREV");

	private String label;

	private FundoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FundoEnum getEnumByString(String str) {
		for (FundoEnum e : FundoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
