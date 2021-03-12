package com.rhlinkcon.model;

public enum TipoAfastamentoEnum {

	FALTA("Falta"), AFASTAMENTO("Afastamento"), SUSPENSAO("Suspensão"), DESLIGAMENTO("Desligamento");

	private String label;

	private TipoAfastamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoAfastamentoEnum getEnumByString(String str) {
		for (TipoAfastamentoEnum e : TipoAfastamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
