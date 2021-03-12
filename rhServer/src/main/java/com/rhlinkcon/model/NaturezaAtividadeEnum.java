package com.rhlinkcon.model;

public enum NaturezaAtividadeEnum {

	TRABALHO_RURAL("Trabalho Rural"), TRABALHO_URBANO("Trabalho Urbano");

	private String label;

	private NaturezaAtividadeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static NaturezaAtividadeEnum getEnumByString(String str) {
		for (NaturezaAtividadeEnum e : NaturezaAtividadeEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
