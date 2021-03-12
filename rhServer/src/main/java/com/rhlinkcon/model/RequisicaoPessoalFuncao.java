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
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.requisicaoPessoalFuncao.RequisicaoPessoalFuncaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Requisiçao Pessoal Função")
@Table(name = "requisicao_pessoal_funcao")
public class RequisicaoPessoalFuncao extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcao_id", nullable = false)
	private Funcao funcao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_contratacao")
	private TipoContratacaoEnum tipoContratacao;

	@NotNull
	@Column(name = "qtd_vagas")
	private Integer qtdVagas;

	@NotNull
	@Column(name = "custo_por_vaga")
	private Double custoPorVaga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "turno_id", nullable = false)
	private Turno turno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requisicao_pessoal_id")
	private RequisicaoPessoal requisicaoPessoal;

	public RequisicaoPessoalFuncao(RequisicaoPessoalFuncaoRequest request) {
		this.id = request.getId();
		this.funcao = new Funcao(request.getFuncaoId());
		this.tipoContratacao = TipoContratacaoEnum.getEnumByString(request.getTipoContratacao());
		this.qtdVagas = request.getQtdVagas();
		this.custoPorVaga = request.getCustoPorVaga();
		this.turno = new Turno(request.getTurnoId());
		this.requisicaoPessoal = new RequisicaoPessoal(request.getRequisicaoPessoalId());
	}

	public RequisicaoPessoalFuncao() {
	}

	public RequisicaoPessoalFuncao(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public TipoContratacaoEnum getTipoContratacao() {
		return tipoContratacao;
	}

	public void setTipoContratacao(TipoContratacaoEnum tipoContratacao) {
		this.tipoContratacao = tipoContratacao;
	}

	public Integer getQtdVagas() {
		return qtdVagas;
	}

	public void setQtdVagas(Integer qtdVagas) {
		this.qtdVagas = qtdVagas;
	}

	public Double getCustoPorVaga() {
		return custoPorVaga;
	}

	public void setCustoPorVaga(Double custoPorVaga) {
		this.custoPorVaga = custoPorVaga;
	}

	public Turno getTurno() {
		return turno;
	}

	public RequisicaoPessoal getRequisicaoPessoal() {
		return requisicaoPessoal;
	}

	public void setRequisicaoPessoal(RequisicaoPessoal requisicaoPessoal) {
		this.requisicaoPessoal = requisicaoPessoal;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
