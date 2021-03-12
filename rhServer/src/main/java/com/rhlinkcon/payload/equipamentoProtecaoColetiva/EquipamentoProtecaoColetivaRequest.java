package com.rhlinkcon.payload.equipamentoProtecaoColetiva;

import java.time.Instant;

import javax.validation.constraints.NotNull;

public class EquipamentoProtecaoColetivaRequest {

	private Long id;
	
	@NotNull
	private String codigo;
	
	@NotNull
	private String tipoProtecao;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private Instant validade;
	
	@NotNull
	private String certificado;
	
	@NotNull
	private String referencia;
	
	private Integer minimo;

	private Integer livre;

	private Integer atual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getTipoProtecao() {
		return tipoProtecao;
	}

	public void setTipoProtecao(String tipoProtecao) {
		this.tipoProtecao = tipoProtecao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Instant getValidade() {
		return validade;
	}

	public void setValidade(Instant validade) {
		this.validade = validade;
	}
	
	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getMinimo() {
		return minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	}

	public Integer getLivre() {
		return livre;
	}

	public void setLivre(Integer livre) {
		this.livre = livre;
	}

	public Integer getAtual() {
		return atual;
	}

	public void setAtual(Integer atual) {
		this.atual = atual;
	}
	
}