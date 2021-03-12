package com.rhlinkcon.model;

public enum StatusRelatorioFinanceiroEnum {

	PREVIA("Prévia"), CALCULADA("Calculada"), SALVO("Salvo");

	private String label;

	private StatusRelatorioFinanceiroEnum (String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	public static StatusRelatorioFinanceiroEnum getEnumByString(String str) {
		for (StatusRelatorioFinanceiroEnum e : StatusRelatorioFinanceiroEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
