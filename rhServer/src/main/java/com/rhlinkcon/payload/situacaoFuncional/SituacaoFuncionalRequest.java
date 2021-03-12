package com.rhlinkcon.payload.situacaoFuncional;

import javax.validation.constraints.NotNull;

public class SituacaoFuncionalRequest {

	private Long id;

	@NotNull
	private String codigo;

	@NotNull
	private String descricao;

	private Integer codigoMotivoRetorno;

	private Integer raisSituacao;

	private Integer rais;

	private String tipo;

	private String modalidade;

	private Integer qtdDias;

	private Long motivoAfastamentoId;
	
	private Long motivoDesligamentoId;
	
	private Long causaAfastamentoId;
	
	private boolean entraFolha;  
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigoMotivoRetorno() {
		return codigoMotivoRetorno;
	}

	public void setCodigoMotivoRetorno(Integer codigoMotivoRetorno) {
		this.codigoMotivoRetorno = codigoMotivoRetorno;
	}

	public Integer getRaisSituacao() {
		return raisSituacao;
	}

	public void setRaisSituacao(Integer raisSituacao) {
		this.raisSituacao = raisSituacao;
	}

	public Integer getRais() {
		return rais;
	}

	public void setRais(Integer rais) {
		this.rais = rais;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	public Long getMotivoAfastamentoId() {
		return motivoAfastamentoId;
	}

	public void setMotivoAfastamentoId(Long motivoAfastamentoId) {
		this.motivoAfastamentoId = motivoAfastamentoId;
	}

	public Long getMotivoDesligamentoId() {
		return motivoDesligamentoId;
	}

	public void setMotivoDesligamentoId(Long motivoDesligamentoId) {
		this.motivoDesligamentoId = motivoDesligamentoId;
	}

	public Long getCausaAfastamentoId() {
		return causaAfastamentoId;
	}

	public void setCausaAfastamentoId(Long causaAfastamentoId) {
		this.causaAfastamentoId = causaAfastamentoId;
	}

	public boolean isEntraFolha() {
		return entraFolha;
	}

	public void setEntraFolha(boolean entraFolha) {
		this.entraFolha = entraFolha;
	}

}