package com.rhlinkcon.model;

public enum IndiceContribuicaoPrevidenciaEnum {

	CONT_DESC_1_EMPREGO("Contribuição Descontada Pelo 1º Emprego"),
	CONT_DESC_OUTROS_EMPREGOS_SOBRE_VALOR("Contribuição Descontada por Outros Empregos Sobre Valor"),
	CONT_SOB_LIMITE_MAXI_SALARIO("Contribuição Sobre Limite Máximo de Salário");

	private String label;

	private IndiceContribuicaoPrevidenciaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static IndiceContribuicaoPrevidenciaEnum getEnumByString(String str) {
		for (IndiceContribuicaoPrevidenciaEnum e : IndiceContribuicaoPrevidenciaEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
