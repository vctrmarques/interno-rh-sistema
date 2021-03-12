package com.rhlinkcon.model;

public enum FuncaoDeclaracaoAposentadoriaEnum {
	RESPONSAVEL_INFORMACAO("Responsável pela informação"), GERENTE_COMPENSACAO("Gerente de compensação previdenciária"),
	DIRETOR_BENEFICIOS("Diretor de Benefícios previdenciários"), PRESIDENTE_GOIANIAPREV("Presidente do GOIANIAPREV");

	private String label;

	private FuncaoDeclaracaoAposentadoriaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FuncaoDeclaracaoAposentadoriaEnum getEnumByString(String str) {
		for (FuncaoDeclaracaoAposentadoriaEnum e : FuncaoDeclaracaoAposentadoriaEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
