package com.rhlinkcon.model;

public enum EsferaOrgaoEnum {

	EMPRESA_PUBLICA("Empresa Pública"), EMPRESA_PRIVADA("Empresa Privada"), GOVERNO_FEDERAL("Governo Federal"),
	GOVERNO_ESTADUAL("Governo Estadual"), GOVERNO_ESTADUAL_SECRETARIAS("Governo Estadual (Secretarias)"),
	GOVERNO_ESTADUAL_FUNCACOES("Governo Estadual (Estações)"),
	GOVERNO_ESTADUAL_EMP_PUBLICAS("Governo Estadual (Empresas Públicas)"), MUNICIPAL("Municipal"),
	SOCIEDADE_ECONOMIA_MISTA("Sociedade de Economia Mista"), AUTARQUIA_MUNICIPAL("Autarquia Municipal");

	private String label;

	private EsferaOrgaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static EsferaOrgaoEnum getEnumByString(String str) {
		for (EsferaOrgaoEnum e : EsferaOrgaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
