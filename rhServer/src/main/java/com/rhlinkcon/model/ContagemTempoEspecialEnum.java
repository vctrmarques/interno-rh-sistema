package com.rhlinkcon.model;

import java.util.Objects;

public enum ContagemTempoEspecialEnum {

	NAO("Não"), PROFESSOR("Professor"), ATIVIDADE_RISCO("Atividade de Risco");

	private String label;

	private ContagemTempoEspecialEnum(String tipoValor) {
		this.label = tipoValor;
	}

	public String getLabel() {
		return label;
	}

	public static ContagemTempoEspecialEnum getEnumByString(String str) {
		for (ContagemTempoEspecialEnum e : ContagemTempoEspecialEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}
}
