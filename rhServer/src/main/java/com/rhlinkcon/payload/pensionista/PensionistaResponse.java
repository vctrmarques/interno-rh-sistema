package com.rhlinkcon.payload.pensionista;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.pensionistaVerba.PensionistaVerbaResponse;
import com.rhlinkcon.payload.responsavelLegal.ResponsavelLegalResponse;
import com.rhlinkcon.util.Projecao;

public class PensionistaResponse extends DadoCadastralResponse {

	private Long id;

	private FuncionarioResponse funcionario;

	private String FuncionarioNome;

	private PensaoPrevidenciariaPagamentoResponse pensaoPagamento;

	private Integer matricula;

	private String nome;

	private LocalDate dataNascimento;

	private String genero;

	private String estadoCivil;

	private String grauInstrucao;

	private MunicipioResponse naturalidade;

	private String tituloEleitor;

	private String nomeMae;

	private String nomePai;

	private Integer cep;

	private String logradouro;

	private String numeroLogradouro;

	private String complementoLogradouro;

	private String bairro;

	private MunicipioResponse municipio;

	private String tipoFamilia;

	private String grauParentesco;

	private String motivo;

	private ResponsavelLegalResponse responsavelLegal;

	private LocalDate dataInicioResponsavel;

	private LocalDate dataVencimentoResponsavel;

	private Integer numeroProcessoResponsavel;

	private AnexoResponse anexo;

	private boolean status;

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

	private List<PensionistaVerbaResponse> verbas;

	public PensionistaResponse() {
	}

	public PensionistaResponse(Pensionista response, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = response.getId();
			this.funcionario = new FuncionarioResponse(response.getFuncionario(), Projecao.BASICA);
			this.matricula = response.getMatricula();
			this.nome = response.getNome();
			this.dataNascimento = response.getDataNascimento();
			this.genero = response.getGenero().name();
			this.estadoCivil = response.getEstadoCivil().name();
			this.grauInstrucao = response.getGrauInstrucao().name();
			this.naturalidade = new MunicipioResponse(response.getNaturalidade());
			this.tituloEleitor = response.getTituloEleitor();
			this.nomeMae = response.getNomeMae();
			this.nomePai = response.getNomePai();
			this.cep = response.getCep();
			this.logradouro = response.getLogradouro();
			this.numeroLogradouro = response.getNumeroLogradouro();
			this.complementoLogradouro = response.getComplementoLogradouro();
			this.bairro = response.getBairro();
			this.status = response.isStatus();

			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				this.cpf = response.getCpf();
				this.identidade = response.getIdentidade();
				this.dataExpedicaoRg = response.getDataExpedicaoRg();
				this.tipoSanguineo = response.getTipoSanguineo();
				this.emailPessoal = response.getEmailPessoal();
				this.telefoneFixo = response.getTelefoneFixo();
				this.celular = response.getCelular();
				this.observacao = response.getObservacao();

				if (Objects.nonNull(response.getNacionalidade()))
					this.nacionalidade = new DadoBasicoDto(response.getNacionalidade());
				if (response.getCorPele() != null)
					this.setCorPele(response.getCorPele().toString());

				if (Objects.nonNull(response.getMunicipio()))
					this.municipio = new MunicipioResponse(response.getMunicipio());
				if (Objects.nonNull(response.getTipoFamilia()))
					this.tipoFamilia = response.getTipoFamilia().name();
				if (Objects.nonNull(response.getGrauParentesco())) {
					this.grauParentesco = response.getGrauParentesco().name();
				}
				if (Objects.nonNull(response.getMotivo()))
					this.motivo = response.getMotivo().name();
				if (Objects.nonNull(response.getResponsavelLegal())) {
					this.responsavelLegal = new ResponsavelLegalResponse(response.getResponsavelLegal());
				}
				this.dataInicioResponsavel = response.getDataInicioResponsavel();
				this.dataVencimentoResponsavel = response.getDataVencimentoResponsavel();
				this.numeroProcessoResponsavel = response.getNumeroProcessoResponsavel();

				this.pensaoPagamento = new PensaoPrevidenciariaPagamentoResponse(response.getPensaoPagamento());
			}

			if (projecao.equals(Projecao.COMPLETA)) {
				if (Objects.nonNull(response.getAnexo())) {
					this.setAnexo(new AnexoResponse(response.getAnexo()));
				}
			}
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public PensaoPrevidenciariaPagamentoResponse getPensaoPagamento() {
		return pensaoPagamento;
	}

	public void setPensaoPagamento(PensaoPrevidenciariaPagamentoResponse pensaoPagamento) {
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

	public MunicipioResponse getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(MunicipioResponse naturalidade) {
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

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
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

	public ResponsavelLegalResponse getResponsavelLegal() {
		return responsavelLegal;
	}

	public void setResponsavelLegal(ResponsavelLegalResponse responsavelLegal) {
		this.responsavelLegal = responsavelLegal;
	}

	public LocalDate getDataInicioResposavel() {
		return dataInicioResponsavel;
	}

	public void setDataInicioResposavel(LocalDate dataInicioResposavel) {
		this.dataInicioResponsavel = dataInicioResposavel;
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

	public AnexoResponse getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexoResponse anexo) {
		this.anexo = anexo;
	}

	public List<PensionistaVerbaResponse> getVerbas() {
		return verbas;
	}

	public void setVerbas(List<PensionistaVerbaResponse> verbas) {
		this.verbas = verbas;
	}

	public LocalDate getDataInicioResponsavel() {
		return dataInicioResponsavel;
	}

	public void setDataInicioResponsavel(LocalDate dataInicioResponsavel) {
		this.dataInicioResponsavel = dataInicioResponsavel;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getFuncionarioNome() {
		return FuncionarioNome;
	}

	public void setFuncionarioNome(String funcionarioNome) {
		FuncionarioNome = funcionarioNome;
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

}
