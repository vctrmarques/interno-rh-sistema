package com.rhlinkcon.model;

public enum StatusSituacaoCertidaoExSeguradoEnum {
	RASCUNHO("Rascunho"), AGUARDANDO_IMPRESSAO("Aguardando impressão"), ARQUIVADO("Arquivada");

	private String label;

	private StatusSituacaoCertidaoExSeguradoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static StatusSituacaoCertidaoExSeguradoEnum getEnumByString(String str) {
		for (StatusSituacaoCertidaoExSeguradoEnum e : StatusSituacaoCertidaoExSeguradoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}

}
