package com.rhlinkcon.model;

public enum UnidadePagamentoEnum {

	POR_HORA("Por Hora"), POR_DIA("Por Dia"), POR_SEMANA("Por Semana"), POR_QUINZENA("Por Quinzena"),
	POR_MES("Por Mês"), POR_TAREFA("Por Tarefa"), NAO_APLICAVEL("Não Aplicável");

	private String label;

	private UnidadePagamentoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static UnidadePagamentoEnum getEnumByString(String str) {
		for (UnidadePagamentoEnum e : UnidadePagamentoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
