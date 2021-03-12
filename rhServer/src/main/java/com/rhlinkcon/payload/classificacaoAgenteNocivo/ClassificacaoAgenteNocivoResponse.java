package com.rhlinkcon.payload.classificacaoAgenteNocivo;

import java.time.Instant;

import com.rhlinkcon.model.ClassificacaoAgenteNocivo;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ClassificacaoAgenteNocivoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	private Integer tempoExposicao;

	public ClassificacaoAgenteNocivoResponse(ClassificacaoAgenteNocivo agenteNocivo) {
		this.setId(agenteNocivo.getId());
		this.setCodigo(agenteNocivo.getCodigo());
		this.setDescricao(agenteNocivo.getDescricao());
		this.setTempoExposicao(agenteNocivo.getTempoExposicao());
	}

	public ClassificacaoAgenteNocivoResponse(ClassificacaoAgenteNocivo agenteNocivo, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		this.setId(agenteNocivo.getId());
		this.setCodigo(agenteNocivo.getCodigo());
		this.setDescricao(agenteNocivo.getDescricao());
		this.setTempoExposicao(agenteNocivo.getTempoExposicao());
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

	public Integer getTempoExposicao() {
		return tempoExposicao;
	}

	public void setTempoExposicao(Integer tempoExposicao) {
		this.tempoExposicao = tempoExposicao;
	}

}
