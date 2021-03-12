package com.rhlinkcon.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.dependenteBeneficio.DependenteBeneficioRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Dependente Benefício")
@Table(name = "dependente_beneficio")
public class DependenteBeneficio extends UserDateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consignado_id")
	private Consignado consignado;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dependente_id")
	private Dependente dependente;

	@NotNull

	@JoinColumn(name = "valor")
	private BigDecimal valor;

	public DependenteBeneficio() {

	}

	public DependenteBeneficio(DependenteBeneficioRequest dependenteBeneficioRequest) {
		this.setValor(dependenteBeneficioRequest.getValor());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Consignado getConsignado() {
		return consignado;
	}

	public void setConsignado(Consignado consignado) {
		this.consignado = consignado;
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
