package com.rhlinkcon.payload.funcionario;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ExperienciaProfissional;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.acidenteTrabalho.AcidenteTrabalhoResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.experienciaProfissional.ExperienciaProfissionalResponse;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoResponse;
import com.rhlinkcon.payload.frequencia.FrequenciaResponse;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.funcionarioExercicio.FuncionarioExercicioResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.pensaoAlimenticia.PensaoAlimenticiaDto;
import com.rhlinkcon.payload.processo.ProcessoResponse;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;
import com.rhlinkcon.payload.tempoServico.TempoServicoResponse;
import com.rhlinkcon.payload.turno.TurnoResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuncionarioResponse extends DadoCadastralResponse {

	// Início aba Dados do Contrato
	private Long id;

	private String nome;

	private Integer matricula;

	private EmpresaFilialResponse empresa;

	private EmpresaFilialResponse filial;

	private Instant dataNascimento;

	private char sexo;

	private String estadoCivil;

	private String grauInstrucao;

	private DadoBasicoDto nacionalidade;

	private DadoBasicoDto naturalidade;

	private Long municipioId;

	private MunicipioResponse municipio;

	private String conhecidoComo;

	private String tipoSanguineo;

	private String corPele;

	private String nomeMae;

	private String nomePai;

	private CentroCustoResponse centroCusto;

	// Fim aba Dados do Contrato

	// inicio aba Contato & Endereço

	private Boolean estrangeiro;

	private Boolean naturalizado;

	private Long registroEstrangeiroUfId;

	private Long municipioRegistroEstrangeiroId;

	private Boolean casamentoBrasileiro;

	private Boolean filhoBrasileiro;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private Long ufId;

	private String cep;

	private String emailPessoal;

	private String emailCorporativo;

	private String telefonePrincipal;

	private String telefoneOpcional;

	private String telefoneComercial;

	// Fim aba Contato & Endereço

	// Início aba Documentação

	private String identidade;

	private String orgaoExpeditor;

	private Long ufOrgaoExpeditorId;

	private Instant dataExpedicaoRg;

	private String numeroCtps;

	private String serieCtps;

	private Long ufCtpsId;

	private String cpf;

	private String pisPasep;

	private Instant dataEmissaoPisPasep;

	private Integer agenciaPisPasep;

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

	private ClassificacaoAtoResponse classificacaoAto;

	private Integer numeroProcesso;

	private Integer numeroAto;

	private Instant dataNomeacao;

	private Integer numeroDiarioOficial;

	private Instant dataPublicacaoDiarioOficial;

	private String numeroSus;

	// Fim Aba Documentação
	// Incício Aba de Dados do Pagamento

	private Long vinculoId;

	private Instant dataAdmissao;

	private Integer numeroDependentesImpostoRenda;

	private Integer numeroDependentesSalarioFamilia;

	private FuncaoResponse funcao;

	private CargoResponse cargo;

	private Long faixaSalarialCargoId;

	private Long referenciaSalarialCargoId;

	private Long faixaSalarialFuncaoId;

	private Long referenciaSalarialFuncaoId;

	private TurnoResponse jornadaTrabalho;

	private Boolean opcaoFgts;

	private Instant dataOpcaoFgts;

	private Integer agenciaFgts;

	private Integer contaFgts;

	private Instant dataExercicioAdmissaoAts;

	private String tipoAdicionalTempoServico;

	private Boolean estabilidade;

	private Long municipioTrabalhoId;

	private Long lotacaoId;

	private DadoBasicoDto banco;

	private DadoBasicoDto agenciaBancaria;

	private String agencia;

	private String tipoConta;

	private String numeroConta;

	private String digitoConta;

	private String operacao;

	private Boolean probatorio;

	private Long jornadaTrabalhoId;

	private String cargaHoraria;

	private Long sindicatoId;

	private String tipoFrequencia;

	private Long tipoSituacaoFuncionalId;

	private SituacaoFuncionalResponse situacaoFuncional;

	private Long motivoAfastamentoId;

	private Instant dataInicioSituacaoFuncional;

	private Instant dataFimSituacaoFuncional;

	private Instant inicioContratoTemporario;

	private Instant fimContratoTemporario;

	private Integer diasAfastado;

	private String cessaoEmpresaDestino;

	private Integer matriculaDestino;

	private List<ProcessoResponse> processos;

	private List<PensaoAlimenticiaDto> pensoes;

	private List<FuncionarioValeTransporteResponse> valesTransporte;

	private Long ufTrabalhoId;

	private String tipoEstabilidade;

	private String matriculaVinculo;

	private String descricaoVinculo;

	private Double valorVinculo;

	// Fim Aba de Dados do Pagamento

	private Integer quantDependentes;

	private List<ExperienciaProfissionalResponse> experienciasProfissionais;

	private Integer quantAnexos;

	private List<AnexoResponse> anexos;

	private List<TempoServicoResponse> tempoServico;

	private List<FuncionarioExercicioResponse> exercicios;

	private List<AcidenteTrabalhoResponse> acidentesDeTrabalho;

	private List<FrequenciaResponse> frequencias;

	private LotacaoResponse lotacao;

	private List<FeriasProgramacaoResponse> feriasProgramadas;

	private List<VerbaResponse> verbas;

	private List<FuncionarioVerbaResponse> verbasFuncionario;

	private String municipioNome;
	private String ufNome;
	private String ufCtpsNome;
	private String tipoContratoNome;
	private ReferenciaSalarialResponse referenciaSalarialResponse;
	private VinculoResponse vinculo;

	/**
	 * @author Davi Construtor genérico do funcionário
	 */
	public FuncionarioResponse() {
	}

	/**
	 * @author Davi
	 * @param id
	 * @param matricula
	 * @param empresa
	 * @param experienciasProfissionais
	 * @param nome
	 * 
	 *                                  construtor para experiência profissional do
	 *                                  funcionário
	 */
	public FuncionarioResponse(Long id, Integer matricula, EmpresaFilialResponse empresa,
			Set<ExperienciaProfissional> experienciasProfissionais, String nome) {
		this.id = id;
		this.nome = nome;
		this.empresa = empresa;
		this.matricula = matricula;
		addExperienciasProfissionais(experienciasProfissionais);
	}

	/**
	 * @author Davi
	 * @param funcionario
	 * 
	 *                    construtor com todas as informações para funcionário
	 */
	public FuncionarioResponse(Funcionario funcionario) {
		setFuncionario(funcionario);
	}

	/**
	 * @author Rodrigo
	 * @param id
	 * @param matricula
	 * @param empresa
	 * @param nome
	 * 
	 *                  construtor para pensao previdenciaria
	 */

	public FuncionarioResponse(Long id, Integer matricula, EmpresaFilialResponse empresa, String nome) {
		this.id = id;
		this.nome = nome;
		this.empresa = empresa;
		this.matricula = matricula;
	}

	/**
	 * @author Davi
	 * @param id
	 * @param nome
	 * 
	 *             construtor para frequência do funcionário
	 */
	public FuncionarioResponse(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	/**
	 * @author Davi
	 * @param funcionario
	 * @param pensoes
	 * 
	 *                    construtor para pensão alimentícia do funcionário
	 */
	public FuncionarioResponse(Funcionario funcionario, List<PensaoAlimenticiaDto> pensoes) {
		this.id = funcionario.getId();
		this.nome = funcionario.getNome();
		this.matricula = funcionario.getMatricula();
		this.empresa = new EmpresaFilialResponse(funcionario.getEmpresa());
		this.pensoes = pensoes;
	}

	/**
	 * @author Marconi Motta
	 * @param funcionario construtor para requisicao de pessoal
	 * 
	 */
	public FuncionarioResponse(Funcionario funcionario, Lotacao lotacao) {
		this.id = funcionario.getId();
		this.nome = funcionario.getNome();
		this.matricula = funcionario.getMatricula();
		this.empresa = new EmpresaFilialResponse(funcionario.getEmpresa());
		this.lotacao = new LotacaoResponse(lotacao, Projecao.BASICA);
	}

	/**
	 * 
	 * @param funcionario
	 * @param lotacao
	 * @param vinculo     Construtor utilizado na funcionalidade de Ficha Financeira
	 */
	public FuncionarioResponse(Funcionario funcionario, Lotacao lotacao, Vinculo vinculo) {
		this.id = funcionario.getId();
		this.nome = funcionario.getNome();
		this.dataAdmissao = funcionario.getDataAdmissao();
		this.filial = new EmpresaFilialResponse(funcionario.getFilial(), Projecao.BASICA);
		this.matricula = funcionario.getMatricula();
		this.empresa = new EmpresaFilialResponse(funcionario.getEmpresa());
		this.vinculo = new VinculoResponse(vinculo, Projecao.BASICA);
		this.setSituacaoFuncional(new SituacaoFuncionalResponse(funcionario.getTipoSituacaoFuncional()));
		this.setTipoSituacaoFuncionalId(funcionario.getTipoSituacaoFuncional().getId());
		if (Objects.nonNull(funcionario.getLotacao())) {
			this.lotacao = new LotacaoResponse(funcionario.getLotacao(), Projecao.BASICA);
		}
		if (Objects.nonNull(funcionario.getFuncao()))
			this.setFuncao(new FuncaoResponse(funcionario.getFuncao(), Projecao.BASICA));
		if (Objects.nonNull(funcionario.getCargo()))
			this.setCargo(new CargoResponse(funcionario.getCargo(), Projecao.BASICA));
	}

	/**
	 * @author Davi
	 * @param id
	 * @param nome
	 * @param matricula
	 * @param processos
	 * 
	 *                  construtor para processos do funcionário
	 */
	public FuncionarioResponse(Long id, String nome, Integer matricula, List<ProcessoResponse> processos) {
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.processos = processos;
	}

	/**
	 * @author Davi
	 * @param funcionario
	 * @param criadoEm
	 * @param criadoPor
	 * @param alteradoEm
	 * @param alteradoPor
	 * 
	 *                    construtor para detalhes de funcionário
	 */
	public FuncionarioResponse(Funcionario funcionario, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setFuncionario(funcionario);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	/**
	 * @author Rodrigo
	 * @param funcionario
	 * @param projecao
	 * 
	 *                    construtor para modulo previdenciario
	 */
	public FuncionarioResponse(Funcionario funcionario, Projecao projecao) {

		this.id = funcionario.getId();
		this.nome = funcionario.getNome();
		this.matricula = funcionario.getMatricula();
		this.setCpf(funcionario.getCpf());
		this.setPisPasep(funcionario.getPisPasep());
		if (Objects.nonNull(funcionario.getTipoSituacaoFuncional())) {
			this.setSituacaoFuncional(new SituacaoFuncionalResponse(funcionario.getTipoSituacaoFuncional()));
			this.setTipoSituacaoFuncionalId(funcionario.getTipoSituacaoFuncional().getId());
		}
		if (projecao.equals(Projecao.MEDIA)) {
			if (Objects.nonNull(funcionario.getLotacao())) {
				this.lotacao = new LotacaoResponse(funcionario.getLotacao(), Projecao.BASICA);
			}
		}

		if (projecao.equals(Projecao.CARGO)) {
			if (Objects.nonNull(funcionario.getCargo())) {
				this.cargo = new CargoResponse(funcionario.getCargo(), Projecao.BASICA);
			} else {
				this.cargo = new CargoResponse();
			}
		}

		if (projecao.equals(Projecao.APOSENTADORIA)) {
			this.sexo = funcionario.getSexo();
			this.identidade = funcionario.getIdentidade();
			this.cpf = funcionario.getCpf();
			this.pisPasep = funcionario.getPisPasep();
			this.dataNascimento = funcionario.getDataNascimento();
			this.nomeMae = funcionario.getNomeMae();
			this.nomePai = funcionario.getNomePai();
			this.empresa = new EmpresaFilialResponse(funcionario.getEmpresa(), Projecao.APOSENTADORIA);
			if (Objects.nonNull(funcionario.getLotacao())) {
				this.lotacao = new LotacaoResponse(funcionario.getLotacao(), Projecao.BASICA);
			}
			if (Objects.nonNull(funcionario.getTipoSituacaoFuncional())) {
				this.situacaoFuncional = new SituacaoFuncionalResponse(funcionario.getTipoSituacaoFuncional());
				this.tipoSituacaoFuncionalId = funcionario.getTipoSituacaoFuncional().getId();
			}
		}

		if (projecao.equals(Projecao.CERTIDAO_COMPENSACAO)) {
			this.sexo = funcionario.getSexo();
			this.identidade = funcionario.getIdentidade();
			this.cpf = funcionario.getCpf();
			this.pisPasep = funcionario.getPisPasep();
			this.dataNascimento = funcionario.getDataNascimento();
			this.nomeMae = funcionario.getNomeMae();
			this.nomePai = funcionario.getNomePai();
			this.empresa = new EmpresaFilialResponse(funcionario.getEmpresa(), Projecao.CERTIDAO_COMPENSACAO);
			if (Objects.nonNull(funcionario.getNumeroCtps())) {
				this.numeroCtps = funcionario.getNumeroCtps();
			} else {
				this.numeroCtps = "";
			}
			if (Objects.nonNull(funcionario.getSerieCtps())) {
				this.serieCtps = funcionario.getSerieCtps();
			} else {
				this.serieCtps = "";
			}
			if (Objects.nonNull(funcionario.getLotacao())) {
				this.lotacao = new LotacaoResponse(funcionario.getLotacao(), Projecao.BASICA);
			}
			if (Objects.nonNull(funcionario.getTipoSituacaoFuncional())) {
				this.tipoSituacaoFuncionalId = funcionario.getTipoSituacaoFuncional().getId();
			}
			if (Objects.nonNull(funcionario.getFuncao())) {
				this.funcao = new FuncaoResponse(funcionario.getFuncao(), Projecao.BASICA);
			}
		}

		if (projecao.equals(Projecao.CERTIDAO_EXSEGURADO)) {
			this.sexo = funcionario.getSexo();
			this.identidade = funcionario.getIdentidade();
			this.orgaoExpeditor = funcionario.getOrgaoExpeditor();
			this.dataExpedicaoRg = funcionario.getDataExpedicaoRg();
			this.ufOrgaoExpeditorId = funcionario.getUfOrgaoExpeditor().getId();
			this.cpf = funcionario.getCpf();
			this.pisPasep = funcionario.getPisPasep();
			this.dataNascimento = funcionario.getDataNascimento();
			this.nomeMae = funcionario.getNomeMae();
			this.nomePai = funcionario.getNomePai();
			this.logradouro = funcionario.getLogradouro();
			this.numero = funcionario.getNumero();
			this.complemento = funcionario.getComplemento();
			this.bairro = funcionario.getBairro();
			this.cep = funcionario.getCep();
			this.empresa = new EmpresaFilialResponse(funcionario.getEmpresa(), Projecao.CERTIDAO_COMPENSACAO);
			this.dataAdmissao = funcionario.getDataAdmissao();

			if (Objects.nonNull(funcionario.getMunicipio())) {
				this.municipio = new MunicipioResponse(funcionario.getMunicipio());
			}
			if (Objects.nonNull(funcionario.getNumeroCtps())) {
				this.numeroCtps = funcionario.getNumeroCtps();
			} else {
				this.numeroCtps = "";
			}
			if (Objects.nonNull(funcionario.getSerieCtps())) {
				this.serieCtps = funcionario.getSerieCtps();
			} else {
				this.serieCtps = "";
			}
			if (Objects.nonNull(funcionario.getLotacao())) {
				this.lotacao = new LotacaoResponse(funcionario.getLotacao(), Projecao.BASICA);
			}
			if (Objects.nonNull(funcionario.getTipoSituacaoFuncional())) {
				this.situacaoFuncional = new SituacaoFuncionalResponse(funcionario.getTipoSituacaoFuncional());
				this.tipoSituacaoFuncionalId = funcionario.getTipoSituacaoFuncional().getId();
			}
			if (Objects.nonNull(funcionario.getFuncao())) {
				this.funcao = new FuncaoResponse(funcionario.getFuncao(), Projecao.BASICA);
			}
		}
	}

	/**
	 * @author Davi
	 * @param funcionario
	 * 
	 *                    método para edição e detalhes do funcionário
	 */
	public void setFuncionario(Funcionario funcionario) {

		// aba Dados do Contrato
		this.setId(funcionario.getId());
		this.setNome(funcionario.getNome());
		this.setMatricula(funcionario.getMatricula());
		this.setEmpresa(new EmpresaFilialResponse(funcionario.getEmpresa()));
		this.setFilial(new EmpresaFilialResponse(funcionario.getFilial()));

		if (Objects.nonNull(funcionario.getNacionalidade()))
			this.setNacionalidade(new DadoBasicoDto(funcionario.getNacionalidade()));

		if (Objects.nonNull(funcionario.getNaturalidade()))
			this.setNaturalidade(new DadoBasicoDto(funcionario.getNaturalidade()));

		if (Objects.nonNull(funcionario.getLotacao()))
			this.setLotacao(new LotacaoResponse(funcionario.getLotacao()));

		if (Objects.nonNull(funcionario.getCentroCusto()))
			this.setCentroCusto(new CentroCustoResponse(funcionario.getCentroCusto()));

		this.setDataNascimento(funcionario.getDataNascimento());
		this.setSexo(funcionario.getSexo());

		if (funcionario.getEstadoCivil() != null)
			this.setEstadoCivil(funcionario.getEstadoCivil().toString());

		if (funcionario.getGrauInstrucao() != null)
			this.setGrauInstrucao(funcionario.getGrauInstrucao().toString());

		if (Objects.nonNull(funcionario.getMunicipio()))
			this.setMunicipioId(funcionario.getMunicipio().getId());

		if (funcionario.getCorPele() != null)
			this.setCorPele(funcionario.getCorPele().toString());

		this.setConhecidoComo(funcionario.getConhecidoComo());
		this.setTipoSanguineo(funcionario.getTipoSanguineo());
		this.setNomeMae(funcionario.getNomeMae());
		this.setNomePai(funcionario.getNomePai());

		// aba Contato & Endereço
		this.setEstrangeiro(funcionario.getEstrangeiro());
		this.setNaturalizado(funcionario.getNaturalizado());

		if (Objects.nonNull(funcionario.getRegistroEstrangeiroUf()))
			this.setRegistroEstrangeiroUfId(funcionario.getRegistroEstrangeiroUf().getId());

		if (Objects.nonNull(funcionario.getMunicipioRegistroEstrangeiro()))
			this.setMunicipioRegistroEstrangeiroId(funcionario.getMunicipioRegistroEstrangeiro().getId());

		if (Objects.nonNull(funcionario.getUf()))
			this.setUfId(funcionario.getUf().getId());

		this.setCasamentoBrasileiro(funcionario.getCasamentoBrasileiro());
		this.setFilhoBrasileiro(funcionario.getFilhoBrasileiro());
		this.setLogradouro(funcionario.getLogradouro());
		this.setNumero(funcionario.getNumero());
		this.setComplemento(funcionario.getComplemento());
		this.setBairro(funcionario.getBairro());
		this.setCep(funcionario.getCep());
		this.setEmailPessoal(funcionario.getEmailPessoal());
		this.setEmailCorporativo(funcionario.getEmailCorporativo());
		this.setTelefonePrincipal(funcionario.getTelefonePrincipal());
		this.setTelefoneOpcional(funcionario.getTelefoneOpcional());
		this.setTelefoneComercial(funcionario.getTelefoneComercial());

		// aba Documentação
		this.setIdentidade(funcionario.getIdentidade());
		this.setOrgaoExpeditor(funcionario.getOrgaoExpeditor());
		this.setUfOrgaoExpeditorId(funcionario.getUfOrgaoExpeditor().getId());
		this.setDataExpedicaoRg(funcionario.getDataExpedicaoRg());
		this.setNumeroCtps(funcionario.getNumeroCtps());

		if (funcionario.getUfCtps() != null)
			this.setUfCtpsId(funcionario.getUfCtps().getId());

		this.setCpf(funcionario.getCpf());
		this.setPisPasep(funcionario.getPisPasep());
		this.setDataEmissaoPisPasep(funcionario.getDataEmissaoPisPasep());
		this.setAgenciaPisPasep(funcionario.getAgenciaPisPasep());
		this.setTituloEleitor(funcionario.getTituloEleitor());
		this.setZona(funcionario.getZona());
		this.setSecao(funcionario.getSecao());
		this.setNumeroSus(funcionario.getNumeroSus());

		if (Objects.nonNull(funcionario.getUfCtps()))
			this.setUfCtpsId(funcionario.getUfCtps().getId());

		if (Objects.nonNull(funcionario.getUfTituloEleitor()))
			this.setUfTituloEleitorId(funcionario.getUfTituloEleitor().getId());

		this.setCnh(funcionario.getCnh());
		this.setDataValidadeCnh(funcionario.getDataValidadeCnh());

		if (funcionario.getCategoriaCnh() != null)
			this.setCategoriaCnh(funcionario.getCategoriaCnh().toString());

		if (Objects.nonNull(funcionario.getClassificacaoAto())) {
			this.setClassificacaoAtoId(funcionario.getClassificacaoAto().getId());

			this.setClassificacaoAto(new ClassificacaoAtoResponse(funcionario.getClassificacaoAto()));
		}

		this.setRegistroAlistamento(funcionario.getRegistroAlistamento());
		this.setCategoriaAlistamento(funcionario.getCategoriaAlistamento());

		this.setNumeroProcesso(funcionario.getNumeroProcesso());
		this.setNumeroAto(funcionario.getNumeroAto());
		this.setDataNomeacao(funcionario.getDataNomeacao());
		this.setNumeroDiarioOficial(funcionario.getNumeroDiarioOficial());
		this.setDataPublicacaoDiarioOficial(funcionario.getDataPublicacaoDiarioOficial());

		// aba Dados de Pagamento
		this.setDataAdmissao(funcionario.getDataAdmissao());
		this.setNumeroDependentesImpostoRenda(funcionario.getNumeroDependentesImpostoRenda());
		this.setNumeroDependentesSalarioFamilia(funcionario.getNumeroDependentesSalarioFamilia());
		this.setOpcaoFgts(funcionario.getOpcaoFgts());
		this.setAgenciaFgts(funcionario.getAgenciaFgts());
		this.setContaFgts(funcionario.getContaFgts());
		this.setDataExercicioAdmissaoAts(funcionario.getDataExercicioAdmissaoAts());
		this.setEstabilidade(funcionario.getEstabilidade());
		this.setNumeroConta(funcionario.getNumeroConta());
		this.setDigitoConta(funcionario.getDigitoConta());
		this.setOperacao(funcionario.getOperacao());
		this.setProbatorio(funcionario.getProbatorio());
		this.setDataInicioSituacaoFuncional(funcionario.getDataInicioSituacaoFuncional());
		this.setDataFimSituacaoFuncional(funcionario.getDataFimSituacaoFuncional());
		this.setAgencia(funcionario.getAgencia());
		this.setInicioContratoTemporario(funcionario.getInicioContratoTemporario());
		this.setFimContratoTemporario(funcionario.getFimContratoTemporario());
		this.setDiasAfastado(funcionario.getDiasAfastado());
		this.setMatriculaDestino(funcionario.getMatriculaDestino());
		this.setCessaoEmpresaDestino(funcionario.getCessaoEmpresaDestino());
		this.setDataOpcaoFgts(funcionario.getDataOpcaoFgts());
		this.setMatriculaVinculo(funcionario.getMatriculaVinculo());
		this.setDescricaoVinculo(funcionario.getDescricaoVinculo());
		this.setValorVinculo(funcionario.getValorVinculo());

		if (Objects.nonNull(funcionario.getCargo()))
			this.setCargo(new CargoResponse(funcionario.getCargo()));

		if (Objects.nonNull(funcionario.getReferenciaSalarialCargo()))
			this.setReferenciaSalarialCargoId(funcionario.getReferenciaSalarialCargo().getId());

		if (Objects.nonNull(funcionario.getFaixaSalarialCargo()))
			this.setFaixaSalarialCargoId(funcionario.getFaixaSalarialCargo().getId());

		if (Objects.nonNull(funcionario.getReferenciaSalarialFuncao()))
			this.setReferenciaSalarialFuncaoId(funcionario.getReferenciaSalarialFuncao().getId());

		if (Objects.nonNull(funcionario.getFaixaSalarialFuncao()))
			this.setFaixaSalarialFuncaoId(funcionario.getFaixaSalarialFuncao().getId());

		if (Objects.nonNull(funcionario.getTipoEstabilidade()))
			this.setTipoEstabilidade(funcionario.getTipoEstabilidade().toString());

		if (Objects.nonNull(funcionario.getUfTrabalho()))
			this.setUfTrabalhoId(funcionario.getUfTrabalho().getId());

		if (Objects.nonNull(funcionario.getVinculo())) {
			this.setVinculo(new VinculoResponse(funcionario.getVinculo()));
			this.setVinculoId(funcionario.getVinculo().getId());
		}

		if (Objects.nonNull(funcionario.getFuncao()))
			this.setFuncao(new FuncaoResponse(funcionario.getFuncao()));

		if (Objects.nonNull(funcionario.getJornadaTrabalho()))
			this.setJornadaTrabalho(new TurnoResponse(funcionario.getJornadaTrabalho()));

		if (Objects.nonNull(funcionario.getTipoAdicionalTempoServico()))
			this.setTipoAdicionalTempoServico(funcionario.getTipoAdicionalTempoServico().toString());

		if (Objects.nonNull(funcionario.getMunicipioTrabalho()))
			this.setMunicipioTrabalhoId(funcionario.getMunicipioTrabalho().getId());

		if (Objects.nonNull(funcionario.getLotacao()))
			this.setLotacaoId(funcionario.getLotacao().getId());

		if (Objects.nonNull(funcionario.getBanco()))
			this.setBanco(new DadoBasicoDto(funcionario.getBanco()));

		if (Objects.nonNull(funcionario.getAgenciaBancaria()))
			this.setAgenciaBancaria(new DadoBasicoDto(funcionario.getAgenciaBancaria()));

		if (Objects.nonNull(funcionario.getTipoConta()))
			this.setTipoConta(funcionario.getTipoConta().toString());

		if (Objects.nonNull(funcionario.getJornadaTrabalho()))
			this.setJornadaTrabalhoId(funcionario.getJornadaTrabalho().getId());

		if (Objects.nonNull(funcionario.getCargaHoraria()))
			this.setCargaHoraria(funcionario.getCargaHoraria().toString());

		if (Objects.nonNull(funcionario.getSindicato()))
			this.setSindicatoId(funcionario.getSindicato().getId());

		if (Objects.nonNull(funcionario.getTipoFrequencia()))
			this.setTipoFrequencia(funcionario.getTipoFrequencia().toString());

		if (Objects.nonNull(funcionario.getTipoSituacaoFuncional())) {
			this.setSituacaoFuncional(new SituacaoFuncionalResponse(funcionario.getTipoSituacaoFuncional()));
			this.setTipoSituacaoFuncionalId(funcionario.getTipoSituacaoFuncional().getId());
		}

		if (Objects.nonNull(funcionario.getMotivoAfastamento()))
			this.setMotivoAfastamentoId(funcionario.getMotivoAfastamento().getId());

		if (Utils.checkSetList(funcionario.getValesTransportes())) {
			this.setValesTransporte(new ArrayList<>());

			funcionario.getValesTransportes().forEach(valesTransporte -> this.getValesTransporte()
					.add(new FuncionarioValeTransporteResponse(valesTransporte)));
		}

	}

	public void addExperienciasProfissionais(Set<ExperienciaProfissional> experienciasProfissionais) {
		if (experienciasProfissionais != null) {
			List<ExperienciaProfissionalResponse> experienciaProfissionalResponses = new ArrayList<>();
			for (ExperienciaProfissional e : experienciasProfissionais) {
				experienciaProfissionalResponses.add(new ExperienciaProfissionalResponse(e));
			}
			this.experienciasProfissionais = experienciaProfissionalResponses.isEmpty() ? null
					: experienciaProfissionalResponses;
		}
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

	public EmpresaFilialResponse getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFilialResponse empresa) {
		this.empresa = empresa;
	}

	public EmpresaFilialResponse getFilial() {
		return filial;
	}

	public void setFilial(EmpresaFilialResponse filial) {
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

	public DadoBasicoDto getBanco() {
		return banco;
	}

	public void setBanco(DadoBasicoDto banco) {
		this.banco = banco;
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

	public FuncaoResponse getFuncao() {
		return funcao;
	}

	public void setFuncao(FuncaoResponse funcao) {
		this.funcao = funcao;
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

	public Integer getQuantDependentes() {
		return quantDependentes;
	}

	public void setQuantDependentes(Integer quantDependentes) {
		this.quantDependentes = quantDependentes;
	}

	public List<ProcessoResponse> getProcessos() {
		return processos;
	}

	public void setProcessos(List<ProcessoResponse> processos) {
		this.processos = processos;
	}

	public Integer getQuantAnexos() {
		return quantAnexos;
	}

	public void setQuantAnexos(Integer quantAnexos) {
		this.quantAnexos = quantAnexos;
	}

	public List<AnexoResponse> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoResponse> anexos) {
		this.anexos = anexos;
	}

	public List<FuncionarioExercicioResponse> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<FuncionarioExercicioResponse> exercicios) {
		this.exercicios = exercicios;
	}

	public List<TempoServicoResponse> getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(List<TempoServicoResponse> tempoServico) {
		this.tempoServico = tempoServico;
	}

	public List<ExperienciaProfissionalResponse> getExperienciasProfissionais() {
		return experienciasProfissionais;
	}

	public void setExperienciasProfissionais(List<ExperienciaProfissionalResponse> experienciasProfissionais) {
		this.experienciasProfissionais = experienciasProfissionais;
	}

	public List<AcidenteTrabalhoResponse> getAcidentesDeTrabalho() {
		return acidentesDeTrabalho;
	}

	public void setAcidentesDeTrabalho(List<AcidenteTrabalhoResponse> acidentesDeTrabalho) {
		this.acidentesDeTrabalho = acidentesDeTrabalho;
	}

	public List<FeriasProgramacaoResponse> getFeriasProgramadas() {
		return feriasProgramadas;
	}

	public void setFeriasProgramadas(List<FeriasProgramacaoResponse> feriasProgramadas) {
		this.feriasProgramadas = feriasProgramadas;
	}

	public List<PensaoAlimenticiaDto> getPensoes() {
		return pensoes;
	}

	public void setPensoes(List<PensaoAlimenticiaDto> pensoes) {
		this.pensoes = pensoes;
	}

	public LotacaoResponse getLotacao() {
		return lotacao;
	}

	public void setLotacao(LotacaoResponse lotacao) {
		this.lotacao = lotacao;
	}

	public List<VerbaResponse> getVerbas() {
		return verbas;
	}

	public void setVerbas(List<VerbaResponse> verbas) {
		this.verbas = verbas;
	}

	public List<FuncionarioValeTransporteResponse> getValesTransporte() {
		return valesTransporte;
	}

	public void setValesTransporte(List<FuncionarioValeTransporteResponse> valesTransporte) {
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

	public TurnoResponse getJornadaTrabalho() {
		return jornadaTrabalho;
	}

	public void setJornadaTrabalho(TurnoResponse jornadaTrabalho) {
		this.jornadaTrabalho = jornadaTrabalho;
	}

	public List<FrequenciaResponse> getFrequencias() {
		return frequencias;
	}

	public void setFrequencias(List<FrequenciaResponse> frequencias) {
		this.frequencias = frequencias;
	}

	public CentroCustoResponse getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCustoResponse centroCusto) {
		this.centroCusto = centroCusto;
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

	public ClassificacaoAtoResponse getClassificacaoAto() {
		return classificacaoAto;
	}

	public void setClassificacaoAto(ClassificacaoAtoResponse classificacaoAto) {
		this.classificacaoAto = classificacaoAto;
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

	public String getMunicipioNome() {
		return municipioNome;
	}

	public void setMunicipioNome(String municipioNome) {
		this.municipioNome = municipioNome;
	}

	public String getUfNome() {
		return ufNome;
	}

	public void setUfNome(String ufNome) {
		this.ufNome = ufNome;
	}

	public String getUfCtpsNome() {
		return ufCtpsNome;
	}

	public void setUfCtpsNome(String ufCtpsNome) {
		this.ufCtpsNome = ufCtpsNome;
	}

	public String getTipoContratoNome() {
		return tipoContratoNome;
	}

	public void setTipoContratoNome(String tipoContratoNome) {
		this.tipoContratoNome = tipoContratoNome;
	}

	public CargoResponse getCargo() {
		return cargo;
	}

	public void setCargo(CargoResponse cargo) {
		this.cargo = cargo;
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

	public ReferenciaSalarialResponse getReferenciaSalarialResponse() {
		return referenciaSalarialResponse;
	}

	public void setReferenciaSalarialResponse(ReferenciaSalarialResponse referenciaSalarialResponse) {
		this.referenciaSalarialResponse = referenciaSalarialResponse;
	}

	public VinculoResponse getVinculo() {
		return vinculo;
	}

	public void setVinculo(VinculoResponse vinculo) {
		this.vinculo = vinculo;
	}

	public SituacaoFuncionalResponse getSituacaoFuncional() {
		return situacaoFuncional;
	}

	public void setSituacaoFuncional(SituacaoFuncionalResponse situacaoFuncional) {
		this.situacaoFuncional = situacaoFuncional;
	}

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
	}

	public List<FuncionarioVerbaResponse> getVerbasFuncionario() {
		return verbasFuncionario;
	}

	public void setVerbasFuncionario(List<FuncionarioVerbaResponse> verbasFuncionario) {
		this.verbasFuncionario = verbasFuncionario;
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

	public DadoBasicoDto getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public void setAgenciaBancaria(DadoBasicoDto agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}

}
