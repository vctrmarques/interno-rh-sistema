package com.rhlinkcon.model;

import java.time.Instant;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.prestadorServico.PrestadorServicoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Prestador de Serviço")
@Table(name = "prestador_servico")
public class PrestadorServico extends UserDateAudit {

	// DADOS PESSOAIS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome_civil")
	private String nomeCivil;

	@Column(name = "nome_social")
	private String nomeSocial;

	@Size(min = 11, max = 11)
	@Column(name = "cpf")
	private String cpf;

	@NotNull
	@Column(name = "dt_nascimento")
	private Instant dataNascimento;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "estado_civil")
	private EstadoCivilEnum estadoCivil;

	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "sexo")
	private SexoPrestadorEnum sexo;

	@Enumerated(EnumType.STRING)
	@Column(name = "cor")
	private CorPrestadorEnum cor;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "nacionalidade_id")
	private Nacionalidade nacionalidade;

	@NotNull
	@Column(name = "nome_mae")
	private String nomeMae;

	@Column(name = "nome_pai")
	private String nomePai;

	@NotNull
	@Column(name = "logradouro")
	private String logradouro;

	@NotNull
	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@Column(name = "bairro")
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id_endereco")
	private UnidadeFederativa ufEndereco;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_prestador_id")
	private Municipio municipio;

	@NotNull
	@Column(name = "cep")
	private String cep;

	@NotNull
	@Column(name = "ddd_celular")
	private Integer dddCelular;

	@NotNull
	@Column(name = "numero_celular")
	private String numeroCelular;

	@Column(name = "ddd_telefone")
	private Integer dddTelefone;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "email")
	private String email;

	// DADOS TRABALHISTAS

	@Enumerated(EnumType.STRING)
	@Column(name = "locacao")
	private LocacaoPrestadorEnum locacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_filial_id")
	private EmpresaFilial empresaFilial;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "categoria_prestador_id")
	private CategoriaProfissional categoriaProfissional;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "cbo_prestador_id")
	private Cbo cbo;

	@Column(name = "dt_inicial")
	private Instant dataInicial;

	@Column(name = "dt_final")
	private Instant dataFinal;

	@Enumerated(EnumType.STRING)
	@Column(name = "unidade_pgto_fixo")
	private UnidadePagamentoEnum unidadePagamento;

	@Enumerated(EnumType.STRING)
	@Column(name = "natureza_atividade")
	private NaturezaAtividadeEnum naturezaAtividade;

	@Column(name = "agente_nocivo")
	private Boolean agenteNocivo;

	@Column(name = "exposicao_passado")
	private Boolean exposicaoPassado;

	@Enumerated(EnumType.STRING)
	@Column(name = "tempo_contribuicao")
	private TempoContribuicaoEnum tempoContribuicao;

// DADOS FISCAIS

	@Column(name = "recolhe_inss")
	private Boolean recolheInss;

	@Enumerated(EnumType.STRING)
	@Column(name = "modo_pagamento")
	private ModoPagamentoEnum modoPagamento;

	@Column(name = "valor_pago")
	private Double valorPago;

	@Column(name = "desconto_inss")
	private Double descontoInss;

	@Column(name = "base_calculo")
	private Double baseCalculo;

	@Column(name = "desconto_ir")
	private Double descontoIr;

	@Column(name = "registro_fgts")
	private String registroFgts;

	@Size(min = 14, max = 14)
	@NotNull
	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "ir_retido")
	private Double irRetido;

	@Column(name = "inss_pago")
	private Double inssPago;

	@Size(min = 14, max = 14)
	@NotNull
	@Column(name = "cnpj_empresa_pagadora")
	private String cnpjEmpresaPagadora;

	// @Digits(integer = 2, fraction = 0)
	@NotNull
	@Column(name = "dia_pagamento")
	private Integer diaPagamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verba_id")
	private Verba verba;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convenio_id")
	private Convenio convenio;

	@Column(name = "numero_empenho")
	private Integer numeroEmpenho;

	@Column(name = "dt_empenho")
	private Instant dataEmpenho;

	@Column(name = "numero_nf")
	private Integer numeroNotaFiscal;

	@Column(name = "dt_emissao_nf")
	private Instant dataEmissaoNf;

