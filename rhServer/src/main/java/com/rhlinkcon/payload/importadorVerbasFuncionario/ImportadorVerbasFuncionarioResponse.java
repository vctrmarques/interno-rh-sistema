package com.rhlinkcon.payload.importadorVerbasFuncionario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ImportadorVerbasFuncionario;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportadorVerbasFuncionarioResponse extends DadoCadastralResponse {

	private Long id;

	private AnexoResponse arquivoImportacao;

	private Boolean excluido;

	public ImportadorVerbasFuncionarioResponse(ImportadorVerbasFuncionario importador) {
		this.setId(importador.getId());
		this.setArquivoImportacao(new AnexoResponse(importador.getArquivoImportacao()));
		this.excluido = importador.getExcluido();
		this.setCriadoEm(importador.getCreatedAt());
		this.setAlteradoEm(importador.getUpdatedAt());
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AnexoResponse getArquivoImportacao() {
		return arquivoImportacao;
	}

	public void setArquivoImportacao(AnexoResponse arquivoImportacao) {
		this.arquivoImportacao = arquivoImportacao;
	}

}
