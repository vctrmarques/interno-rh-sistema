package com.rhlinkcon.payload.tipoAposentadoria;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TipoAposentadoriaRequest {
	
	private Long id;
	
	@NotNull
	private Integer codigo;
	
	@NotNull
	@NotBlank
	private String descricao;

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
	
	

}
