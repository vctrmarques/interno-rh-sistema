package com.rhlinkcon.payload.cnae;


import com.rhlinkcon.model.Cnae;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CnaeResponse extends DadoCadastralResponse{

	private Long id;

	private String codigoSecao;

	private String nomeSecao;

	private Long codigoClasse;

	private String nomeClasse;

	public CnaeResponse() {
		
	}
	
	public CnaeResponse(Cnae cnae) {
		this.setId(cnae.getId());
		this.setCodigoSecao(cnae.getCodigoSecao());
		this.setNomeSecao(cnae.getNomeSecao());
		this.setCodigoClasse(cnae.getCodigoClasse());
		this.setNomeClasse(cnae.getNomeClasse());
		super.setCriadoEm(cnae.getCreatedAt());
		super.setAlteradoEm(cnae.getUpdatedAt());
	}
	
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
