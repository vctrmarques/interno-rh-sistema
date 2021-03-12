package com.rhlinkcon.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.telefone.TelefoneRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Telefone")
@Table(name = "telefone")
public class Telefone extends UserDateAudit {

	private static final long serialVersionUID = -3109241676452180387L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String numero;

	@Enumerated(EnumType.STRING)
	private TipoTelefoneEnum tipo;

	public Telefone() {
	}

	public Telefone(Long id) {
		this.id = id;
	}

	public Telefone(TelefoneRequest request) {
		setId(request.getId());
		setNumero(request.getNumero());
		setTipo(TipoTelefoneEnum.getEnumByString(request.getTipo()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoTelefoneEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefoneEnum tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
