package com.rhlinkcon.model;

public enum GeneroEnum {

	FEMININO("Feminino"), MASCULINO("Masculino"), OUTROS("Outros");

	private String label;

	private GeneroEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static GeneroEnum getEnumByString(String str) {
		for (GeneroEnum e : GeneroEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
