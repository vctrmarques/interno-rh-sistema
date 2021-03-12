package com.rhlinkcon.model;

public enum SituacaoEventoEnum {

	ATIVO("Ativo"), AFASTADO("Afastado");

	private String label;

	private SituacaoEventoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SituacaoEventoEnum getEnumByString(String str) {
		for (SituacaoEventoEnum e : SituacaoEventoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
