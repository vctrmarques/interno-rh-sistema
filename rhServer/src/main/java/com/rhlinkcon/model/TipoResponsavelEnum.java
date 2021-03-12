package com.rhlinkcon.model;

public enum TipoResponsavelEnum {

	CURADOR("Curador", "3"), GENITOR("Genitor", ""), GUARDIAO("Guardião", ""), MENTOR("Mentor", ""),
	PROCURADOR("Procurador", "4"), TUTOR("Tutor", "2"), OUTROS("Outros", "2");

	private String label;

	// Código SIPREV
	private String other;

	private TipoResponsavelEnum(String label, String other) {
		this.label = label;
		this.other = other;
	}

	public String getLabel() {
		return label;
	}

	public String getOther() {
		return other;
	}

	public static TipoResponsavelEnum getEnumByString(String str) {
		for (TipoResponsavelEnum e : TipoResponsavelEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
