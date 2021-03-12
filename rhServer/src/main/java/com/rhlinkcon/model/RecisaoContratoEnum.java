package com.rhlinkcon.model;

import java.util.Objects;

public enum RecisaoContratoEnum {
	SIMULADA("Simulada"), EFETIVADA("Efetivada"), PAGA("Paga"), CANCELADA("Cancelada");

	private String label;

	private RecisaoContratoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public static RecisaoContratoEnum getEnumByString(String str) {
		for (RecisaoContratoEnum e : RecisaoContratoEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.getLabel()))
				return e;
		}

		return null;
	}
}
