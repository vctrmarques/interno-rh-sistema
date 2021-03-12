package com.rhlinkcon.payload.declaracaoAposentadoria;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura.DeclaracaoAposentadoriaAssinaturaRequest;
import com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao.DeclaracaoAposentadoriaAverbacaoRequest;

public class DeclaracaoAposentadoriaRequest {

	private Long id;

	private Long numero;

	private Integer ano;

	private Long funcionarioId;

	private List<Long> anexos;

	@NotEmpty
	@NotNull
	private Set<DeclaracaoAposentadoriaAverbacaoRequest> averbacoes;

	private Set<DeclaracaoAposentadoriaAssinaturaRequest> assinaturas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public List<Long> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Long> anexos) {
		this.anexos = anexos;
	}

	public Set<DeclaracaoAposentadoriaAverbacaoRequest> getAverbacoes() {
		return averbacoes;
	}

	public void setAverbacoes(Set<DeclaracaoAposentadoriaAverbacaoRequest> averbacoes) {
		this.averbacoes = averbacoes;
	}

	public Set<DeclaracaoAposentadoriaAssinaturaRequest> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(Set<DeclaracaoAposentadoriaAssinaturaRequest> assinaturas) {
		this.assinaturas = assinaturas;
	}

}
