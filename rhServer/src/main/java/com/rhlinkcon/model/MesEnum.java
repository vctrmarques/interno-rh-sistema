package com.rhlinkcon.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MesEnum {
	JANEIRO(1, "Janeiro"), 
	FEVEREIRO(2, "Fevereiro"), 
	MARCO(3, "Março"), 
	ABRIL(4, "Abril"), 
	MAIO(5, "Maio"),
	JUNHO(6, "Junho"), 
	JULHO(7, "Julho"), 
	AGOSTO(8, "Agosto"), 
	SETEMBRO(9, "Setembro"), 
	OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"), 
	DEZEMBRO(12, "Dezembro");

	private Integer value;
	private String label;

	private MesEnum(Integer value, String label) {
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

	public static MesEnum getEnumByInteger(Integer str) {
		for (MesEnum e : MesEnum.values()) {
			if (str.equals(e.getValue()))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}
	
	public static MesEnum getEnumByString(String str) {
		for (MesEnum e : MesEnum.values()) {
			if (str.equals(e.getValue()) || str.equals(e.name()))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}
	
	public static MesEnum getEnumByLabel(String str) {
		for (MesEnum e : MesEnum.values()) {
			if (!Objects.isNull(str) && (str.equals(e.toString()) || str.equals(e.getLabel())))
				return e;
		}
		throw new IllegalArgumentException("Inválido");
	}
}
