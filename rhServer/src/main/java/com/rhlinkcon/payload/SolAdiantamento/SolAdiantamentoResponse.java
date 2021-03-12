package com.rhlinkcon.payload.SolAdiantamento;

import com.rhlinkcon.model.SolAdiantamento;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

public class SolAdiantamentoResponse extends DadoCadastralResponse{

	private Long id;

	private FuncionarioResponse solicitante;

	private String porcentagemAdiantamento;
	
	private String mesAdiantamento;
	
	private String situacao;

	public SolAdiantamentoResponse(SolAdiantamento sol){
		this.id = sol.getId();
		this.solicitante = new FuncionarioResponse(sol.getSolicitante());
		this.porcentagemAdiantamento = sol.getPorcentagemAdiantamento().getLabel();
		this.mesAdiantamento = sol.getMesAdiantamento().getLabel();
		this.situacao = sol.getSituacao().getLabel();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(FuncionarioResponse solicitante) {
		this.solicitante = solicitante;
	}

	public String getPorcentagemAdiantamento() {
		return porcentagemAdiantamento;
	}

	public void setPorcentagemAdiantamento(String porcentagemAdiantamento) {
		this.porcentagemAdiantamento = porcentagemAdiantamento;
	}

	public String getMesAdiantamento() {
		return mesAdiantamento;
	}

	public void setMesAdiantamento(String mesAdiantamento) {
		this.mesAdiantamento = mesAdiantamento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	
}
