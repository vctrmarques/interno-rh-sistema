package com.rhlinkcon.model;

import java.util.Objects;

public enum RecorrenciaEnum {

	FIXA("Fixa"), EM_PARCELA("Em Parcela(s)");

	private String label;

	private RecorrenciaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static RecorrenciaEnum getEnumByString(String str) {
		for (RecorrenciaEnum e : RecorrenciaEnum.values()) {
			if (!Objects.isNull(str) && (str.equals(e.toString()) || str.equals(e.getLabel())))
				return e;
		}
		return null;
	}

}
