package com.rhlinkcon.payload.evento;

import java.time.Instant;

import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.Evento;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class EventoResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;

	private String situacao;

	public EventoResponse(Evento evento) {
		this.setId(evento.getId());
		this.setDescricao(evento.getDescricao());

		if (!evento.getSituacao().getLabel().isEmpty())
			this.setSituacao(evento.getSituacao().getLabel());
	}

	public EventoResponse(Evento evento, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		this.setId(evento.getId());
		this.setDescricao(evento.getDescricao());

		if (!evento.getSituacao().getLabel().isEmpty())
			this.setSituacao(evento.getSituacao().getLabel());

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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
