package com.rhlinkcon.model;

public enum LocacaoPrestadorEnum {

	INTERNO("Interno"), EXTERNO("Externo");

	private String label;

	private LocacaoPrestadorEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static LocacaoPrestadorEnum getEnumByString(String str) {
		for (LocacaoPrestadorEnum e : LocacaoPrestadorEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
