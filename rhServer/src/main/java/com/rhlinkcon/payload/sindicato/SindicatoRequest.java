package com.rhlinkcon.payload.sindicato;

import javax.validation.constraints.NotNull;

public class SindicatoRequest {

	private Long id;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private Integer mesBase;
	
	@NotNull
	private Integer pisoSalarial;
	
	@NotNull
	private String cnpj;
	
	@NotNull
	private String codigoEntidade;
	
	@NotNull
	private Integer ddd;

	@NotNull
	private String telefone;

	@NotNull
	private String patronal;
	
	@NotNull
	private String endereco;

	@NotNull
	private String numero;
	
	@NotNull
	private String complemento;

	@NotNull
	private String bairro;

	@NotNull
	private String municipio;
	
	@NotNull
	private Long unidadeFederativaId;
	
	@NotNull
	private String cep;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getMesBase() {
		return mesBase;
	}
	
	public void setMesBase(Integer mesBase) {
		this.mesBase = mesBase;
	}
	
	public Integer getPisoSalarial() {
		return pisoSalarial;
	}
	
	public void setPisoSalarial(Integer pisoSalarial) {
		this.pisoSalarial = pisoSalarial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCodigoEntidade() {
		return codigoEntidade;
	}

	public void setCodigoEntidade(String codigoEntidade) {
		this.codigoEntidade = codigoEntidade;
	}
	
	public Integer getDdd() {
		return ddd;
	}
	
	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getPatronal() {
		return patronal;
	}

	public void setPatronal(String patronal) {
		this.patronal = patronal;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public Long getUnidadeFederativaId() {
		return unidadeFederativaId;
	}

	public void setUnidadeFederativaId(Long unidadeFederativaId) {
		this.unidadeFederativaId = unidadeFederativaId;
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
}