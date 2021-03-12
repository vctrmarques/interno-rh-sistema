package com.rhlinkcon.payload.naturezaFuncao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.NaturezaFuncao;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaturezaFuncaoResponse extends DadoCadastralResponse {

	private Long id;
	
	private String descricao;
	
	public NaturezaFuncaoResponse(NaturezaFuncao naturezaFuncao) {
		setId(naturezaFuncao.getId());
		setDescricao(naturezaFuncao.getDescricao());
	}
	
	public NaturezaFuncaoResponse(NaturezaFuncao naturezaFuncao, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		setId(naturezaFuncao.getId());
		setDescricao(naturezaFuncao.getDescricao());
		
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
