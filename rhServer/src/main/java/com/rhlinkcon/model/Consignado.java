package com.rhlinkcon.model;

import java.time.Instant;
import java.util.Objects;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.consignado.ConsignadoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Consignado")
@Table(name = "consignado")
public class Consignado extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2279486229331793271L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@Embedded
	private Endereco endereco;

	@Column(name = "telefone")
	private String telefone;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "id_centro_custo")
	private CentroCusto centroCusto;

	@Size(min = 14, max = 14)
	@Column(name = "cnpj")
	@NotNull
	private String cnpj;

	@Column(name = "rg")
	private String rg;

	@Column(name = "orgao_expedidor")
	private String orgaoExpedidor;

	@Column(name = "margem_consignavel")
	private Double margemConsignavel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_banco")
	private Banco banco;

	@Column(name = "agencia")
	private Integer agencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agencia_id")
	private Agencia agenciaBancaria;

	@Column(name = "conta_corrente")
	private String contaCorrente;

	@Column(name = "digito_conta")
	private String digitoConta;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipoConta")
	private TipoContaBancariaEnum tipoConta;

	@Column(name = "contrato")
	@NotNull
	private String contrato;

	@Column(name = "tipo_calculo")
	private Double tipoCalculo;

	@Column(name = "email")
	private String email;

	@Column(name = "taxa_operacional")
	private Double taxaOperacional;

	@Column(name = "tac")
	private Double tac;

	@Column(name = "taxa_cadastro_sigla_consignado")
	private Integer taxaCadastroSiglaConsignado;

	@Column(name = "sigla_consignado")
	private String siglaConsignado;

	@Column(name = "registro_ans")
	private Integer registroAns;

	@Column(name = "codigo_convergencia")
	private Integer codigoConvergencia;

	@Column(name = "digito_convergencia")
	private Integer digitoConvergencia;

	@Column(name = "extrato_convenente")
	private Integer extratoConvenente;

	@Column(name = "digito_convenente")
	private Integer digitoConvenente;

	@Column(name = "nome_convenente")
	private String nomeConvenente;

	@Column(name = "pv_responsavel")
	private String pvResponsavel;

	@Column(name = "dt_competencia_processamento")
	private Instant dataCompetenciaProcessamento;

	@Column(name = "verba_desconto")
	private Integer verbaDesconto;

	@Enumerated(EnumType.STRING)
	@Column(name = "modalidade")
	private ModalidadeConsignadoEnum modalidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidade_federativa_consignatario")
	private UnidadeFederativa unidadeFederativa;

	public Consignado() {

	}

	public Consignado(ConsignadoRequest consignadoRequest) {
		this.setId(consignadoRequest.getId());
		this.setDescricao(consignadoRequest.getDescricao());

		if (consignadoRequest.getEndereco() != null)
			this.setEndereco(new Endereco(consignadoRequest.getEndereco()));

		this.setTelefone(consignadoRequest.getTelefone());
		this.setCnpj(consignadoRequest.getCnpj());
		this.setRg(consignadoRequest.getRg());
		this.setOrgaoExpedidor(consignadoRequest.getOrgaoExpedidor());
		this.setMargemConsignavel(consignadoRequest.getMargemConsignavel());
		this.setAgencia(consignadoRequest.getAgencia());
		this.setContaCorrente(consignadoRequest.getContaCorrente());
		this.setContrato(consignadoRequest.getContrato());
		this.setTipoCalculo(consignadoRequest.getTipoCalculo());
		this.setEmail(consignadoRequest.getEmail());
		this.setTaxaOperacional(consignadoRequest.getTaxaOperacional());
		this.setTac(consignadoRequest.getTac());
		this.setTaxaCadastroSiglaConsignado(consignadoRequest.getTaxaCadastroSiglaConsignado());
		this.setSiglaConsignado(consignadoRequest.getSiglaConsignado());
		this.setRegistroAns(consignadoRequest.getRegistroAns());
		this.setCodigoConvergencia(consignadoRequest.getCodigoConvergencia());
		this.setDigitoConvergencia(consignadoRequest.getDigitoConvergencia());
		this.setExtratoConvenente(consignadoRequest.getExtratoConvenente());
		this.setNomeConvenente(consignadoRequest.getNomeConvenente());
		this.setPvResponsavel(consignadoRequest.getPvResponsavel());
		this.setDataCompetenciaProcessamento(consignadoRequest.getDataCompetenciaProcessamento());
		this.setDigitoConvenente(consignadoRequest.getDigitoConvenente());
		this.setVerbaDesconto(consignadoRequest.getVerbaDesconto());

		if (!Objects.isNull(consignadoRequest.getModalidade()) && !consignadoRequest.getModalidade().isEmpty())
			this.setModalidade(ModalidadeConsignadoEnum.getEnumByString(consignadoRequest.getModalidade()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoExpedidor() {
		return orgaoExpedidor;
	}

	public void setOrgaoExpedidor(String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}

	public Double getMargemConsignavel() {
		return margemConsignavel;
	}

	public void setMargemConsignavel(Double margemConsignavel) {
		this.margemConsignavel = margemConsignavel;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public String getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public Double getTipoCalculo() {
		return tipoCalculo;
	}

	public void setTipoCalculo(Double tipoCalculo) {
		this.tipoCalculo = tipoCalculo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getTaxaOperacional() {
		return taxaOperacional;
	}

	public void setTaxaOperacional(Double taxaOperacional) {
		this.taxaOperacional = taxaOperacional;
	}

	public Double getTac() {
		return tac;
	}

	public void setTac(Double tac) {
		this.tac = tac;
	}

	public Integer getTaxaCadastroSiglaConsignado() {
		return taxaCadastroSiglaConsignado;
	}

	public void setTaxaCadastroSiglaConsignado(Integer taxaCadastroSiglaConsignado) {
		this.taxaCadastroSiglaConsignado = taxaCadastroSiglaConsignado;
	}

	public String getSiglaConsignado() {
		return siglaConsignado;
	}

	public void setSiglaConsignado(String siglaConsignado) {
		this.siglaConsignado = siglaConsignado;
	}

	public Integer getRegistroAns() {
		return registroAns;
	}

	public void setRegistroAns(Integer registroAns) {
		this.registroAns = registroAns;
	}

	public Integer getCodigoConvergencia() {
		return codigoConvergencia;
	}

	public void setCodigoConvergencia(Integer codigoConvergencia) {
		this.codigoConvergencia = codigoConvergencia;
	}

	public Integer getDigitoConvergencia() {
		return digitoConvergencia;
	}

	public void setDigitoConvergencia(Integer digitoConvergencia) {
		this.digitoConvergencia = digitoConvergencia;
	}

	public Integer getExtratoConvenente() {
		return extratoConvenente;
	}

	public void setExtratoConvenente(Integer extratoConvenente) {
		this.extratoConvenente = extratoConvenente;
	}

	public String getNomeConvenente() {
		return nomeConvenente;
	}

	public void setNomeConvenente(String nomeConvenente) {
		this.nomeConvenente = nomeConvenente;
	}

	public String getPvResponsavel() {
		return pvResponsavel;
	}

	public void setPvResponsavel(String pvResponsavel) {
		this.pvResponsavel = pvResponsavel;
	}

	public Instant getDataCompetenciaProcessamento() {
		return dataCompetenciaProcessamento;
	}

	public void setDataCompetenciaProcessamento(Instant dataCompetenciaProcessamento) {
		this.dataCompetenciaProcessamento = dataCompetenciaProcessamento;
	}

	public Integer getDigitoConvenente() {
		return digitoConvenente;
	}

	public void setDigitoConvenente(Integer digitoConvenente) {
		this.digitoConvenente = digitoConvenente;
	}

	public Integer getVerbaDesconto() {
		return verbaDesconto;
	}

	public void setVerbaDesconto(Integer verbaDesconto) {
		this.verbaDesconto = verbaDesconto;
	}

	public ModalidadeConsignadoEnum getModalidade() {
		return modalidade;
	}

	public void setModalidade(ModalidadeConsignadoEnum modalidade) {
		this.modalidade = modalidade;
	}

	public UnidadeFederativa getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(UnidadeFederativa unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public Agencia getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public void setAgenciaBancaria(Agencia agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public TipoContaBancariaEnum getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoContaBancariaEnum tipoConta) {
		this.tipoConta = tipoConta;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}