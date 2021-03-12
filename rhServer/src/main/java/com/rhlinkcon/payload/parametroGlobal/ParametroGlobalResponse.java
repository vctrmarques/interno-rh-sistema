package com.rhlinkcon.payload.parametroGlobal;

import com.rhlinkcon.model.ParametroGlobal;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.model.ParametroGlobalTipoEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ParametroGlobalResponse extends DadoCadastralResponse {

	private Long id;

	private ParametroGlobalChaveEnum chave;

	private ParametroGlobalTipoEnum tipo;

	private String valor;

	private boolean ativo;

	public ParametroGlobalResponse(ParametroGlobal parametroGlobal) {
		this.id = parametroGlobal.getId();
		this.setChave(parametroGlobal.getChave());
		this.tipo = parametroGlobal.getTipo();
		this.valor = parametroGlobal.getValor();
		this.ativo = parametroGlobal.isAtivo();
	}

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
