package com.rhlinkcon.payload.parametroGlobal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.model.ParametroGlobalTipoEnum;

public class ParametroGlobalRequest {

	private Long id;

	@NotBlank
	@NotNull
	private ParametroGlobalChaveEnum chave;

	@NotNull
	private ParametroGlobalTipoEnum tipo;

	@NotBlank
	@NotNull
	private String valor;

	private boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ParametroGlobalTipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(ParametroGlobalTipoEnum tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public ParametroGlobalChaveEnum getChave() {
		return chave;
	}

	public void setChave(ParametroGlobalChaveEnum chave) {
		this.chave = chave;
	}

}
