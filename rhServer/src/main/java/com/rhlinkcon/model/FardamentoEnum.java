package com.rhlinkcon.model;

import java.util.Objects;

public enum FardamentoEnum {

	PP("PP"), P("P"), M("M"), G("G"), GG("GG"), XG("XG");

	private String label;

	private FardamentoEnum(String tipoValor) {
		this.label = tipoValor;
	}

	public String getLabel() {
		return label;
	}

	public static FardamentoEnum getEnumByString(String str) {
		for (FardamentoEnum e : FardamentoEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}
}
