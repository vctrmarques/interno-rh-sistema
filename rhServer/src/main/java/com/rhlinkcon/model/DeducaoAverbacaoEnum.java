package com.rhlinkcon.model;

public enum DeducaoAverbacaoEnum {

	SIM("Sim"), NAO("Não");

	private String label;

	private DeducaoAverbacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DeducaoAverbacaoEnum getEnumByString(String str) {
		for (DeducaoAverbacaoEnum e : DeducaoAverbacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
