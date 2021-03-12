package com.rhlinkcon.payload.arquivoRemessaPagamento;

public class ArquivoRemessaHistoricoSituacaoRequest {

	private Long id;
	
	private Long arquivoRemessaId;
	
	private String situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArquivoRemessaId() {
		return arquivoRemessaId;
	}

	public void setArquivoRemessaId(Long arquivoRemessaId) {
		this.arquivoRemessaId = arquivoRemessaId;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
	
}
