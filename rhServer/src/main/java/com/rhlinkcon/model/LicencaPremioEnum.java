package com.rhlinkcon.model;

public enum LicencaPremioEnum {

	SIM("Sim"), NAO("Não");

	private String label;

	private LicencaPremioEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static LicencaPremioEnum getEnumByString(String str) {
		for (LicencaPremioEnum e : LicencaPremioEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
