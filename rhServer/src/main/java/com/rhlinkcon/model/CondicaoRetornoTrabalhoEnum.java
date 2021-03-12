package com.rhlinkcon.model;

import java.util.Objects;

public enum CondicaoRetornoTrabalhoEnum {

	BENEFICIARIO("Beneficiário"), REABILITADO("Reabilitado"),
	PORTADOR_DEFICIENCIA_HABILITADO("Portador de Deficiência Habilitado"), NAO_APLICAVEL("Não Aplicável");

	private String label;

	private CondicaoRetornoTrabalhoEnum(String tipoValor) {
		this.label = tipoValor;
	}

	public String getLabel() {
		return label;
	}

	public static CondicaoRetornoTrabalhoEnum getEnumByString(String str) {
		for (CondicaoRetornoTrabalhoEnum e : CondicaoRetornoTrabalhoEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}
}
