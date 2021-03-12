package com.rhlinkcon.payload.tipoContrato;

import java.time.Instant;

import com.rhlinkcon.model.TipoContrato;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class TipoContratoResponse extends DadoCadastralResponse{

	private Long id;
	
	private String nome;
	
	public TipoContratoResponse() {}
	
	public TipoContratoResponse (TipoContrato tipoContrato) {
		this.setId(tipoContrato.getId());
		this.setNome(tipoContrato.getNome());
	}
	
	public TipoContratoResponse (TipoContrato tipoContrato, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(tipoContrato.getId());
		this.setNome(tipoContrato.getNome());
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
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

}
