package com.rhlinkcon.model;

import java.util.Objects;

public enum RecebimentoEnum {
	INDIVIDUAL("Individual"), TODOS("Todos");

	private String label;

	private RecebimentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public static RecebimentoEnum getEnumByString(String str) {
		for (RecebimentoEnum e : RecebimentoEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.getLabel()))
				return e;
		}

		return null;
	}
}
