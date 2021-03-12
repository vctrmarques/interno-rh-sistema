package com.rhlinkcon.model.legislacao;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Legislação")
@Table(name = "legislacao", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "numero_norma", "ente_federado_id", "norma_id" }) })
public class Legislacao extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1781937262865647992L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ente_federado_id")
	private EnteFederado enteFederado;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "norma_id")
	private Norma norma;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detalhamento_norma_id")
	private DetalhamentoNorma detalhamentoNorma;

	@NotNull
	@Column(name = "numero_norma")
	private Integer numeroNorma;

	@NotNull
	@Column(name = "ano_norma")
	private Integer anoNorma;

	@NotNull
	@Column(name = "ementa_norma")
	private String ementaNorma;

	@NotNull
	@Column(name = "inicio_efeito_financeiro")
	private Instant inicioEfeitoFinanceiro;

	@NotNull
	@Column(name = "publicacao")
	private Instant publicacao;

	@NotNull
	@Column(name = "inicio_vigencia")
	private Instant inicioVigencia;

	@Column(name = "fim_vigencia")
	private Instant fimVigencia;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assunto_norma_id")
	private AssuntoNorma assuntoNorma;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidade_gestora_id")
	private UnidadeGestora unidadeGestora;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pessoal_legislacao_id")
	private Legislacao pessoalLegislacao;

	@OneToMany(mappedBy = "legislacao")
	private List<LegislacaoAnexo> anexos;

	public Legislacao() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnteFederado getEnteFederado() {
		return enteFederado;
	}

	public void setEnteFederado(EnteFederado enteFederado) {
		this.enteFederado = enteFederado;
	}

	public Norma getNorma() {
		return norma;
	}

	public void setNorma(Norma norma) {
		this.norma = norma;
	}

	public DetalhamentoNorma getDetalhamentoNorma() {
		return detalhamentoNorma;
	}

	public void setDetalhamentoNorma(DetalhamentoNorma detalhamentoNorma) {
		this.detalhamentoNorma = detalhamentoNorma;
	}

	public Integer getNumeroNorma() {
		return numeroNorma;
	}

	public void setNumeroNorma(Integer numeroNorma) {
		this.numeroNorma = numeroNorma;
	}

	public Integer getAnoNorma() {
		return anoNorma;
	}

	public void setAnoNorma(Integer anoNorma) {
		this.anoNorma = anoNorma;
	}

	public String getEmentaNorma() {
		return ementaNorma;
	}

	public void setEmentaNorma(String ementaNorma) {
		this.ementaNorma = ementaNorma;
	}

	public Instant getInicioEfeitoFinanceiro() {
		return inicioEfeitoFinanceiro;
	}

	public void setInicioEfeitoFinanceiro(Instant inicioEfeitoFinanceiro) {
		this.inicioEfeitoFinanceiro = inicioEfeitoFinanceiro;
	}

	public Instant getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Instant publicacao) {
		this.publicacao = publicacao;
	}

	public Instant getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Instant inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Instant getFimVigencia() {
		return fimVigencia;
	}

	public void setFimVigencia(Instant fimVigencia) {
		this.fimVigencia = fimVigencia;
	}

	public AssuntoNorma getAssuntoNorma() {
		return assuntoNorma;
	}

	public void setAssuntoNorma(AssuntoNorma assuntoNorma) {
		this.assuntoNorma = assuntoNorma;
	}

	public UnidadeGestora getUnidadeGestora() {
		return unidadeGestora;
	}

	public void setUnidadeGestora(UnidadeGestora unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}

	public Legislacao getPessoalLegislacao() {
		return pessoalLegislacao;
	}

	public void setPessoalLegislacao(Legislacao pessoalLegislacao) {
		this.pessoalLegislacao = pessoalLegislacao;
	}

	public List<LegislacaoAnexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<LegislacaoAnexo> anexos) {
		this.anexos = anexos;
	}

	@Override
	public String getLabel() {
		return this.numeroNorma.toString() + " - " + this.anoNorma.toString() + " - "
				+ this.enteFederado.getDescricao();
	}

}
