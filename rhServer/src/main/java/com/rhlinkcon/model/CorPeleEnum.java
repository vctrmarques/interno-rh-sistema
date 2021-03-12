package com.rhlinkcon.model;

public enum CorPeleEnum {

	AMARELO("Amarelo(a)"), BRANCO("Branco(a)"), NEGRO("Negro(a)"), PARDO("Pardo(a)"), INDIGENA("Indígena"),
	NAO_INFORMADO("Não Informado");

	private String label;

	private CorPeleEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static CorPeleEnum getEnumByString(String str) {
		for (CorPeleEnum e : CorPeleEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
