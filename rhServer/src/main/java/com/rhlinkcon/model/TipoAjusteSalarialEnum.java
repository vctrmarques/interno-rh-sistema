package com.rhlinkcon.model;

public enum TipoAjusteSalarialEnum {

	VALOR("Adição de Valor"), PERCENTUAL("Percentual");

	private String label;

	private TipoAjusteSalarialEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoAjusteSalarialEnum getEnumByString(String str) {
		for (TipoAjusteSalarialEnum e : TipoAjusteSalarialEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
