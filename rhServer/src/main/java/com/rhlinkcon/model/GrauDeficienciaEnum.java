package com.rhlinkcon.model;

public enum GrauDeficienciaEnum {

	LEVE("Leve"), MODERADA("Moderada"), GRAVE("Grave");

	private String label;

	private GrauDeficienciaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static GrauDeficienciaEnum getEnumByString(String str) {
		for (GrauDeficienciaEnum e : GrauDeficienciaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
