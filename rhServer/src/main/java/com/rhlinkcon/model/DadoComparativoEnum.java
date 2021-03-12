package com.rhlinkcon.model;

public enum DadoComparativoEnum {

	IDADE("Idade"), GRAU_INSTRUCAO("Grau de Instrução"), LOCAL_RESIDENCIA("Local de Residência"), SEXO("Sexo"),
	TEMPO_SERVICO("Tempo de Serviço"), VINCULO_EMPREGATICIO("Vínculo Empregatício");

	private String label;

	private DadoComparativoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static DadoComparativoEnum getEnumByString(String str) {
		for (DadoComparativoEnum e : DadoComparativoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
