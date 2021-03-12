package com.rhlinkcon.model;

public enum TipoExoneracaoDemissaoEnum {

	EXONERACAO_PEDIDO("Exoneração à Pedido"), EXONERACAO_OFICIO("Exoneração à Ofício");

	private String label;

	private TipoExoneracaoDemissaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoExoneracaoDemissaoEnum getEnumByString(String str) {
		for (TipoExoneracaoDemissaoEnum e : TipoExoneracaoDemissaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
