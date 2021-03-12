package com.rhlinkcon.model;

public enum TipoFamiliaEnum {

	PRIMEIRA("Primeira"), SEGUNDA("Segunda"), TERCEIRA("Terceira"), QUARTA("Quarta"), QUINTA("Quinta");

	private String label;

	private TipoFamiliaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoFamiliaEnum getEnumByString(String str) {
		for (TipoFamiliaEnum e : TipoFamiliaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
