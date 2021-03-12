package com.rhlinkcon.payload.funcionario;

import javax.validation.constraints.NotNull;

public class FuncionarioValeTransporteRequest {

	private Long id;

	private Long funcionarioId;

	@NotNull
	private Integer quantidade;

	@NotNull
	private Long valeTransporteId;

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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Long getValeTransporteId() {
		return valeTransporteId;
	}

	public void setValeTransporteId(Long valeTransporteId) {
		this.valeTransporteId = valeTransporteId;
	}

}
