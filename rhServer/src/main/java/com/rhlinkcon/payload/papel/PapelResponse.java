package com.rhlinkcon.payload.papel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.payload.menu.MenuResponse;
import com.rhlinkcon.util.Projecao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PapelResponse {

	private Long id;

	private String nome;

	private MenuResponse menu;

	public PapelResponse() {
	}

	public PapelResponse(Papel papel, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = papel.getId();
			this.nome = papel.getNome().getLabel();

			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				if (papel.getMenu() != null)
					this.menu = new MenuResponse(papel.getMenu());
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public MenuResponse getMenu() {
		return menu;
	}

	public void setMenu(MenuResponse menu) {
		this.menu = menu;
	}

}
