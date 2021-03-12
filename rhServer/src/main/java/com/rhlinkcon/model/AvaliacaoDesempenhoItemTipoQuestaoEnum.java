package com.rhlinkcon.model;

public enum AvaliacaoDesempenhoItemTipoQuestaoEnum {
	DISCIPLINA("Disciplina"), DESEMPENHO_FUNCIONAL("Desempenho Funcional"),
	HABILIDADES_PESSOAIS("Habilidades Pessoais");

	private String label;

	private AvaliacaoDesempenhoItemTipoQuestaoEnum(String label) {
		this.label = label;
	}

	public static AvaliacaoDesempenhoItemTipoQuestaoEnum getEnumByString(String str) {
		for (AvaliacaoDesempenhoItemTipoQuestaoEnum e : AvaliacaoDesempenhoItemTipoQuestaoEnum.values()) {
			if (str.equals(e.getLabel()))
				return e;
		}
		return null;
	}

	public String getLabel() {
		return label;
	}
}