package com.rhlinkcon.model;

public enum TipoNotificacao {

	REUNIAO("Você possui uma reunião agendada."), EMAIL("Você possui um novo e-mail.");

	private String label;

	private TipoNotificacao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
