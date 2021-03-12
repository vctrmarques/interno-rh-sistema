package com.rhlinkcon.payload.cnae;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CnaeRequest {

	private Long id;

	@NotNull
	private String codigoSecao;

	@NotBlank
	@NotNull
	private String nomeSecao;

	@NotNull
	private Long codigoClasse;

	@NotBlank
	@NotNull
	private String nomeClasse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoSecao() {
		return codigoSecao;
	}

	public void setCodigoSecao(String codigoSecao) {
		this.codigoSecao = codigoSecao;
	}

	public String getNomeSecao() {
		return nomeSecao;
	}

	public void setNomeSecao(String nomeSecao) {
		this.nomeSecao = nomeSecao;
	}

	public Long getCodigoClasse() {
		return codigoClasse;
	}

	public void setCodigoClasse(Long codigoClasse) {
		this.codigoClasse = codigoClasse;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}
	
	

	
	
	

}
