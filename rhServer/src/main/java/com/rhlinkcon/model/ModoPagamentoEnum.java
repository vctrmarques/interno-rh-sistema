package com.rhlinkcon.model;

public enum ModoPagamentoEnum {

	PARCELA_UNICA("Parcela Única"), PARCELADO("Parcelado");

	private String label;

	private ModoPagamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ModoPagamentoEnum getEnumByString(String str) {
		for (ModoPagamentoEnum e : ModoPagamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
