package com.rhlinkcon.model;

public enum CondicaoIsencaoEnum {

	DESCONTAR_IR("Descontar IR"), NAO_DESCONTAR_IR("Não descontar IR");

	private String label;

	private CondicaoIsencaoEnum(String tipoValor) {
		this.label = tipoValor;
	}

	public String getLabel() {
		return label;
	}

	public static CondicaoIsencaoEnum getEnumByString(String str) {
		for (CondicaoIsencaoEnum e : CondicaoIsencaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
