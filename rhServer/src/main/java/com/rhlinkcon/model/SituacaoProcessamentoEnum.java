package com.rhlinkcon.model;

public enum SituacaoProcessamentoEnum {
	CONCLUIDO("Concluído"), FALHO("Falhou"), PENDENTE("Pendente");

	private String label;

	public String getLabel() {
		return label;
	}

	private SituacaoProcessamentoEnum(String label) {
		this.label = label;
	}

}
