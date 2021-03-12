package com.rhlinkcon.model;

public enum TipoVigenciaEnum {
	IGNORAR("IGNORAR"), ATE("ATE"), ANTES("ANTES"), DEPOIS("DEPOIS");

	private String tipoVigencia;

	private TipoVigenciaEnum(String tipoVigencia) {
		this.tipoVigencia = tipoVigencia;
	}

	public String getTipoVigencia() {
		return tipoVigencia;
	}

	public static TipoVigenciaEnum getEnumByString(String str) {
		for (TipoVigenciaEnum e : TipoVigenciaEnum.values()) {
			if (str.equals(e.tipoVigencia))
				return e;
		}
		return null;
	}
}
