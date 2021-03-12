package com.rhlinkcon.model;

public enum DeclaracaoAposentadoriaTipoEnum {

	DECLARACAO("Declaração"), RASCUNHO("Rascunho"), RETIFICACAO("Retificação");

	private String label;

	private DeclaracaoAposentadoriaTipoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DeclaracaoAposentadoriaTipoEnum getEnumByString(String str) {
		for (DeclaracaoAposentadoriaTipoEnum e : DeclaracaoAposentadoriaTipoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
