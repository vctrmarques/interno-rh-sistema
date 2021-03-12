package com.rhlinkcon.model;

import java.util.Objects;

public enum MotivoLeiEnum {

	CRIACAO("Criação"), EXTINCAO("Extinção"), REESTRUTURACAO("Reestruturação");

	private String label;

	private MotivoLeiEnum(String tipoValor) {
		this.label = tipoValor;
	}

	public String getLabel() {
		return label;
	}

	public static MotivoLeiEnum getEnumByString(String str) {
		for (MotivoLeiEnum e : MotivoLeiEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}
}
