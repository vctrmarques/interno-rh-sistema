package com.rhlinkcon.model;

public enum TipoRateioEnum {

	PERCENTUAL("Percentual"), QUANTIDADE_SALARIO_MINIMO("Quantidade salário mínimo"),
	VALOR_INFORMADO("Valor informado");

	private String label;

	private TipoRateioEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoRateioEnum getEnumByString(String str) {
		for (TipoRateioEnum e : TipoRateioEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
