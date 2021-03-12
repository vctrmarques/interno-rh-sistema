package com.rhlinkcon.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FaixaEnum {
	INSS("INSS"), RPPS("RPPS"), FGTS("FGTS"), IRRF("IRRF");

	private String label;

	private FaixaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FaixaEnum getEnumByString(String str) {
		for (FaixaEnum e : FaixaEnum.values()) {
			if (str.toUpperCase().equals(e.getLabel().toUpperCase()))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}
}
