package com.rhlinkcon.model;

public enum TipoRegraEnum {
	CONVENCIONAL("Convencional"), TRANSICAO("Transição");

	private String tipoRegra;

	private TipoRegraEnum(String tipoRegra) {
		this.tipoRegra = tipoRegra;
	}

	public String getTipoRegra() {
		return tipoRegra;
	}

	public static TipoRegraEnum getEnumByString(String str) {
		for (TipoRegraEnum e : TipoRegraEnum.values()) {
			if (str.equals(e.tipoRegra))
				return e;
		}
		return null;
	}
}
