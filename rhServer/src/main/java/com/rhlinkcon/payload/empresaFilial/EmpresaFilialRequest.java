package com.rhlinkcon.payload.empresaFilial;

import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpresaFilialRequest {

	private Long id;

	private String nomeResponsavel;

	private Date vigenciaInicial;

	private Date vigenciaFinal;

	private String emailResponsavel;

	private String sigla;

	@NotNull
	private String nomeFilial;

	private Boolean empresaMatriz;

	@NotNull
	private String tipoFilial;

	@NotNull
	private Long cnaeId;

	@NotNull
	private Long codigoGpsId;

	@NotNull
	private String situacao;

	@NotNull
	private String esferaOrgao;

	private Integer qtdProprietario;

	@NotNull
	private String tipoEstabelecimento;

	@NotNull
	private Double capitalSocialAnual;

	@NotNull
	private Instant dataInicialAtividade;

	@NotNull
	private String referenciaContribuicao;

	@NotNull
	private String atividadePrimaria;

	@NotNull
	private String naturezaEstabelecimento;

	@NotNull
	private Long bancoId;
	
	private Integer codigoConvenio;
	
	private String tipoOperacao;

	private Long agenciaBancariaId;

	@NotNull
	private Integer agencia;

	@NotNull
	private Integer fpas;

	private Integer codigoGrpasAcidenteTrabalho;

	private String cnpj;

	private String cei;

	@NotNull
	private Integer codigoEmpregador;

	@NotNull
	private Integer rNegativa;

	private Double percentualEmpregador;

	private Double sat;

	private Double salarioEducacao;

	private Double senai;

	private Double sesi;

	private Double senac;

	private Double sesc;

	private Double sebrae;

	private Double senar;

	private Double senat;

	private Double set;

	private Double secoop;

	private Double dpc;

	private Double forcaAerea;

	private Integer fap;

	private String logradouro;

	private String numero;

	private String complemento;

	private String cep;

	private Long ufId;

	private String bairro;

	private Long municipioId;

	private String telefone;

	private Long anexoId;

	private String tipoInscricao;

	private Long paisId;

	public Long getId() {
		return id;
	}

	public String getSigla() {
		return sigla;
	}

	public String getNomeFilial() {
		return nomeFilial;
	}

	public Boolean getEmpresaMatriz() {
		return empresaMatriz;
	}

	public String getTipoFilial() {
		return tipoFilial;
	}

	public Long getCnaeId() {
		return cnaeId;
	}

	public Long getCodigoGpsId() {
		return codigoGpsId;
	}

	public String getSituacao() {
		return situacao;
	}

	public String getEsferaOrgao() {
		return esferaOrgao;
	}

	public Integer getQtdProprietario() {
		return qtdProprietario;
	}

	public String getTipoEstabelecimento() {
		return tipoEstabelecimento;
	}

	public Double getCapitalSocialAnual() {
		return capitalSocialAnual;
	}

	public Instant getDataInicialAtividade() {
		return dataInicialAtividade;
	}

	public String getReferenciaContribuicao() {
		return referenciaContribuicao;
	}

	public String getAtividadePrimaria() {
		return atividadePrimaria;
	}

	public String getNaturezaEstabelecimento() {
		return naturezaEstabelecimento;
	}

	public Long getBancoId() {
		return bancoId;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public Integer getFpas() {
		return fpas;
	}

	public Integer getCodigoGrpasAcidenteTrabalho() {
		return codigoGrpasAcidenteTrabalho;
	}

	public String getCnpj() {
		return cnpj;
	}

	public Integer getCodigoEmpregador() {
		return codigoEmpregador;
	}

	public Double getPercentualEmpregador() {
		return percentualEmpregador;
	}

	public Double getSat() {
		return sat;
	}

	public Double getSalarioEducacao() {
		return salarioEducacao;
	}

	public Double getSenai() {
		return senai;
	}

	public Double getSesi() {
		return sesi;
	}

	public Double getSenac() {
		return senac;
	}

	public Double getSesc() {
		return sesc;
	}

	public Double getSebrae() {
		return sebrae;
	}

	public Double getSenar() {
		return senar;
	}

	public Double getSenat() {
		return senat;
	}

	public Double getSet() {
		return set;
	}

	public Double getSecoop() {
		return secoop;
	}

	public Double getDpc() {
		return dpc;
	}

	public Double getForcaAerea() {
		return forcaAerea;
	}

	public Integer getFap() {
		return fap;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getCep() {
		return cep;
	}

	public Long getUfId() {
		return ufId;
	}

	public String getBairro() {
		return bairro;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public String getTelefone() {
		return telefone;
	}

	public Long getAnexoId() {
		return anexoId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public void setEmpresaMatriz(Boolean empresaMatriz) {
		this.empresaMatriz = empresaMatriz;
	}

	public void setTipoFilial(String tipoFilial) {
		this.tipoFilial = tipoFilial;
	}

	public void setCnaeId(Long cnaeId) {
		this.cnaeId = cnaeId;
	}

	public void setCodigoGpsId(Long codigoGpsId) {
		this.codigoGpsId = codigoGpsId;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public void setEsferaOrgao(String esferaOrgao) {
		this.esferaOrgao = esferaOrgao;
	}

	public void setQtdProprietario(Integer qtdProprietario) {
		this.qtdProprietario = qtdProprietario;
	}

	public void setTipoEstabelecimento(String tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
	}

	public void setCapitalSocialAnual(Double capitalSocialAnual) {
		this.capitalSocialAnual = capitalSocialAnual;
	}

	public void setDataInicialAtividade(Instant dataInicialAtividade) {
		this.dataInicialAtividade = dataInicialAtividade;
	}

	public void setReferenciaContribuicao(String referenciaContribuicao) {
		this.referenciaContribuicao = referenciaContribuicao;
	}

	public void setAtividadePrimaria(String atividadePrimaria) {
		this.atividadePrimaria = atividadePrimaria;
	}

	public void setNaturezaEstabelecimento(String naturezaEstabelecimento) {
		this.naturezaEstabelecimento = naturezaEstabelecimento;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public void setFpas(Integer fpas) {
		this.fpas = fpas;
	}

	public void setCodigoGrpasAcidenteTrabalho(Integer codigoGrpasAcidenteTrabalho) {
		this.codigoGrpasAcidenteTrabalho = codigoGrpasAcidenteTrabalho;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setCodigoEmpregador(Integer codigoEmpregador) {
		this.codigoEmpregador = codigoEmpregador;
	}

	public void setPercentualEmpregador(Double percentualEmpregador) {
		this.percentualEmpregador = percentualEmpregador;
	}

	public void setSat(Double sat) {
		this.sat = sat;
	}

	public void setSalarioEducacao(Double salarioEducacao) {
		this.salarioEducacao = salarioEducacao;
	}

	public void setSenai(Double senai) {
		this.senai = senai;
	}

	public void setSesi(Double sesi) {
		this.sesi = sesi;
	}

	public void setSenac(Double senac) {
		this.senac = senac;
	}

	public void setSesc(Double sesc) {
		this.sesc = sesc;
	}

	public void setSebrae(Double sebrae) {
		this.sebrae = sebrae;
	}

	public void setSenar(Double senar) {
		this.senar = senar;
	}

	public void setSenat(Double senat) {
		this.senat = senat;
	}

	public void setSet(Double set) {
		this.set = set;
	}

	public void setSecoop(Double secoop) {
		this.secoop = secoop;
	}

	public void setDpc(Double dpc) {
		this.dpc = dpc;
	}

	public void setForcaAerea(Double forcaAerea) {
		this.forcaAerea = forcaAerea;
	}

	public void setFap(Integer fap) {
		this.fap = fap;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setUfId(Long ufId) {
		this.ufId = ufId;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

	public String getTipoInscricao() {
		return tipoInscricao;
	}

	public void setTipoInscricao(String tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public Integer getrNegativa() {
		return rNegativa;
	}

	public void setrNegativa(Integer rNegativa) {
		this.rNegativa = rNegativa;
	}

	public Long getPaisId() {
		return paisId;
	}

	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}

	public String getCei() {
		return cei;
	}

	public void setCei(String cei) {
		this.cei = cei;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Date getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Date vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public Integer getCodigoConvenio() {
		return codigoConvenio;
	}

	public void setCodigoConvenio(Integer codigoConvenio) {
		this.codigoConvenio = codigoConvenio;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	
	public Long getAgenciaBancariaId() {
		return agenciaBancariaId;
	}

	public void setAgenciaBancariaId(Long agenciaBancariaId) {
		this.agenciaBancariaId = agenciaBancariaId;
	}

}