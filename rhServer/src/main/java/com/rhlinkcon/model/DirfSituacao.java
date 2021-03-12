package com.rhlinkcon.model;

public enum DirfSituacao {
	CRIADA("Criada"), GERADO("Gerado"), ERRO_GERACAO("Erro na geração do arquivo");

	private String label;

	private DirfSituacao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DirfSituacao getEnumByString(String str) {
		for (DirfSituacao e : DirfSituacao.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
