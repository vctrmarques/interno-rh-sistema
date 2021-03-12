package com.rhlinkcon.model;

import javax.persistence.Column;
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
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Simulador de Nível Salarial Valores")
@Table(name = "simulador_nivel_salarial_valores")
public class SimuladorNivelSalarialValores extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8770252859250324943L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "valor_retroativo")
	private Double valorRetroativo;

	@NotNull
	@Column(name = "valor_ajustado")
	private Double valorAjustado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nivel_salarial_id")
	private ReferenciaSalarial nivelSalarial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "simulador_id")
	private SimuladorNivelSalarial simulador;

	public SimuladorNivelSalarialValores() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorRetroativo() {
		return valorRetroativo;
	}

	public void setValorRetroativo(Double valorRetroativo) {
		this.valorRetroativo = valorRetroativo;
	}

	public Double getValorAjustado() {
		return valorAjustado;
	}

	public void setValorAjustado(Double valorAjustado) {
		this.valorAjustado = valorAjustado;
	}

	public ReferenciaSalarial getNivelSalarial() {
		return nivelSalarial;
	}

	public void setNivelSalarial(ReferenciaSalarial nivelSalarial) {
		this.nivelSalarial = nivelSalarial;
	}

	public SimuladorNivelSalarial getSimulador() {
		return simulador;
	}

	public void setSimulador(SimuladorNivelSalarial simulador) {
		this.simulador = simulador;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
