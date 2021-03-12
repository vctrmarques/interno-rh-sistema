package com.rhlinkcon.model;

public enum StatusCertidaoCompensacaoEnum {
	NAO_TEM("Não tem compensação/Arquivo"), AGUARDANDO_ENVIO("Aguardando envio"), ENVIADOS("Enviados"),
	OUTROS_RPPS("Outros RPPS"), AGUARDANDO_IMAGEM("Aguardando imagem"), EM_ANALISE("Em análise"),
	INDEFERIDOS("Indeferidos"), COMPENSADOS_ARQUIVO("Compensados/Arquivo"), PROCESSO_CRIADO("Processo criado"),
	CRIADA("Criada"), EXCLUIDA("Excluída"), DEFERIDO("Deferido");

	private String label;

	private StatusCertidaoCompensacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static StatusCertidaoCompensacaoEnum getEnumByString(String str) {
		for (StatusCertidaoCompensacaoEnum e : StatusCertidaoCompensacaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
