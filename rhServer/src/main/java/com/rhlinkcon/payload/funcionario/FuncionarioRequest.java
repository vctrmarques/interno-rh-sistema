package com.rhlinkcon.payload.funcionario;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.payload.DadoBasicoDto;

public class FuncionarioRequest {

	// Início aba Dados do Contrato
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	private Integer matricula;

	@NotNull
	private Long empresaId;

	@NotNull
	private Long filialId;

	@NotNull
	private Instant dataNascimento;
	
	private int rowNum;

	@NotNull
	private char sexo;

	private String estadoCivil;

	private String grauInstrucao;

	private DadoBasicoDto nacionalidade;

	private DadoBasicoDto naturalidade;
	
	private Long naturalidadeUf;

	private Long municipioId;

	private String conhecidoComo;

	private String tipoSanguineo;

	private String corPele;

	@NotNull
	private String nomeMae;

	private String nomePai;

	private Long centroCustoId;

	// Fim aba Dados do Contrato

	// inicio aba Contato & Endereço

	private Boolean estrangeiro;

	private Boolean naturalizado;

	private Long registroEstrangeiroUfId;

	private Long municipioRegistroEstrangeiroId;

	private Boolean casamentoBrasileiro;

	private Boolean filhoBrasileiro;

	@NotNull
	private String logradouro;

	@NotNull
	private String numero;

	private String complemento;

	@NotNull
	private String bairro;

	@NotNull
	private Long ufId;

	@NotNull
	private String cep;

	private String emailPessoal;

	private String emailCorporativo;

	private String telefonePrincipal;

	private String telefoneOpcional;

	private String telefoneComercial;

	// Fim aba Contato & Endereço
	// Início aba Documentação

	@NotNull
	private String identidade;

	@NotNull
	private String orgaoExpeditor;

	@NotNull
	private Long ufOrgaoExpeditorId;

	@NotNull
	private Instant dataExpedicaoRg;

	private String numeroCtps;

	private String serieCtps;

	private Long ufCtpsId;

	@NotNull
	@Size(min = 11, max = 11)
	private String cpf;

	@NotNull
	private String pisPasep;

	private Instant dataEmissaoPisPasep;

	private Integer agenciaPisPasep;

	@Size(min = 12, max = 12)
	private String tituloEleitor;

	private Integer zona;

	private Integer secao;

	private Long ufTituloEleitorId;

	private String cnh;

	private Instant dataValidadeCnh;

	private String categoriaCnh;

	private Integer registroAlistamento;

	private String categoriaAlistamento;

	private Long classificacaoAtoId;

	private Integer numeroProcesso;

	private Integer numeroAto;

	private Instant dataNomeacao;

	private Integer numeroDiarioOficial;

	private Instant dataPublicacaoDiarioOficial;

	private String numeroSus;

	// Fim Aba Documentação
	// Início Aba Dados do Pagamento

	@NotNull
	private Long vinculoId;

	@NotNull
	private Instant dataAdmissao;

	private Integer numeroDependentesImpostoRenda;

	private Integer numeroDependentesSalarioFamilia;

	@NotNull
	private Long funcaoId;

	private Long faixaSalarialFuncaoId;

	private Long referenciaSalarialFuncaoId;

	private Long cargoId;

	private Long faixaSalarialCargoId;

	private Long referenciaSalarialCargoId;

	private Boolean opcaoFgts;

	private Instant dataOpcaoFgts;

	private Integer agenciaFgts;

	private Integer contaFgts;

	private Instant dataExercicioAdmissaoAts;

	private String tipoAdicionalTempoServico;

	private Boolean estabilidade;

	private Long municipioTrabalhoId;

	private Long lotacaoId;

	@NotNull
	private Long bancoId;
	
	private Long agenciaBancariaId;

	@NotNull
	private String agencia;

	@NotNull
	private String tipoConta;

	@NotNull
	private String numeroConta;

	@NotNull
	private String digitoConta;

	private String operacao;

	@NotNull
	private Boolean probatorio;

