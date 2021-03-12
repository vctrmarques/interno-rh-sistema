package com.rhlinkcon.filtro;

public class BancoFiltro {
	private Integer codigo;

	private String nome;

	public BancoFiltro(String nome, String codigoStr) {
		this.nome = nome;
		if (codigoStr != null && !codigoStr.isEmpty()) {
			this.codigo = Integer.parseInt(codigoStr);
		}
	}
	
	public BancoFiltro() {
		
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
