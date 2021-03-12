package com.rhlinkcon.model;

public enum ReferenciaContribuicaoEnum {

	EMPREGADOR("Empregador"), AUTONOMO_LIBERAL("Autônomo Liberal"), EMPREGADOS("Empregados");

	private String label;

	private ReferenciaContribuicaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ReferenciaContribuicaoEnum getEnumByString(String str) {
		for (ReferenciaContribuicaoEnum e : ReferenciaContribuicaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
