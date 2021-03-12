package com.rhlinkcon.model;

public enum SituacaoFilialEnum {

	LIBERADO("Liberado"), BLOQUEADO("Bloqueado");

	private String label;

	private SituacaoFilialEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SituacaoFilialEnum getEnumByString(String str) {
		for (SituacaoFilialEnum e : SituacaoFilialEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
