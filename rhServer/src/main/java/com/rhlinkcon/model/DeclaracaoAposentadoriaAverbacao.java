package com.rhlinkcon.model;

import java.time.LocalDate;

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
import com.rhlinkcon.payload.declaracaoAposentadoriaAverbacao.DeclaracaoAposentadoriaAverbacaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Declaração Aposentadoria Averbação")
@Table(name = "declaracao_aposentadoria_averbacao")
public class DeclaracaoAposentadoriaAverbacao extends UserDateAudit {

	private static final long serialVersionUID = -5975479725636782618L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "declaracao_aposentadoria_id", nullable = false)
	private DeclaracaoAposentadoria declaracaoAposentadoria;

	private String empregador;

	@Column(name = "periodo_inicio")
	private LocalDate periodoInicio;

	@Column(name = "periodo_fim")
	private LocalDate periodoFim;

	@Column(name = "fonte_inf")
	private String fonteInf;

	private String observacao;

	@NotNull
	private Boolean averbado;

	public DeclaracaoAposentadoriaAverbacao() {
	}

	public DeclaracaoAposentadoriaAverbacao(DeclaracaoAposentadoriaAverbacaoRequest request) {
		this.id = request.getId();
		this.declaracaoAposentadoria = new DeclaracaoAposentadoria(request.getDeclaracaoAposentadoriaId());
		this.empregador = request.getEmpregador();
		this.periodoInicio = request.getPeriodoInicio();
		this.periodoFim = request.getPeriodoFim();
		this.fonteInf = request.getFonteInf();
		this.observacao = request.getObservacao();
		this.averbado = request.getAverbado();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeclaracaoAposentadoria getDeclaracaoAposentadoria() {
		return declaracaoAposentadoria;
	}

	public void setDeclaracaoAposentadoria(DeclaracaoAposentadoria declaracaoAposentadoria) {
		this.declaracaoAposentadoria = declaracaoAposentadoria;
	}

	public String getEmpregador() {
		return empregador;
	}

	public void setEmpregador(String empregador) {
		this.empregador = empregador;
	}

	public LocalDate getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(LocalDate periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public LocalDate getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(LocalDate periodoFim) {
		this.periodoFim = periodoFim;
	}

	public String getFonteInf() {
		return fonteInf;
	}

	public void setFonteInf(String fonteInf) {
		this.fonteInf = fonteInf;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Boolean getAverbado() {
		return averbado;
	}

	public void setAverbado(Boolean averbado) {
		this.averbado = averbado;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
