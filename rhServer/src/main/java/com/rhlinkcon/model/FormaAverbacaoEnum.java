package com.rhlinkcon.model;

public enum FormaAverbacaoEnum {

	PERIODO("Período"), DIAS("Dias"), ANUENIO("Anuênio");

	private String label;

	private FormaAverbacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FormaAverbacaoEnum getEnumByString(String str) {
		for (FormaAverbacaoEnum e : FormaAverbacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
