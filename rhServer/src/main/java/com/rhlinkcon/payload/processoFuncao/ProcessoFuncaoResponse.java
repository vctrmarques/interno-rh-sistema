package com.rhlinkcon.payload.processoFuncao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ProcessoFuncao;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessoFuncaoResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;

	public ProcessoFuncaoResponse(ProcessoFuncao processoFuncao) {
		
		this.setId(processoFuncao.getId());
		this.setDescricao(processoFuncao.getDescricao());
		
	}

	public ProcessoFuncaoResponse(ProcessoFuncao processoFuncao, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(processoFuncao.getId());
		this.setDescricao(processoFuncao.getDescricao());
		
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
