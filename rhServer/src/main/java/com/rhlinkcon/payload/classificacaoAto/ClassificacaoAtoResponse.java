package com.rhlinkcon.payload.classificacaoAto;

import java.time.Instant;

import com.rhlinkcon.model.ClassificacaoAto;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ClassificacaoAtoResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;


	public ClassificacaoAtoResponse(ClassificacaoAto classificacaoAto) {
		this.setId(classificacaoAto.getId());
		this.setDescricao(classificacaoAto.getDescricao());
	}

	public ClassificacaoAtoResponse(ClassificacaoAto classificacaoAto, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(classificacaoAto.getId());
		this.setDescricao(classificacaoAto.getDescricao());
		
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
