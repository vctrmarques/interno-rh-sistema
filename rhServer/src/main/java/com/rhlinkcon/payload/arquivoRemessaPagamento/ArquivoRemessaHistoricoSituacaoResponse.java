package com.rhlinkcon.payload.arquivoRemessaPagamento;

import com.rhlinkcon.model.ArquivoRemessaHistoricoSituacao;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ArquivoRemessaHistoricoSituacaoResponse extends DadoCadastralResponse {

	private Long id;
	
	private ArquivoRemessaPagamentoResponse arquivoRemessa;
	
	private String situacao;
	
	public ArquivoRemessaHistoricoSituacaoResponse(ArquivoRemessaHistoricoSituacao obj) {
		setId(obj.getId());
		setArquivoRemessa(new ArquivoRemessaPagamentoResponse(obj.getArquivoRemessa()));
		setSituacao(obj.getSituacao().getLabel());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArquivoRemessaPagamentoResponse getArquivoRemessa() {
		return arquivoRemessa;
	}

	public void setArquivoRemessa(ArquivoRemessaPagamentoResponse arquivoRemessa) {
		this.arquivoRemessa = arquivoRemessa;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
