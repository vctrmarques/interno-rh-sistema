package com.rhlinkcon.model;

public enum ItemFormulaTipoEnum {

	CODIGO("Código"), ROTINA("Rotina"), ATRIBUTO("Atributo"), DATA("Data"), TEXTO("Texto"), NUMERAL("Numeral");

	private String label;

	private ItemFormulaTipoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
