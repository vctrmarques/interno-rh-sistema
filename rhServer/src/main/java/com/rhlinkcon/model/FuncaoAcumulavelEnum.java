package com.rhlinkcon.model;

import java.util.Objects;

public enum FuncaoAcumulavelEnum {

	NAO_ACUMULAVEL("Não Acumulável"), PROFESSOR("Professor"), PROFISSIONAL_SAUDE("Profissional da Saúde"),
	FUNCAO_TECNICA_CIENTIFICA("Função Técnica/Científica");

	private String label;

	private FuncaoAcumulavelEnum(String tipoValor) {
		this.label = tipoValor;
	}

	public String getLabel() {
		return label;
	}

	public static FuncaoAcumulavelEnum getEnumByString(String str) {
		for (FuncaoAcumulavelEnum e : FuncaoAcumulavelEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}
}
