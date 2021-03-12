package com.rhlinkcon.model;

public enum ColetivoEnum {

	SIM("Sim"), NAO("Não");

	private String label;

	private ColetivoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ColetivoEnum getEnumByString(String str) {
		for (ColetivoEnum e : ColetivoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
	
	public static ColetivoEnum getEnumByLabel(String str) {
		for (ColetivoEnum e : ColetivoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
