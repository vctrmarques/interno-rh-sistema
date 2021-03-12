package com.rhlinkcon.model;

public enum TipoInscricaoEnum {

	CNPJ("CNPJ"), CEI("CEI"), CNPJ_CEI("CNPJ/CEI");

	private String label;

	private TipoInscricaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoInscricaoEnum getEnumByString(String str) {
		for (TipoInscricaoEnum e : TipoInscricaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
