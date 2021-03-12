package com.rhlinkcon.model;

public enum TipoTreinamentoSugerido {
	INTERNO("Interno"), EXTERNO("Externo");

	private String label;

	private TipoTreinamentoSugerido(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoTreinamentoSugerido getEnumByString(String str) {
		for (TipoTreinamentoSugerido e : TipoTreinamentoSugerido.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
