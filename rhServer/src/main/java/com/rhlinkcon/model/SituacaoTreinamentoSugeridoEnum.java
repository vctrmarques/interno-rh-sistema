package com.rhlinkcon.model;

public enum SituacaoTreinamentoSugeridoEnum {
	EM_ABERTO("Em Aberto"), APROVADO("Aprovado"), REJEITADO("Rejeitado");

	private String label;

	private SituacaoTreinamentoSugeridoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SituacaoTreinamentoSugeridoEnum getEnumByString(String str) {
		for (SituacaoTreinamentoSugeridoEnum e : SituacaoTreinamentoSugeridoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