// DOCUMENTOS PESSOAIS

	@Column(name = "pis_pasep")
	private String pisPasep;

	@NotNull
	@Column(name = "numero_ctps")
	private Integer numeroCtps;

	@NotNull
	@Column(name = "serie_ctps")
	private Integer serieCtps;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id_ctps")
	private UnidadeFederativa ufCtps;

	@Column(name = "numero_ident_civil_nac")
	private Integer numeroIdentCivilNac;

	@Column(name = "org_emissor_ident_civil_nac")
	private String orgEmissorIdentCivilNac;

	@Column(name = "dt_emissao_ident_civil_nac")
	private Instant dataEmissaoIdentCivilNac;

	@NotNull
	@Column(name = "numero_rg")
	private Integer numeroRg;

	@NotNull
	@Column(name = "orgao_emissor_rg")
	private String orgaoEmissorRg;

	@NotNull
	@Column(name = "dt_emissao_rg")
	private Instant dataEmissaoRg;

	@Column(name = "num_reg_nac_estrangeiro")
	private Integer numeroRegNacEstrangeiro;

	@Column(name = "org_emissor_reg_nac_estrangeiro")
	private String orgaoEmissorRegNacEstrangeiro;

	@Column(name = "dt_emissao_reg_nac_estrangeiro")
	private Instant dataEmissaoRegNacEstrangeiro;

	@Column(name = "registro_profissional")
	private Integer registroProfissional;

	@Column(name = "dt_emissao_reg_profissional")
	private Instant dataEmissaoRegProfissional;

	@Column(name = "dt_validade_reg_profissional")
	private Instant dataValidadeRegProfissional;

	@Column(name = "numero_cnh")
	private Integer numeroCnh;

	@Column(name = "dt_validade_cnh")
	private Instant dataValidadeCnh;

	@Column(name = "dt_primeira_cnh")
	private Instant dataPrimeiraCnh;

	@Column(name = "dt_emissao_cnh")
	private Instant dataEmissaoCnh;

	@Enumerated(EnumType.STRING)
	@Column(name = "categoria_cnh")
	private CategoriaCnhEnum categoriaCnh;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id_cnh")
	private UnidadeFederativa ufCnh;

