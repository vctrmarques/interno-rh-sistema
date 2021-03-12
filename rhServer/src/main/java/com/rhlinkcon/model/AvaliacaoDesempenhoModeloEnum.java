package com.rhlinkcon.model;

public enum AvaliacaoDesempenhoModeloEnum {
	POR_360GRAUS("360 Graus"), POR_ESCOLHA_FORCADA("Por escolha forçada"), POR_COMPETENCIAS("Por competências"),
	POR_METAS_E_RESULTADOS("Por metas e resultados");

	private String label;

	private AvaliacaoDesempenhoModeloEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static AvaliacaoDesempenhoModeloEnum getEnumByString(String str) {
		for (AvaliacaoDesempenhoModeloEnum e : AvaliacaoDesempenhoModeloEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}