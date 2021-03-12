package com.rhlinkcon.payload.consignado;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.payload.generico.EnderecoDto;

public class ConsignadoRequest {

	private Long id;

	@NotNull
	private String descricao;

	private EnderecoDto endereco;

	private String telefone;

	@NotNull
	private Long centroCustoId;

	@NotNull
	private String cnpj;

	private String rg;

	private String orgaoExpedidor;

	private Double margemConsignavel;

	private Long bancoId;

	private Integer agencia;

	private String contaCorrente;

	@NotNull
	private String contrato;

	private Double tipoCalculo;

	private String email;

	private Double taxaOperacional;

	private Double tac;

	private Integer taxaCadastroSiglaConsignado;

	private String siglaConsignado;

	private Integer registroAns;

	private Integer codigoConvergencia;

	private Integer digitoConvergencia;

	private Integer extratoConvenente;

	private String nomeConvenente;

	private String pvResponsavel;

	private Instant dataCompetenciaProcessamento;

	private Integer digitoConvenente;

	private Integer verbaDesconto;

	private String modalidade;

	private Long unidadeFederativaId;

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

	public EnderecoDto getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDto endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
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

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
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

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public Long getUnidadeFederativaId() {
		return unidadeFederativaId;
	}

	public void setUnidadeFederativaId(Long unidadeFederativaId) {
		this.unidadeFederativaId = unidadeFederativaId;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

}