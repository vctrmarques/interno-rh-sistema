package com.rhlinkcon.model;

public enum TipoAposentadoriaEnum {

	// Tipo de aposentadoria pertinente ao simulador
	VOLUNTARIA("Voluntária"), COMPULSORIA("Compulsória"), // usada também para dados complementares do funcionário
	INVALIDEZ("Invalidez"),

	// Tipo de aposentadoria pertinente aos dados complementares do funcionário
	TEMPO_SERVICO_INTEGRAL("Tempo de Serviço Integral"), TEMPO_SERVICO_PROPORCIAL("Tempo de Serviço Proporcinal"),
	IDADE_INTEGRAL("Idade Integral"), IDADE_PROPORCIONAL("Idade Proporcional"),
	INVALIDEZ_INTEGRAL("Invalidez Integral"), INVALIDEZ_PROPORCIONAL("Invalidez Proporcional"),
	ESPECIAL_PROFESSOR("Especial Professor Regente"), ESPECIAL_ATIVIDADE_INSALUBRE("Especial Atividade Insalubre");

	private String tipoAposentadoria;

	private TipoAposentadoriaEnum(String tipoAposentadoria) {
		this.tipoAposentadoria = tipoAposentadoria;
	}

	public String getTipoAposentadoria() {
		return tipoAposentadoria;
	}

	public static TipoAposentadoriaEnum getEnumByString(String str) {
		for (TipoAposentadoriaEnum e : TipoAposentadoriaEnum.values()) {
			if (str.equals(e.tipoAposentadoria))
				return e;
		}
		return null;
	}
}
