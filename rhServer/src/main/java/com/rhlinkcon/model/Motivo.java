package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.motivo.MotivoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Motivo")
@Table(name = "motivo")
public class Motivo extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoMotivoEnum tipo;

	@Enumerated(EnumType.STRING)
	@Column(name = "evento")
	private EventoMotivoEnum evento;

	public Motivo() {
	}

	public Motivo(Long id) {
		this.id = id;
	}

	public Motivo(MotivoRequest motivoRequest) {
		this.setDescricao(motivoRequest.getDescricao());

		if (!motivoRequest.getTipo().isEmpty())
			this.setTipo(TipoMotivoEnum.getEnumByString(motivoRequest.getTipo()));

		if (!motivoRequest.getEvento().isEmpty())
			this.setEvento(EventoMotivoEnum.getEnumByString(motivoRequest.getEvento()));
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

	public TipoMotivoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoMotivoEnum tipo) {
		this.tipo = tipo;
	}

	public EventoMotivoEnum getEvento() {
		return evento;
	}

	public void setEvento(EventoMotivoEnum evento) {
		this.evento = evento;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}