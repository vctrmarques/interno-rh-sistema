package com.rhlinkcon.model;

public enum FuncaoCertidaoCompensacaoEnum {
	RESPONSAVEL_INFORMACAO("Responsável pela informação"), PRESIDENTE_GOIANIAPREV("Presidente do GOIANIAPREV"),
	GERENTE_COMPENSACAO("Gerente de compensação previdenciária"),
	DIRETOR_BENEFICIOS("Diretor de Benefícios previdenciários");

	private String label;

	private FuncaoCertidaoCompensacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FuncaoCertidaoCompensacaoEnum getEnumByString(String str) {
		for (FuncaoCertidaoCompensacaoEnum e : FuncaoCertidaoCompensacaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
