package com.rhlinkcon.payload.arquivoRemessaPagamento;

import java.time.LocalDate;

import com.rhlinkcon.model.ArquivoRemessaPagamento;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;

public class ArquivoRemessaPagamentoResponse extends DadoCadastralResponse {

	private Long id;
	
	private FolhaPagamentoResponse folhaPagamento;
	
	private Integer numeroRemessa;
	
	private LocalDate dataPagamento;
	
	private String situacao;
	
	private String motivo;
	
	public ArquivoRemessaPagamentoResponse(ArquivoRemessaPagamento obj) {
		setId(obj.getId());
		setFolhaPagamento(new FolhaPagamentoResponse(obj.getFolhaPagamento()));
		setNumeroRemessa(obj.getNumeroRemessa());
		setDataPagamento(obj.getDataPagamento());
		setSituacao(obj.getSituacao().getLabel());
		setMotivo(obj.getMotivo());
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

	public Integer getNumeroRemessa() {
		return numeroRemessa;
	}

	public void setNumeroRemessa(Integer numeroRemessa) {
		this.numeroRemessa = numeroRemessa;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
