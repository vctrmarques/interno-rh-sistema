package com.rhlinkcon.model;

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

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Solicitação de Adiantamento")
@Table(name = "sol_adiantamento")
public class SolAdiantamento extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "solicitante", nullable = false)
	private Funcionario solicitante;

	@Enumerated(EnumType.STRING)
	@Column(name = "porcentagem_adiantamento")
	private PorcentagemAdiantamentoEnum porcentagemAdiantamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "mes_adiantamento")
	private MesAdiantamentoEnum mesAdiantamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private SituacaoSolAdiantamentoEnum situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Funcionario solicitante) {
		this.solicitante = solicitante;
	}

	public PorcentagemAdiantamentoEnum getPorcentagemAdiantamento() {
		return porcentagemAdiantamento;
	}

	public void setPorcentagemAdiantamento(PorcentagemAdiantamentoEnum porcentagemAdiantamento) {
		this.porcentagemAdiantamento = porcentagemAdiantamento;
	}

	public MesAdiantamentoEnum getMesAdiantamento() {
		return mesAdiantamento;
	}

	public void setMesAdiantamento(MesAdiantamentoEnum mesAdiantamento) {
		this.mesAdiantamento = mesAdiantamento;
	}

	public SituacaoSolAdiantamentoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoSolAdiantamentoEnum situacao) {
		this.situacao = situacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
