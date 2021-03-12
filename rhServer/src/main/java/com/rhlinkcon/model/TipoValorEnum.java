package com.rhlinkcon.model;

import java.util.Objects;

public enum TipoValorEnum {

	MOEDA("Moeda"), PERCENTUAL("Percentual"), QUANTIDADE("Quantidade");

	private String label;

	private TipoValorEnum(String tipoValor) {
		this.label = tipoValor;
	}

	public String getLabel() {
		return label;
	}

	public static TipoValorEnum getEnumByString(String str) {
		for (TipoValorEnum e : TipoValorEnum.values()) {
			if (!Objects.isNull(str) && (str.equals(e.toString()) || str.equals(e.getLabel())))
				return e;
		}
		return null;
	}

}
