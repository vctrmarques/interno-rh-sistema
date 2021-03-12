package com.rhlinkcon.model;

import java.util.Objects;

public enum TipoPagamentoEnum {
	AVULSO("Avulso"), QUINZENAL("Quinzenal");

	private String label;

	private TipoPagamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public static TipoPagamentoEnum getEnumByString(String str) {
		for (TipoPagamentoEnum e : TipoPagamentoEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.getLabel()))
				return e;
		}

		return null;
	}
}
