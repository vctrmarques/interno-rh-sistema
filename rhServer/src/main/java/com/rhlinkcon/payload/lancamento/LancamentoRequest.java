package com.rhlinkcon.payload.lancamento;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LancamentoRequest {
	private Long id;

	private Long funcionarioId;

	private Long verbaId;

	private Double valorReferencia;

	@NotNull
	private Long folhaPagamentoId;

	@NotEmpty
	private List<Long> funcionariosId;

	private List<LancamentoVerbaManualRequest> verbaManualList;

	public LancamentoRequest() {

	}

	public List<Long> getFuncionariosId() {
		return funcionariosId;
	}

	public void setFuncionariosId(List<Long> funcionariosId) {
		this.funcionariosId = funcionariosId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getVerbaId() {
		return verbaId;
	}

	public void setVerbaId(Long verbaId) {
		this.verbaId = verbaId;
	}

	public Double getValorReferencia() {
		return valorReferencia;
	}

	public void setValorReferencia(Double valorReferencia) {
		this.valorReferencia = valorReferencia;
	}

	public Long getFolhaPagamentoId() {
		return folhaPagamentoId;
	}

	public void setFolhaPagamentoId(Long folhaPagamentoId) {
		this.folhaPagamentoId = folhaPagamentoId;
	}

	public List<LancamentoVerbaManualRequest> getVerbaManualList() {
		return verbaManualList;
	}

	public void setVerbaManualList(List<LancamentoVerbaManualRequest> verbaManualList) {
		this.verbaManualList = verbaManualList;
	}

}
