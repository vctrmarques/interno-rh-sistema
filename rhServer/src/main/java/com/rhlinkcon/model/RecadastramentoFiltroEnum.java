package com.rhlinkcon.model;

public enum RecadastramentoFiltroEnum {

	RECADASTRADO("Recadastrado"), NAO_RECADASTRADO("Não recadastrado"), VENCER("Perto de vencer");

	private String label;

	private RecadastramentoFiltroEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static RecadastramentoFiltroEnum getEnumByString(String str) {
		for (RecadastramentoFiltroEnum e : RecadastramentoFiltroEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
