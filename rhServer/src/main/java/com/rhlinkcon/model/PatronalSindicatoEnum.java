package com.rhlinkcon.model;

public enum PatronalSindicatoEnum {

	SIM("Sim"), NAO("Não");

	private String label;

	private PatronalSindicatoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static PatronalSindicatoEnum getEnumByString(String str) {
		for (PatronalSindicatoEnum e : PatronalSindicatoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
