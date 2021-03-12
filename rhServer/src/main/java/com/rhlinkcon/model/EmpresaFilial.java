package com.rhlinkcon.model;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.jrimum.utilix.Objects;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Empresa Filial")
@Table(name = "empresa_filial")
public class EmpresaFilial extends UserDateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3551593307022134631L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sigla")
	private String sigla;

	@Column(name = "nome_responsavel")
	private String nomeResponsavel;

	@Column(name = "vigencia_inicial")
	private Date vigenciaInicial;

	@Column(name = "vigencia_final")
	private Date vigenciaFinal;

	@Column(name = "email_responsavel")
	private String emailResponsavel;

	@NotNull
	@Column(name = "nome_filial")
	private String nomeFilial;

	@Column(name = "empresa_matriz")
	private Boolean empresaMatriz;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "tipo_filial")
	private TipoFilialEnum tipoFilial;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "cnae_id")
	private Cnae cnae;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "codigo_pagamento_gps_id")
	private CodigoPagamentoGps codigoGps;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "situacao")
	private SituacaoFilialEnum situacao;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "esfera_orgao")
	private EsferaOrgaoEnum esferaOrgao;

	@Column(name = "qtd_proprietario")
	private Integer qtdProprietario;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "tipo_estabelecimento")
	private TipoEstabelecimentoEnum tipoEstabelecimento;

	@NotNull
	@Column(name = "capital_social_anual")
	private Double capitalSocialAnual;

	@NotNull
	@Column(name = "dt_inicial_atividade")
	private Instant dataInicialAtividade;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "referencia_contribuicao")
	private ReferenciaContribuicaoEnum referenciaContribuicao;

	@NotNull
	@Column(name = "atividade_primaria")
	private String atividadePrimaria;

	@NotNull
	@Column(name = "natureza_estabelecimento")
	private String naturezaEstabelecimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "banco_id")
	private Banco banco;

	@NotNull
	@Column(name = "agencia")
	private Integer agencia;

	@NotNull
	@Column(name = "codigo_convenio")
	private Integer codigoConvenio;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_operacao")
	private TipoOperacaoEnum tipoOperacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agencia_id")
	private Agencia agenciaBancaria;

	@NotNull
	@Column(name = "fpas")
	private Integer fpas;

	@Column(name = "cd_gpras_acidente_trabalho")
	private Integer codigoGrpasAcidenteTrabalho;

	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "cei")
	private String cei;

	@NotNull
	@Column(name = "codigo_empregador")
	private Integer codigoEmpregador;

	@NotNull
	@Column(name = "rais_negativa")
	private Integer rNegativa;

	@Column(name = "percentual_empregador")
	private Double percentualEmpregador;

	@Column(name = "sat")
	private Double sat;

	@Column(name = "salario_educacao")
	private Double salarioEducacao;

	@Column(name = "senai")
	private Double senai;

	@Column(name = "sesi")
	private Double sesi;

	@Column(name = "senac")
	private Double senac;

	@Column(name = "sesc")
	private Double sesc;

	@Column(name = "sebrae")
	private Double sebrae;

	@Column(name = "senar")
	private Double senar;

	@Column(name = "senat")
	private Double senat;

	@Column(name = "set_col")
	private Double set;

	@Column(name = "secoop")
	private Double secoop;

	@Column(name = "dpc")
	private Double dpc;

	@Column(name = "forca_aerea")
	private Double forcaAerea;

	@Column(name = "fap")
	private Integer fap;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "cep")
	private String cep;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id")
	private UnidadeFederativa uf;

	@Column(name = "bairro")
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;

	@Column(name = "telefone")
	private String telefone;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anexo_id")
	private Anexo anexo;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_inscricao")
	private TipoInscricaoEnum tipoInscricao;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "empresa_filial_lotacao", joinColumns = @JoinColumn(name = "empresa_filial_id"), inverseJoinColumns = @JoinColumn(name = "lotacao_id"))
	private Set<Lotacao> lotacoes = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pais_id")
	private Nacionalidade pais;

	public Set<Lotacao> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(Set<Lotacao> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public EmpresaFilial() {
	}

	public EmpresaFilial(Long id) {
		this.id = id;
	}

	public EmpresaFilial(EmpresaFilialRequest empresaFilialRequest) {
		this.setSigla(empresaFilialRequest.getSigla());
		this.setNomeFilial(empresaFilialRequest.getNomeFilial());
		this.setEmpresaMatriz(Utils.setBool(empresaFilialRequest.getEmpresaMatriz()));

		if (Utils.checkStr(empresaFilialRequest.getTipoFilial()))
			this.setTipoFilial(TipoFilialEnum.getEnumByString(empresaFilialRequest.getTipoFilial()));

		if (Utils.checkStr(empresaFilialRequest.getSituacao()))
			this.setSituacao(SituacaoFilialEnum.getEnumByString(empresaFilialRequest.getSituacao()));

		if (Utils.checkStr(empresaFilialRequest.getEsferaOrgao()))
			this.setEsferaOrgao(EsferaOrgaoEnum.getEnumByString(empresaFilialRequest.getEsferaOrgao()));

		this.setQtdProprietario(empresaFilialRequest.getQtdProprietario());

		if (Utils.checkStr(empresaFilialRequest.getTipoEstabelecimento()))
			this.setTipoEstabelecimento(
					TipoEstabelecimentoEnum.getEnumByString(empresaFilialRequest.getTipoEstabelecimento()));

		this.setCapitalSocialAnual(empresaFilialRequest.getCapitalSocialAnual());
		this.setDataInicialAtividade(empresaFilialRequest.getDataInicialAtividade());

		if (Utils.checkStr(empresaFilialRequest.getReferenciaContribuicao()))
			this.setReferenciaContribuicao(
					ReferenciaContribuicaoEnum.getEnumByString(empresaFilialRequest.getReferenciaContribuicao()));

		this.setAtividadePrimaria(empresaFilialRequest.getAtividadePrimaria());
		this.setNaturezaEstabelecimento(empresaFilialRequest.getNaturezaEstabelecimento());
		this.setAgencia(empresaFilialRequest.getAgencia());
		this.setFpas(empresaFilialRequest.getFpas());
		this.setCodigoGrpasAcidenteTrabalho(empresaFilialRequest.getCodigoGrpasAcidenteTrabalho());
		this.setCnpj(empresaFilialRequest.getCnpj());
		this.setCei(empresaFilialRequest.getCei());
		this.setCodigoEmpregador(empresaFilialRequest.getCodigoEmpregador());
		this.setrNegativa(empresaFilialRequest.getrNegativa());
		this.setPercentualEmpregador(empresaFilialRequest.getPercentualEmpregador());
		this.setSat(empresaFilialRequest.getSat());
		this.setSalarioEducacao(empresaFilialRequest.getSalarioEducacao());
		this.setSenai(empresaFilialRequest.getSenai());
		this.setSesi(empresaFilialRequest.getSesi());
		this.setSenac(empresaFilialRequest.getSenac());
		this.setSesc(empresaFilialRequest.getSesc());
		this.setSebrae(empresaFilialRequest.getSebrae());
		this.setSenar(empresaFilialRequest.getSenar());
		this.setSenat(empresaFilialRequest.getSenat());
		this.setSet(empresaFilialRequest.getSet());
		this.setSecoop(empresaFilialRequest.getSecoop());
		this.setDpc(empresaFilialRequest.getDpc());
		this.setForcaAerea(empresaFilialRequest.getForcaAerea());
		this.setFap(empresaFilialRequest.getFap());
		this.setLogradouro(empresaFilialRequest.getLogradouro());
		this.setNumero(empresaFilialRequest.getNumero());
		this.setComplemento(empresaFilialRequest.getComplemento());
		this.setCep(empresaFilialRequest.getCep());
		this.setBairro(empresaFilialRequest.getBairro());
		this.setTelefone(empresaFilialRequest.getTelefone());
		this.setNomeResponsavel(empresaFilialRequest.getNomeResponsavel());
		this.setEmailResponsavel(empresaFilialRequest.getEmailResponsavel());
		this.setVigenciaFinal(empresaFilialRequest.getVigenciaFinal());
		this.setVigenciaInicial(empresaFilialRequest.getVigenciaInicial());

		this.setCodigoConvenio(empresaFilialRequest.getCodigoConvenio());

		if (Objects.isNotNull(empresaFilialRequest.getTipoOperacao()))
			this.setTipoOperacao(TipoOperacaoEnum.getEnumByString(empresaFilialRequest.getTipoOperacao()));
	}

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

	public TipoFilialEnum getTipoFilial() {
		return tipoFilial;
	}

	public Cnae getCnae() {
		return cnae;
	}

	public CodigoPagamentoGps getCodigoGps() {
		return codigoGps;
	}

	public SituacaoFilialEnum getSituacao() {
		return situacao;
	}

	public EsferaOrgaoEnum getEsferaOrgao() {
		return esferaOrgao;
	}

	public Integer getQtdProprietario() {
		return qtdProprietario;
	}

	public TipoEstabelecimentoEnum getTipoEstabelecimento() {
		return tipoEstabelecimento;
	}

	public Double getCapitalSocialAnual() {
		return capitalSocialAnual;
	}

	public Instant getDataInicialAtividade() {
		return dataInicialAtividade;
	}

	public ReferenciaContribuicaoEnum getReferenciaContribuicao() {
		return referenciaContribuicao;
	}

	public String getAtividadePrimaria() {
		return atividadePrimaria;
	}

	public String getNaturezaEstabelecimento() {
		return naturezaEstabelecimento;
	}

	public Banco getBanco() {
		return banco;
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

	public Integer getrNegativa() {
		return rNegativa;
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

	public UnidadeFederativa getUf() {
		return uf;
	}

	public String getBairro() {
		return bairro;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public String getTelefone() {
		return telefone;
	}

	public Anexo getAnexo() {
		return anexo;
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

	public void setTipoFilial(TipoFilialEnum tipoFilial) {
		this.tipoFilial = tipoFilial;
	}

	public void setCnae(Cnae cnae) {
		this.cnae = cnae;
	}

	public void setCodigoGps(CodigoPagamentoGps codigoGps) {
		this.codigoGps = codigoGps;
	}

	public void setSituacao(SituacaoFilialEnum situacao) {
		this.situacao = situacao;
	}

	public void setEsferaOrgao(EsferaOrgaoEnum esferaOrgao) {
		this.esferaOrgao = esferaOrgao;
	}

	public void setQtdProprietario(Integer qtdProprietario) {
		this.qtdProprietario = qtdProprietario;
	}

	public void setTipoEstabelecimento(TipoEstabelecimentoEnum tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
	}

	public void setCapitalSocialAnual(Double capitalSocialAnual) {
		this.capitalSocialAnual = capitalSocialAnual;
	}

	public void setDataInicialAtividade(Instant dataInicialAtividade) {
		this.dataInicialAtividade = dataInicialAtividade;
	}

	public void setReferenciaContribuicao(ReferenciaContribuicaoEnum referenciaContribuicao) {
		this.referenciaContribuicao = referenciaContribuicao;
	}

	public void setAtividadePrimaria(String atividadePrimaria) {
		this.atividadePrimaria = atividadePrimaria;
	}

	public void setNaturezaEstabelecimento(String naturezaEstabelecimento) {
		this.naturezaEstabelecimento = naturezaEstabelecimento;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
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

	public void setrNegativa(Integer rNegativa) {
		this.rNegativa = rNegativa;
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

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public TipoInscricaoEnum getTipoInscricao() {
		return tipoInscricao;
	}

	public void setTipoInscricao(TipoInscricaoEnum tipoInscricao) {
		this.tipoInscricao = tipoInscricao;
	}

	public Nacionalidade getPais() {
		return pais;
	}

	public void setPais(Nacionalidade pais) {
		this.pais = pais;
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

	public TipoOperacaoEnum getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacaoEnum tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Agencia getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public void setAgenciaBancaria(Agencia agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}

	@Override
	public String getLabel() {

		return this.nomeFilial;
	}

}