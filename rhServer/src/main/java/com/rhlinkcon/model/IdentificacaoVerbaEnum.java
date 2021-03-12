package com.rhlinkcon.model;

public enum IdentificacaoVerbaEnum {
	
	DIFERENCA("Diferença"), DEVOLUCAO("Devolução");

	private String label;

	private IdentificacaoVerbaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static IdentificacaoVerbaEnum getEnumByString(String str) {
		for (IdentificacaoVerbaEnum e : IdentificacaoVerbaEnum.values()) {
			if (str.equals(e.toString())) {
				return e;
			}
		}
		return null;
	}
	
	public static IdentificacaoVerbaEnum getEnumByLabel(String str) {
		for (IdentificacaoVerbaEnum e : IdentificacaoVerbaEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
