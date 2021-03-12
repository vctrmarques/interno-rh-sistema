package com.rhlinkcon.payload.etapa;

import javax.validation.constraints.NotNull;

public class EtapaRequest {

	private Long id;

	@NotNull
	private String descricao;
	
	@NotNull
	private Integer codigo;

	@NotNull
	private String processo;

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

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

}