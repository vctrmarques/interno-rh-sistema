package com.rhlinkcon.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.funcionario.FuncionarioRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Funcionário")
@Table(name = "funcionario")
public class Funcionario extends UserDateAudit {

	/**
	 *
	 */
	private static final long serialVersionUID = 6711572737571157313L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Início Aba de Dados do Contrato

	@NotNull
	@NotBlank
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Column(name = "matricula", unique = true)
	private Integer matricula;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private EmpresaFilial empresa;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filial_id")
	private EmpresaFilial filial;

	@NotNull
	@Column(name = "dt_nascimento")
	private Instant dataNascimento;

	@NotNull
	@Column(name = "sexo")
	private char sexo;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_civil")
	private EstadoCivilEnum estadoCivil;

	@Enumerated(EnumType.STRING)
	@Column(name = "grau_instrucao")
	private GrauInstrucaoEnum grauInstrucao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nacionalidade_id")
	private Nacionalidade nacionalidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "naturalidade_id")
	private Municipio naturalidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_id")
	private Municipio municipio;

	@Column(name = "conhecido_como")
	private String conhecidoComo;

	@Column(name = "tipo_sanguineo")
	private String tipoSanguineo;

	@Enumerated(EnumType.STRING)
	@Column(name = "cor_pele")
	private CorPeleEnum corPele;

	@NotNull
	@Column(name = "nome_mae")
	private String nomeMae;

	@Column(name = "nome_pai")
	private String nomePai;

	// Fim Aba Dados do Contrato

	// Início Aba Contato & Endereço

	@Column(name = "estrangeiro")
	private Boolean estrangeiro;

	@Column(name = "naturalizado")
	private Boolean naturalizado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "registro_estrangeiro_uf_id")
	private UnidadeFederativa registroEstrangeiroUf;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_id")
	private UnidadeFederativa uf;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_registro_id")
	private Municipio municipioRegistroEstrangeiro;

	@Column(name = "casamento_brasileiro")
	private Boolean casamentoBrasileiro;

	@Column(name = "filho_brasileiro")
	private Boolean filhoBrasileiro;

	@NotNull
	@Column(name = "logradouro")
	private String logradouro;

	@NotNull
	@Column(name = "numero")
	private String numero;

	@Column(name = "complemento")
	private String complemento;

	@NotNull
	@Column(name = "bairro")
	private String bairro;

	@NotNull
	@Size(min = 8, max = 8)
	@Column(name = "cep")
	private String cep;

	@Email
	@Column(name = "email_pessoal")
	private String emailPessoal;

	@Email
	@Column(name = "email_corporativo")
	private String emailCorporativo;

	@Column(name = "telefone_principal")
	private String telefonePrincipal;

	@Column(name = "telefone_opcional")
	private String telefoneOpcional;

	@Column(name = "dt_nomeacao")
	private Instant dataNomeacao;

	@Column(name = "telefone_comercial")
	private String telefoneComercial;

	// Fim Aba Contato & Endereço
	// Início Aba Documentação

	@NotNull
	@Column(name = "identidade")
	private String identidade;

	@NotNull
	@Column(name = "orgao_expeditor")
	private String orgaoExpeditor;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgao_expeditor_uf_id")
	private UnidadeFederativa ufOrgaoExpeditor;

	@NotNull
	@Column(name = "dt_expedicao_rg")
	private Instant dataExpedicaoRg;

	@Column(name = "numero_ctps")
	private String numeroCtps;

	@Column(name = "serie_ctps")
	private String serieCtps;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ctps_uf_id")
	private UnidadeFederativa ufCtps;

	@NotNull
	@Size(min = 11, max = 11)
	@Column(name = "cpf")
	private String cpf;

	@NotNull
	@Column(name = "pis_pasep")
	private String pisPasep;

	@Column(name = "dt_emissao_pis_pasep")
	private Instant dataEmissaoPisPasep;

	@Column(name = "agencia_pis_pasep")
	private Integer agenciaPisPasep;

	@Size(max = 12)
	@Column(name = "titulo_eleitor")
	private String tituloEleitor;

	@Column(name = "zona")
	private Integer zona;

	@Column(name = "secao")
	private Integer secao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "titulo_eleitor_uf_id")
	private UnidadeFederativa ufTituloEleitor;

	@Size(min = 11, max = 11)
	@Column(name = "cnh")
	private String cnh;

	@Column(name = "dt_validade_cnh")
	private Instant dataValidadeCnh;

	@Enumerated(EnumType.STRING)
	@Column(name = "categoria_cnh")
	private CategoriaCnhEnum categoriaCnh;

	@Column(name = "registro_alistamento")
	private Integer registroAlistamento;

	@Column(name = "categoria_alistamento")
	private String categoriaAlistamento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classificacao_ato_id")
	private ClassificacaoAto classificacaoAto;

	@Column(name = "numero_processo")
	private Integer numeroProcesso;

	@Column(name = "numero_ato")
	private Integer numeroAto;

	@Column(name = "numero_diario_oficial")
	private Integer numeroDiarioOficial;

	@Column(name = "dt_publicacao_diario_oficial")
	private Instant dataPublicacaoDiarioOficial;

	@Column(name = "numero_sus")
	private String numeroSus;

	// Fim aba Documentaçao
	// Início Aba de Dados do Pagamento

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vinculo_id")
	private Vinculo vinculo;

	@NotNull
	@Column(name = "dt_admissao")
	private Instant dataAdmissao;

	@Column(name = "n_dependentes_ir")
	private Integer numeroDependentesImpostoRenda;

	@Column(name = "n_dependentes_sf")
	private Integer numeroDependentesSalarioFamilia;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcao_id")
	private Funcao funcao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cargo_id")
	private Cargo cargo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faixa_salarial_cargo_id")
	private FaixaSalarial faixaSalarialCargo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referencia_salarial_cargo_id")
	private ReferenciaSalarial referenciaSalarialCargo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faixa_salarial_funcao_id")
	private FaixaSalarial faixaSalarialFuncao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referencia_salarial_funcao_id")
	private ReferenciaSalarial referenciaSalarialFuncao;

	@Column(name = "opc_fgts")
	private Boolean opcaoFgts;

	@Column(name = "dt_opc_fgts")
	private Instant dataOpcaoFgts;

	@Column(name = "agencia_fgts")
	private Integer agenciaFgts;

	@Column(name = "conta_fgts")
	private Integer contaFgts;

	@Column(name = "dt_exercicio_admissao_ats")
	private Instant dataExercicioAdmissaoAts;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_adicional_tempo_servico")
	private TipoAdicionalTempoServicoEnum tipoAdicionalTempoServico;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_estabilidade")
	private TipoEstabilidadeEnum tipoEstabilidade;

	@Column(name = "estabilidade")
	private Boolean estabilidade;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_trabalho_id")
	private Municipio municipioTrabalho;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uf_trabalho_id")
	private UnidadeFederativa ufTrabalho;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lotacao_id")
	private Lotacao lotacao;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banco_id")
	private Banco banco;

	@NotNull
	@Column(name = "agencia")
	private String agencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agencia_id")
	private Agencia agenciaBancaria;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_conta")
	private TipoContaResponsavelEnum tipoConta;

	@NotNull
	@Column(name = "numero_conta")
	private String numeroConta;

	@NotNull
	@Column(name = "digito_conta")
	private String digitoConta;

	@Column(name = "operacao")
	private String operacao;

	@NotNull
	@Column(name = "probatorio")
	private Boolean probatorio;

	@OneToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "jornada_trabalho_turno_id")
	private Turno jornadaTrabalho;

	@Enumerated(EnumType.STRING)
	@Column(name = "carga_horaria")
	private CargaHorariaSemanalEnum cargaHoraria;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sindicato_id")
	private Sindicato sindicato;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_frequencia")
	private TipoFrequenciaEnum tipoFrequencia;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tp_sit_func_afastamento_id")
	private SituacaoFuncional tipoSituacaoFuncional;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "motivo_afastamento_id")
	private MotivoAfastamento motivoAfastamento;

	@Column(name = "dt_ini_situacao_funcional")
	private Instant dataInicioSituacaoFuncional;

	@Column(name = "dt_fim_situacao_funcional")
	private Instant dataFimSituacaoFuncional;

	@Column(name = "ini_contrat_temp")
	private Instant inicioContratoTemporario;

	@Column(name = "fim_contrat_temp")
	private Instant fimContratoTemporario;

	@Column(name = "dias_afastado")
	private Integer diasAfastado;

	@Column(name = "matricula_destino")
	private Integer matriculaDestino;

	@Column(name = "ces_empresa_destino")
	private String cessaoEmpresaDestino;

	@Column(name = "matricula_vinculo")
	private String matriculaVinculo;

	@Column(name = "descricao_vinculo")
	private String descricaoVinculo;

	@Column(name = "valor_vinculo")
	private Double valorVinculo;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private Set<FuncionarioValeTransporte> valesTransportes;
	// Fim Aba Dados do Pagamento

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "funcionario_anexo", joinColumns = @JoinColumn(name = "funcionario_id"), inverseJoinColumns = @JoinColumn(name = "anexo_id"))
	private List<Anexo> anexos = new ArrayList<Anexo>();

	// Dado Cadastral Complementar

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "dado_cadastral_complementar_id")
	private DadoCadastralComplementar dadoCadastral;

	// Outros relacionamentos

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private List<Processo> processos;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private List<PensaoAlimenticia> pensoes;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private Set<ExperienciaProfissional> experienciaProfissionais;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private Set<AcidenteTrabalho> acidentesDeTrabalho;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private Set<FuncionarioVerba> funcionarioVerbas;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_custo_id")
	private CentroCusto centroCusto;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY)
	private Set<RecisaoContrato> recisoesContrato;

	@Transient
	private LocalDate dataBaseRecadastramento;

	public Funcionario() {
	}

	public Funcionario(Long id) {
		this.id = id;
	}

	public Funcionario(FuncionarioRequest funcionarioRequest) {
		// aba de Dados Cadastrais
		this.setNome(funcionarioRequest.getNome());
		this.setCpf(funcionarioRequest.getCpf());
		this.setPisPasep(funcionarioRequest.getPisPasep());
		if (Objects.nonNull(funcionarioRequest.getTipoSituacaoFuncionalId()))
			this.setTipoSituacaoFuncional(new SituacaoFuncional(funcionarioRequest.getTipoSituacaoFuncionalId()));
		this.setMatricula(funcionarioRequest.getMatricula());
		this.setDataNascimento(funcionarioRequest.getDataNascimento());
		this.setSexo(funcionarioRequest.getSexo());

		if (Utils.checkStr(funcionarioRequest.getEstadoCivil()))
			this.setEstadoCivil(EstadoCivilEnum.valueOf(funcionarioRequest.getEstadoCivil()));
		if (Utils.checkStr(funcionarioRequest.getGrauInstrucao()))
			this.setGrauInstrucao(GrauInstrucaoEnum.valueOf(funcionarioRequest.getGrauInstrucao()));
		if (Utils.checkStr(funcionarioRequest.getCorPele()))
			this.setCorPele(CorPeleEnum.valueOf(funcionarioRequest.getCorPele()));

		this.setConhecidoComo(funcionarioRequest.getConhecidoComo());
		this.setTipoSanguineo(funcionarioRequest.getTipoSanguineo());
		this.setNomeMae(funcionarioRequest.getNomeMae());
		this.setNomePai(funcionarioRequest.getNomePai());

		if (Objects.nonNull(funcionarioRequest.getCentroCustoId()))
			this.setCentroCusto(new CentroCusto(funcionarioRequest.getCentroCustoId()));

		// aba Contato e Endereço
		this.setEstrangeiro(funcionarioRequest.getEstrangeiro());
		this.setNaturalizado(funcionarioRequest.getNaturalizado());
		this.setCasamentoBrasileiro(funcionarioRequest.getCasamentoBrasileiro());
		this.setFilhoBrasileiro(funcionarioRequest.getFilhoBrasileiro());
		this.setLogradouro(funcionarioRequest.getLogradouro());
		this.setNumero(funcionarioRequest.getNumero());
		this.setComplemento(funcionarioRequest.getComplemento());
		this.setBairro(funcionarioRequest.getBairro());
		this.setCep(funcionarioRequest.getCep());
		this.setEmailPessoal(funcionarioRequest.getEmailPessoal());
		this.setEmailCorporativo(funcionarioRequest.getEmailCorporativo());
		this.setTelefoneOpcional(funcionarioRequest.getTelefoneOpcional());
		this.setTelefonePrincipal(funcionarioRequest.getTelefonePrincipal());
		this.setTelefoneComercial(funcionarioRequest.getTelefoneComercial());

		// aba Documentação
		this.setIdentidade(funcionarioRequest.getIdentidade());
		this.setOrgaoExpeditor(funcionarioRequest.getOrgaoExpeditor());
		this.setDataExpedicaoRg(funcionarioRequest.getDataExpedicaoRg());
		this.setNumeroCtps(funcionarioRequest.getNumeroCtps());
		this.setSerieCtps(funcionarioRequest.getSerieCtps());
		this.setCpf(funcionarioRequest.getCpf());
		this.setPisPasep(funcionarioRequest.getPisPasep());
		this.setDataEmissaoPisPasep(funcionarioRequest.getDataEmissaoPisPasep());
		this.setAgenciaPisPasep(funcionarioRequest.getAgenciaPisPasep());
		this.setTituloEleitor(funcionarioRequest.getTituloEleitor());
		this.setSecao(funcionarioRequest.getSecao());
		this.setZona(funcionarioRequest.getZona());
		this.setCnh(funcionarioRequest.getCnh());
		this.setDataValidadeCnh(funcionarioRequest.getDataValidadeCnh());

		if (Utils.checkStr(funcionarioRequest.getCategoriaCnh()))
			this.setCategoriaCnh(CategoriaCnhEnum.valueOf(funcionarioRequest.getCategoriaCnh()));

		this.setRegistroAlistamento(funcionarioRequest.getRegistroAlistamento());
		this.setCategoriaAlistamento(funcionarioRequest.getCategoriaAlistamento());
		this.setNumeroProcesso(funcionarioRequest.getNumeroProcesso());
		this.setNumeroAto(funcionarioRequest.getNumeroAto());
		this.setDataNomeacao(funcionarioRequest.getDataNomeacao());
		this.setNumeroDiarioOficial(funcionarioRequest.getNumeroDiarioOficial());
		this.setDataPublicacaoDiarioOficial(funcionarioRequest.getDataPublicacaoDiarioOficial());
		this.setNumeroSus(funcionarioRequest.getNumeroSus());

		// aba Dados do Pagamento
		this.setDataAdmissao(funcionarioRequest.getDataAdmissao());
		this.setNumeroDependentesImpostoRenda(funcionarioRequest.getNumeroDependentesImpostoRenda());
		this.setNumeroDependentesSalarioFamilia(funcionarioRequest.getNumeroDependentesSalarioFamilia());
		this.setOpcaoFgts(funcionarioRequest.getOpcaoFgts());
		this.setAgenciaFgts(funcionarioRequest.getAgenciaFgts());
		this.setContaFgts(funcionarioRequest.getContaFgts());
		this.setDataExercicioAdmissaoAts(funcionarioRequest.getDataExercicioAdmissaoAts());
		this.setEstabilidade(funcionarioRequest.getEstabilidade());
		this.setNumeroConta(funcionarioRequest.getNumeroConta());
		this.setOperacao(funcionarioRequest.getOperacao());
		this.setDigitoConta(funcionarioRequest.getDigitoConta());
		this.setProbatorio(funcionarioRequest.getProbatorio());
		this.setDataInicioSituacaoFuncional(funcionarioRequest.getDataInicioSituacaoFuncional());
		this.setDataFimSituacaoFuncional(funcionarioRequest.getDataFimSituacaoFuncional());
		this.setAgencia(funcionarioRequest.getAgencia());
		this.setInicioContratoTemporario(funcionarioRequest.getInicioContratoTemporario());
		this.setFimContratoTemporario(funcionarioRequest.getFimContratoTemporario());
		this.setDiasAfastado(funcionarioRequest.getDiasAfastado());
		this.setMatriculaDestino(funcionarioRequest.getMatriculaDestino());
		this.setCessaoEmpresaDestino(funcionarioRequest.getCessaoEmpresaDestino());
		this.setTipoConta(TipoContaResponsavelEnum.valueOf(funcionarioRequest.getTipoConta()));
		this.setMatriculaVinculo(funcionarioRequest.getMatriculaVinculo());
		this.setDescricaoVinculo(funcionarioRequest.getDescricaoVinculo());
		this.setValorVinculo(funcionarioRequest.getValorVinculo());

		if (Objects.nonNull(funcionarioRequest.getTipoEstabilidade()))
			this.setTipoEstabilidade(TipoEstabilidadeEnum.valueOf(funcionarioRequest.getTipoEstabilidade()));

	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public EmpresaFilial getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFilial empresa) {
		this.empresa = empresa;
	}

	public EmpresaFilial getFilial() {
		return filial;
	}

	public GrauInstrucaoEnum getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setFilial(EmpresaFilial filial) {
		this.filial = filial;
	}

	public Instant getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Instant dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public void setGrauInstrucao(GrauInstrucaoEnum grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getConhecidoComo() {
		return conhecidoComo;
	}

	public void setConhecidoComo(String conhecidoComo) {
		this.conhecidoComo = conhecidoComo;
	}

	public String getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public CorPeleEnum getCorPele() {
		return corPele;
	}

	public void setCorPele(CorPeleEnum corPele) {
		this.corPele = corPele;
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

	public Boolean getEstrangeiro() {
		return estrangeiro;
	}

	public void setEstrangeiro(Boolean estrangeiro) {
		this.estrangeiro = estrangeiro;
	}

	public Boolean getNaturalizado() {
		return naturalizado;
	}

	public void setNaturalizado(Boolean naturalizado) {
		this.naturalizado = naturalizado;
	}

	public UnidadeFederativa getRegistroEstrangeiroUf() {
		return registroEstrangeiroUf;
	}

	public void setRegistroEstrangeiroUf(UnidadeFederativa registroEstrangeiroUf) {
		this.registroEstrangeiroUf = registroEstrangeiroUf;
	}

	public Municipio getMunicipioRegistroEstrangeiro() {
		return municipioRegistroEstrangeiro;
	}

	public void setMunicipioRegistroEstrangeiro(Municipio municipioRegistroEstrangeiro) {
		this.municipioRegistroEstrangeiro = municipioRegistroEstrangeiro;
	}

	public Boolean getCasamentoBrasileiro() {
		return casamentoBrasileiro;
	}

	public void setCasamentoBrasileiro(Boolean casamentoBrasileiro) {
		this.casamentoBrasileiro = casamentoBrasileiro;
	}

	public Boolean getFilhoBrasileiro() {
		return filhoBrasileiro;
	}

	public void setFilhoBrasileiro(Boolean filhoBrasileiro) {
		this.filhoBrasileiro = filhoBrasileiro;
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

	public UnidadeFederativa getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativa uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEmailPessoal() {
		return emailPessoal;
	}

	public void setEmailPessoal(String emailPessoal) {
		this.emailPessoal = emailPessoal;
	}

	public String getEmailCorporativo() {
		return emailCorporativo;
	}

	public void setEmailCorporativo(String emailCorporativo) {
		this.emailCorporativo = emailCorporativo;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getOrgaoExpeditor() {
		return orgaoExpeditor;
	}

	public void setOrgaoExpeditor(String orgaoExpeditor) {
		this.orgaoExpeditor = orgaoExpeditor;
	}

	public UnidadeFederativa getUfOrgaoExpeditor() {
		return ufOrgaoExpeditor;
	}

	public void setUfOrgaoExpeditor(UnidadeFederativa ufOrgaoExpeditor) {
		this.ufOrgaoExpeditor = ufOrgaoExpeditor;
	}

	public Instant getDataExpedicaoRg() {
		return dataExpedicaoRg;
	}

	public void setDataExpedicaoRg(Instant dataExpedicaoRg) {
		this.dataExpedicaoRg = dataExpedicaoRg;
	}

	public String getNumeroCtps() {
		return numeroCtps;
	}

	public void setNumeroCtps(String numeroCtps) {
		this.numeroCtps = numeroCtps;
	}

	public UnidadeFederativa getUfCtps() {
		return ufCtps;
	}

	public void setUfCtps(UnidadeFederativa ufCtps) {
		this.ufCtps = ufCtps;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPisPasep() {
		return pisPasep;
	}

	public void setPisPasep(String pisPasep) {
		this.pisPasep = pisPasep;
	}

	public Instant getDataEmissaoPisPasep() {
		return dataEmissaoPisPasep;
	}

	public void setDataEmissaoPisPasep(Instant dataEmissaoPisPasep) {
		this.dataEmissaoPisPasep = dataEmissaoPisPasep;
	}

	public Integer getAgenciaPisPasep() {
		return agenciaPisPasep;
	}

	public void setAgenciaPisPasep(Integer agenciaPisPasep) {
		this.agenciaPisPasep = agenciaPisPasep;
	}

	public String getTituloEleitor() {
		return tituloEleitor;
	}

	public void setTituloEleitor(String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
	}

	public UnidadeFederativa getUfTituloEleitor() {
		return ufTituloEleitor;
	}

	public void setUfTituloEleitor(UnidadeFederativa ufTituloEleitor) {
		this.ufTituloEleitor = ufTituloEleitor;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public Instant getDataValidadeCnh() {
		return dataValidadeCnh;
	}

	public void setDataValidadeCnh(Instant dataValidadeCnh) {
		this.dataValidadeCnh = dataValidadeCnh;
	}

	public CategoriaCnhEnum getCategoriaCnh() {
		return categoriaCnh;
	}

	public void setCategoriaCnh(CategoriaCnhEnum categoriaCnh) {
		this.categoriaCnh = categoriaCnh;
	}

	public Integer getRegistroAlistamento() {
		return registroAlistamento;
	}

	public void setRegistroAlistamento(Integer registroAlistamento) {
		this.registroAlistamento = registroAlistamento;
	}

	public ClassificacaoAto getClassificacaoAto() {
		return classificacaoAto;
	}

	public void setClassificacaoAto(ClassificacaoAto classificacaoAto) {
		this.classificacaoAto = classificacaoAto;
	}

	public Integer getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(Integer numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public Integer getNumeroAto() {
		return numeroAto;
	}

	public void setNumeroAto(Integer numeroAto) {
		this.numeroAto = numeroAto;
	}

	public Instant getDataNomeacao() {
		return dataNomeacao;
	}

	public void setDataNomeacao(Instant dataNomeacao) {
		this.dataNomeacao = dataNomeacao;
	}

	public Integer getNumeroDiarioOficial() {
		return numeroDiarioOficial;
	}

	public void setNumeroDiarioOficial(Integer numeroDiarioOficial) {
		this.numeroDiarioOficial = numeroDiarioOficial;
	}

	public Instant getDataPublicacaoDiarioOficial() {
		return dataPublicacaoDiarioOficial;
	}

	public void setDataPublicacaoDiarioOficial(Instant dataPublicacaoDiarioOficial) {
		this.dataPublicacaoDiarioOficial = dataPublicacaoDiarioOficial;
	}

	public Vinculo getVinculo() {
		return vinculo;
	}

	public void setVinculo(Vinculo vinculo) {
		this.vinculo = vinculo;
	}

	public Instant getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Instant dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Integer getNumeroDependentesImpostoRenda() {
		return numeroDependentesImpostoRenda;
	}

	public void setNumeroDependentesImpostoRenda(Integer numeroDependentesImpostoRenda) {
		this.numeroDependentesImpostoRenda = numeroDependentesImpostoRenda;
	}

	public Integer getNumeroDependentesSalarioFamilia() {
		return numeroDependentesSalarioFamilia;
	}

	public void setNumeroDependentesSalarioFamilia(Integer numeroDependentesSalarioFamilia) {
		this.numeroDependentesSalarioFamilia = numeroDependentesSalarioFamilia;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Boolean getOpcaoFgts() {
		return opcaoFgts;
	}

	public void setOpcaoFgts(Boolean opcaoFgts) {
		this.opcaoFgts = opcaoFgts;
	}

	public Instant getDataOpcaoFgts() {
		return dataOpcaoFgts;
	}

	public void setDataOpcaoFgts(Instant dataOpcaoFgts) {
		this.dataOpcaoFgts = dataOpcaoFgts;
	}

	public Integer getAgenciaFgts() {
		return agenciaFgts;
	}

	public void setAgenciaFgts(Integer agenciaFgts) {
		this.agenciaFgts = agenciaFgts;
	}

	public Integer getContaFgts() {
		return contaFgts;
	}

	public void setContaFgts(Integer contaFgts) {
		this.contaFgts = contaFgts;
	}

	public Instant getDataExercicioAdmissaoAts() {
		return dataExercicioAdmissaoAts;
	}

	public void setDataExercicioAdmissaoAts(Instant dataExercicioAdmissaoAts) {
		this.dataExercicioAdmissaoAts = dataExercicioAdmissaoAts;
	}

	public TipoAdicionalTempoServicoEnum getTipoAdicionalTempoServico() {
		return tipoAdicionalTempoServico;
	}

	public void setTipoAdicionalTempoServico(TipoAdicionalTempoServicoEnum tipoAdicionalTempoServico) {
		this.tipoAdicionalTempoServico = tipoAdicionalTempoServico;
	}

	public Boolean getEstabilidade() {
		return estabilidade;
	}

	public void setEstabilidade(Boolean estabilidade) {
		this.estabilidade = estabilidade;
	}

	public Municipio getMunicipioTrabalho() {
		return municipioTrabalho;
	}

	public void setMunicipioTrabalho(Municipio municipioTrabalho) {
		this.municipioTrabalho = municipioTrabalho;
	}

	public Lotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public TipoContaResponsavelEnum getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoContaResponsavelEnum tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Boolean getProbatorio() {
		return probatorio;
	}

	public void setProbatorio(Boolean probatorio) {
		this.probatorio = probatorio;
	}

	public Turno getJornadaTrabalho() {
		return jornadaTrabalho;
	}

	public void setJornadaTrabalho(Turno jornadaTrabalho) {
		this.jornadaTrabalho = jornadaTrabalho;
	}

	public CargaHorariaSemanalEnum getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHorariaSemanalEnum cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Sindicato getSindicato() {
		return sindicato;
	}

	public void setSindicato(Sindicato sindicato) {
		this.sindicato = sindicato;
	}

	public TipoFrequenciaEnum getTipoFrequencia() {
		return tipoFrequencia;
	}

	public void setTipoFrequencia(TipoFrequenciaEnum tipoFrequencia) {
		this.tipoFrequencia = tipoFrequencia;
	}

	public SituacaoFuncional getTipoSituacaoFuncional() {
		return tipoSituacaoFuncional;
	}

	public void setTipoSituacaoFuncional(SituacaoFuncional tipoSituacaoFuncional) {
		this.tipoSituacaoFuncional = tipoSituacaoFuncional;
	}

	public MotivoAfastamento getMotivoAfastamento() {
		return motivoAfastamento;
	}

	public void setMotivoAfastamento(MotivoAfastamento motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}

	public Instant getDataInicioSituacaoFuncional() {
		return dataInicioSituacaoFuncional;
	}

	public void setDataInicioSituacaoFuncional(Instant dataInicioSituacaoFuncional) {
		this.dataInicioSituacaoFuncional = dataInicioSituacaoFuncional;
	}

	public Instant getDataFimSituacaoFuncional() {
		return dataFimSituacaoFuncional;
	}

	public void setDataFimSituacaoFuncional(Instant dataFimSituacaoFuncional) {
		this.dataFimSituacaoFuncional = dataFimSituacaoFuncional;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Instant getInicioContratoTemporario() {
		return inicioContratoTemporario;
	}

	public void setInicioContratoTemporario(Instant inicioContratoTemporario) {
		this.inicioContratoTemporario = inicioContratoTemporario;
	}

	public Instant getFimContratoTemporario() {
		return fimContratoTemporario;
	}

	public void setFimContratoTemporario(Instant fimContratoTemporario) {
		this.fimContratoTemporario = fimContratoTemporario;
	}

	public Integer getDiasAfastado() {
		return diasAfastado;
	}

	public void setDiasAfastado(Integer diasAfastado) {
		this.diasAfastado = diasAfastado;
	}

	public Integer getMatriculaDestino() {
		return matriculaDestino;
	}

	public void setMatriculaDestino(Integer matriculaDestino) {
		this.matriculaDestino = matriculaDestino;
	}

	public String getCessaoEmpresaDestino() {
		return cessaoEmpresaDestino;
	}

	public void setCessaoEmpresaDestino(String cessaoEmpresaDestino) {
		this.cessaoEmpresaDestino = cessaoEmpresaDestino;
	}

	public DadoCadastralComplementar getDadoCadastral() {
		return dadoCadastral;
	}

	public void setDadoCadastral(DadoCadastralComplementar dadoCadastral) {
		this.dadoCadastral = dadoCadastral;
	}

	public Set<ExperienciaProfissional> getExperienciaProfissionais() {
		return experienciaProfissionais;
	}

	public void setExperienciaProfissionais(Set<ExperienciaProfissional> experienciaProfissionais) {
		this.experienciaProfissionais = experienciaProfissionais;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	public Set<AcidenteTrabalho> getAcidentesDeTrabalho() {
		return acidentesDeTrabalho;
	}

	public void setAcidentesDeTrabalho(Set<AcidenteTrabalho> acidentesDeTrabalho) {
		this.acidentesDeTrabalho = acidentesDeTrabalho;
	}

	public List<PensaoAlimenticia> getPensoes() {
		return pensoes;
	}

	public void setPensoes(List<PensaoAlimenticia> pensoes) {
		this.pensoes = pensoes;
	}

	public Set<FuncionarioValeTransporte> getValesTransportes() {
		return valesTransportes;
	}

	public void setValesTransportes(Set<FuncionarioValeTransporte> valesTransportes) {
		this.valesTransportes = valesTransportes;
	}

	public Integer getZona() {
		return zona;
	}

	public void setZona(Integer zona) {
		this.zona = zona;
	}

	public Integer getSecao() {
		return secao;
	}

	public void setSecao(Integer secao) {
		this.secao = secao;
	}

	public String getTelefonePrincipal() {
		return telefonePrincipal;
	}

	public void setTelefonePrincipal(String telefonePrincipal) {
		this.telefonePrincipal = telefonePrincipal;
	}

	public String getTelefoneOpcional() {
		return telefoneOpcional;
	}

	public UnidadeFederativa getUfTrabalho() {
		return ufTrabalho;
	}

	public void setUfTrabalho(UnidadeFederativa ufTrabalho) {
		this.ufTrabalho = ufTrabalho;
	}

	public void setTelefoneOpcional(String telefoneOpcional) {
		this.telefoneOpcional = telefoneOpcional;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getSerieCtps() {
		return serieCtps;
	}

	public void setSerieCtps(String serieCtps) {
		this.serieCtps = serieCtps;
	}

	public String getCategoriaAlistamento() {
		return categoriaAlistamento;
	}

	public void setCategoriaAlistamento(String categoriaAlistamento) {
		this.categoriaAlistamento = categoriaAlistamento;
	}

	public TipoEstabilidadeEnum getTipoEstabilidade() {
		return tipoEstabilidade;
	}

	public void setTipoEstabilidade(TipoEstabilidadeEnum tipoEstabilidade) {
		this.tipoEstabilidade = tipoEstabilidade;
	}

	public String getNumeroSus() {
		return numeroSus;
	}

	public void setNumeroSus(String numeroSus) {
		this.numeroSus = numeroSus;
	}

	public String getMatriculaVinculo() {
		return matriculaVinculo;
	}

	public void setMatriculaVinculo(String matriculaVinculo) {
		this.matriculaVinculo = matriculaVinculo;
	}

	public String getDescricaoVinculo() {
		return descricaoVinculo;
	}

	public void setDescricaoVinculo(String descricaoVinculo) {
		this.descricaoVinculo = descricaoVinculo;
	}

	public Double getValorVinculo() {
		return valorVinculo;
	}

	public void setValorVinculo(Double valorVinculo) {
		this.valorVinculo = valorVinculo;
	}

	public Set<RecisaoContrato> getRecisoesContrato() {
		return recisoesContrato;
	}

	public void setRecisoesContrato(Set<RecisaoContrato> recisoesContrato) {
		this.recisoesContrato = recisoesContrato;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public FaixaSalarial getFaixaSalarialCargo() {
		return faixaSalarialCargo;
	}

	public void setFaixaSalarialCargo(FaixaSalarial faixaSalarialCargo) {
		this.faixaSalarialCargo = faixaSalarialCargo;
	}

	public ReferenciaSalarial getReferenciaSalarialCargo() {
		return referenciaSalarialCargo;
	}

	public void setReferenciaSalarialCargo(ReferenciaSalarial referenciaSalarialCargo) {
		this.referenciaSalarialCargo = referenciaSalarialCargo;
	}

	public FaixaSalarial getFaixaSalarialFuncao() {
		return faixaSalarialFuncao;
	}

	public void setFaixaSalarialFuncao(FaixaSalarial faixaSalarialFuncao) {
		this.faixaSalarialFuncao = faixaSalarialFuncao;
	}

	public ReferenciaSalarial getReferenciaSalarialFuncao() {
		return referenciaSalarialFuncao;
	}

	public void setReferenciaSalarialFuncao(ReferenciaSalarial referenciaSalarialFuncao) {
		this.referenciaSalarialFuncao = referenciaSalarialFuncao;
	}

	public Set<FuncionarioVerba> getFuncionarioVerbas() {
		return funcionarioVerbas;
	}

	public void setFuncionarioVerbas(Set<FuncionarioVerba> funcionarioVerbas) {
		this.funcionarioVerbas = funcionarioVerbas;
	}

	public FundoEnum getFundo() {
		return Utils.getFilialPensao(getDataNascimento(), getDataAdmissao());
	}

	public Municipio getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(Municipio naturalidade) {
		this.naturalidade = naturalidade;
	}

	public Agencia getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public void setAgenciaBancaria(Agencia agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}

	@Override
	public String getLabel() {
		return this.nome;
	}

	public LocalDate getDataBaseRecadastramento() {
		return dataBaseRecadastramento;
	}

	public void setDataBaseRecadastramento(LocalDate dataBaseRecadastramento) {
		this.dataBaseRecadastramento = dataBaseRecadastramento;
	}

}
