package com.rhlinkcon.model;

public enum MotivoAjusteSalarialEnum {

	DISSIDIO("Dissídio"), REAJUSE_SALARIAL("Reajuste Salarial"), OUTROS("Outros");

	private String label;

	private MotivoAjusteSalarialEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static MotivoAjusteSalarialEnum getEnumByString(String str) {
		for (MotivoAjusteSalarialEnum e : MotivoAjusteSalarialEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
