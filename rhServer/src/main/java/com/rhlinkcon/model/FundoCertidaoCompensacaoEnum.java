package com.rhlinkcon.model;

public enum FundoCertidaoCompensacaoEnum {
	FUNFIN("FUNFIN"), FUNPREV("FUNPREV");

	private String label;

	private FundoCertidaoCompensacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FundoCertidaoCompensacaoEnum getEnumByString(String str) {
		for (FundoCertidaoCompensacaoEnum e : FundoCertidaoCompensacaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
