package com.rhlinkcon.payload.banco;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BancoRequest {

	private Long id;

	@NotNull
	private String codigo;

	@NotBlank
	@NotNull
	private String nome;

	private boolean bloqueado;
	
	private Boolean principal;

	private List<Long> agenciasIds;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public List<Long> getAgenciasIds() {
		return agenciasIds;
	}

	public void setAgenciasIds(List<Long> agenciasIds) {
		this.agenciasIds = agenciasIds;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

}
