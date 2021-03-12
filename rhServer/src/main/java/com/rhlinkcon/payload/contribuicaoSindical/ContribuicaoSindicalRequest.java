package com.rhlinkcon.payload.contribuicaoSindical;

import javax.validation.constraints.NotNull;

public class ContribuicaoSindicalRequest {
	
	private Long id;
	
	@NotNull
	private Long funcionarioId;
	
	@NotNull
	private Integer ano;
	
	@NotNull
	private Long sindicatoId;
	
	@NotNull
	private boolean permiteDesconto;
	
	private Long anexoId;

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

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Long getSindicatoId() {
		return sindicatoId;
	}

	public void setSindicatoId(Long sindicatoId) {
		this.sindicatoId = sindicatoId;
	}

	public boolean getPermiteDesconto() {
		return permiteDesconto;
	}

	public void setPermiteDesconto(boolean permiteDesconto) {
		this.permiteDesconto = permiteDesconto;
	}

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

}
