package com.rhlinkcon.model;

public enum TipoOperacaoEnum {
	C("Compromisso de pagamento"), D("Compromisso de recebimento");

	private String label;

	private TipoOperacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoOperacaoEnum getEnumByString(String str) {
		for (TipoOperacaoEnum e : TipoOperacaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
