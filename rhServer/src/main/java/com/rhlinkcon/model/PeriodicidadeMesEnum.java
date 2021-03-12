package com.rhlinkcon.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PeriodicidadeMesEnum {

	MENSAL(1, "Mensal"),
	BIMESTRAL(2, "Bimestral"),
	TRIMESTRAL(3, "Trimestral"),
	SEMESTRAL(4, "Semestral"),
	ANUAL(5, "Anual");
	
	private Integer value;
	private String label;

	private PeriodicidadeMesEnum(String label) {
		this.label = label;
	}
	
	private PeriodicidadeMesEnum(Integer value, String label) {
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

	public static PeriodicidadeMesEnum getEnumByString(String str) {
		for (PeriodicidadeMesEnum e : PeriodicidadeMesEnum.values()) {
			if (!Objects.isNull(str) && (str.equals(e.toString()) || str.equals(e.getLabel())))
				return e;
		}
		throw new IllegalArgumentException("Inválido PeriodicidadeMesEnum");
	}
	
	public static PeriodicidadeMesEnum getEnumByLabel(String str) {
		for (PeriodicidadeMesEnum e : PeriodicidadeMesEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.getLabel()))
				return e;
		}
		throw new IllegalArgumentException("Inválido PeriodicidadeMesEnum");
	}
	
}
