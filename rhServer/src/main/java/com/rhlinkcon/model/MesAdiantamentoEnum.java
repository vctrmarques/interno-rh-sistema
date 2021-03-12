package com.rhlinkcon.model;

public enum MesAdiantamentoEnum {

	COMPETENCIA("Competência"), ANIVERSARIO("Aniversário"), FERIAS("Férias");

	private String label;

	private MesAdiantamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static MesAdiantamentoEnum getEnumByString(String str) {
		for (MesAdiantamentoEnum e : MesAdiantamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
