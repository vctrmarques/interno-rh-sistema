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
import com.rhlinkcon.payload.sefip.SefipRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "SEFIP")
@Table(name = "sefip")
public class Sefip extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8632107956314662272L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoSefipEnum tipo;

	public Sefip() {
	}

	public Sefip(SefipRequest sefipRequest) {
		this.setCodigo(sefipRequest.getCodigo());
		this.setDescricao(sefipRequest.getDescricao());
		this.setTipo(TipoSefipEnum.getEnumByString(sefipRequest.getTipo()));
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

	public TipoSefipEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoSefipEnum tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}