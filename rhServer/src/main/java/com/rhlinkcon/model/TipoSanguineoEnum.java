package com.rhlinkcon.model;

public enum TipoSanguineoEnum {

	A_Mais("A+"), _Menos("A-"), B_Mais("B+"), B_Menos("B-"), AB_Mais("AB+"),
	AB_Menos("AB-"),O_Mais("O+"), O_Menos("O-");

	private String label;

	private TipoSanguineoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoSanguineoEnum getEnumByString(String str) {
		for (TipoSanguineoEnum e : TipoSanguineoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
