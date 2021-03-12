package com.rhlinkcon.payload.entidadeExame;

import java.util.List;

import com.rhlinkcon.payload.generico.EnderecoDto;

public class EntidadeExameRequest {

	private Long id;

	private Long tomadorServicoId;

	private String tipo;

	private List<Long> examesIds;

	private EnderecoDto endereco;

	private String telefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTomadorServicoId() {
		return tomadorServicoId;
	}

	public void setTomadorServicoId(Long tomadorServicoId) {
		this.tomadorServicoId = tomadorServicoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Long> getExamesIds() {
		return examesIds;
	}

	public void setExamesIds(List<Long> examesIds) {
		this.examesIds = examesIds;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
