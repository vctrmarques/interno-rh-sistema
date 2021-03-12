package com.rhlinkcon.model;

public enum TipoCotaEnum {

	NORMAL("Normal"), RESERVADA("Reservada");

	private String label;

	private TipoCotaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoCotaEnum getEnumByString(String str) {
		for (TipoCotaEnum e : TipoCotaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
