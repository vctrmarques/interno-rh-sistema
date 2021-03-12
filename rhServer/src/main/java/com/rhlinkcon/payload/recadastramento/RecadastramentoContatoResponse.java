package com.rhlinkcon.payload.recadastramento;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.rhlinkcon.model.RecadastramentoContato;
import com.rhlinkcon.payload.telefone.TelefoneReponse;

public class RecadastramentoContatoResponse {

	private Long id;
	
	private String nome;
	
	private String email;
	
	private String observacao;
	
	private List<TelefoneReponse> telefones;
	
	public RecadastramentoContatoResponse(RecadastramentoContato obj) {
		
		setId(obj.getId());
		setNome(obj.getNome());
		setEmail(obj.getEmail());
		setObservacao(obj.getObservacao());
		
		if (Objects.nonNull(obj.getTelefones()))
			setTelefones(obj.getTelefones().stream().map(tel -> new TelefoneReponse(tel)).collect(Collectors.toList()));
		
	}

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

	public List<TelefoneReponse> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<TelefoneReponse> telefones) {
		this.telefones = telefones;
	}
	
}
