package com.rhlinkcon.model;

public enum TipoTelefoneEnum {

	FIXO("Fixo"), MOVEL("Móvel"), COMERCIAL("Comercial");

	private String label;

	private TipoTelefoneEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoTelefoneEnum getEnumByString(String str) {
		for (TipoTelefoneEnum e : TipoTelefoneEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
