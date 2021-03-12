package com.rhlinkcon.payload.motivoDesligamento;

import java.time.Instant;

import com.rhlinkcon.model.MotivoDesligamento;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class MotivoDesligamentoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;


	public MotivoDesligamentoResponse(MotivoDesligamento motivoDesligamento) {
		this.setId(motivoDesligamento.getId());
		this.setCodigo(motivoDesligamento.getCodigo());
		this.setDescricao(motivoDesligamento.getDescricao());
	}

	public MotivoDesligamentoResponse(MotivoDesligamento motivoDesligamento, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(motivoDesligamento.getId());
		this.setCodigo(motivoDesligamento.getCodigo());
		this.setDescricao(motivoDesligamento.getDescricao());
		
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
