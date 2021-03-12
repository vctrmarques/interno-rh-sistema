package com.rhlinkcon.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExSeguradoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Certidão Ex Segurado")
@Table(name = "certidao_ex_segurado")
public class CertidaoExSegurado extends UserDateAudit {

	private static final long serialVersionUID = -5144321308925374556L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_funcionario")
	private Funcionario funcionario;

	@Column(name = "numero_certidao")
	private Integer numeroCertidao;

	@Column(name = "ano_certidao")
	private Integer anoCertidao;

	@Column(name = "numero_retificacao")
	private Long numeroRetificacao;

	@Column(name = "status_atual")
	@Enumerated(EnumType.STRING)
	private StatusSituacaoCertidaoExSeguradoEnum statusSituacaoCertidao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_lotacao")
	private Lotacao lotacao;

	@Column(name = "data_exoneracao")
	private Instant dataExoneracao;

	@Column(name = "fonte_informacao")
	private String fonteInformacao;

	@ManyToMany
	@JoinTable(name = "certidao_ex_segurado_anexo", joinColumns = {
			@JoinColumn(name = "certidao_ex_segurado_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "anexo_id", referencedColumnName = "id") })
	private List<Anexo> anexos;

	@OneToMany(mappedBy = "certidaoExSegurado", fetch = FetchType.EAGER)
	private Set<AssinaturaCertidaoExSegurado> assinaturas;
	@OneToMany(mappedBy = "certidaoExSegurado")
	private Set<FrequenciaCertidaoExSegurado> frequencias;
	@OneToMany(mappedBy = "certidaoExSegurado")
	private Set<PeriodoCertidaoExSegurado> periodos;
	@OneToMany(mappedBy = "certidaoExSegurado")
	private Set<RelacaoRemuneracaoCertidaoExSegurado> relacaoRemuneracoes;
	@OneToMany(mappedBy = "certidaoExSegurado")
	private Set<TempoEspecialCertidaoExSegurado> tempoEspecial;
	@OneToMany(mappedBy = "certidaoExServidor")
	private Set<CertidaoExServidorCargo> cargos;
	@OneToMany(mappedBy = "certidaoExServidor")
	private Set<CertidaoExServidorOrgaoLotacao> orgaosLotacao;
	@OneToMany(mappedBy = "certidaoExSegurado")
	private Set<FrequenciaCertidaoExServidorDetalhamento> detalhamentosFrequencia;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "certidao_ex_segurado_id")
	private CertidaoExSegurado certidaoExSegurado;

	public CertidaoExSegurado() {
	}

	public CertidaoExSegurado(CertidaoExSeguradoRequest certidaoExSeguradoRequest) {
		this.setId(certidaoExSeguradoRequest.getId());
		this.setFuncionario(new Funcionario(certidaoExSeguradoRequest.getFuncionarioId()));

		this.setNumeroCertidao(certidaoExSeguradoRequest.getNumeroCertidao());
		this.setNumeroRetificacao(certidaoExSeguradoRequest.getNumeroRetificacao());
		this.setAnoCertidao(certidaoExSeguradoRequest.getAnoCertidao());
		this.setStatusSituacaoCertidao(StatusSituacaoCertidaoExSeguradoEnum
				.getEnumByString(certidaoExSeguradoRequest.getStatusSituacaoCertidao()));

		if (Objects.nonNull(certidaoExSeguradoRequest.getLotacaoId()))
			this.setLotacao(new Lotacao(certidaoExSeguradoRequest.getLotacaoId()));

		this.setDataExoneracao(certidaoExSeguradoRequest.getDataExoneracao());
		this.setFonteInformacao(certidaoExSeguradoRequest.getFonteInformacao());

		if (Objects.nonNull(certidaoExSeguradoRequest.getCertidaoExSeguradoId())) {
			this.setCertidaoExSegurado(new CertidaoExSegurado(certidaoExSeguradoRequest.getCertidaoExSeguradoId()));
		}

	}

	public CertidaoExSegurado(Long certidaoExSeguradoId) {
		this.id = certidaoExSeguradoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getNumeroCertidao() {
		return numeroCertidao;
	}

	public void setNumeroCertidao(Integer numeroCertidao) {
		this.numeroCertidao = numeroCertidao;
	}

	public Integer getAnoCertidao() {
		return anoCertidao;
	}

	public void setAnoCertidao(Integer anoCertidao) {
		this.anoCertidao = anoCertidao;
	}

	public StatusSituacaoCertidaoExSeguradoEnum getStatusSituacaoCertidao() {
		return statusSituacaoCertidao;
	}

	public void setStatusSituacaoCertidao(StatusSituacaoCertidaoExSeguradoEnum statusSituacaoCertidao) {
		this.statusSituacaoCertidao = statusSituacaoCertidao;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public Instant getDataExoneracao() {
		return dataExoneracao;
	}

	public void setDataExoneracao(Instant dataExoneracao) {
		this.dataExoneracao = dataExoneracao;
	}

	public String getFonteInformacao() {
		return fonteInformacao;
	}

	public void setFonteInformacao(String fonteInformacao) {
		this.fonteInformacao = fonteInformacao;
	}

	public Set<CertidaoExServidorCargo> getCargos() {
		return cargos;
	}

	public void setCargos(Set<CertidaoExServidorCargo> cargos) {
		this.cargos = cargos;
	}

	public Set<CertidaoExServidorOrgaoLotacao> getOrgaosLotacao() {
		return orgaosLotacao;
	}

	public void setOrgaosLotacao(Set<CertidaoExServidorOrgaoLotacao> orgaosLotacao) {
		this.orgaosLotacao = orgaosLotacao;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public boolean isRetificacao() {
		return Objects.nonNull(getNumeroRetificacao());
	}

	public Set<AssinaturaCertidaoExSegurado> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(Set<AssinaturaCertidaoExSegurado> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public Set<FrequenciaCertidaoExSegurado> getFrequencias() {
		return frequencias;
	}

	public void setFrequencias(Set<FrequenciaCertidaoExSegurado> frequencias) {
		this.frequencias = frequencias;
	}

	public Set<PeriodoCertidaoExSegurado> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<PeriodoCertidaoExSegurado> periodos) {
		this.periodos = periodos;
	}

	public Set<RelacaoRemuneracaoCertidaoExSegurado> getRelacaoRemuneracoes() {
		return relacaoRemuneracoes;
	}

	public void setRelacaoRemuneracoes(Set<RelacaoRemuneracaoCertidaoExSegurado> relacaoRemuneracoes) {
		this.relacaoRemuneracoes = relacaoRemuneracoes;
	}

	public Set<TempoEspecialCertidaoExSegurado> getTempoEspecial() {
		return tempoEspecial;
	}

	public void setTempoEspecial(Set<TempoEspecialCertidaoExSegurado> tempoEspecial) {
		this.tempoEspecial = tempoEspecial;
	}

	public Set<FrequenciaCertidaoExServidorDetalhamento> getDetalhamentosFrequencia() {
		return detalhamentosFrequencia;
	}

	public void setDetalhamentosFrequencia(Set<FrequenciaCertidaoExServidorDetalhamento> detalhamentosFrequencia) {
		this.detalhamentosFrequencia = detalhamentosFrequencia;
	}

	public CertidaoExSegurado getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSegurado certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}