package com.rhlinkcon.payload.tipoProcessamento;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TipoProcessamentoRequest {

	private Long id;

	@NotNull
	private String codigo;

	@NotBlank
	@NotNull
	private String descricao;

	private List<Long> verbaIds = new ArrayList<Long>();

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

	public List<Long> getVerbaIds() {
		return verbaIds;
	}

	public void setVerbaIds(List<Long> verbaIds) {
		this.verbaIds = verbaIds;
	}

}
