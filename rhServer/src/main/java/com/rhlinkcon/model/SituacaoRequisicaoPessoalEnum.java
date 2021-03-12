package com.rhlinkcon.model;

public enum SituacaoRequisicaoPessoalEnum {
	RASCUNHO("Rascunho"), ABERTO("Aberto"), EM_PROCESSO("Em Processo"), APROVADO("Aprovado"), REJEITADO("Rejeitado"),
	CANCELADA("Cancelada"), CONCLUIDO("Concluido");

	private String label;

	private SituacaoRequisicaoPessoalEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SituacaoRequisicaoPessoalEnum getEnumByString(String str) {
		for (SituacaoRequisicaoPessoalEnum e : SituacaoRequisicaoPessoalEnum.values()) {
			if (str.equalsIgnoreCase(e.getLabel())) {
				return e;
			}
		}
		return null;
	}
}
