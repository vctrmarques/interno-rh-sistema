package com.rhlinkcon.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Processo")
@Table(name = "processo")
public class Processo extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", nullable = false)
	private Funcionario funcionario;

	@NotNull
	@NotBlank
	@Column(name = "numero_processo")
	private String numeroProcesso;

	@NotNull
	@NotBlank
	private String assunto;

	@NotNull
	@Column(name = "data_inicio")
	private Instant dataInicio;

	@Column(name = "data_fim")
	private Instant dataFim;

	private String requerente;

	@Column(name = "ato_portaria")
	private Long atoPortaria;

	@Column(name = "data_ato")
	private Instant dataAto;

	private String doe;

	@Column(name = "data_doe")
	private Instant dataDoe;

	@Column(name = "impacto_financeiro")
	private Boolean impactoFinanceiro;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_reflexo")
	private TipoReflexoEnum tipoReflexo;

	@Column(name = "inicio_impacto_financeiro")
	private Instant inicioImpactoFinanco;

	@Column(name = "fim_impacto_financeiro")
	private Instant fimImpactoFinanco;

	@Enumerated(EnumType.STRING)
	private SituacaoProcessoEnum situacao;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "anexo_processo", joinColumns = @JoinColumn(name = "processo_id"), inverseJoinColumns = @JoinColumn(name = "anexo_id"))
	private List<Anexo> anexos = new ArrayList<>();

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public Processo() {
		super();
	}

	public Processo(Long id, Funcionario funcionario, @NotNull @NotBlank String numeroProcesso,
			@NotNull @NotBlank String assunto, @NotNull @NotBlank Instant dataInicio, Instant dataFim,
			String requerente, Long atoPortaria, Instant dataAto, String doe, Instant dataDoe,
			@NotNull @NotBlank Boolean impactoFinanceiro, String tipoReflexo, Instant inicioImpactoFinanco,
			Instant fimImpactoFinanco, List<Anexo> anexos, String situacao) {
		super();
		this.id = id;
		this.funcionario = funcionario;
		this.numeroProcesso = numeroProcesso;
		this.assunto = assunto;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.requerente = requerente;
		this.atoPortaria = atoPortaria;
		this.dataAto = dataAto;
		this.doe = doe;
		this.dataDoe = dataDoe;
		this.impactoFinanceiro = impactoFinanceiro;
		this.tipoReflexo = TipoReflexoEnum.getEnumByString(tipoReflexo);
		this.inicioImpactoFinanco = inicioImpactoFinanco;
		this.fimImpactoFinanco = fimImpactoFinanco;
		this.anexos = anexos;
		this.situacao = SituacaoProcessoEnum.getEnumByString(situacao);
	}

	public SituacaoProcessoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProcessoEnum situacao) {
		this.situacao = situacao;
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

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataFim() {
		return dataFim;
	}

	public void setDataFim(Instant dataFim) {
		this.dataFim = dataFim;
	}

	public String getRequerente() {
		return requerente;
	}

	public void setRequerente(String requerente) {
		this.requerente = requerente;
	}

	public Long getAtoPortaria() {
		return atoPortaria;
	}

	public void setAtoPortaria(Long atoPortaria) {
		this.atoPortaria = atoPortaria;
	}

	public Instant getDataAto() {
		return dataAto;
	}

	public void setDataAto(Instant dataAto) {
		this.dataAto = dataAto;
	}

	public String getDoe() {
		return doe;
	}

	public void setDoe(String doe) {
		this.doe = doe;
	}

	public Instant getDataDoe() {
		return dataDoe;
	}

	public void setDataDoe(Instant dataDoe) {
		this.dataDoe = dataDoe;
	}

	public Boolean getImpactoFinanceiro() {
		return impactoFinanceiro;
	}

	public void setImpactoFinanceiro(Boolean impactoFinanceiro) {
		this.impactoFinanceiro = impactoFinanceiro;
	}

	public TipoReflexoEnum getTipoReflexo() {
		return tipoReflexo;
	}

	public void setTipoReflexo(TipoReflexoEnum tipoReflexo) {
		this.tipoReflexo = tipoReflexo;
	}

	public Instant getInicioImpactoFinanco() {
		return inicioImpactoFinanco;
	}

	public void setInicioImpactoFinanco(Instant inicioImpactoFinanco) {
		this.inicioImpactoFinanco = inicioImpactoFinanco;
	}

	public Instant getFimImpactoFinanco() {
		return fimImpactoFinanco;
	}

	public void setFimImpactoFinanco(Instant fimImpactoFinanco) {
		this.fimImpactoFinanco = fimImpactoFinanco;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}
