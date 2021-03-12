package com.rhlinkcon.payload.causaAfastamento;

import java.time.Instant;

import com.rhlinkcon.model.CausaAfastamento;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CausaAfastamentoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;


	public CausaAfastamentoResponse(CausaAfastamento causaAfastamento) {
		this.setId(causaAfastamento.getId());
		this.setCodigo(causaAfastamento.getCodigo());
		this.setDescricao(causaAfastamento.getDescricao());
	}

	public CausaAfastamentoResponse(CausaAfastamento causaAfastamento, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(causaAfastamento.getId());
		this.setCodigo(causaAfastamento.getCodigo());
		this.setDescricao(causaAfastamento.getDescricao());
		
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
