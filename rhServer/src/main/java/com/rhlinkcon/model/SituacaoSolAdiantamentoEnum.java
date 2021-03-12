package com.rhlinkcon.model;

public enum SituacaoSolAdiantamentoEnum {

	SOLICITADO("Solicitado"), APROVADO("Aprovado"), REJEITADO("Rejeitado");

	private String label;

	private SituacaoSolAdiantamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SituacaoSolAdiantamentoEnum getEnumByString(String str) {
		for (SituacaoSolAdiantamentoEnum e : SituacaoSolAdiantamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
