package com.rhlinkcon.model;

import java.util.Objects;

public enum TipoVerba {

	DESCONTO("Desconto"), VANTAGEM("Vantagem"), OUTROS("Outros");

	private String label;

	private TipoVerba(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoVerba getEnumByString(String str) {
		for (TipoVerba e : TipoVerba.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}

}
