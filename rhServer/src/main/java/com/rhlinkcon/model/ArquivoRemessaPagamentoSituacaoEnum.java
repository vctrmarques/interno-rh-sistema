package com.rhlinkcon.model;

public enum ArquivoRemessaPagamentoSituacaoEnum {
	EM_ABERTO("Em aberto"), ARQUIVO_GERADO("Arquivo gerado"), ERRO_GERACAO_ARQUIVO("Erro ao gerar arquivo"),
	ARQUIVO_PROCESSAMENTO("Arquivo em processamento"), PAGAMENTO_EFETUADO("Pagamento efetuado"),
	REJEITADO("Arquivo rejeitado"), CANCELADO("Arquivo cancelado");

	private String label;

	private ArquivoRemessaPagamentoSituacaoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static ArquivoRemessaPagamentoSituacaoEnum getEnumByString(String str) {
		for (ArquivoRemessaPagamentoSituacaoEnum e : ArquivoRemessaPagamentoSituacaoEnum.values()) {
			if (str.equals(e.getLabel()) || str.equals(e.name()))
				return e;
		}
		return null;
	}
}
