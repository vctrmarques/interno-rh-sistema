package com.rhlinkcon.model;

public enum StatusEnum {

	NOVA_REQUISICAO("Nova Requisição"), RASCUNHO("Rascunho"), APROVADO("Aprovado"),
	AGUARDANDO_ATENDIMENTO("Aguardando Atendimento"), ESTORNADA("Estornada"), CANCELADA("Cancelada"),
	ATENDIDA("Atendida"), PARCIALMENTE_ATENTIDA("Parcialmente Atentida"), VAZIO("Vazio"), BLOQUEADO("Bloqueado"),
	DESBLOQUEADO("Desbloqueado");

	private String label;

	private StatusEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
