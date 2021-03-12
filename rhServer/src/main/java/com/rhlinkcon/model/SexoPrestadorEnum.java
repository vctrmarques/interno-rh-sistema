package com.rhlinkcon.model;

public enum SexoPrestadorEnum {

	FEMININO("Feminino"), MASCULINO("Masculino");

	private String label;

	private SexoPrestadorEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SexoPrestadorEnum getEnumByString(String str) {
		for (SexoPrestadorEnum e : SexoPrestadorEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
