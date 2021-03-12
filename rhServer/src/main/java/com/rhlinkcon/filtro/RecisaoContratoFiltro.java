package com.rhlinkcon.filtro;

import com.rhlinkcon.model.RecisaoContratoEnum;

public class RecisaoContratoFiltro {
	private String nome;
	private String matricula;
	private RecisaoContratoEnum recisaoContratoEnum;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public RecisaoContratoEnum getRecisaoContratoEnum() {
		return recisaoContratoEnum;
	}
	
	public void setRecisaoContratoEnum(RecisaoContratoEnum recisaoContratoEnum) {
		this.recisaoContratoEnum = recisaoContratoEnum;
	}
}
