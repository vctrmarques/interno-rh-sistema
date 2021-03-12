package com.rhlinkcon.payload.funcionario;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.payload.funcao.FuncaoRequest;

public class FuncionarioAnexoRequest {

	// Início aba Dados Cadastrais
	private Long id;

	@NotNull
	private Long funcionarioId;
	
	@NotNull
	private Long anexoId;
	
	private String observacao;



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

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


}
