package com.rhlinkcon.model;

public enum ModalidadeConsignadoEnum {

	APOSENTADOS("Aposentados"), PENSIONISTAS_DO_INSS("Pensionistas do INSS"),
	SERVIDORES_PUBLICOS("Servidores Públicos");

	private String label;

	private ModalidadeConsignadoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ModalidadeConsignadoEnum getEnumByString(String str) {
		for (ModalidadeConsignadoEnum e : ModalidadeConsignadoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
