package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Compensação")
@Table(name = "certidao_compensacao")
public class CertidaoCompensacao extends UserDateAudit {

	private static final long serialVersionUID = -1145104501685590480L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long numero;

	@NotNull
	private Integer ano;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;

	@NotNull
	@ElementCollection
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "certidao_compensacao_classificacao", joinColumns = @JoinColumn(name = "certidao_compensacao_id"))
	@Column(name = "classificacao", nullable = false)
	private List<ClassificacaoCertidaoCompensacaoEnum> classificacoes;

	@Enumerated(EnumType.STRING)
	@Column(name = "status_atual")
	private StatusCertidaoCompensacaoEnum statusAtual;

	@Enumerated(EnumType.STRING)
	private FundoCertidaoCompensacaoEnum fundo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lotacao_id", nullable = false)
	private Lotacao lotacao;

	private String processo;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "certidao_compensacao_anexo", joinColumns = @JoinColumn(name = "certidao_compensacao_id"), inverseJoinColumns = @JoinColumn(name = "anexo_id"))
	private List<Anexo> anexos;

	@OneToMany(mappedBy = "certidaoCompensacao")
	private Set<CertidaoCompensacaoPeriodo> periodos = new HashSet<>();

	@OneToMany(mappedBy = "certidaoCompensacao")
	private Set<CertidaoCompensacaoAssinatura> assinaturas = new HashSet<>();

	@Column(name = "numero_retificacao")
	private Long numeroRetificacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_compensacao_id")
	private CertidaoCompensacao certidaoCompensacao;

	private Boolean arquivada;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_certidao_compensacao")
	private CertidaoCompensacaoTipoEnum tipoCertidaoCompensacao;

	public CertidaoCompensacao() {
		// vazio
	}

	public CertidaoCompensacao(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getAno() {
		return ano;
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public CertidaoCompensacao getCertidaoCompensacao() {
		return certidaoCompensacao;
	}

	public void setCertidaoCompensacao(CertidaoCompensacao certidaoCompensacao) {
		this.certidaoCompensacao = certidaoCompensacao;
	}

	public Boolean getArquivada() {
		return arquivada;
	}

	public void setArquivada(Boolean arquivada) {
		this.arquivada = arquivada;
	}

	public CertidaoCompensacaoTipoEnum getTipoCertidaoCompensacao() {
		return tipoCertidaoCompensacao;
	}

	public void setTipoCertidaoCompensacao(CertidaoCompensacaoTipoEnum tipoCertidaoCompensacao) {
		this.tipoCertidaoCompensacao = tipoCertidaoCompensacao;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public StatusCertidaoCompensacaoEnum getStatusAtual() {
		return statusAtual;
	}

	public void setStatusAtual(StatusCertidaoCompensacaoEnum statusAtual) {
		this.statusAtual = statusAtual;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public Set<CertidaoCompensacaoPeriodo> getPeriodos() {
		return periodos;
	}

	public void setAverbacoes(Set<CertidaoCompensacaoPeriodo> periodos) {
		this.periodos = periodos;
	}

	public Set<CertidaoCompensacaoAssinatura> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(Set<CertidaoCompensacaoAssinatura> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public FundoCertidaoCompensacaoEnum getFundo() {
		return fundo;
	}

	public void setFundo(FundoCertidaoCompensacaoEnum fundo) {
		this.fundo = fundo;
	}

	public void setPeriodos(Set<CertidaoCompensacaoPeriodo> periodos) {
		this.periodos = periodos;
	}

	public List<ClassificacaoCertidaoCompensacaoEnum> getClassificacoes() {
		return classificacoes;
	}

	public void setClassificacoes(List<ClassificacaoCertidaoCompensacaoEnum> classificacoes) {
		this.classificacoes = classificacoes;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
