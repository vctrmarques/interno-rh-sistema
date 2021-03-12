package com.rhlinkcon.payload.menu;

import java.util.List;

public class MenuCategoriaResponse {

	private String nome;

	private List<MenuResponse> subMenus;

	public MenuCategoriaResponse(String nome, List<MenuResponse> subMenus) {
		this.nome = nome;
		this.subMenus = subMenus;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<MenuResponse> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<MenuResponse> subMenus) {
		this.subMenus = subMenus;
	}

}
