package com.rhlinkcon.payload.tipoAverbacao;

import javax.validation.constraints.NotNull;

public class TipoAverbacaoRequest {

	private Long id;
	
	@NotNull
	private Integer codigo;

	@NotNull
	private String descricao;

	private String formaAverbacao;

	private String deducaoAverbacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFormaAverbacao() {
		return formaAverbacao;
	}

	public void setFormaAverbacao(String formaAverbacao) {
		this.formaAverbacao = formaAverbacao;
	}

	public String getDeducaoAverbacao() {
		return deducaoAverbacao;
	}

	public void setDeducaoAverbacao(String deducaoAverbacao) {
		this.deducaoAverbacao = deducaoAverbacao;
	}
	
}