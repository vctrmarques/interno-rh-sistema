package com.rhlinkcon.model;

public enum TipoDependenteEnum {

	COMPANHEIRO_A("Companheiro(a)"), FILHO_AS("Filho(a)"), IRMAO_A("Irmão(ã)"), PAI_MAE("Pai/Mãe"), AVOS("Avôs(ós)"),
	BISAVOS("Bisavôs(ós)"), ALIMENTADO("Alimentado");

	private String label;

	private TipoDependenteEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoDependenteEnum getEnumByString(String str) {
		for (TipoDependenteEnum e : TipoDependenteEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
