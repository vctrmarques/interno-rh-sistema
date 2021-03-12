package com.rhlinkcon.payload.motivo;

import java.time.Instant;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Motivo;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotivoResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;
	
	private String tipo;

	private String evento;

	public MotivoResponse(Motivo motivo) {
		setMotivo(motivo);
	}

	public MotivoResponse(Motivo motivo, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setMotivo(motivo);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setMotivo(Motivo motivo) {
		this.setId(motivo.getId());
		this.setDescricao(motivo.getDescricao());
		
		if (Objects.nonNull(motivo.getTipo()))
			this.setTipo(motivo.getTipo().getLabel());

		if (Objects.nonNull(motivo.getEvento()))
			this.setEvento(motivo.getEvento().getLabel());

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

}
