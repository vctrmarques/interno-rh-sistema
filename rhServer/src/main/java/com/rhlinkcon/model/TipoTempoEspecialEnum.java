package com.rhlinkcon.model;

public enum TipoTempoEspecialEnum {

	EXERCIDO_CONDICAO_DEFICIENCIA("Exercido na condição de pessoas com deficiência"),
	EXERCIDO_ATIVIDADE_RISCO("Exercido em atividades de risco"), EXERCIDO_ATIVIDADE_CONDICAO_ESPECIAL_PREJUDICIAL_SAUDE(
			"Exercido em atividades sob condições especiais que prejudiquem a saúde ou a integridade física");

	private String label;

	private TipoTempoEspecialEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoTempoEspecialEnum getEnumByString(String str) {
		for (TipoTempoEspecialEnum e : TipoTempoEspecialEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
