package com.rhlinkcon.model;

public enum EventoMotivoEnum {

	NOMEACAO("Nomeação"), PROMOCAO("Promoção"), EXONERACAO("Exoneração");

	private String label;

	private EventoMotivoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static EventoMotivoEnum getEnumByString(String str) {
		for (EventoMotivoEnum e : EventoMotivoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
