package com.rhlinkcon.payload.requisicaoPessoalFuncao;

import com.rhlinkcon.model.RequisicaoPessoalFuncao;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.turno.TurnoResponse;

public class RequisicaoPessoalFuncaoReponse {

	private Long id;

	private FuncaoResponse funcao;

	private String tipoContratacao;

	private Integer qtdVagas;

	private Double custoPorVaga;

	private TurnoResponse turno;

	public RequisicaoPessoalFuncaoReponse(RequisicaoPessoalFuncao response) {
		this.id = response.getId();
		this.funcao = new FuncaoResponse(response.getFuncao());
		this.tipoContratacao = response.getTipoContratacao().getLabel();
		this.qtdVagas = response.getQtdVagas();
		this.custoPorVaga = response.getCustoPorVaga();
		this.turno = new TurnoResponse(response.getTurno());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncaoResponse getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoResponse funcao) {
		this.funcao = funcao;
	}

	public String getTipoContratacao() {
		return tipoContratacao;
	}

	public void setTipoContratacao(String tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}

	public Integer getQtdVagas() {
		return qtdVagas;
	}

	public void setQtdVagas(Integer qtdVagas) {
		this.qtdVagas = qtdVagas;
	}

	public Double getCustoPorVaga() {
		return custoPorVaga;
	}

	public void setCustoPorVaga(Double custoPorVaga) {
		this.custoPorVaga = custoPorVaga;
	}

	public TurnoResponse getTurno() {
		return turno;
	}

	public void setTurno(TurnoResponse turno) {
		this.turno = turno;
	}

}
