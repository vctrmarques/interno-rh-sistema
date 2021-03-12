package com.rhlinkcon.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Folha de Pagamento")
@Table(name = "folha_pagamento")
public class FolhaPagamento extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -190446777281682696L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_processamento_id")
	private TipoProcessamento tipoProcessamento;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha_competencia_id")
	private FolhaCompetencia folhaCompetencia;

	@NotNull
	@Column(name = "periodo_processamento_inicio")
	private Date periodoProcessamentoInicio;

	@NotNull
	@Column(name = "periodo_processamento_fim")
	private Date periodoProcessamentoFim;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusFolhaEnum status;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private SituacaoProcessamentoEnum situacao;

	@Column(name = "processamentos")
	private Long processamentos;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_filial_id")
	private EmpresaFilial filial;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "folha_pagamento_lotacao", joinColumns = @JoinColumn(name = "folha_pagamento_id"), inverseJoinColumns = @JoinColumn(name = "lotacao_id"))
	private Set<Lotacao> lotacoes = new HashSet<>();

	public FolhaPagamento(Long id) {
		this.id = id;
	}

	public FolhaPagamento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoProcessamento getTipoProcessamento() {
		return tipoProcessamento;
	}

	public void setTipoProcessamento(TipoProcessamento tipoProcessamento) {
		this.tipoProcessamento = tipoProcessamento;
	}

	public Date getPeriodoProcessamentoInicio() {
		return periodoProcessamentoInicio;
	}

	public void setPeriodoProcessamentoInicio(Date periodoProcessamentoInicio) {
		this.periodoProcessamentoInicio = periodoProcessamentoInicio;
	}

	public Date getPeriodoProcessamentoFim() {
		return periodoProcessamentoFim;
	}

	public void setPeriodoProcessamentoFim(Date periodoProcessamentoFim) {
		this.periodoProcessamentoFim = periodoProcessamentoFim;
	}

	public StatusFolhaEnum getStatus() {
		return status;
	}

	public void setStatus(StatusFolhaEnum status) {
		this.status = status;
	}

	public EmpresaFilial getFilial() {
		return filial;
	}

	public void setFilial(EmpresaFilial filial) {
		this.filial = filial;
	}

	public Set<Lotacao> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(Set<Lotacao> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public FolhaCompetencia getFolhaCompetencia() {
		return folhaCompetencia;
	}

	public void setFolhaCompetencia(FolhaCompetencia folhaCompetencia) {
		this.folhaCompetencia = folhaCompetencia;
	}

	public SituacaoProcessamentoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProcessamentoEnum situacao) {
		this.situacao = situacao;
	}

	public Long getProcessamentos() {
		return processamentos;
	}

	public void setProcessamentos(Long processamentos) {
		this.processamentos = processamentos;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
