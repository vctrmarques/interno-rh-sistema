package com.rhlinkcon.model;

public enum FeriasProgramacaoTipoFeriasEnum {
	COM_ABONO_E_DECIMO_TERCEIRO("Com abono e 13°salário"), COM_ABONO_SEM_DECIMO_TERCEIRO("Com abono e sem 13°salário"),
	FERIAS_COLETIVAS("Férias coletivas"), GOZADO("Gozado"), INCORPORADO("Incorporado"),
	SEM_ABONO_E_DECIMO_TERCEIRO("Sem abono e com 13° salário"),
	SEM_ABONO_SEM_DECIMO_TERCEIRO("Sem abono e sem 13° salário");

	private String label;

	private FeriasProgramacaoTipoFeriasEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static FeriasProgramacaoTipoFeriasEnum getEnumByString(String str) {
		for (FeriasProgramacaoTipoFeriasEnum e : FeriasProgramacaoTipoFeriasEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}
}
