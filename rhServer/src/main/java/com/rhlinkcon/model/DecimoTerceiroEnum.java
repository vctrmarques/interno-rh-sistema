package com.rhlinkcon.model;

public enum DecimoTerceiroEnum {

	SIM("Sim"), NAO("Não");

	private String label;

	private DecimoTerceiroEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DecimoTerceiroEnum getEnumByString(String str) {
		for (DecimoTerceiroEnum e : DecimoTerceiroEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
