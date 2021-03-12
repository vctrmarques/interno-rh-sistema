package com.rhlinkcon.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SituacaoProcessoEnum {
	ANDAMENTO("Andamento"), COM_FINANCEIRO("Com o Financeiro"), COM_PROCURARIA("Com a Procuradoria"),
	DEFERIDO("Deferido"), INDEFERIDO("Indeferido"), RETIFICAR("Retificar"), CANCELADO("Cancelado"),
	DESISTENCIA("Desistência"), AGUARDAR("Aguardar"), ARQUIVADO("Arquivado");

	private String label;

	private SituacaoProcessoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static SituacaoProcessoEnum getEnumByString(String str) {
		for (SituacaoProcessoEnum e : SituacaoProcessoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}
}
