package com.rhlinkcon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Pensão Alimentícia")
@Table(name = "pensao_alimenticia")
public class PensaoAlimenticia extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Bloco Inicial

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	// Bloco do Alimentando

	@NotNull
	@NotBlank
	@Size(max = 255)
	@Column(name = "nome_alimentando")
	private String nomeAlimentando;

	@NotNull
	@Column(name = "nascimento_alimentando")
	private Date nascimentoAlimentando;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pensao")
	private TipoPensaoEnum tipoPensao;

	@NotNull
	@NotBlank
	@Size(max = 255)
	@Column(name = "rg")
	private String rg;

	@NotNull
	@NotBlank
	@Size(max = 255)
	@Column(name = "orgao")
	private String orgao;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_documento_id")
	private UnidadeFederativa estadoDocumento;

	@NotNull
	@NotBlank
	@Size(min = 11, max = 11)
	@Column(name = "cpf")
	private String cpf;

	@Size(max = 255)
	@Column(name = "numero_telefone")
	private String numeroTelefone;

	@NotNull
	@Embedded
	private Endereco enderecoAlimentando;

	// Bloco do Responsável Legal

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsavel_id")
	private ResponsavelLegal responsavel;

	@Column(name = "numero_processo_responsavel")
	@Size(max = 255)
	private String numeroProcessoResponsavel;

	@Column(name = "data_inicial")
	private Date dataInicial;

	@Column(name = "data_final")
	private Date dataFinal;

	// Bloco de Pagamento Parte 1

	@NotNull
	@Column(name = "data_inicial_pagamento")
	private Date dataInicialPagamento;

	@Column(name = "data_final_pagamento")
	private Date dataFinalPagamento;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_custo_id")
	private CentroCusto centroCusto;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verba_id")
	private Verba verba;

	@NotNull
	@Embedded
	private DadoBancario dadoBancario;

	// Bloco de Pagamento Parte 2

	@NotNull
	@Size(max = 255)
	@Column(name = "numero_processo_pagamento")
	private String numeroProcessoPagamento;

	@NotNull
	@Column(name = "vencimento")
	private Date vencimento;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_desconto")
	private TipoValorEnum tipoValor;

	@NotNull
	@Column(name = "valor")
	private Double valor;

	@Column(name = "salario_familia")
	private Long salarioFamilia;

	@Enumerated(EnumType.STRING)
	@Column(name = "incidencia_principal")
	private TipoIncidenciaPrincipalPensaoEnum tipoIncidenciaPrincipalPensao;

	@Column(name = "salario_13")
	private boolean salario13;

	@Column(name = "ferias")
	private boolean ferias;

	@Column(name = "recisao")
	private boolean recisao;

	public PensaoAlimenticia() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeAlimentando() {
		return nomeAlimentando;
	}

	public void setNomeAlimentando(String nomeAlimentando) {
		this.nomeAlimentando = nomeAlimentando;
	}

	public Date getNascimentoAlimentando() {
		return nascimentoAlimentando;
	}

	public void setNascimentoAlimentando(Date nascimentoAlimentando) {
		this.nascimentoAlimentando = nascimentoAlimentando;
	}

	public TipoPensaoEnum getTipoPensao() {
		return tipoPensao;
	}

	public void setTipoPensao(TipoPensaoEnum tipoPensao) {
		this.tipoPensao = tipoPensao;
	}

	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	public UnidadeFederativa getEstadoDocumento() {
		return estadoDocumento;
	}

	public void setEstadoDocumento(UnidadeFederativa estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}

	public ResponsavelLegal getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(ResponsavelLegal responsavel) {
		this.responsavel = responsavel;
	}

	public String getNumeroProcessoResponsavel() {
		return numeroProcessoResponsavel;
	}

	public String getNumeroProcessoPagamento() {
		return numeroProcessoPagamento;
	}

	public void setNumeroProcessoResponsavel(String numeroProcessoResponsavel) {
		this.numeroProcessoResponsavel = numeroProcessoResponsavel;
	}

	public void setNumeroProcessoPagamento(String numeroProcessoPagamento) {
		this.numeroProcessoPagamento = numeroProcessoPagamento;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public Verba getVerba() {
		return verba;
	}

	public void setVerba(Verba verba) {
		this.verba = verba;
	}

	public Date getDataInicialPagamento() {
		return dataInicialPagamento;
	}

	public void setDataInicialPagamento(Date dataInicialPagamento) {
		this.dataInicialPagamento = dataInicialPagamento;
	}

	public Date getDataFinalPagamento() {
		return dataFinalPagamento;
	}

	public void setDataFinalPagamento(Date dataFinalPagamento) {
		this.dataFinalPagamento = dataFinalPagamento;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Long getSalarioFamilia() {
		return salarioFamilia;
	}

	public void setSalarioFamilia(Long salarioFamilia) {
		this.salarioFamilia = salarioFamilia;
	}

	public TipoIncidenciaPrincipalPensaoEnum getTipoIncidenciaPrincipalPensao() {
		return tipoIncidenciaPrincipalPensao;
	}

	public void setTipoIncidenciaPrincipalPensao(TipoIncidenciaPrincipalPensaoEnum tipoIncidenciaPrincipalPensao) {
		this.tipoIncidenciaPrincipalPensao = tipoIncidenciaPrincipalPensao;
	}

	public boolean isSalario13() {
		return salario13;
	}

	public void setSalario13(boolean salario13) {
		this.salario13 = salario13;
	}

	public boolean isFerias() {
		return ferias;
	}

	public void setFerias(boolean ferias) {
		this.ferias = ferias;
	}

	public boolean isRecisao() {
		return recisao;
	}

	public void setRecisao(boolean recisao) {
		this.recisao = recisao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public Endereco getEnderecoAlimentando() {
		return enderecoAlimentando;
	}

	public void setEnderecoAlimentando(Endereco enderecoAlimentando) {
		this.enderecoAlimentando = enderecoAlimentando;
	}

	public DadoBancario getDadoBancario() {
		return dadoBancario;
	}

	public void setDadoBancario(DadoBancario dadoBancario) {
		this.dadoBancario = dadoBancario;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public String getLabel() {
		return this.nomeAlimentando;
	}

	public TipoValorEnum getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(TipoValorEnum tipoValor) {
		this.tipoValor = tipoValor;
	}

}
