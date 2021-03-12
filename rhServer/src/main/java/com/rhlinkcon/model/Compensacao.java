package com.rhlinkcon.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.compensacao.CompensacaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Compensação")
@Table(name = "compensacao")
public class Compensacao extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tomador_servico_id")
	private TomadorServico tomadorServico;

	@NotNull
	@Column(name = "competencia")
	private Instant competencia;

	@NotNull
	@Column(name = "valor_compensacao")
	private Double valorCompensacao;

	@Column(name = "data_compensacao_inicial")
	private Instant dataCompensacaoInicial;

	@Column(name = "data_compensacao_final")
	private Instant dataCompensacaoFinal;

	@Column(name = "campo_seis_gps_anterior")
	private Integer campoSeisGpsAnterior;

	@Column(name = "campo_nove_gps_anterior")
	private Integer campoNoveGpsAnterior;

	public Compensacao() {
	}

	public Compensacao(CompensacaoRequest compensacaoRequest) {
		this.setCompetencia(compensacaoRequest.getCompetencia());
		this.setValorCompensacao(compensacaoRequest.getValorCompensacao());
		this.setDataCompensacaoInicial(compensacaoRequest.getDataCompensacaoInicial());
		this.setDataCompensacaoFinal(compensacaoRequest.getDataCompensacaoFinal());
		this.setCampoSeisGpsAnterior(compensacaoRequest.getCampoSeisGpsAnterior());
		this.setCampoNoveGpsAnterior(compensacaoRequest.getCampoNoveGpsAnterior());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TomadorServico getTomadorServico() {
		return tomadorServico;
	}

	public void setTomadorServico(TomadorServico tomadorServico) {
		this.tomadorServico = tomadorServico;
	}

	public Instant getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Instant competencia) {
		this.competencia = competencia;
	}

	public Double getValorCompensacao() {
		return valorCompensacao;
	}

	public void setValorCompensacao(Double valorCompensacao) {
		this.valorCompensacao = valorCompensacao;
	}

	public Instant getDataCompensacaoInicial() {
		return dataCompensacaoInicial;
	}

	public void setDataCompensacaoInicial(Instant dataCompensacaoInicial) {
		this.dataCompensacaoInicial = dataCompensacaoInicial;
	}

	public Instant getDataCompensacaoFinal() {
		return dataCompensacaoFinal;
	}

	public void setDataCompensacaoFinal(Instant dataCompensacaoFinal) {
		this.dataCompensacaoFinal = dataCompensacaoFinal;
	}

	public Integer getCampoSeisGpsAnterior() {
		return campoSeisGpsAnterior;
	}

	public void setCampoSeisGpsAnterior(Integer campoSeisGpsAnterior) {
		this.campoSeisGpsAnterior = campoSeisGpsAnterior;
	}

	public Integer getCampoNoveGpsAnterior() {
		return campoNoveGpsAnterior;
	}

	public void setCampoNoveGpsAnterior(Integer campoNoveGpsAnterior) {
		this.campoNoveGpsAnterior = campoNoveGpsAnterior;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}