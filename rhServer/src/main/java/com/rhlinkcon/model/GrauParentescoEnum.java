package com.rhlinkcon.model;

public enum GrauParentescoEnum {

	FILHO("Filho(a)"), CONJUGE("Conjuge"), COMPANHEIRO("Companheiro(a)"), MAE("Mãe"), PAI("Pai"), IRMAO("Irmão(ã)"),
	SOGRO("Sogro(a)"), OUTROS("Outros");

	private String label;

	private GrauParentescoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static GrauParentescoEnum getEnumByString(String str) {
		for (GrauParentescoEnum e : GrauParentescoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
