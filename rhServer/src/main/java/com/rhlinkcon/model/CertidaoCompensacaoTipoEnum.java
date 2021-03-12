package com.rhlinkcon.model;

public enum CertidaoCompensacaoTipoEnum {

	CERTIDAO_COMPENSACAO("Certidão Compensação"), RASCUNHO("Rascunho"), RETIFICACAO("Retificação");

	private String label;

	private CertidaoCompensacaoTipoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static CertidaoCompensacaoTipoEnum getEnumByString(String str) {
		for (CertidaoCompensacaoTipoEnum e : CertidaoCompensacaoTipoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
