package com.rhlinkcon.model;

public enum AnexoPastaEnum {
	EMPRESA_FILIAL("empresafilial"),
	USUARIO("usuario"),
	FUNCIONARIO("funcionario"),
	DESCONTO_SINDICAL("descontosindical"),
	PROCESSO("processo"),
	ACIDENTE_TRABALHO("acidentetrabalho"),
	PENSAO_PREVIDENCIARIA("pensaoPrevidenciaria"),
	DECLARACAO_APOSENTADORIA("declaracaoAposentadoria"),
	REQUISICAO_PESSOAL_CURRICULO("requisicaoPessoalCurriculo"),
	CERTIDAO_EX_SEGURADO("certidaoExSegurado"),
	FALTA("falta"),
	IMPORTACAO_LANCAMENTO_MANUAL("importadorLancamentoManual"),
	CERTIDAO_COMPENSACAO("certidaoCompensacao"),
	RECADASTRAMENTO("recadastramento"),
	IMPORTACAO_VERBA_FUNCIONARIO("importacaoVerbaFuncionario"),
	LEGISLACAO("legislacao"),
	ARQUIVO_REMESSA("arquivoRemessaPagamento"),
	RELATORIO_FINANCEIRO_FOLHA_PAGAMENTO("relatorioFinanceiroFolhaPagamento"),
	DIRF("dirf");
	

	private String pasta;

	private AnexoPastaEnum(String pasta) {
		this.pasta = pasta;
	}

	public String getPasta() {
		return pasta;
	}

	public static AnexoPastaEnum getEnumByString(String pastaStr) {
		for (AnexoPastaEnum e : AnexoPastaEnum.values()) {
			if (pastaStr.equals(e.pasta))
				return e;
		}
		return null;
	}

}
