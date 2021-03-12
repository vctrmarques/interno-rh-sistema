package com.rhlinkcon.model;

public enum RecadastramentoTipoEnum {

	APOSENTADO("Aposentado"), PENSIONISTA("Pensionista");

	private String label;

	private RecadastramentoTipoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static RecadastramentoTipoEnum getEnumByString(String str) {
		for (RecadastramentoTipoEnum e : RecadastramentoTipoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
