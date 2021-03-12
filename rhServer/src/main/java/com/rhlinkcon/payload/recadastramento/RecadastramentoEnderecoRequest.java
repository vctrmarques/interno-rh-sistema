package com.rhlinkcon.payload.recadastramento;

import java.util.List;

import com.rhlinkcon.payload.telefone.TelefoneRequest;

public class RecadastramentoEnderecoRequest {

	private Long id;
	
	private Long municipioId;
	
	private String cep;
	
	private String bairro;
	
	private String logradouro;
	
	private String complemento;
	
	private String numero;
	
	private String email;
	
	private String observacao;
	
	private List<TelefoneRequest> telefones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TelefoneRequest> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneRequest> telefones) {
		this.telefones = telefones;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
	
}
