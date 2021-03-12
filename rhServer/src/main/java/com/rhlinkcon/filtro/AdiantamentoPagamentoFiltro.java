package com.rhlinkcon.filtro;

import com.rhlinkcon.model.StatusAdiantamentoEnum;

public class AdiantamentoPagamentoFiltro {
	private String nome;
	private String matricula;
	private StatusAdiantamentoEnum status;
	private String competencia;
	private Long filialId;
	private Long lotacaoId;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public StatusAdiantamentoEnum getStatus() {
		return status;
	}
	
	public void setStatus(StatusAdiantamentoEnum status) {
		this.status = status;
	}
	
	public String getCompetencia() {
		return competencia;
	}
	
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	
	public Long getFilialId() {
		return filialId;
	}
	
	public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}
	
	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}
}
