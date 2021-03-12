package com.rhlinkcon.payload.perfil;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class PerfilUsuarioRequest {

	@NotNull
	private Long id;

	private Long usuarioId;

	private List<Long> usuarioIds = new ArrayList<Long>();

	public PerfilUsuarioRequest() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getUsuarioIds() {
		return usuarioIds;
	}

	public void setUsuarioIds(List<Long> usuarioIds) {
		this.usuarioIds = usuarioIds;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

}
