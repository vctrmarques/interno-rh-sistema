package com.rhlinkcon.model;

public enum TipoEstabilidadeEnum {

	SINDICATO("Sindicato"), CIPA("Cipa");

	private String label;

	private TipoEstabilidadeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoEstabilidadeEnum getEnumByString(String str) {
		for (TipoEstabilidadeEnum e : TipoEstabilidadeEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
