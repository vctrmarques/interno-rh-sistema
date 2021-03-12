package com.rhlinkcon.payload.naturezaJuridica;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.NaturezaJuridica;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaturezaJuridicaResponse extends DadoCadastralResponse {

	private Long id;
	
	private String codigo;

	private String nome;

	private String grupamento;
	
	public NaturezaJuridicaResponse(NaturezaJuridica naturezaJuridica) {
		setNaturezaJuridica(naturezaJuridica);
	}
	
	public NaturezaJuridicaResponse(NaturezaJuridica naturezaJuridica, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setNaturezaJuridica(naturezaJuridica);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);		
	}
	
	private void setNaturezaJuridica(NaturezaJuridica naturezaJuridica) {
		this.setId(naturezaJuridica.getId());
		this.setCodigo(naturezaJuridica.getCodigo());
		this.setNome(naturezaJuridica.getNome());
		this.setGrupamento(naturezaJuridica.getGrupamento().getLabel());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGrupamento() {
		return grupamento;
	}

	public void setGrupamento(String grupamento) {
		this.grupamento = grupamento;
	}

}
