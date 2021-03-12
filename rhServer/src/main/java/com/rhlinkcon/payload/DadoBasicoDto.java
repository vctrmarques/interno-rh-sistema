package com.rhlinkcon.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.audit.PersistentId;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DadoBasicoDto {

	private Long id;

	private String label;

	private String descricao;

	public DadoBasicoDto() {

	}

	public DadoBasicoDto(Long id, String label) {
		this.id = id;
		this.descricao = label;
		this.label = label;
	}

	public DadoBasicoDto(PersistentId persistentId) {
		this.id = persistentId.getId();
		this.label = persistentId.getLabel();
		this.descricao = this.label;
	}

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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
