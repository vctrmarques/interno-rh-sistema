package com.rhlinkcon.model;

import java.time.Instant;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.pensionista.PensionistaRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Pensionista")
@Table(name = "pensionista")
public class Pensionista extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2539721698937298286L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "funcionario_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Funcionario funcionario;

	@JoinColumn(name = "pensao_previdenciaria_pagamento_id", nullable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private PensaoPrevidenciariaPagamento pensaoPagamento;

	@NotNull
	private Integer matricula;

	@NotNull
	private String nome;

	@NotNull
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	@NotNull
	@Enumerated(EnumType.STRING)
	private GeneroEnum genero;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_civil")
	private EstadoCivilEnum estadoCivil;

	@Column(name = "status")
	private boolean status;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "grau_instrucao")
	private GrauInstrucaoEnum grauInstrucao;

	@JoinColumn(name = "naturalidade", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Municipio naturalidade;

	@Size(min = 12, max = 12)
	@Column(name = "titulo_eleitor")
	private String tituloEleitor;

	@Column(name = "nome_mae")
	private String nomeMae;

	@Column(name = "nome_pai")
	private String nomePai;

	private Integer cep;

	private String logradouro;

	@Column(name = "numero_logradouro")
	private String numeroLogradouro;

	@Column(name = "complemento_logradouro")
	private String complementoLogradouro;

	private String bairro;

	@JoinColumn(name = "municipio_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Municipio municipio;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_familia")
	private TipoFamiliaEnum tipoFamilia;

	@Enumerated(EnumType.STRING)
	@Column(name = "grau_parentesco")
	private GrauParentescoEnum grauParentesco;

	@Enumerated(EnumType.STRING)
	private MotivoPensionistaEnum motivo;

	@JoinColumn(name = "responsavel_legal_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private ResponsavelLegal responsavelLegal;

	@Column(name = "data_inicio_resp")
	private LocalDate dataInicioResponsavel;

	@Column(name = "data_vencimento_resp")
	private LocalDate dataVencimentoResponsavel;

	@Column(name = "numero_processo_resp")
	private Integer numeroProcessoResponsavel;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anexo_id", nullable = true)
	private Anexo anexo;

	@Size(min = 11, max = 11)
	@Column(name = "cpf")
	private String cpf;

	@Column(name = "identidade")
	private String identidade;

	@Column(name = "dt_expedicao_rg")
	private Instant dataExpedicaoRg;

	@Column(name = "tipo_sanguineo")
	private String tipoSanguineo;

	@Enumerated(EnumType.STRING)
	@Column(name = "cor_pele")
	private CorPeleEnum corPele;

	@JoinColumn(name = "nacionalidade_id", nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Nacionalidade nacionalidade;

	@Column(name = "email_pessoal")
	private String emailPessoal;

	@Column(name = "telefone_fixo")
	private String telefoneFixo;

	@Column(name = "celular")
	private String celular;

	@Column(name = "observacao")
	private String observacao;

	@OneToMany(mappedBy = "pensionista")
	private Set<PensionistaVerba> verbas;

	@Transient
	private LocalDate dataBaseRecadastramento;
	
	public Pensionista() {
	}

	public Pensionista(PensionistaRequest request) {
		// Dados pessoais
		this.id = request.getId();
		if (request.getFuncionarioId() != null) {

			this.funcionario = new Funcionario(request.getFuncionarioId());
		}
		this.pensaoPagamento = new PensaoPrevidenciariaPagamento(request.getPensaoPagamentoId());
		this.matricula = request.getMatricula();
		this.nome = request.getNome();
		this.dataNascimento = request.getDataNascimento();
		this.genero = GeneroEnum.getEnumByString(request.getGenero());
		this.estadoCivil = EstadoCivilEnum.getEnumByString(request.getEstadoCivil());
		this.status = request.isStatus();
		this.grauInstrucao = GrauInstrucaoEnum.getEnumByString(request.getGrauInstrucao());
		this.naturalidade = new Municipio(request.getNaturalidadeId());
		this.tituloEleitor = request.getTituloEleitor();
		this.nomeMae = request.getNomeMae();
		this.nomePai = request.getNomePai();
		this.cpf = request.getCpf();
		this.identidade = request.getIdentidade();
		this.dataExpedicaoRg = request.getDataExpedicaoRg();
		this.tipoSanguineo = request.getTipoSanguineo();
		if (Utils.checkStr(request.getCorPele()))
			this.setCorPele(CorPeleEnum.valueOf(request.getCorPele()));
		this.emailPessoal = request.getEmailPessoal();
		this.telefoneFixo = request.getTelefoneFixo();
		this.celular = request.getCelular();
		this.observacao = request.getObservacao();

		// Endereço
		this.cep = request.getCep();
		this.logradouro = request.getLogradouro();
		this.numeroLogradouro = request.getNumeroLogradouro();
		this.complementoLogradouro = request.getComplementoLogradouro();
		this.bairro = request.getBairro();
		if (Objects.nonNull(request.getMunicipioId()))
			this.municipio = new Municipio(request.getMunicipioId());

		if (Objects.nonNull(request.getNacionalidade()))
			this.nacionalidade = new Nacionalidade(request.getNacionalidade().getId());

		// Dependência
		if (Objects.nonNull(request.getTipoFamilia()))
			this.tipoFamilia = TipoFamiliaEnum.getEnumByString(request.getTipoFamilia());
		if (Objects.nonNull(request.getGrauParentesco()))
			this.grauParentesco = GrauParentescoEnum.getEnumByString(request.getGrauParentesco());
		if (Objects.nonNull(request.getMotivo()))
			this.motivo = MotivoPensionistaEnum.getEnumByString(request.getMotivo());

		// Responsável
		if (Objects.nonNull(request.getResponsavelLegalId()))
			this.responsavelLegal = new ResponsavelLegal(request.getResponsavelLegalId());

		this.dataInicioResponsavel = request.getDataInicioResponsavel();
		this.dataVencimentoResponsavel = request.getDataVencimentoResponsavel();
		this.numeroProcessoResponsavel = request.getNumeroProcessoResponsavel();
		if (Objects.nonNull(request.getAnexoId())) {
			this.anexo = new Anexo(request.getAnexoId());
		}
	}

	public Pensionista(Long pensaoId) {
		this.id = pensaoId;
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

	public PensaoPrevidenciariaPagamento getPensaoPagamento() {
		return pensaoPagamento;
	}

	public void setPensaoPagamento(PensaoPrevidenciariaPagamento pensaoPagamento) {
		this.pensaoPagamento = pensaoPagamento;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public GeneroEnum getGenero() {
		return genero;
	}

	public void setGenero(GeneroEnum genero) {
		this.genero = genero;
	}

	public EstadoCivilEnum getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public GrauInstrucaoEnum getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(GrauInstrucaoEnum grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public Municipio getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(Municipio naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getTituloEleitor() {
		return tituloEleitor;
	}

	public void setTituloEleitor(String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
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

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumeroLogradouro() {
		return numeroLogradouro;
	}

	public void setNumeroLogradouro(String numeroLogradouro) {
		this.numeroLogradouro = numeroLogradouro;
	}

	public String getComplementoLogradouro() {
		return complementoLogradouro;
	}

	public void setComplementoLogradouro(String complementoLogradouro) {
		this.complementoLogradouro = complementoLogradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public TipoFamiliaEnum getTipoFamilia() {
		return tipoFamilia;
	}

	public void setTipoFamilia(TipoFamiliaEnum tipoFamilia) {
		this.tipoFamilia = tipoFamilia;
	}

	public GrauParentescoEnum getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(GrauParentescoEnum grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	public MotivoPensionistaEnum getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoPensionistaEnum motivo) {
		this.motivo = motivo;
	}

	public ResponsavelLegal getResponsavelLegal() {
		return responsavelLegal;
	}

	public void setResponsavelLegal(ResponsavelLegal responsavelLegal) {
		this.responsavelLegal = responsavelLegal;
	}

	public LocalDate getDataInicioResponsavel() {
		return dataInicioResponsavel;
	}

	public void setDataInicioResponsavel(LocalDate dataInicioResponsavel) {
		this.dataInicioResponsavel = dataInicioResponsavel;
	}

	public LocalDate getDataVencimentoResponsavel() {
		return dataVencimentoResponsavel;
	}

	public void setDataVencimentoResponsavel(LocalDate dataVencimentoResponsavel) {
		this.dataVencimentoResponsavel = dataVencimentoResponsavel;
	}

	public Integer getNumeroProcessoResponsavel() {
		return numeroProcessoResponsavel;
	}

	public void setNumeroProcessoResponsavel(Integer numeroProcessoResponsavel) {
		this.numeroProcessoResponsavel = numeroProcessoResponsavel;
	}

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public Set<PensionistaVerba> getVerbas() {
		return verbas;
	}

	public void setVerbas(Set<PensionistaVerba> verbas) {
		this.verbas = verbas;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public Instant getDataExpedicaoRg() {
		return dataExpedicaoRg;
	}

	public void setDataExpedicaoRg(Instant dataExpedicaoRg) {
		this.dataExpedicaoRg = dataExpedicaoRg;
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

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getEmailPessoal() {
		return emailPessoal;
	}

	public void setEmailPessoal(String emailPessoal) {
		this.emailPessoal = emailPessoal;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
