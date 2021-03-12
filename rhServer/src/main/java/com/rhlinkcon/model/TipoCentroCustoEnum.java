package com.rhlinkcon.model;

public enum TipoCentroCustoEnum {

	SINTETICO("Sintético"), ANALITICO("Analítico");

	private String label;

	private TipoCentroCustoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoCentroCustoEnum getEnumByString(String str) {
		for (TipoCentroCustoEnum e : TipoCentroCustoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
