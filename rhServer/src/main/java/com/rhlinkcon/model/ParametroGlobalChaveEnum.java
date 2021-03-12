package com.rhlinkcon.model;

public enum ParametroGlobalChaveEnum {
	TETO_PREFEITURA("Teto da Prefeitura"), TETO_INSS("Teto do INSS"),
	VALOR_DEPENDENTE_IRRF("Valor Por Dependente - IRRF"), NUMERO_REMESSA("Número Remessa"),
	FOLHA_13_SALARIO("Folha do 13º Salário"), SALARIO_MINIMO("Salário Mínimo");

	private String label;

	private ParametroGlobalChaveEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ParametroGlobalChaveEnum getEnumByString(String str) {
		for (ParametroGlobalChaveEnum e : ParametroGlobalChaveEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
