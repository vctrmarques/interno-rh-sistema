package com.rhlinkcon.payload.importadorLancamentoManual;

public class ErroImportData {

	private Integer linha;

	private String descricao;

	public ErroImportData(Integer linha, String descricao) {
		this.linha = linha;
		this.descricao = descricao;
	}

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
