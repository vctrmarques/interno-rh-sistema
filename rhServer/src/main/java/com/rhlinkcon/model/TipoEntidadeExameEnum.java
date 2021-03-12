package com.rhlinkcon.model;

public enum TipoEntidadeExameEnum {
	EXTERNO("Externo"), INTERNO_CUSTO("Interno (custo)"), INTERNO_FUNCIONARIO("Interno (funcionário)"),
	JUNTA_MEDICA("Junta Médica");

	private String label;

	private TipoEntidadeExameEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoEntidadeExameEnum getEnumByString(String str) {
		for (TipoEntidadeExameEnum e : TipoEntidadeExameEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
