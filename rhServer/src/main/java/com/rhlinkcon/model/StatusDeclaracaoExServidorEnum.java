package com.rhlinkcon.model;

public enum StatusDeclaracaoExServidorEnum {

	RASCUNHO("Rascunho"), AGUARDANDO_IMPRESSAO("Aguardando impressão"), ARQUIVADO("Arquivado");

	private String label;

	private StatusDeclaracaoExServidorEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static StatusDeclaracaoExServidorEnum getEnumByString(String str) {
		for (StatusDeclaracaoExServidorEnum e : StatusDeclaracaoExServidorEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
