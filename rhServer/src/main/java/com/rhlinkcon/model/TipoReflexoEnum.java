package com.rhlinkcon.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoReflexoEnum {
	PAGAR_ABONO_PERMANENCIA("Pagar abono Permanência"), OUTROS("Ouros");

	private String label;

	private TipoReflexoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoReflexoEnum getEnumByString(String str) {
		for (TipoReflexoEnum e : TipoReflexoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}
}
