package com.rhlinkcon.model;

public enum GrupamentoNaturezaEnum {

	ADMINSTRACAO_PUBLICA("Administração Pública"), ENTIDADES_EMPRESARIAIS("Entidades Empresariais"),
	ENTIDADES_SEM_FINS_LUCRATIVOS("Entidades Sem Fins Lucrativos"), PESSOAS_FISICAS("Pessoas Físicas"),
	ORG_INT_OUT_INST_EXT("Organizações Internacionais e Outras Instituições Extraterritoriais");

	private String label;

	private GrupamentoNaturezaEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static GrupamentoNaturezaEnum getEnumByString(String str) {
		for (GrupamentoNaturezaEnum e : GrupamentoNaturezaEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

}
