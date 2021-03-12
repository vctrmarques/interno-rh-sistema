package com.rhlinkcon.model;

public enum TipoFilialEnum {

	ATIVA("Ativa"), INATIVA("Inativa"), CORRECAO_HISTORICO("Correção Histórico");

	private String label;

	private TipoFilialEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoFilialEnum getEnumByString(String str) {
		for (TipoFilialEnum e : TipoFilialEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
