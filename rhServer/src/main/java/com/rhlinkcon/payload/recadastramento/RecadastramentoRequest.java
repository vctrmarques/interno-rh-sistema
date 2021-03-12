package com.rhlinkcon.payload.recadastramento;

import java.time.LocalDate;
import java.util.List;

public class RecadastramentoRequest {

	private Long id;
	
	private Long funcionarioId;
	
	private Long pensaoId;
	
	private RecadastramentoDadosRequest dados;
	
	private RecadastramentoEnderecoRequest endereco;
	
	private RecadastramentoContatoRequest contato;
	
	private LocalDate data;
	
	private boolean status;
	
	private List<Long> anexos;

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

	public Long getPensaoId() {
		return pensaoId;
	}

	public void setPensaoId(Long pensaoId) {
		this.pensaoId = pensaoId;
	}

	public RecadastramentoDadosRequest getDados() {
		return dados;
	}

	public void setDados(RecadastramentoDadosRequest dados) {
		this.dados = dados;
	}

	public RecadastramentoEnderecoRequest getEndereco() {
		return endereco;
	}

	public void setEndereco(RecadastramentoEnderecoRequest endereco) {
		this.endereco = endereco;
	}

	public RecadastramentoContatoRequest getContato() {
		return contato;
	}

	public void setContato(RecadastramentoContatoRequest contato) {
		this.contato = contato;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Long> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Long> anexos) {
		this.anexos = anexos;
	}
	
	
	
}
