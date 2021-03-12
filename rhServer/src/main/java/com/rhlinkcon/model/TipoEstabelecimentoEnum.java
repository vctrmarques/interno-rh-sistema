package com.rhlinkcon.model;

public enum TipoEstabelecimentoEnum {

	UNICO("Único"), PRINCIPAL("Principal"), OUTROS("Outros");

	private String label;

	private TipoEstabelecimentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoEstabelecimentoEnum getEnumByString(String str) {
		for (TipoEstabelecimentoEnum e : TipoEstabelecimentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
