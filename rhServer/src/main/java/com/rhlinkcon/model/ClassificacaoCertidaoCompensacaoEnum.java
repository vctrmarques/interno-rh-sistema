package com.rhlinkcon.model;

public enum ClassificacaoCertidaoCompensacaoEnum {
	APOSENTADORIA("Aposentadoria"), PENSAO("Pensão"), APOSENTADORIA_E_PENSAO("Aposentadoria e Pensão"),
	OUTROS_RPPS("Outros RPPS"), APOSENTADORIA_SEM_COMPENSACAO("Aposentadoria Sem Compensação");

	private String label;

	private ClassificacaoCertidaoCompensacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ClassificacaoCertidaoCompensacaoEnum getEnumByString(String str) {
		for (ClassificacaoCertidaoCompensacaoEnum e : ClassificacaoCertidaoCompensacaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
