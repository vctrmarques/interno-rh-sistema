package com.rhlinkcon.model;

public enum ParametroGlobalTipoEnum {
	INTEIRO("Inteiro"), DECIMAL("Decimal"), TEXTO("Texto");

	private String label;

	private ParametroGlobalTipoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ParametroGlobalTipoEnum getEnumByString(String str) {
		for (ParametroGlobalTipoEnum e : ParametroGlobalTipoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
