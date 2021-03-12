package com.rhlinkcon.model;

public enum SexoDependenteEnum {

	FEMININO("Feminino"), MASCULINO("Masculino");

	private String label;

	private SexoDependenteEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SexoDependenteEnum getEnumByString(String str) {
		for (SexoDependenteEnum e : SexoDependenteEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
