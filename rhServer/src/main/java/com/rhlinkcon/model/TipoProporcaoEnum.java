package com.rhlinkcon.model;

public enum TipoProporcaoEnum {

	AVOS("Avos"), PERCENTUAL("Percentual"), INTEGRAL("Integral"), PROPORCIONAL("Proporcional");

	private String label;

	private TipoProporcaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoProporcaoEnum getEnumByString(String str) {
		for (TipoProporcaoEnum e : TipoProporcaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
