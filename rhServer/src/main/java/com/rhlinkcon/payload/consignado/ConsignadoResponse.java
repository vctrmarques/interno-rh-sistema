package com.rhlinkcon.payload.consignado;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Consignado;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.banco.BancoResponse;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.generico.EnderecoDto;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.util.Projecao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsignadoResponse extends DadoCadastralResponse {

	private Long id;

	private Integer codigo;

	private String descricao;

	private EnderecoDto endereco;

	private String telefone;

	private CentroCustoResponse centroCusto;

	private String cnpj;

	private String rg;

	private String orgaoExpedidor;

	private Double margemConsignavel;

	private BancoResponse banco;

	private Integer agencia;

	private String contaCorrente;

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

	private UnidadeFederativaResponse unidadeFederativa;

	public ConsignadoResponse() {

	}

	public ConsignadoResponse(Consignado consignado) {
		this.setId(consignado.getId());
		this.setDescricao(consignado.getDescricao());

		if (consignado.getEndereco() != null)
			this.setEndereco(new EnderecoDto(consignado.getEndereco()));

		this.setTelefone(consignado.getTelefone());

		if (consignado.getCentroCusto() != null)
			this.setCentroCusto(new CentroCustoResponse(consignado.getCentroCusto()));

		this.setCnpj(consignado.getCnpj());
		this.setRg(consignado.getRg());
		this.setOrgaoExpedidor(consignado.getOrgaoExpedidor());
		this.setMargemConsignavel(consignado.getMargemConsignavel());

		if (consignado.getBanco() != null)
			this.setBanco(new BancoResponse(consignado.getBanco(), Projecao.BASICA));

		this.setAgencia(consignado.getAgencia());
		this.setContaCorrente(consignado.getContaCorrente());
		this.setContrato(consignado.getContrato());
		this.setTipoCalculo(consignado.getTipoCalculo());
		this.setEmail(consignado.getEmail());
		this.setTaxaOperacional(consignado.getTaxaOperacional());
		this.setTac(consignado.getTac());
		this.setTaxaCadastroSiglaConsignado(consignado.getTaxaCadastroSiglaConsignado());
		this.setSiglaConsignado(consignado.getSiglaConsignado());
		this.setRegistroAns(consignado.getRegistroAns());
		this.setCodigoConvergencia(consignado.getCodigoConvergencia());
		this.setDigitoConvergencia(consignado.getDigitoConvergencia());
		this.setExtratoConvenente(consignado.getExtratoConvenente());
		this.setNomeConvenente(consignado.getNomeConvenente());
		this.setPvResponsavel(consignado.getPvResponsavel());
		this.setDataCompetenciaProcessamento(consignado.getDataCompetenciaProcessamento());
		this.setDigitoConvenente(consignado.getDigitoConvenente());
		this.setVerbaDesconto(consignado.getVerbaDesconto());

		if (!Objects.isNull(consignado.getModalidade()) && !consignado.getModalidade().getLabel().isEmpty())
			this.setModalidade(consignado.getModalidade().getLabel());

		if (consignado.getUnidadeFederativa() != null)
			this.setUnidadeFederativa(new UnidadeFederativaResponse(consignado.getUnidadeFederativa()));

	}

	public ConsignadoResponse(Consignado consignado, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(consignado.getId());
		this.setDescricao(consignado.getDescricao());

		if (consignado.getEndereco() != null)
			this.setEndereco(new EnderecoDto(consignado.getEndereco()));

		this.setTelefone(consignado.getTelefone());

		if (consignado.getCentroCusto() != null)
			this.setCentroCusto(new CentroCustoResponse(consignado.getCentroCusto()));

		this.setCnpj(consignado.getCnpj());
		this.setRg(consignado.getRg());
		this.setOrgaoExpedidor(consignado.getOrgaoExpedidor());
		this.setMargemConsignavel(consignado.getMargemConsignavel());

		if (consignado.getBanco() != null)
			this.setBanco(new BancoResponse(consignado.getBanco(), Projecao.BASICA));

		this.setAgencia(consignado.getAgencia());
		this.setContaCorrente(consignado.getContaCorrente());
		this.setContrato(consignado.getContrato());
		this.setTipoCalculo(consignado.getTipoCalculo());
		this.setEmail(consignado.getEmail());
		this.setTaxaOperacional(consignado.getTaxaOperacional());
		this.setTac(consignado.getTac());
		this.setTaxaCadastroSiglaConsignado(consignado.getTaxaCadastroSiglaConsignado());
		this.setSiglaConsignado(consignado.getSiglaConsignado());
		this.setRegistroAns(consignado.getRegistroAns());
		this.setCodigoConvergencia(consignado.getCodigoConvergencia());
		this.setDigitoConvergencia(consignado.getDigitoConvergencia());
		this.setExtratoConvenente(consignado.getExtratoConvenente());
		this.setNomeConvenente(consignado.getNomeConvenente());
		this.setPvResponsavel(consignado.getPvResponsavel());
		this.setDataCompetenciaProcessamento(consignado.getDataCompetenciaProcessamento());
		this.setDigitoConvenente(consignado.getDigitoConvenente());
		this.setVerbaDesconto(consignado.getVerbaDesconto());

		if (!Objects.isNull(consignado.getModalidade()) && !consignado.getModalidade().getLabel().isEmpty())
			this.setModalidade(consignado.getModalidade().getLabel());

		if (consignado.getUnidadeFederativa() != null)
			this.setUnidadeFederativa(new UnidadeFederativaResponse(consignado.getUnidadeFederativa()));

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public BancoResponse getBanco() {
		return banco;
	}

	public void setBanco(BancoResponse banco) {
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

	public CentroCustoResponse getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCustoResponse centroCusto) {
		this.centroCusto = centroCusto;
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

	public UnidadeFederativaResponse getUnidadeFederativa() {
		return unidadeFederativa;
	}

	public void setUnidadeFederativa(UnidadeFederativaResponse unidadeFederativa) {
		this.unidadeFederativa = unidadeFederativa;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

}
