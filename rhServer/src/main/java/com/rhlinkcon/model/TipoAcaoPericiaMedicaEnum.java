package com.rhlinkcon.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoAcaoPericiaMedicaEnum {

	APONSENTAR(1, "Aposentar"),
	NAO_APONSETAR(2, "Não aposentar"),
	REMARCAR_CLINICO_GERAL_PRESENCIAL(3, "Remarcar Clínico Geral (Presencial)"),
	REMARCAR_CLINICO_GERAL_NAO_PRESENCIAL(4, "Remarcar Clínico Geral (Não presencial)"),
	RETORNO_CLINICO_GERAL_PRESENCIAL(5, "Retorno Clínico Geral (Presencial)"),
	RETORNO_CLINICO_GERAL_NAO_PRESENCIAL(6, "Retorno Clínico Geral (Não presencial)"),
	AGENDAR_ESPECIALISTA(7, "Agendar especialista (Não presencial)"),
	DESAPOSENTAR(8, "Desaposentar");
	
	private Integer value;
	private String label;

	private TipoAcaoPericiaMedicaEnum(String label) {
		this.label = label;
	}

	private TipoAcaoPericiaMedicaEnum(Integer value, String label) {
		this.value = value;
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static TipoAcaoPericiaMedicaEnum getEnumByString(String str) {
		for (TipoAcaoPericiaMedicaEnum e : TipoAcaoPericiaMedicaEnum.values()) {
			if (!Objects.isNull(str) && (str.equals(e.toString()) || str.equals(e.getLabel())))
				return e;
		}
		throw new IllegalArgumentException("Inválido TipoAcaoPericiaMedicaEnum");
	}

	public static TipoAcaoPericiaMedicaEnum getEnumByLabel(String str) {
		for (TipoAcaoPericiaMedicaEnum e : TipoAcaoPericiaMedicaEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.getLabel()))
				return e;
		}
		throw new IllegalArgumentException("Inválido TipoAcaoPericiaMedicaEnum");
	}

}
