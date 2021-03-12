package com.rhlinkcon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@AuditLabelClass(label = "Nível Salarial Histórico")
@Table(name = "nivel_salarial_historico")
public class NivelSalarialHistorico extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8770252859250324943L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "data_ajuste")
	private Date dataAjuste;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "origem_ajuste")
	private NivelSalarialHistoricoOrigemAjusteEnum origemAjuste;

	@NotNull
	@Column(name = "valor_original")
	private Double valorOriginal;

	@NotNull
	@Column(name = "valor_ajustado")
	private Double valorAjustado;

	@Column(name = "valor_retroativo")
	private Double valorRetroativo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "simulador_nivel_salarial_id")
	private SimuladorNivelSalarial simulacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nivel_salarial_id")
	private ReferenciaSalarial nivelSalarial;

	public NivelSalarialHistorico() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReferenciaSalarial getNivelSalarial() {
		return nivelSalarial;
	}

	public void setNivelSalarial(ReferenciaSalarial nivelSalarial) {
		this.nivelSalarial = nivelSalarial;
	}

	public Date getDataAjuste() {
		return dataAjuste;
	}

	public void setDataAjuste(Date dataAjuste) {
		this.dataAjuste = dataAjuste;
	}

	public NivelSalarialHistoricoOrigemAjusteEnum getOrigemAjuste() {
		return origemAjuste;
	}

	public void setOrigemAjuste(NivelSalarialHistoricoOrigemAjusteEnum origemAjuste) {
		this.origemAjuste = origemAjuste;
	}

	public Double getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(Double valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	public Double getValorAjustado() {
		return valorAjustado;
	}

	public void setValorAjustado(Double valorAjustado) {
		this.valorAjustado = valorAjustado;
	}

	public Double getValorRetroativo() {
		return valorRetroativo;
	}

	public void setValorRetroativo(Double valorRetroativo) {
		this.valorRetroativo = valorRetroativo;
	}

	public SimuladorNivelSalarial getSimulacao() {
		return simulacao;
	}

	public void setSimulacao(SimuladorNivelSalarial simulacao) {
		this.simulacao = simulacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
