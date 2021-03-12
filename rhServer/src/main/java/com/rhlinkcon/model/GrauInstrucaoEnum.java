package com.rhlinkcon.model;

public enum GrauInstrucaoEnum {

	NAO_INFORMADO("Náo Informado", "99"), ANALFABETO("Analfabeto", "1"), ALFABETIZADO("Alfabetizado", "2"),
	FUNDAMENTAL_INCOMPLETO("Fundamental Incompleto", "3"), FUNDAMENTAL_COMPLETO("Fundamental Completo", "4"),
	MEDIO_INCOMPLETO("Médio Incompleto", "5"), MEDIO_COMPLETO("Médio Completo", "6"),
	SUPERIOR_INCOMPLETO("Superior Incompleto", "7"), SUPERIOR_COMPLETO("Superior Completo", "8"),
	ESPECIALIZACAO_POS_GRADUACAO("Especialização / Pós Graduação", "9"), MESTRADO("Mestrado", "10"),
	DOUTORADO("Doutorado", "11"), POS_DOUTORADO("Pós Doutorado", "11"), OUTROS("Outros", "90");

	private String label;

	// Código SIPREV
	private String other;

	private GrauInstrucaoEnum(String label, String other) {
		this.label = label;
		this.other = other;
	}

	public String getLabel() {
		return label;
	}

	public String getOther() {
		return other;
	}

	public static GrauInstrucaoEnum getEnumByString(String str) {
		for (GrauInstrucaoEnum e : GrauInstrucaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
