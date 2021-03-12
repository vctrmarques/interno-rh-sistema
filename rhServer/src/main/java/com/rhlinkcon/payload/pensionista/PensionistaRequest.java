package com.rhlinkcon.payload.pensionista;

import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.PensaoPrevidenciariaPagamento;
import com.rhlinkcon.payload.DadoBasicoDto;

public class PensionistaRequest {

	private Long id;

	@NotNull
	private Long funcionarioId;

	private Long pensaoPagamentoId;

	@NotNull
	private Integer matricula;

	@NotNull
	private String nome;

	private boolean status;

	@NotNull
	private LocalDate dataNascimento;

	@NotNull
	private String genero;

	@NotNull
	private String estadoCivil;

	@NotNull
	private String grauInstrucao;

	@NotNull
	private Long naturalidadeId;

	private String tituloEleitor;

	private String nomeMae;

	private String nomePai;

	private Integer cep;

	private String logradouro;

	private String numeroLogradouro;

	private String complementoLogradouro;

	private String bairro;

	@NotNull
	private Long municipioId;

	private String tipoFamilia;

	private String grauParentesco;

	private String motivo;

	private Long responsavelLegalId;

	private LocalDate dataInicioResponsavel;

	private LocalDate dataVencimentoResponsavel;

	private Integer numeroProcessoResponsavel;

	private Long anexoId;

	private String cpf;

	private String identidade;

	private Instant dataExpedicaoRg;

	private String tipoSanguineo;

	private String corPele;

	private DadoBasicoDto nacionalidade;

	private String emailPessoal;

	private String telefoneFixo;

	private String celular;

	private String observacao;
	
	private PensaoPrevidenciariaPagamento pensaoPagamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getPensaoPagamentoId() {
		return pensaoPagamentoId;
	}

	public void setPensaoPagamentoId(Long pensaoPagamentoId) {
		this.pensaoPagamentoId = pensaoPagamentoId;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public Long getNaturalidadeId() {
		return naturalidadeId;
	}

	public void setNaturalidadeId(Long naturalidadeId) {
		this.naturalidadeId = naturalidadeId;
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

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
	}

	public String getTipoFamilia() {
		return tipoFamilia;
	}

	public void setTipoFamilia(String tipoFamilia) {
		this.tipoFamilia = tipoFamilia;
	}

	public String getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(String grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Long getResponsavelLegalId() {
		return responsavelLegalId;
	}

	public void setResponsavelLegalId(Long responsavelLegalId) {
		this.responsavelLegalId = responsavelLegalId;
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

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
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

	public String getCorPele() {
		return corPele;
	}

	public void setCorPele(String corPele) {
		this.corPele = corPele;
	}

	public DadoBasicoDto getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(DadoBasicoDto nacionalidade) {
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

	public PensaoPrevidenciariaPagamento getPensaoPagamento() {
		return pensaoPagamento;
	}

	public void setPensaoPagamento(PensaoPrevidenciariaPagamento pensaoPagamento) {
		this.pensaoPagamento = pensaoPagamento;
	}

}
