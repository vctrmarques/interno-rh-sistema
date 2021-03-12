package com.rhlinkcon.model;

public enum NivelSalarialHistoricoOrigemAjusteEnum {
	AJUSTE_PROGRAMADO("Ajuste Programado"), AJUSTE_MANUAL("Ajuste Manual");

	private String origemAjuste;

	private NivelSalarialHistoricoOrigemAjusteEnum(String origemAjuste) {
		this.origemAjuste = origemAjuste;
	}

	public String getOrigemAjuste() {
		return origemAjuste;
	}

	public static NivelSalarialHistoricoOrigemAjusteEnum getEnumByString(String str) {
		for (NivelSalarialHistoricoOrigemAjusteEnum e : NivelSalarialHistoricoOrigemAjusteEnum.values()) {
			if (str.equals(e.origemAjuste))
				return e;
		}
		return null;
	}
}
