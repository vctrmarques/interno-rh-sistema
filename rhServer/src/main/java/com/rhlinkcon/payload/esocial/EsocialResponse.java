package com.rhlinkcon.payload.esocial;

import java.time.Instant;

import com.rhlinkcon.model.Esocial;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class EsocialResponse extends DadoCadastralResponse{
	
	private Long id;

	private String descricao;
	
	public EsocialResponse() {
		
	}
	
	public EsocialResponse(Esocial esocial) {
		this.setId(esocial.getId());
		this.setDescricao(esocial.getDescricao());
	}
	

	public EsocialResponse(Esocial esocial, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		this.setId(esocial.getId());
		this.setDescricao(esocial.getDescricao());
		
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


}
