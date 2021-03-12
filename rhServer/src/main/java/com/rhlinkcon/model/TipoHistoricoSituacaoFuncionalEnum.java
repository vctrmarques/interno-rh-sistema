package com.rhlinkcon.model;

public enum TipoHistoricoSituacaoFuncionalEnum {

	PROMOCAO("Promoção"), NOMEACAO_COMISSIONADO("Nomeação Comissionado"), EXONERACAO("Exoneração"),
	SITUACAO_AFASTAMENTO("Situação Afastamento");

	private String label;

	private TipoHistoricoSituacaoFuncionalEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static TipoHistoricoSituacaoFuncionalEnum getEnumByString(String str) {
		for (TipoHistoricoSituacaoFuncionalEnum e : TipoHistoricoSituacaoFuncionalEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
