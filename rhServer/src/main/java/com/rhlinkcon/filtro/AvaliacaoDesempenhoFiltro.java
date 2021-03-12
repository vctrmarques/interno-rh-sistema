package com.rhlinkcon.filtro;

public class AvaliacaoDesempenhoFiltro {

	private String nome;
	
	public AvaliacaoDesempenhoFiltro(String nome){
		if(nome != null && !nome.isEmpty()){
			this.nome = nome;
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
