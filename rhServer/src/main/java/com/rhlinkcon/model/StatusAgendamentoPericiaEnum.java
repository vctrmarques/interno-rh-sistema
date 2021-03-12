package com.rhlinkcon.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusAgendamentoPericiaEnum {

	AGENDADO("Agendado"), NAO_AGENDADO("Não Agendado");
	
	private Integer value;
	private String label;

	private StatusAgendamentoPericiaEnum(String label) {
		this.label = label;
	}
	
	private StatusAgendamentoPericiaEnum(Integer value, String label) {
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

	public static StatusAgendamentoPericiaEnum getEnumByString(String str) {
		for (StatusAgendamentoPericiaEnum e : StatusAgendamentoPericiaEnum.values()) {
			if (str.equals(e.getValue()) || str.equals(e.name()))
				return e;
		}
		throw new IllegalArgumentException("Inválido StatusAgendamentoPericiaEnum");
	}
	
	public static StatusAgendamentoPericiaEnum getEnumByLabel(String str) {
		for (StatusAgendamentoPericiaEnum e : StatusAgendamentoPericiaEnum.values()) {
			if (!Objects.isNull(str) && (str.equals(e.toString()) || str.equals(e.getLabel())))
				return e;
		}
		throw new IllegalArgumentException("Inválido StatusAgendamentoPericiaEnum");
	}
}
