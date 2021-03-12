package com.rhlinkcon.model;

import java.util.Objects;

public enum GrupoVinculoEnum {

	EFETIVO("Efetivo"), COMISSIONADO("Comissionado"), ESTAGIARIO("Estagiário"), POLICIAL("Policial"),
	VOLUNTARIO("Voluntário"), SALARIO_FAMILIA("Salário Família");

	private String label;

	private GrupoVinculoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static GrupoVinculoEnum getEnumByString(String str) {
		for (GrupoVinculoEnum e : GrupoVinculoEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}

}
