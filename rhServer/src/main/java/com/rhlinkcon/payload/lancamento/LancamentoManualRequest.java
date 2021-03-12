package com.rhlinkcon.payload.lancamento;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LancamentoManualRequest {

	@NotNull
	private Long folhaPagamentoId;

	@NotNull
	@NotEmpty
	private List<Long> funcionariosId;

	@NotNull
	@NotEmpty
	private List<LancamentoVerbaManualRequest> verbaManualList;

	private Long folhaPagamentoFuncionarioVerbaId;

	public LancamentoManualRequest() {

	}

	public Long getFolhaPagamentoId() {
		return folhaPagamentoId;
	}

	public void setFolhaPagamentoId(Long folhaPagamentoId) {
		this.folhaPagamentoId = folhaPagamentoId;
	}

	public Long getFolhaPagamentoFuncionarioVerbaId() {
		return folhaPagamentoFuncionarioVerbaId;
	}

	public void setFolhaPagamentoFuncionarioVerbaId(Long folhaPagamentoFuncionarioVerbaId) {
		this.folhaPagamentoFuncionarioVerbaId = folhaPagamentoFuncionarioVerbaId;
	}

	public List<Long> getFuncionariosId() {
		return funcionariosId;
	}

	public void setFuncionariosId(List<Long> funcionariosId) {
		this.funcionariosId = funcionariosId;
	}

	public List<LancamentoVerbaManualRequest> getVerbaManualList() {
		return verbaManualList;
	}

	public void setVerbaManualList(List<LancamentoVerbaManualRequest> verbaManualList) {
		this.verbaManualList = verbaManualList;
	}

}
