package com.rhlinkcon.model;

public enum FeriasProgramacaoTipoProcessamentoEnum {

	FOLHA_FERIAS("Folha de Férias"), FOLHA_MENSAL("Folha Mensal");

	private String label;

	private FeriasProgramacaoTipoProcessamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FeriasProgramacaoTipoProcessamentoEnum getEnumByString(String str) {
		for (FeriasProgramacaoTipoProcessamentoEnum e : FeriasProgramacaoTipoProcessamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
