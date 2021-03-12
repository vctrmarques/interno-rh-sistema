package com.rhlinkcon.payload.recadastramento;

import java.util.List;

import com.rhlinkcon.payload.telefone.TelefoneRequest;

public class RecadastramentoContatoRequest {

	private Long id;
	
	private String nome;
	
	private String email;
	
	private String observacao;
	
	private List<TelefoneRequest> telefones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<TelefoneRequest> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneRequest> telefones) {
		this.telefones = telefones;
	}
	
}
