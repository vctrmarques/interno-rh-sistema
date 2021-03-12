package com.rhlinkcon.payload.importadorLancamentoManual;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ImportadorLancamentoManual;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportadorLancamentoManualResponse extends DadoCadastralResponse {

	private Long id;

	private FolhaPagamentoResponse folhaPagamento;

	private AnexoResponse arquivoImportacao;

	private Boolean excluido;

	public ImportadorLancamentoManualResponse(ImportadorLancamentoManual importador) {
		this.id = importador.getId();
		this.arquivoImportacao = new AnexoResponse(importador.getArquivoImportacao());
		this.excluido = importador.getExcluido();
		this.setCriadoEm(importador.getCreatedAt());
		this.setAlteradoEm(importador.getUpdatedAt());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FolhaPagamentoResponse getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(FolhaPagamentoResponse folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public AnexoResponse getArquivoImportacao() {
		return arquivoImportacao;
	}

	public void setArquivoImportacao(AnexoResponse arquivoImportacao) {
		this.arquivoImportacao = arquivoImportacao;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

}
