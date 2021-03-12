package com.rhlinkcon.model;

public enum TipoContaLotacaoEnum {

	DEBITO("Débito"), CREDITO("Crédito");

	private String label;

	private TipoContaLotacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoContaLotacaoEnum getEnumByString(String str) {
		for (TipoContaLotacaoEnum e : TipoContaLotacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
