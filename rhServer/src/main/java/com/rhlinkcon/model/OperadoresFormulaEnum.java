package com.rhlinkcon.model;

import java.util.Objects;

public enum OperadoresFormulaEnum {

	SE("SE"), SENAO("SENÃO"), SENAO_SE("SENÃO SE"), FIM_SE("FIM DO SE"), ENQUANTO("ENQUANTO"),
	FIM_ENQUANTO("FIM ENQUANTO"), ENTAO("ENTÃO"), E("E"), OU("OU"), IGUAL("IGUAL"), DATA("DATA");

	private String label;

	private OperadoresFormulaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static OperadoresFormulaEnum getEnumByString(String str) {
		for (OperadoresFormulaEnum e : OperadoresFormulaEnum.values()) {
			if (!Objects.isNull(str) && str.equals(e.toString()))
				return e;
		}
		return null;
	}
}
