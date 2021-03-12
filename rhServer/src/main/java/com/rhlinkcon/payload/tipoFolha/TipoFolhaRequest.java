package com.rhlinkcon.payload.tipoFolha;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TipoFolhaRequest {

	private Long id;
	
	@NotNull
	private String codigo;
	
	@NotNull
	@NotBlank
	private String descricao;
	
	private List<Long> verbasId;

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

	public List<Long> getVerbasId() {
		return verbasId;
	}

	public void setVerbasId(List<Long> verbasId) {
		this.verbasId = verbasId;
	}
	
}