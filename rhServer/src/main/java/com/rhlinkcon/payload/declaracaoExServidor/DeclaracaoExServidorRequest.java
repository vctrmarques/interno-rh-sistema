package com.rhlinkcon.payload.declaracaoExServidor;

import java.util.Set;

public class DeclaracaoExServidorRequest {

	private Long id;

	private Long funcionarioId;
	
	private Long responsavelId;
	
	private Long dirigenteId;
	
	private String status;
	
	private Set<DeclaracaoExServidorDadosFuncionaisRequest> dadosFuncionais;
	
	private boolean rascunho;

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

	public Long getResponsavelId() {
		return responsavelId;
	}

	public void setResponsavelId(Long responsavelId) {
		this.responsavelId = responsavelId;
	}

	public Long getDirigenteId() {
		return dirigenteId;
	}

	public void setDirigenteId(Long dirigenteId) {
		this.dirigenteId = dirigenteId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<DeclaracaoExServidorDadosFuncionaisRequest> getDadosFuncionais() {
		return dadosFuncionais;
	}

	public void setDadosFuncionais(Set<DeclaracaoExServidorDadosFuncionaisRequest> dadosFuncionais) {
		this.dadosFuncionais = dadosFuncionais;
	}

	public boolean isRascunho() {
		return rascunho;
	}

	public void setRascunho(boolean rascunho) {
		this.rascunho = rascunho;
	}

}
