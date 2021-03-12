package com.rhlinkcon.model;

public enum ProcessoEtapaEnum {

	AVALIACAO("Avaliação"), ADMISSAO("Admissão"), DEMISSAO("Demissão"), PROMOCAO("Promoção"),
	TRANSFERENCIA("Transferênia"), TREINAMENTO("Treinamento"), SELECAO("Seleção");

	private String label;

	private ProcessoEtapaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ProcessoEtapaEnum getEnumByString(String str) {
		for (ProcessoEtapaEnum e : ProcessoEtapaEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
