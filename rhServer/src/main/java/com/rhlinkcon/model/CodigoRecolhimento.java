package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.codigoRecolhimento.CodigoRecolhimentoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Código de Recolhimento")
@Table(name = "codigo_recolhimento")
public class CodigoRecolhimento extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "codigo")
	private Integer codigo;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	public CodigoRecolhimento() {
	}

	public CodigoRecolhimento(CodigoRecolhimentoRequest codigoRecolhimentoRequest) {
		this.setId(codigoRecolhimentoRequest.getId());
		this.setCodigo(codigoRecolhimentoRequest.getCodigo());
		this.setDescricao(codigoRecolhimentoRequest.getDescricao());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}