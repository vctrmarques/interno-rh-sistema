package com.rhlinkcon.model;

public enum CorPrestadorEnum {

	AMARELA("Amarela"), BRANCA("Branca"), INDIGENA("Indígena"), NEGRA("Negra"), PARDA("Parda"),
	NAO_INFORMADA("Não Informada");

	private String label;

	private CorPrestadorEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static CorPrestadorEnum getEnumByString(String str) {
		for (CorPrestadorEnum e : CorPrestadorEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
