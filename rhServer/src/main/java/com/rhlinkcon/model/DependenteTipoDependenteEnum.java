package com.rhlinkcon.model;

public enum DependenteTipoDependenteEnum {

	CONJUGE("Cônjuge"), COMPANHEIRO_A("Companheiro(a)"), FILHO_AS_MENOR("Filho(a) Menor"),
	FILHO_AS_INVALIDO("Filho(a) Inválido"), PAI_MAE("Pai / Mãe"), IRMAO_A_MENOR("Irmão(ã) Menor"),
	IRMAO_A_INVALIDO("Irmão(ã) Inválido"), ENTEADO_A_MENOR("Enteado(a) Menor"),
	ENTEADO_A_INVALIDO("Enteado(a) Inválido"), MENOR_TUTELADO("Menor Tutelado"),
	MENOR_TUTELADO_INVALIDO("Menor Tutelado Inválido"), MENOR_SEM_GUARDA("Menor Sem Guarda"),
	UNIVERSITARIO("Universitário"), BENEFICIARIO_FACULTATIVO("Beneficiário Facultativo"), OUTROS("Outros");

	private String label;

	private DependenteTipoDependenteEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DependenteTipoDependenteEnum getEnumByString(String str) {
		for (DependenteTipoDependenteEnum e : DependenteTipoDependenteEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
