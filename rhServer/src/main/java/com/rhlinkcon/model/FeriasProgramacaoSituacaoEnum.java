package com.rhlinkcon.model;

public enum FeriasProgramacaoSituacaoEnum {

	EM_ABERTO("Em aberto"), AGENDADO("Agendado"), CANCELADO("Cancelado"), PAGO("Pago"),
	DIREITO_PERDIDO("Direito Perdido");

	private String label;

	private FeriasProgramacaoSituacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FeriasProgramacaoSituacaoEnum getEnumByString(String str) {
		for (FeriasProgramacaoSituacaoEnum e : FeriasProgramacaoSituacaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
