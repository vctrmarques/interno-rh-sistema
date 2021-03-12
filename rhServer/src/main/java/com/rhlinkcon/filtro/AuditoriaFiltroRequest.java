package com.rhlinkcon.filtro;

import java.util.List;

public class AuditoriaFiltroRequest {

	private String periodoInicialStr;

	private String periodoFinalStr;

	private String nome;

	private List<Long> usuarioIdsByNome;

	private List<String> entidadeNomeList;

	public AuditoriaFiltroRequest() {
	}

	public String getPeriodoInicialStr() {
		return periodoInicialStr;
	}

	public void setPeriodoInicialStr(String periodoInicialStr) {
		this.periodoInicialStr = periodoInicialStr;
	}

	public String getPeriodoFinalStr() {
		return periodoFinalStr;
	}

	public void setPeriodoFinalStr(String periodoFinalStr) {
		this.periodoFinalStr = periodoFinalStr;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Long> getUsuarioIdsByNome() {
		return usuarioIdsByNome;
	}

	public void setUsuarioIdsByNome(List<Long> usuarioIdsByNome) {
		this.usuarioIdsByNome = usuarioIdsByNome;
	}

	public List<String> getEntidadeNomeList() {
		return entidadeNomeList;
	}

	public void setEntidadeNomeList(List<String> entidadeNomeList) {
		this.entidadeNomeList = entidadeNomeList;
	}

}