	@NotNull
	private Long jornadaTrabalhoId;

	private String cargaHoraria;

	private Long sindicatoId;

	private String tipoFrequencia;

	private Long tipoSituacaoFuncionalId;

	private Long motivoAfastamentoId;

	private Instant dataInicioSituacaoFuncional;

	private Instant dataFimSituacaoFuncional;

	private Instant inicioContratoTemporario;

	private Instant fimContratoTemporario;

	private Integer diasAfastado;

	private Integer matriculaDestino;

	private String cessaoEmpresaDestino;

	private Long ufTrabalhoId;

	private String tipoEstabilidade;

	private List<FuncionarioValeTransporteRequest> valesTransporte;

	private String matriculaVinculo;

	private String descricaoVinculo;

	private Double valorVinculo;

	// Fim aba de Dados do Pagamento

	private List<Long> verbasId;

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

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getFilialId() {
		return filialId;
	}

	public void setFilialId(Long filialId) {
		this.filialId = filialId;
	}

	public Long getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Long municipioId) {
		this.municipioId = municipioId;
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

	public String getCorPele() {
		return corPele;
	}

	public void setCorPele(String corPele) {
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

	public Long getRegistroEstrangeiroUfId() {
		return registroEstrangeiroUfId;
	}

	public void setRegistroEstrangeiroUfId(Long registroEstrangeiroUfId) {
		this.registroEstrangeiroUfId = registroEstrangeiroUfId;
	}

	public Long getMunicipioRegistroEstrangeiroId() {
		return municipioRegistroEstrangeiroId;
	}

	public void setMunicipioRegistroEstrangeiroId(Long municipioRegistroEstrangeiroId) {
		this.municipioRegistroEstrangeiroId = municipioRegistroEstrangeiroId;
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

	public Long getUfId() {
		return ufId;
	}

	public void setUfId(Long ufId) {
		this.ufId = ufId;
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

	public Long getUfOrgaoExpeditorId() {
		return ufOrgaoExpeditorId;
	}

	public void setUfOrgaoExpeditorId(Long ufOrgaoExpeditorId) {
		this.ufOrgaoExpeditorId = ufOrgaoExpeditorId;
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

	public Long getUfCtpsId() {
		return ufCtpsId;
	}

	public void setUfCtpsId(Long ufCtpsId) {
		this.ufCtpsId = ufCtpsId;
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

	public Long getUfTituloEleitorId() {
		return ufTituloEleitorId;
	}

	public void setUfTituloEleitorId(Long ufTituloEleitorId) {
		this.ufTituloEleitorId = ufTituloEleitorId;
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

	public String getCategoriaCnh() {
		return categoriaCnh;
	}

	public void setCategoriaCnh(String categoriaCnh) {
		this.categoriaCnh = categoriaCnh;
	}

	public Integer getRegistroAlistamento() {
		return registroAlistamento;
	}

	public void setRegistroAlistamento(Integer registroAlistamento) {
		this.registroAlistamento = registroAlistamento;
	}

	public Long getClassificacaoAtoId() {
		return classificacaoAtoId;
	}

	public void setClassificacaoAtoId(Long classificacaoAtoId) {
		this.classificacaoAtoId = classificacaoAtoId;
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

	public Long getVinculoId() {
		return vinculoId;
	}

	public void setVinculoId(Long vinculoId) {
		this.vinculoId = vinculoId;
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

	public String getTipoAdicionalTempoServico() {
		return tipoAdicionalTempoServico;
	}

	public void setTipoAdicionalTempoServico(String tipoAdicionalTempoServico) {
		this.tipoAdicionalTempoServico = tipoAdicionalTempoServico;
	}

	public Boolean getEstabilidade() {
		return estabilidade;
	}

	public void setEstabilidade(Boolean estabilidade) {
		this.estabilidade = estabilidade;
	}

	public Long getMunicipioTrabalhoId() {
		return municipioTrabalhoId;
	}

	public void setMunicipioTrabalhoId(Long municipioTrabalhoId) {
		this.municipioTrabalhoId = municipioTrabalhoId;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
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

	public Long getJornadaTrabalhoId() {
		return jornadaTrabalhoId;
	}

	public void setJornadaTrabalhoId(Long jornadaTrabalhoId) {
		this.jornadaTrabalhoId = jornadaTrabalhoId;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Long getSindicatoId() {
		return sindicatoId;
	}

	public void setSindicatoId(Long sindicatoId) {
		this.sindicatoId = sindicatoId;
	}

	public String getTipoFrequencia() {
		return tipoFrequencia;
	}

	public void setTipoFrequencia(String tipoFrequencia) {
		this.tipoFrequencia = tipoFrequencia;
	}

	public Long getTipoSituacaoFuncionalId() {
		return tipoSituacaoFuncionalId;
	}

	public void setTipoSituacaoFuncionalId(Long tipoSituacaoFuncionalId) {
		this.tipoSituacaoFuncionalId = tipoSituacaoFuncionalId;
	}

	public Long getMotivoAfastamentoId() {
		return motivoAfastamentoId;
	}

	public void setMotivoAfastamentoId(Long motivoAfastamentoId) {
		this.motivoAfastamentoId = motivoAfastamentoId;
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

	public Long getFuncaoId() {
		return funcaoId;
	}

	public void setFuncaoId(Long funcaoId) {
		this.funcaoId = funcaoId;
	}

	public Long getCentroCustoId() {
		return centroCustoId;
	}

	public void setCentroCustoId(Long centroCustoId) {
		this.centroCustoId = centroCustoId;
	}

	public List<FuncionarioValeTransporteRequest> getValesTransporte() {
		return valesTransporte;
	}

	public void setValesTransporte(List<FuncionarioValeTransporteRequest> valesTransporte) {
		this.valesTransporte = valesTransporte;
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

	public void setTelefoneOpcional(String telefoneOpcional) {
		this.telefoneOpcional = telefoneOpcional;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public Long getUfTrabalhoId() {
		return ufTrabalhoId;
	}

	public void setUfTrabalhoId(Long ufTrabalhoId) {
		this.ufTrabalhoId = ufTrabalhoId;
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

	public String getTipoEstabilidade() {
		return tipoEstabilidade;
	}

	public void setTipoEstabilidade(String tipoEstabilidade) {
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

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public Long getFaixaSalarialFuncaoId() {
		return faixaSalarialFuncaoId;
	}

	public void setFaixaSalarialFuncaoId(Long faixaSalarialFuncaoId) {
		this.faixaSalarialFuncaoId = faixaSalarialFuncaoId;
	}

	public Long getReferenciaSalarialFuncaoId() {
		return referenciaSalarialFuncaoId;
	}

	public void setReferenciaSalarialFuncaoId(Long referenciaSalarialFuncaoId) {
		this.referenciaSalarialFuncaoId = referenciaSalarialFuncaoId;
	}

	public Long getFaixaSalarialCargoId() {
		return faixaSalarialCargoId;
	}

	public void setFaixaSalarialCargoId(Long faixaSalarialCargoId) {
		this.faixaSalarialCargoId = faixaSalarialCargoId;
	}

	public Long getReferenciaSalarialCargoId() {
		return referenciaSalarialCargoId;
	}

	public void setReferenciaSalarialCargoId(Long referenciaSalarialCargoId) {
		this.referenciaSalarialCargoId = referenciaSalarialCargoId;
	}

	public DadoBasicoDto getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(DadoBasicoDto nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public DadoBasicoDto getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(DadoBasicoDto naturalidade) {
		this.naturalidade = naturalidade;
	}

	public Long getAgenciaBancariaId() {
		return agenciaBancariaId;
	}

	public void setAgenciaBancariaId(Long agenciaBancariaId) {
		this.agenciaBancariaId = agenciaBancariaId;
	}

	public Long getNaturalidadeUf() {
		return naturalidadeUf;
	}

	public void setNaturalidadeUf(Long naturalidadeUf) {
		this.naturalidadeUf = naturalidadeUf;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	

}
