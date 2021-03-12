package com.rhlinkcon.model;

public enum TurnoFolgaDiaEnum {

	DOMINGO("Dom."), SEGUNDA("Seg."), TERCA("Ter."), QUARTA("Qua."), QUINTA("Qui."), SEXTA("Sex."), SABADO("Sáb.");

	private String label;

	private TurnoFolgaDiaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TurnoFolgaDiaEnum getEnumByString(String str) {
		for (TurnoFolgaDiaEnum e : TurnoFolgaDiaEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}