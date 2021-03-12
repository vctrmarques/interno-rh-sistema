package com.rhlinkcon.payload.habilidade;

import java.time.Instant;

import com.rhlinkcon.model.Habilidade;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class HabilidadeResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;


	public HabilidadeResponse(Habilidade habilidade) {
		this.setId(habilidade.getId());
		this.setCodigo(habilidade.getCodigo());
		this.setDescricao(habilidade.getDescricao());
	}

	public HabilidadeResponse(Habilidade habilidade, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(habilidade.getId());
		this.setCodigo(habilidade.getCodigo());
		this.setDescricao(habilidade.getDescricao());
		
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
