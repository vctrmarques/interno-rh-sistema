package com.rhlinkcon.model;

public enum TipoComparacaoEnum {

	NO_MININO("No Mínimo"), NO_MAXIMO("No Máximo"), INTERVALO("Intervalo"), IGUAL_A("Igual à"),
	DIFERENTE_DE("Diferente de");

	private String label;

	private TipoComparacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoComparacaoEnum getEnumByString(String str) {
		for (TipoComparacaoEnum e : TipoComparacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