// SEÇÃO DEPENDENTES	

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_dependente")
	private TipoDependenteEnum tipoDependente;

	@Column(name = "nome_dependente")
	private String nomeDependente;

	@Size(min = 11, max = 11)
	@Column(name = "cpf_dependente")
	private String cpfDependente;

	@Column(name = "imposto_retido_fonte")
	private Boolean impostoRetidoFonte;

	@Column(name = "plano_saude_privado")
	private Boolean planoSaudePrivado;

	@Column(name = "recebe_beneficio")
	private Boolean recebeBeneficio;

	@Column(name = "capacitacao_profissional")
	private Boolean capacitacaoProfissional;

	public PrestadorServico() {
	}

	public PrestadorServico(PrestadorServicoRequest prestadorServicoRequest) {
		this.setNomeCivil(prestadorServicoRequest.getNomeCivil());
		this.setNomeSocial(prestadorServicoRequest.getNomeSocial());
		this.setCpf(prestadorServicoRequest.getCpf());
		this.setDataNascimento(prestadorServicoRequest.getDataNascimento());

		if (Utils.checkStr(prestadorServicoRequest.getEstadoCivil()))
			this.setEstadoCivil(EstadoCivilEnum.getEnumByString(prestadorServicoRequest.getEstadoCivil()));

		if (Utils.checkStr(prestadorServicoRequest.getSexo()))
			this.setSexo(SexoPrestadorEnum.getEnumByString(prestadorServicoRequest.getSexo()));

		if (Utils.checkStr(prestadorServicoRequest.getCor()))
			this.setCor(CorPrestadorEnum.getEnumByString(prestadorServicoRequest.getCor()));

		this.setNomeMae(prestadorServicoRequest.getNomeMae());
		this.setNomePai(prestadorServicoRequest.getNomePai());
		this.setLogradouro(prestadorServicoRequest.getLogradouro());
		this.setNumero(prestadorServicoRequest.getNumero());
		this.setComplemento(prestadorServicoRequest.getComplemento());
		this.setBairro(prestadorServicoRequest.getBairro());
		this.setCep(prestadorServicoRequest.getCep());
		this.setDddCelular(prestadorServicoRequest.getDddCelular());
		this.setNumeroCelular(prestadorServicoRequest.getNumeroCelular());
		this.setDddTelefone(prestadorServicoRequest.getDddTelefone());
		this.setTelefone(prestadorServicoRequest.getTelefone());
		this.setEmail(prestadorServicoRequest.getEmail());

		if (Utils.checkStr(prestadorServicoRequest.getLocacao()))
			this.setLocacao(LocacaoPrestadorEnum.getEnumByString(prestadorServicoRequest.getLocacao()));

		this.setDataInicial(prestadorServicoRequest.getDataInicial());
		this.setDataFinal(prestadorServicoRequest.getDataFinal());

		if (Utils.checkStr(prestadorServicoRequest.getUnidadePagamento()))
			this.setUnidadePagamento(
					UnidadePagamentoEnum.getEnumByString(prestadorServicoRequest.getUnidadePagamento()));

		if (Utils.checkStr(prestadorServicoRequest.getNaturezaAtividade()))
			this.setNaturezaAtividade(
					NaturezaAtividadeEnum.getEnumByString(prestadorServicoRequest.getNaturezaAtividade()));

		this.setAgenteNocivo(prestadorServicoRequest.isAgenteNocivo());
		this.setExposicaoPassado(prestadorServicoRequest.isExposicaoPassado());

		if (Utils.checkStr(prestadorServicoRequest.getTempoContribuicao()))
			this.setTempoContribuicao(
					TempoContribuicaoEnum.getEnumByString(prestadorServicoRequest.getTempoContribuicao()));

		this.setRecolheInss(prestadorServicoRequest.isRecolheInss());

		if (Utils.checkStr(prestadorServicoRequest.getModoPagamento()))
			this.setModoPagamento(ModoPagamentoEnum.getEnumByString(prestadorServicoRequest.getModoPagamento()));

		this.setValorPago(prestadorServicoRequest.getValorPago());
		this.setDescontoInss(prestadorServicoRequest.getDescontoInss());
		this.setBaseCalculo(prestadorServicoRequest.getBaseCalculo());
		this.setDescontoIr(prestadorServicoRequest.getDescontoIr());
		this.setRegistroFgts(prestadorServicoRequest.getRegistroFgts());
		this.setCnpj(prestadorServicoRequest.getCnpj());
		this.setIrRetido(prestadorServicoRequest.getIrRetido());
		this.setInssPago(prestadorServicoRequest.getInssPago());
		this.setCnpjEmpresaPagadora(prestadorServicoRequest.getCnpjEmpresaPagadora());
		this.setDiaPagamento(prestadorServicoRequest.getDiaPagamento());
		this.setNumeroEmpenho(prestadorServicoRequest.getNumeroEmpenho());
		this.setDataEmpenho(prestadorServicoRequest.getDataEmpenho());
		this.setNumeroNotaFiscal(prestadorServicoRequest.getNumeroNotaFiscal());
		this.setDataEmissaoNf(prestadorServicoRequest.getDataEmissaoNf());
		this.setPisPasep(prestadorServicoRequest.getPisPasep());
		this.setNumeroCtps(prestadorServicoRequest.getNumeroCtps());
		this.setSerieCtps(prestadorServicoRequest.getSerieCtps());
		this.setNumeroIdentCivilNac(prestadorServicoRequest.getNumeroIdentCivilNac());
		this.setOrgEmissorIdentCivilNac(prestadorServicoRequest.getOrgEmissorIdentCivilNac());
		this.setDataEmissaoIdentCivilNac(prestadorServicoRequest.getDataEmissaoIdentCivilNac());
		this.setNumeroRg(prestadorServicoRequest.getNumeroRg());
		this.setOrgaoEmissorRg(prestadorServicoRequest.getOrgaoEmissorRg());
		this.setDataEmissaoRg(prestadorServicoRequest.getDataEmissaoRg());
		this.setNumeroRegNacEstrangeiro(prestadorServicoRequest.getNumeroRegNacEstrangeiro());
		this.setOrgaoEmissorRegNacEstrangeiro(prestadorServicoRequest.getOrgaoEmissorRegNacEstrangeiro());
		this.setDataEmissaoRegNacEstrangeiro(prestadorServicoRequest.getDataEmissaoRegNacEstrangeiro());
		this.setRegistroProfissional(prestadorServicoRequest.getRegistroProfissional());
		this.setDataEmissaoRegProfissional(prestadorServicoRequest.getDataEmissaoRegProfissional());
		this.setDataValidadeRegProfissional(prestadorServicoRequest.getDataValidadeRegProfissional());
		this.setNumeroCnh(prestadorServicoRequest.getNumeroCnh());
		this.setDataValidadeCnh(prestadorServicoRequest.getDataValidadeCnh());
		this.setDataPrimeiraCnh(prestadorServicoRequest.getDataPrimeiraCnh());
		this.setDataEmissaoCnh(prestadorServicoRequest.getDataEmissaoCnh());

		if (Utils.checkStr(prestadorServicoRequest.getCategoriaCnh()))
			this.setCategoriaCnh(CategoriaCnhEnum.getEnumByString(prestadorServicoRequest.getCategoriaCnh()));

		if (Utils.checkStr(prestadorServicoRequest.getTipoDependente()))
			this.setTipoDependente(TipoDependenteEnum.getEnumByString(prestadorServicoRequest.getTipoDependente()));

		this.setNomeDependente(prestadorServicoRequest.getNomeDependente());
		this.setCpfDependente(prestadorServicoRequest.getCpfDependente());
		this.setImpostoRetidoFonte(prestadorServicoRequest.isImpostoRetidoFonte());
		this.setPlanoSaudePrivado(prestadorServicoRequest.isPlanoSaudePrivado());
		this.setRecebeBeneficio(prestadorServicoRequest.isRecebeBeneficio());
		this.setCapacitacaoProfissional(prestadorServicoRequest.isCapacitacaoProfissional());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCivil() {
		return nomeCivil;
	}

	public void setNomeCivil(String nomeCivil) {
		this.nomeCivil = nomeCivil;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Instant getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Instant dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public SexoPrestadorEnum getSexo() {
		return sexo;
	}

	public void setSexo(SexoPrestadorEnum sexo) {
		this.sexo = sexo;
	}

	public CorPrestadorEnum getCor() {
		return cor;
	}

	public void setCor(CorPrestadorEnum cor) {
		this.cor = cor;
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public UnidadeFederativa getUfEndereco() {
		return ufEndereco;
	}

	public void setUfEndereco(UnidadeFederativa ufEndereco) {
		this.ufEndereco = ufEndereco;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getDddCelular() {
		return dddCelular;
	}

	public void setDddCelular(Integer dddCelular) {
		this.dddCelular = dddCelular;
	}

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public Integer getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(Integer dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocacaoPrestadorEnum getLocacao() {
		return locacao;
	}

	public void setLocacao(LocacaoPrestadorEnum locacao) {
		this.locacao = locacao;
	}

	public EmpresaFilial getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilial empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	public CategoriaProfissional getCategoriaProfissional() {
		return categoriaProfissional;
	}

	public void setCategoriaProfissional(CategoriaProfissional categoriaProfissional) {
		this.categoriaProfissional = categoriaProfissional;
	}

	public Cbo getCbo() {
		return cbo;
	}

	public void setCbo(Cbo cbo) {
		this.cbo = cbo;
	}

	public Instant getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Instant dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Instant getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Instant dataFinal) {
		this.dataFinal = dataFinal;
	}

	public UnidadePagamentoEnum getUnidadePagamento() {
		return unidadePagamento;
	}

	public void setUnidadePagamento(UnidadePagamentoEnum unidadePagamento) {
		this.unidadePagamento = unidadePagamento;
	}

	public NaturezaAtividadeEnum getNaturezaAtividade() {
		return naturezaAtividade;
	}

	public void setNaturezaAtividade(NaturezaAtividadeEnum naturezaAtividade) {
		this.naturezaAtividade = naturezaAtividade;
	}

	public Boolean isAgenteNocivo() {
		return agenteNocivo;
	}

	public void setAgenteNocivo(Boolean agenteNocivo) {
		this.agenteNocivo = agenteNocivo;
	}

	public Boolean isExposicaoPassado() {
		return exposicaoPassado;
	}

	public void setExposicaoPassado(Boolean exposicaoPassado) {
		this.exposicaoPassado = exposicaoPassado;
	}

	public TempoContribuicaoEnum getTempoContribuicao() {
		return tempoContribuicao;
	}

	public void setTempoContribuicao(TempoContribuicaoEnum tempoContribuicao) {
		this.tempoContribuicao = tempoContribuicao;
	}

	public Boolean isRecolheInss() {
		return recolheInss;
	}

	public void setRecolheInss(Boolean recolheInss) {
		this.recolheInss = recolheInss;
	}

	public ModoPagamentoEnum getModoPagamento() {
		return modoPagamento;
	}

	public void setModoPagamento(ModoPagamentoEnum modoPagamento) {
		this.modoPagamento = modoPagamento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getDescontoInss() {
		return descontoInss;
	}

	public void setDescontoInss(Double descontoInss) {
		this.descontoInss = descontoInss;
	}

	public Double getBaseCalculo() {
		return baseCalculo;
	}

	public void setBaseCalculo(Double baseCalculo) {
		this.baseCalculo = baseCalculo;
	}

	public Double getDescontoIr() {
		return descontoIr;
	}

	public void setDescontoIr(Double descontoIr) {
		this.descontoIr = descontoIr;
	}

	public String getRegistroFgts() {
		return registroFgts;
	}

	public void setRegistroFgts(String registroFgts) {
		this.registroFgts = registroFgts;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Double getIrRetido() {
		return irRetido;
	}

	public void setIrRetido(Double irRetido) {
		this.irRetido = irRetido;
	}

	public Double getInssPago() {
		return inssPago;
	}

	public void setInssPago(Double inssPago) {
		this.inssPago = inssPago;
	}

	public String getCnpjEmpresaPagadora() {
		return cnpjEmpresaPagadora;
	}

	public void setCnpjEmpresaPagadora(String cnpjEmpresaPagadora) {
		this.cnpjEmpresaPagadora = cnpjEmpresaPagadora;
	}

	public Integer getDiaPagamento() {
		return diaPagamento;
	}

	public void setDiaPagamento(Integer diaPagamento) {
		this.diaPagamento = diaPagamento;
	}

	public Verba getVerba() {
		return verba;
	}

	public void setVerba(Verba verba) {
		this.verba = verba;
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public Integer getNumeroEmpenho() {
		return numeroEmpenho;
	}

	public void setNumeroEmpenho(Integer numeroEmpenho) {
		this.numeroEmpenho = numeroEmpenho;
	}

	public Instant getDataEmpenho() {
		return dataEmpenho;
	}

	public void setDataEmpenho(Instant dataEmpenho) {
		this.dataEmpenho = dataEmpenho;
	}

	public Integer getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(Integer numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public Instant getDataEmissaoNf() {
		return dataEmissaoNf;
	}

	public void setDataEmissaoNf(Instant dataEmissaoNf) {
		this.dataEmissaoNf = dataEmissaoNf;
	}

	public String getPisPasep() {
		return pisPasep;
	}

	public void setPisPasep(String pisPasep) {
		this.pisPasep = pisPasep;
	}

	public Integer getNumeroCtps() {
		return numeroCtps;
	}

	public void setNumeroCtps(Integer numeroCtps) {
		this.numeroCtps = numeroCtps;
	}

	public Integer getSerieCtps() {
		return serieCtps;
	}

	public void setSerieCtps(Integer serieCtps) {
		this.serieCtps = serieCtps;
	}

	public UnidadeFederativa getUfCtps() {
		return ufCtps;
	}

	public void setUfCtps(UnidadeFederativa ufCtps) {
		this.ufCtps = ufCtps;
	}

	public Integer getNumeroIdentCivilNac() {
		return numeroIdentCivilNac;
	}

	public void setNumeroIdentCivilNac(Integer numeroIdentCivilNac) {
		this.numeroIdentCivilNac = numeroIdentCivilNac;
	}

	public String getOrgEmissorIdentCivilNac() {
		return orgEmissorIdentCivilNac;
	}

	public void setOrgEmissorIdentCivilNac(String orgEmissorIdentCivilNac) {
		this.orgEmissorIdentCivilNac = orgEmissorIdentCivilNac;
	}

	public Instant getDataEmissaoIdentCivilNac() {
		return dataEmissaoIdentCivilNac;
	}

	public void setDataEmissaoIdentCivilNac(Instant dataEmissaoIdentCivilNac) {
		this.dataEmissaoIdentCivilNac = dataEmissaoIdentCivilNac;
	}

	public Integer getNumeroRg() {
		return numeroRg;
	}

	public void setNumeroRg(Integer numeroRg) {
		this.numeroRg = numeroRg;
	}

	public String getOrgaoEmissorRg() {
		return orgaoEmissorRg;
	}

	public void setOrgaoEmissorRg(String orgaoEmissorRg) {
		this.orgaoEmissorRg = orgaoEmissorRg;
	}

	public Instant getDataEmissaoRg() {
		return dataEmissaoRg;
	}

	public void setDataEmissaoRg(Instant dataEmissaoRg) {
		this.dataEmissaoRg = dataEmissaoRg;
	}

	public Integer getNumeroRegNacEstrangeiro() {
		return numeroRegNacEstrangeiro;
	}

	public void setNumeroRegNacEstrangeiro(Integer numeroRegNacEstrangeiro) {
		this.numeroRegNacEstrangeiro = numeroRegNacEstrangeiro;
	}

	public String getOrgaoEmissorRegNacEstrangeiro() {
		return orgaoEmissorRegNacEstrangeiro;
	}

	public void setOrgaoEmissorRegNacEstrangeiro(String orgaoEmissorRegNacEstrangeiro) {
		this.orgaoEmissorRegNacEstrangeiro = orgaoEmissorRegNacEstrangeiro;
	}

	public Instant getDataEmissaoRegNacEstrangeiro() {
		return dataEmissaoRegNacEstrangeiro;
	}

	public void setDataEmissaoRegNacEstrangeiro(Instant dataEmissaoRegNacEstrangeiro) {
		this.dataEmissaoRegNacEstrangeiro = dataEmissaoRegNacEstrangeiro;
	}

	public Integer getRegistroProfissional() {
		return registroProfissional;
	}

	public void setRegistroProfissional(Integer registroProfissional) {
		this.registroProfissional = registroProfissional;
	}

	public Instant getDataEmissaoRegProfissional() {
		return dataEmissaoRegProfissional;
	}

	public void setDataEmissaoRegProfissional(Instant dataEmissaoRegProfissional) {
		this.dataEmissaoRegProfissional = dataEmissaoRegProfissional;
	}

	public Instant getDataValidadeRegProfissional() {
		return dataValidadeRegProfissional;
	}

	public void setDataValidadeRegProfissional(Instant dataValidadeRegProfissional) {
		this.dataValidadeRegProfissional = dataValidadeRegProfissional;
	}

	public Integer getNumeroCnh() {
		return numeroCnh;
	}

	public void setNumeroCnh(Integer numeroCnh) {
		this.numeroCnh = numeroCnh;
	}

	public Instant getDataValidadeCnh() {
		return dataValidadeCnh;
	}

	public void setDataValidadeCnh(Instant dataValidadeCnh) {
		this.dataValidadeCnh = dataValidadeCnh;
	}

	public Instant getDataPrimeiraCnh() {
		return dataPrimeiraCnh;
	}

	public void setDataPrimeiraCnh(Instant dataPrimeiraCnh) {
		this.dataPrimeiraCnh = dataPrimeiraCnh;
	}

	public Instant getDataEmissaoCnh() {
		return dataEmissaoCnh;
	}

	public void setDataEmissaoCnh(Instant dataEmissaoCnh) {
		this.dataEmissaoCnh = dataEmissaoCnh;
	}

	public CategoriaCnhEnum getCategoriaCnh() {
		return categoriaCnh;
	}

	public void setCategoriaCnh(CategoriaCnhEnum categoriaCnh) {
		this.categoriaCnh = categoriaCnh;
	}

	public UnidadeFederativa getUfCnh() {
		return ufCnh;
	}

	public void setUfCnh(UnidadeFederativa ufCnh) {
		this.ufCnh = ufCnh;
	}

	public TipoDependenteEnum getTipoDependente() {
		return tipoDependente;
	}

	public void setTipoDependente(TipoDependenteEnum tipoDependente) {
		this.tipoDependente = tipoDependente;
	}

	public String getNomeDependente() {
		return nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
	}

	public String getCpfDependente() {
		return cpfDependente;
	}

	public void setCpfDependente(String cpfDependente) {
		this.cpfDependente = cpfDependente;
	}

	public Boolean isImpostoRetidoFonte() {
		return impostoRetidoFonte;
	}

	public void setImpostoRetidoFonte(Boolean impostoRetidoFonte) {
		this.impostoRetidoFonte = impostoRetidoFonte;
	}

	public Boolean isPlanoSaudePrivado() {
		return planoSaudePrivado;
	}

	public void setPlanoSaudePrivado(Boolean planoSaudePrivado) {
		this.planoSaudePrivado = planoSaudePrivado;
	}

	public Boolean isRecebeBeneficio() {
		return recebeBeneficio;
	}

	public void setRecebeBeneficio(Boolean recebeBeneficio) {
		this.recebeBeneficio = recebeBeneficio;
	}

	public Boolean isCapacitacaoProfissional() {
		return capacitacaoProfissional;
	}

	public void setCapacitacaoProfissional(Boolean capacitacaoProfissional) {
		this.capacitacaoProfissional = capacitacaoProfissional;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}