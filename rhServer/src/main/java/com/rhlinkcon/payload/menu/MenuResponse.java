package com.rhlinkcon.payload.menu;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Menu;
import com.rhlinkcon.payload.papel.PapelResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuResponse {

	private Long id;

	private String nome;

	private boolean ativo;

	private String categoria;

	private String url;

	private List<PapelResponse> papeis;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MenuResponse() {
	}

	public MenuResponse(Menu menu) {
		this.id = menu.getId();
		this.ativo = menu.isAtivo();
		this.nome = menu.getNome();
		this.url = menu.getUrl();
		this.categoria = menu.getCategoria().getCategoria();
	}

	public MenuResponse(Menu menu, List<PapelResponse> papeis) {
		this.id = menu.getId();
		this.ativo = menu.isAtivo();
		this.nome = menu.getNome();
		this.url = menu.getUrl();
		this.categoria = menu.getCategoria().getCategoria();
		this.papeis = papeis;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<PapelResponse> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<PapelResponse> papeis) {
		this.papeis = papeis;
	}

}
