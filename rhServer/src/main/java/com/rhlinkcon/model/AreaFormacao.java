package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.areaFormacao.AreaFormacaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Área de Formação")
@Table(name = "area_formacao", uniqueConstraints = { @UniqueConstraint(columnNames = { "area_formacao" }) })
public class AreaFormacao extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "area_formacao")
	private String areaFormacao;

	public AreaFormacao() {

	}

	public AreaFormacao(AreaFormacaoRequest areaFormacaoRequest) {
		this.setAreaFormacao(areaFormacaoRequest.getAreaFormacao());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaFormacao() {
		return areaFormacao;
	}

	public void setAreaFormacao(String areaFormacao) {
		this.areaFormacao = areaFormacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}