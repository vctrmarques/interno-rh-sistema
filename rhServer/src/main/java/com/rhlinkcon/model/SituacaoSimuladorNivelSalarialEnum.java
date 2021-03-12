package com.rhlinkcon.model;

public enum SituacaoSimuladorNivelSalarialEnum {

	NAO_PROGRAMADO("Não Programado"), NAO_APLICADO("Não Aplicado"), PROGRAMADO("Programado"), APLICADO("Aplicado");

	private String label;

	private SituacaoSimuladorNivelSalarialEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SituacaoSimuladorNivelSalarialEnum getEnumByString(String str) {
		for (SituacaoSimuladorNivelSalarialEnum e : SituacaoSimuladorNivelSalarialEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
