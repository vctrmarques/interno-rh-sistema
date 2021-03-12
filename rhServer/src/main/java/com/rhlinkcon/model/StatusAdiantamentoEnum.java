package com.rhlinkcon.model;

import java.util.Objects;

public enum StatusAdiantamentoEnum {
	ATIVO("Ativo"), INATIVO("Inativo");

	private String label;

	private StatusAdiantamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static StatusAdiantamentoEnum getEnumByString(String str) {
		for (StatusAdiantamentoEnum e : StatusAdiantamentoEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.getLabel()))
				return e;
		}

		return null;
	}
}
