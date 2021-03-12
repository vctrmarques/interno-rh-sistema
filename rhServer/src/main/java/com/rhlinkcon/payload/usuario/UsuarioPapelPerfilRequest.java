package com.rhlinkcon.payload.usuario;

import java.util.List;

import javax.validation.constraints.NotNull;

public class UsuarioPapelPerfilRequest {

	@NotNull
	private Long usuarioId;

	private List<Long> papelIds;

	private List<Long> perfilIds;

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public List<Long> getPapelIds() {
		return papelIds;
	}

	public void setPapelIds(List<Long> papelIds) {
		this.papelIds = papelIds;
	}

	public List<Long> getPerfilIds() {
		return perfilIds;
	}

	public void setPerfilIds(List<Long> perfilIds) {
		this.perfilIds = perfilIds;
	}

}
