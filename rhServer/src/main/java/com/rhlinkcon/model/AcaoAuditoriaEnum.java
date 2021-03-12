package com.rhlinkcon.model;

public enum AcaoAuditoriaEnum {
	INSERT("Inserir"), UPDATE("Atualizar"), DELETE("Remover");

	private String label;

	private AcaoAuditoriaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
