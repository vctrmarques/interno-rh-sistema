package com.rhlinkcon.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.model.AcidenteTrabalho;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.model.CargaHorariaSemanalEnum;
import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.CategoriaCnhEnum;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.ClassificacaoAto;
import com.rhlinkcon.model.CorPeleEnum;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.EstadoCivilEnum;
import com.rhlinkcon.model.FaixaSalarial;
import com.rhlinkcon.model.FeriasProgramacao;
import com.rhlinkcon.model.FeriasProgramacaoSituacaoEnum;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FuncionarioExercicio;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.GrauInstrucaoEnum;
import com.rhlinkcon.model.GrupoVinculoEnum;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.MotivoAfastamento;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.Sindicato;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.TempoServico;
import com.rhlinkcon.model.TipoAdicionalTempoServicoEnum;
import com.rhlinkcon.model.TipoContaResponsavelEnum;
import com.rhlinkcon.model.TipoEstabilidadeEnum;
import com.rhlinkcon.model.TipoFrequenciaEnum;
import com.rhlinkcon.model.TipoHistoricoSituacaoFuncionalEnum;
import com.rhlinkcon.model.Turno;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.ValeTransporte;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.acidenteTrabalho.AcidenteTrabalhoResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.certidaoExSegurado.FuncionarioCertidaoRequest;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoResponse;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioAnexoRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioVerbaResponse;
import com.rhlinkcon.payload.funcionarioExercicio.FuncionarioExercicioResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.licencaPremio.LicencaPremioResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoDto;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoSummaryDto;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoTipoEnum;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;
import com.rhlinkcon.payload.tempoServico.TempoServicoResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.repository.ClassificacaoAtoRepository;
import com.rhlinkcon.repository.DependenteRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.FeriasProgramacaoRepository;
import com.rhlinkcon.repository.FolhaCompetenciaRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.FuncionarioExercicioRepository;
import com.rhlinkcon.repository.FuncionarioValeTransporteRepository;
import com.rhlinkcon.repository.FuncionarioVerbaRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.MotivoAfastamentoRepository;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.NacionalidadeRepository;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.SindicatoRepository;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.TempoServicoRepository;
import com.rhlinkcon.repository.TurnoRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.ValeTransporteRepository;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.repository.contracheque.ContrachequeRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.repository.vinculo.VinculoRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class FuncionarioService {

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private ValeTransporteRepository valeTransporteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private DependenteRepository dependenteRepository;

	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private VinculoRepository vinculoRepository;

	@Autowired
	private LotacaoRepository lotacaoRepository;

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private TurnoRepository turnoRepository;

	@Autowired
	private SindicatoRepository sindicatoRepository;

	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	@Autowired
	private MotivoAfastamentoRepository motivoAfastamentoRepository;

	@Autowired
	private AnexoRepository anexoRepository;

	@Autowired
	private FuncionarioExercicioRepository funcionarioExercicioRepository;

	@Autowired
	private LicencaPremioService licencaPremioService;

	@Autowired
	private FuncionarioValeTransporteService funcionarioValeTransporteService;

	@Autowired
	private TempoServicoRepository tempoServicoRepository;

	@Autowired
	private FeriasProgramacaoRepository feriasProgramacaoRepository;

	@Autowired
	private FuncionarioValeTransporteRepository funcionarioValeTransporteRepository;

	@Autowired
	private ClassificacaoAtoRepository classificacaoAtoRepository;

	@Autowired
	private ReferenciaSalarialRepository referenciaSalarialRepository;

	@Autowired
	private AgenciaService agenciaService;

	@Autowired
	private AgenciaRepository agenciaRepository;

	@Autowired
	private FuncionarioVerbaRepository funcionarioVerbaRepository;

	@Autowired
	private RecadastramentoService recadastramentoService;

	@Autowired
	private FolhaCompetenciaService folhaCompetenciaService;

	@Autowired
	private FolhaCompetenciaRepository folhaCompetenciaRepository;

	@Autowired
	private ContrachequeRepository contrachequeRepository;

	public void createFuncionario(FuncionarioRequest funcionarioRequest) {

		Funcionario funcionario = new Funcionario(funcionarioRequest);

		setEntidades(funcionario, funcionarioRequest);

		funcionarioRepository.save(funcionario);

		if (Utils.checkList(funcionarioRequest.getValesTransporte())) {
			funcionarioRequest.getValesTransporte().forEach(valeTransporteRequest -> {
				if (Objects.nonNull(funcionario.getId())) {
					valeTransporteRequest.setFuncionarioId(funcionario.getId());
					funcionarioValeTransporteService.createFuncionarioValeTransporte(valeTransporteRequest);
				}
			});
		}

	}

	public void updateFuncionario(FuncionarioRequest funcionarioRequest) {

		Funcionario funcionario = funcionarioRepository.findById(funcionarioRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioRequest.getId()));

		setEntidades(funcionario, funcionarioRequest);

		if (Objects.nonNull(funcionarioRequest.getNome()))
			funcionario.setNome(funcionarioRequest.getNome());

		if (Objects.nonNull(funcionarioRequest.getMatricula()))
			funcionario.setMatricula(funcionarioRequest.getMatricula());

		if (Objects.nonNull(funcionarioRequest.getDataNascimento()))
			funcionario.setDataNascimento(funcionarioRequest.getDataNascimento());

		if (Objects.nonNull(funcionarioRequest.getSexo()))
			funcionario.setSexo(funcionarioRequest.getSexo());

		if (Utils.checkStr(funcionarioRequest.getEstadoCivil()))
			funcionario.setEstadoCivil(EstadoCivilEnum.valueOf(funcionarioRequest.getEstadoCivil()));

		if (Utils.checkStr(funcionarioRequest.getGrauInstrucao()))
			funcionario.setGrauInstrucao(GrauInstrucaoEnum.valueOf(funcionarioRequest.getGrauInstrucao()));

		if (Utils.checkStr(funcionarioRequest.getCorPele()))
			funcionario.setCorPele(CorPeleEnum.valueOf(funcionarioRequest.getCorPele()));

		if (Objects.nonNull(funcionarioRequest.getCentroCustoId()))
			funcionario.setCentroCusto(new CentroCusto(funcionarioRequest.getCentroCustoId()));

		if (Objects.nonNull(funcionarioRequest.getConhecidoComo()))
			funcionario.setConhecidoComo(funcionarioRequest.getConhecidoComo());

		if (Objects.nonNull(funcionarioRequest.getTipoSanguineo()))
			funcionario.setTipoSanguineo(funcionarioRequest.getTipoSanguineo());

		if (Objects.nonNull(funcionarioRequest.getNomeMae()))
			funcionario.setNomeMae(funcionarioRequest.getNomeMae());

		if (Objects.nonNull(funcionarioRequest.getNomePai()))
			funcionario.setNomePai(funcionarioRequest.getNomePai());

		if (Objects.nonNull(funcionarioRequest.getEstrangeiro()))
			funcionario.setEstrangeiro(Utils.setBool(funcionarioRequest.getEstrangeiro()));

		if (Objects.nonNull(funcionarioRequest.getNaturalizado()))
			funcionario.setNaturalizado(funcionarioRequest.getNaturalizado());

		if (Objects.nonNull(funcionarioRequest.getCasamentoBrasileiro()))
			funcionario.setCasamentoBrasileiro(funcionarioRequest.getCasamentoBrasileiro());

		if (Objects.nonNull(funcionarioRequest.getFilhoBrasileiro()))
			funcionario.setFilhoBrasileiro(funcionarioRequest.getFilhoBrasileiro());

		if (Objects.nonNull(funcionarioRequest.getLogradouro()))
			funcionario.setLogradouro(funcionarioRequest.getLogradouro());

		if (Objects.nonNull(funcionarioRequest.getNumero()))
			funcionario.setNumero(funcionarioRequest.getNumero());

		if (Objects.nonNull(funcionarioRequest.getComplemento()))
			funcionario.setComplemento(funcionarioRequest.getComplemento());

		if (Objects.nonNull(funcionarioRequest.getBairro()))
			funcionario.setBairro(funcionarioRequest.getBairro());

		if (Objects.nonNull(funcionarioRequest.getCep()))
			funcionario.setCep(funcionarioRequest.getCep());

		if (Objects.nonNull(funcionarioRequest.getEmailPessoal()))
			funcionario.setEmailPessoal(funcionarioRequest.getEmailPessoal());

		if (Objects.nonNull(funcionarioRequest.getEmailCorporativo()))
			funcionario.setEmailCorporativo(funcionarioRequest.getEmailCorporativo());

		if (Objects.nonNull(funcionarioRequest.getTelefoneOpcional()))
			funcionario.setTelefoneOpcional(funcionarioRequest.getTelefoneOpcional());

		if (Objects.nonNull(funcionarioRequest.getTelefonePrincipal()))
			funcionario.setTelefonePrincipal(funcionarioRequest.getTelefonePrincipal());

		if (Objects.nonNull(funcionarioRequest.getTelefoneComercial()))
			funcionario.setTelefoneComercial(funcionarioRequest.getTelefoneComercial());

		if (Objects.nonNull(funcionarioRequest.getIdentidade()))
			funcionario.setIdentidade(funcionarioRequest.getIdentidade());

		if (Objects.nonNull(funcionarioRequest.getOrgaoExpeditor()))
			funcionario.setOrgaoExpeditor(funcionarioRequest.getOrgaoExpeditor());

		if (Objects.nonNull(funcionarioRequest.getDataExpedicaoRg()))
			funcionario.setDataExpedicaoRg(funcionarioRequest.getDataExpedicaoRg());

		if (Objects.nonNull(funcionarioRequest.getNumeroCtps()))
			funcionario.setNumeroCtps(funcionarioRequest.getNumeroCtps());

		if (Objects.nonNull(funcionarioRequest.getSerieCtps()))
			funcionario.setSerieCtps(funcionarioRequest.getSerieCtps());

		if (Objects.nonNull(funcionarioRequest.getCpf()))
			funcionario.setCpf(funcionarioRequest.getCpf());

		if (Objects.nonNull(funcionarioRequest.getPisPasep()))
			funcionario.setPisPasep(funcionarioRequest.getPisPasep());

		if (Objects.nonNull(funcionarioRequest.getDataEmissaoPisPasep()))
			funcionario.setDataEmissaoPisPasep(funcionarioRequest.getDataEmissaoPisPasep());

		if (Objects.nonNull(funcionarioRequest.getAgenciaPisPasep()))
			funcionario.setAgenciaPisPasep(funcionarioRequest.getAgenciaPisPasep());

		if (Objects.nonNull(funcionarioRequest.getTituloEleitor()))
			funcionario.setTituloEleitor(funcionarioRequest.getTituloEleitor());

		if (Objects.nonNull(funcionarioRequest.getSecao()))
			funcionario.setSecao(funcionarioRequest.getSecao());

		if (Objects.nonNull(funcionarioRequest.getZona()))
			funcionario.setZona(funcionarioRequest.getZona());

		if (Objects.nonNull(funcionarioRequest.getCnh()))
			funcionario.setCnh(funcionarioRequest.getCnh());

		if (Objects.nonNull(funcionarioRequest.getDataValidadeCnh()))
			funcionario.setDataValidadeCnh(funcionarioRequest.getDataValidadeCnh());

		if (Objects.nonNull(funcionarioRequest.getNumeroSus()))
			funcionario.setNumeroSus(funcionarioRequest.getNumeroSus());

		if (Utils.checkStr(funcionarioRequest.getCategoriaCnh()))
			funcionario.setCategoriaCnh(CategoriaCnhEnum.valueOf(funcionarioRequest.getCategoriaCnh()));

		if (Objects.nonNull(funcionarioRequest.getRegistroAlistamento()))
			funcionario.setRegistroAlistamento(funcionarioRequest.getRegistroAlistamento());

		if (Objects.nonNull(funcionarioRequest.getCategoriaAlistamento()))
			funcionario.setCategoriaAlistamento(funcionarioRequest.getCategoriaAlistamento());

		if (Objects.nonNull(funcionarioRequest.getNumeroProcesso()))
			funcionario.setNumeroProcesso(funcionarioRequest.getNumeroProcesso());

		if (Objects.nonNull(funcionarioRequest.getNumeroAto()))
			funcionario.setNumeroAto(funcionarioRequest.getNumeroAto());

		if (Objects.nonNull(funcionarioRequest.getDataNomeacao()))
			funcionario.setDataNomeacao(funcionarioRequest.getDataNomeacao());

		if (Objects.nonNull(funcionarioRequest.getNumeroDiarioOficial()))
			funcionario.setNumeroDiarioOficial(funcionarioRequest.getNumeroDiarioOficial());

		if (Objects.nonNull(funcionarioRequest.getDataPublicacaoDiarioOficial()))
			funcionario.setDataPublicacaoDiarioOficial(funcionarioRequest.getDataPublicacaoDiarioOficial());

		if (Objects.nonNull(funcionarioRequest.getDataAdmissao()))
			funcionario.setDataAdmissao(funcionarioRequest.getDataAdmissao());

		if (Objects.nonNull(funcionarioRequest.getNumeroDependentesImpostoRenda()))
			funcionario.setNumeroDependentesImpostoRenda(funcionarioRequest.getNumeroDependentesImpostoRenda());

		if (Objects.nonNull(funcionarioRequest.getNumeroDependentesSalarioFamilia()))
			funcionario.setNumeroDependentesSalarioFamilia(funcionarioRequest.getNumeroDependentesSalarioFamilia());

		if (Objects.nonNull(funcionarioRequest.getOpcaoFgts()))
			funcionario.setOpcaoFgts(funcionarioRequest.getOpcaoFgts());

		if (Objects.nonNull(funcionarioRequest.getAgenciaFgts()))
			funcionario.setAgenciaFgts(funcionarioRequest.getAgenciaFgts());

		if (Objects.nonNull(funcionarioRequest.getContaFgts()))
			funcionario.setContaFgts(funcionarioRequest.getContaFgts());

		if (Objects.nonNull(funcionarioRequest.getDataExercicioAdmissaoAts()))
			funcionario.setDataExercicioAdmissaoAts(funcionarioRequest.getDataExercicioAdmissaoAts());

		if (Objects.nonNull(funcionarioRequest.getEstabilidade()))
			funcionario.setEstabilidade(funcionarioRequest.getEstabilidade());

		if (Objects.nonNull(funcionarioRequest.getNumeroConta())) {
			funcionario.setNumeroConta(funcionarioRequest.getNumeroConta());
		}
		if (Objects.nonNull(funcionarioRequest.getDigitoConta())) {
			funcionario.setDigitoConta(funcionarioRequest.getDigitoConta());
		}

		if (Objects.nonNull(funcionarioRequest.getOperacao()))
			funcionario.setOperacao(funcionarioRequest.getOperacao());

		if (Objects.nonNull(funcionarioRequest.getOperacao())) {
			funcionario.setProbatorio(funcionarioRequest.getProbatorio());
		}

		if (Objects.nonNull(funcionarioRequest.getDataInicioSituacaoFuncional()))
			funcionario.setDataInicioSituacaoFuncional(funcionarioRequest.getDataInicioSituacaoFuncional());

		if (Objects.nonNull(funcionarioRequest.getDataFimSituacaoFuncional()))
			funcionario.setDataFimSituacaoFuncional(funcionarioRequest.getDataFimSituacaoFuncional());

		if (Objects.nonNull(funcionarioRequest.getAgencia())) {
			funcionario.setAgencia(funcionarioRequest.getAgencia());
		}

		if (Objects.nonNull(funcionarioRequest.getCessaoEmpresaDestino()))
			funcionario.setCessaoEmpresaDestino(funcionarioRequest.getCessaoEmpresaDestino());

		if (Objects.nonNull(funcionarioRequest.getDataOpcaoFgts()))
			funcionario.setDataOpcaoFgts(funcionarioRequest.getDataOpcaoFgts());

		if (Objects.nonNull(funcionarioRequest.getMatriculaDestino()))
			funcionario.setMatriculaDestino(funcionarioRequest.getMatriculaDestino());

		if (Objects.nonNull(funcionarioRequest.getMatriculaVinculo()))
			funcionario.setMatriculaVinculo(funcionarioRequest.getMatriculaVinculo());

		if (Objects.nonNull(funcionarioRequest.getDescricaoVinculo()))
			funcionario.setDescricaoVinculo(funcionarioRequest.getDescricaoVinculo());

		if (Objects.nonNull(funcionarioRequest.getValorVinculo()))
			funcionario.setValorVinculo(funcionarioRequest.getValorVinculo());

		if (Utils.checkStr(funcionarioRequest.getTipoAdicionalTempoServico()))
			funcionario.setTipoAdicionalTempoServico(TipoAdicionalTempoServicoEnum
					.valueOf(funcionarioRequest.getTipoAdicionalTempoServico().toString()));

		if (Utils.checkStr(funcionarioRequest.getTipoConta()))
			funcionario.setTipoConta(TipoContaResponsavelEnum.valueOf(funcionarioRequest.getTipoConta().toString()));

		if (Utils.checkStr(funcionarioRequest.getCargaHoraria()))
			funcionario
					.setCargaHoraria(CargaHorariaSemanalEnum.valueOf(funcionarioRequest.getCargaHoraria().toString()));

		if (Utils.checkStr(funcionarioRequest.getTipoFrequencia()))
			funcionario
					.setTipoFrequencia(TipoFrequenciaEnum.valueOf(funcionarioRequest.getTipoFrequencia().toString()));

		if (Objects.nonNull(funcionarioRequest.getTipoEstabilidade()))
			funcionario.setTipoEstabilidade(TipoEstabilidadeEnum.valueOf(funcionarioRequest.getTipoEstabilidade()));

		funcionarioRepository.save(funcionario);

		if (Utils.checkList(funcionarioRequest.getValesTransporte())) {

			List<Long> idsFuncionariosValeTransporte = new ArrayList<>();
			List<Long> idsFuncionariosValeTransporteRequest = new ArrayList<>();

			funcionarioRequest.getValesTransporte().forEach(funcionarioValeTransporteRequest -> {
				idsFuncionariosValeTransporteRequest.add(funcionarioValeTransporteRequest.getId());
			});

			idsFuncionariosValeTransporte = funcionarioValeTransporteRepository
					.findIdsByFuncionarioId(funcionario.getId());

			// inclui caso seja novo
			funcionarioRequest.getValesTransporte().forEach(funcionarioValeTransporteRequest -> {
				if (!Objects.nonNull(funcionarioValeTransporteRequest.getId())) {
					funcionarioValeTransporteRequest.setFuncionarioId(funcionario.getId());
					funcionarioValeTransporteService.createFuncionarioValeTransporte(funcionarioValeTransporteRequest);
				}

			});

			// remove caso não contenha na lista atual enviada pelo request
			if (Utils.checkList(idsFuncionariosValeTransporte))
				idsFuncionariosValeTransporte.forEach(id -> {
					if (!idsFuncionariosValeTransporteRequest.contains(id))
						funcionarioValeTransporteRepository.deleteById(id);
				});

		}
	}

	public void deleteFuncionario(Long id) {
		Funcionario funcionario = funcionarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", id));

		funcionarioValeTransporteService.deleteFuncionarioValeTransporte(funcionario.getId());

		funcionarioRepository.delete(funcionario);
	}

	public PagedResponse<FuncionarioResponse> getAllFuncionarios(int page, int size,
			FuncionarioFiltro funcionarioFiltro, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro, pageable);

		if (funcionarios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcionarios.getNumber(), funcionarios.getSize(),
					funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();

		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
			funcionarioResponse.setId(funcionario.getId());
			funcionarioResponse.setNome(funcionario.getNome());
			funcionarioResponse.setMatricula(funcionario.getMatricula());
			funcionarioResponse.setCpf(funcionario.getCpf());
			funcionarioResponse.setPisPasep(funcionario.getPisPasep());
			funcionarioResponse.setTipoSituacaoFuncionalId(funcionario.getTipoSituacaoFuncional().getId());
			funcionarioResponse
					.setSituacaoFuncional(new SituacaoFuncionalResponse(funcionario.getTipoSituacaoFuncional()));
			funcionarioResponse.setEmpresa(new EmpresaFilialResponse(funcionario.getEmpresa()));
			funcionarioResponse.setFilial(new EmpresaFilialResponse(funcionario.getFilial()));
			funcionarioResponse.setFuncao(new FuncaoResponse(funcionario.getFuncao()));
			funcionarioResponse.setDataAdmissao(funcionario.getDataAdmissao());

			if (Objects.nonNull(funcionario.getLotacao()))
				funcionarioResponse.setLotacao(new LotacaoResponse(funcionario.getLotacao()));

			Optional<List<Dependente>> dependentes = dependenteRepository
					.findByFuncionarioId(funcionarioResponse.getId());
			if (dependentes.isPresent())
				funcionarioResponse.setQuantDependentes(dependentes.get().size());

			List<FuncionarioExercicio> exercicios = funcionarioExercicioRepository
					.findByFuncionarioId(funcionarioResponse.getId());
			funcionarioResponse.setExercicios(new ArrayList<FuncionarioExercicioResponse>());

			funcionarioResponse.setQuantAnexos(funcionario.getAnexos().size());

			for (FuncionarioExercicio exercicio : exercicios) {
				List<LicencaPremioResponse> quantLicencaPremio = licencaPremioService
						.getAllLicencasPremioByFuncionarioExercicio(exercicio.getId());

				FuncionarioExercicioResponse funcionarioExercicioResponse = new FuncionarioExercicioResponse(exercicio);
				funcionarioExercicioResponse.setQuantLicencaPremio(quantLicencaPremio.size());

				funcionarioResponse.getExercicios().add(funcionarioExercicioResponse);
			}

			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());

	}

	public PagedResponse<FuncionarioResponse> getAllFuncionariosComAcidentesDeTrabalho(int page, int size,
			Integer matricula, String nomeOrMatricula, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Funcionario> funcionarios = null;

		if (Utils.checkStr(nomeOrMatricula)) {
			funcionarios = funcionarioRepository.findComAcidenteDeTrabalho(nomeOrMatricula, pageable);
		} else {
			funcionarios = funcionarioRepository.findComAcidenteDeTrabalho(pageable);
		}

		if (funcionarios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcionarios.getNumber(), funcionarios.getSize(),
					funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();

		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

			funcionarioResponse.setId(funcionario.getId());
			funcionarioResponse.setNome(funcionario.getNome());
			funcionarioResponse.setMatricula(funcionario.getMatricula());
			funcionarioResponse.setAcidentesDeTrabalho(new ArrayList<>());

			for (AcidenteTrabalho acidenteTrablho : funcionario.getAcidentesDeTrabalho()) {
				AcidenteTrabalhoResponse acidenteTrabalhoResponse = new AcidenteTrabalhoResponse();
				acidenteTrabalhoResponse.setId(acidenteTrablho.getId());
				acidenteTrabalhoResponse.setAviso(acidenteTrablho.getAviso());
				funcionarioResponse.getAcidentesDeTrabalho().add(acidenteTrabalhoResponse);
			}
			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());

	}

	public PagedResponse<FuncionarioResponse> getAllFuncionariosTempoServico(int page, int size, String order,
			String nome) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Funcionario> funcionarios = null;

		if (Utils.checkStr(nome))
			funcionarios = funcionarioRepository.getAllFuncionariosWithTempoServicoByName(pageable, nome);
		else
			funcionarios = funcionarioRepository.getAllFuncionariosWithTempoServico(pageable);

		if (funcionarios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcionarios.getNumber(), funcionarios.getSize(),
					funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();

		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

			funcionarioResponse.setId(funcionario.getId());
			funcionarioResponse.setNome(funcionario.getNome());

			List<TempoServico> temposServico = tempoServicoRepository.findByFuncionarioId(funcionarioResponse.getId());

			funcionarioResponse.setTempoServico(new ArrayList<>());

			for (TempoServico tempoServico : temposServico) {
				funcionarioResponse.getTempoServico().add(new TempoServicoResponse(tempoServico));
			}

			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());

	}

	public PagedResponse<FuncionarioResponse> getAllFuncionariosComExperiencia(int page, int size, String search,
			String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();

		if (Utils.checkStr(search))
			funcionarioFiltro.setSearch(search);

		funcionarioFiltro.setComExperiencia(true);

		Page<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro, pageable);

		if (funcionarios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcionarios.getNumber(), funcionarios.getSize(),
					funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();

		for (Funcionario funcionario : funcionarios) {
			// FuncionarioResponse funcionarioResponse = new
			// FuncionarioResponse(funcionario);

			funcionariosResponse.add(new FuncionarioResponse(funcionario.getId(), funcionario.getMatricula(),
					new EmpresaFilialResponse(funcionario.getEmpresa()), funcionario.getExperienciaProfissionais(),
					funcionario.getNome()));
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());

	}

	public FuncionarioResponse listAnexosByFuncionarioId(Long funcionarioId) {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));
		FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
		funcionarioResponse.setId(funcionario.getId());
		funcionario.setMatricula(funcionario.getMatricula());
		funcionario.setNome(funcionario.getNome());
		funcionarioResponse.setAnexos(new ArrayList<AnexoResponse>());
		funcionario.getAnexos().forEach(anexo -> funcionarioResponse.getAnexos().add(new AnexoResponse(anexo)));

		return funcionarioResponse;

	}

	public FuncionarioResponse getFuncionarioById(Long funcionarioId) {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		if (Objects.isNull(funcionario.getAgenciaBancaria()) && Objects.nonNull(funcionario.getAgencia())
				&& Objects.nonNull(funcionario.getBanco())) {
			Agencia agencia = agenciaService.findByNumeroAndBanco(funcionario.getBanco(),
					Integer.valueOf(funcionario.getAgencia()));
			if (Objects.nonNull(agencia)) {
				funcionario.setAgenciaBancaria(agencia);
			}
		}

		Usuario userCreated = usuarioRepository.findById(funcionario.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcionario.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(funcionario.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcionario.getUpdatedBy()));

		FuncionarioResponse funcionarioResponse = new FuncionarioResponse(funcionario, funcionario.getCreatedAt(),
				userCreated.getNome(), funcionario.getUpdatedAt(), userUpdated.getNome());
		funcionarioResponse.setAnexos(new ArrayList<AnexoResponse>());
		funcionario.getAnexos().forEach(anexo -> funcionarioResponse.getAnexos().add(new AnexoResponse(anexo)));

		if (Utils.checkList(funcionarioResponse.getValesTransporte()))
			funcionarioResponse.getValesTransporte().forEach(valeTransporteResponse -> {
				ValeTransporte valeTransporte = valeTransporteRepository
						.findById(valeTransporteResponse.getValeTransporteId())
						.orElseThrow(() -> new ResourceNotFoundException("ValeTransporte", "id",
								valeTransporteResponse.getValeTransporteId()));

				if (Objects.nonNull(valeTransporte)) {
					valeTransporteResponse.setValor(valeTransporte.getValor());
					valeTransporteResponse.setCodigo(valeTransporte.getCodigo());
				}
			});

		return funcionarioResponse;
	}

	private void setEntidades(Funcionario funcionario, FuncionarioRequest funcionarioRequest) {

		if (Objects.nonNull(funcionarioRequest.getNaturalidade())) {
			funcionario.setNaturalidade(new Municipio(funcionarioRequest.getNaturalidade().getId()));
		} else {
			funcionario.setNaturalidade(null);
		}

		if (Objects.nonNull(funcionarioRequest.getNacionalidade())) {
			funcionario.setNacionalidade(new Nacionalidade(funcionarioRequest.getNacionalidade().getId()));
		} else {
			funcionario.setNacionalidade(null);
		}

		if (Objects.nonNull(funcionarioRequest.getCargoId())) {
			Cargo cargo = new Cargo(funcionarioRequest.getCargoId());

			funcionario.setCargo(cargo);
		}

		if (Objects.nonNull(funcionarioRequest.getFaixaSalarialCargoId())) {
			funcionario.setFaixaSalarialCargo(new FaixaSalarial(funcionarioRequest.getFaixaSalarialCargoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getReferenciaSalarialCargoId())) {
			funcionario.setReferenciaSalarialCargo(
					new ReferenciaSalarial(funcionarioRequest.getReferenciaSalarialCargoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getFaixaSalarialFuncaoId())) {
			funcionario.setFaixaSalarialFuncao(new FaixaSalarial(funcionarioRequest.getFaixaSalarialFuncaoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getReferenciaSalarialFuncaoId())) {
			funcionario.setReferenciaSalarialFuncao(
					new ReferenciaSalarial(funcionarioRequest.getReferenciaSalarialFuncaoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getClassificacaoAtoId())) {
			ClassificacaoAto classificacaoAto = classificacaoAtoRepository
					.findById(funcionarioRequest.getClassificacaoAtoId())
					.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAto", "id",
							funcionarioRequest.getClassificacaoAtoId()));

			funcionario.setClassificacaoAto(classificacaoAto);
		}

		if (Objects.nonNull(funcionarioRequest.getEmpresaId())) {
			funcionario.setEmpresa(new EmpresaFilial(funcionarioRequest.getEmpresaId()));
		}

		if (Objects.nonNull(funcionarioRequest.getFilialId())) {
			funcionario.setFilial(new EmpresaFilial(funcionarioRequest.getFilialId()));
		}

		if (Objects.nonNull(funcionarioRequest.getMunicipioId())) {
			funcionario.setMunicipio(new Municipio(funcionarioRequest.getMunicipioId()));
		}

		if (Objects.nonNull(funcionarioRequest.getRegistroEstrangeiroUfId())) {
			funcionario
					.setRegistroEstrangeiroUf(new UnidadeFederativa(funcionarioRequest.getRegistroEstrangeiroUfId()));
		}

		if (Objects.nonNull(funcionarioRequest.getMunicipioRegistroEstrangeiroId())) {
			funcionario.setMunicipioRegistroEstrangeiro(
					new Municipio(funcionarioRequest.getMunicipioRegistroEstrangeiroId()));
		}

		if (Objects.nonNull(funcionarioRequest.getUfId())) {
			funcionario.setUf(new UnidadeFederativa(funcionarioRequest.getUfId()));
		}

		if (Objects.nonNull(funcionarioRequest.getUfOrgaoExpeditorId())) {
			funcionario.setUfOrgaoExpeditor(new UnidadeFederativa(funcionarioRequest.getUfOrgaoExpeditorId()));
		}

		if (Objects.nonNull(funcionarioRequest.getUfCtpsId())) {
			funcionario.setUfCtps(new UnidadeFederativa(funcionarioRequest.getUfCtpsId()));
		}

		if (Objects.nonNull(funcionarioRequest.getUfTituloEleitorId())) {
			funcionario.setUfTituloEleitor(new UnidadeFederativa(funcionarioRequest.getUfTituloEleitorId()));
		}

		if (Objects.nonNull(funcionarioRequest.getVinculoId())) {
			funcionario.setVinculo(new Vinculo(funcionarioRequest.getVinculoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getFuncaoId())) {
			funcionario.setFuncao(new Funcao(funcionarioRequest.getFuncaoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getMunicipioTrabalhoId())) {
			funcionario.setMunicipioTrabalho(new Municipio(funcionarioRequest.getMunicipioTrabalhoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getLotacaoId())) {
			funcionario.setLotacao(new Lotacao(funcionarioRequest.getLotacaoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getBancoId())) {
			funcionario.setBanco(new Banco(funcionarioRequest.getBancoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getAgenciaBancariaId())) {
			funcionario.setAgenciaBancaria(new Agencia(funcionarioRequest.getAgenciaBancariaId()));
		}

		if (Objects.nonNull(funcionarioRequest.getJornadaTrabalhoId())) {
			funcionario.setJornadaTrabalho(new Turno(funcionarioRequest.getJornadaTrabalhoId()));
		}

		if (Objects.nonNull(funcionarioRequest.getSindicatoId())) {
			Sindicato sindicato = sindicatoRepository.findById(funcionarioRequest.getSindicatoId()).orElseThrow(
					() -> new ResourceNotFoundException("Sindicato", "id", funcionarioRequest.getSindicatoId()));

			funcionario.setSindicato(sindicato);
		}

		if (Objects.nonNull(funcionarioRequest.getTipoSituacaoFuncionalId())) {
			SituacaoFuncional tipoSituacaoFuncional = situacaoFuncionalRepository
					.findById(funcionarioRequest.getTipoSituacaoFuncionalId())
					.orElseThrow(() -> new ResourceNotFoundException("Afastamento", "id",
							funcionarioRequest.getTipoSituacaoFuncionalId()));

			funcionario.setTipoSituacaoFuncional(tipoSituacaoFuncional);
		}

		if (Objects.nonNull(funcionarioRequest.getMotivoAfastamentoId())) {
			MotivoAfastamento motivoAfastamento = motivoAfastamentoRepository
					.findById(funcionarioRequest.getMotivoAfastamentoId())
					.orElseThrow(() -> new ResourceNotFoundException("MotivoAfastamento", "id",
							funcionarioRequest.getMotivoAfastamentoId()));

			funcionario.setMotivoAfastamento(motivoAfastamento);
		}

		if (Objects.nonNull(funcionarioRequest.getUfTrabalhoId())) {
			UnidadeFederativa ufTrabalho = unidadeFederativaRepository.findById(funcionarioRequest.getUfTrabalhoId())
					.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id",
							funcionarioRequest.getUfTrabalhoId()));
			funcionario.setUfTrabalho(ufTrabalho);
		}

	}

	public List<FuncionarioResponse> getAllFuncionariosFindByNome(String search, String essencial) {

		List<Funcionario> funcionarios = funcionarioRepository.findByNomeIgnoreCaseContaining(search);
		List<FuncionarioResponse> funcionariosResponse = new ArrayList<>();

		if (!funcionarios.isEmpty()) {
			for (Funcionario funcionario : funcionarios) {
				FuncionarioResponse funcionarioResponse = null;
				if (Utils.checkStr(essencial)) {
					funcionarioResponse = new FuncionarioResponse();
					funcionarioResponse.setId(funcionario.getId());
					funcionarioResponse.setNome(funcionario.getNome());
				} else {
					funcionarioResponse = new FuncionarioResponse(funcionario);
				}
				funcionariosResponse.add(funcionarioResponse);
			}
		}

		return funcionariosResponse;
	}

	public List<FuncionarioResponse> getAllSemRecisaoEfetivada(String search) {
		List<Funcionario> funcionarios = funcionarioRepository.findSemRecisaoEfetivadas(search);
		List<FuncionarioResponse> funcionariosResponse = new ArrayList<>();

		if (!funcionarios.isEmpty()) {
			for (Funcionario funcionario : funcionarios) {
				funcionariosResponse.add(new FuncionarioResponse(funcionario));
			}
		}

		return funcionariosResponse;
	}

	public void updateFuncionarioAnexo(FuncionarioAnexoRequest funcionarioAnexoRequest) {

		Anexo anexo = anexoRepository.findById(funcionarioAnexoRequest.getAnexoId())
				.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", funcionarioAnexoRequest.getAnexoId()));

		anexo.setDescription(funcionarioAnexoRequest.getObservacao());

		anexoRepository.save(anexo);

		Funcionario funcionario = funcionarioRepository.findById(funcionarioAnexoRequest.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
						funcionarioAnexoRequest.getFuncionarioId()));

		if (!funcionario.getAnexos().contains(anexo)) {
			funcionario.getAnexos().add(anexo);
		}

		funcionarioRepository.save(funcionario);

	}

	public void deleteFuncionarioAnexo(Long funcionarioId, Long anexoId) {

		Anexo anexo = anexoRepository.findById(anexoId)
				.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", anexoId));

		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		if (funcionario.getAnexos().contains(anexo)) {
			funcionario.getAnexos().remove(anexo);

			funcionarioRepository.save(funcionario);
		}

		anexoRepository.delete(anexo);

	}

	public List<FuncionarioResponse> getAllFuncionarioFindByNomeOrMatricula(String search) {
		List<Funcionario> funcionarios = new ArrayList<>();
		if (Utils.checkNumber(search)) {
			funcionarios = funcionarioRepository.findByNomeIgnoreCaseContainingOrMatricula(search,
					Integer.valueOf(search));
		} else {
			funcionarios = funcionarioRepository.findByNomeIgnoreCaseContaining(search);
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<>();

		if (!funcionarios.isEmpty()) {
			for (Funcionario funcionario : funcionarios) {
				funcionariosResponse.add(new FuncionarioResponse(funcionario));
			}
			return funcionariosResponse;
		}

		return funcionariosResponse;
	}

	public PagedResponse<FuncionarioResponse> getAllFuncionariosToFeriasProgramacao(int page, int size, String search,
			String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();

		if (Utils.checkStr(search))
			funcionarioFiltro.setSearch(search);

		Page<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro, pageable);

		if (funcionarios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcionarios.getNumber(), funcionarios.getSize(),
					funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();

		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

			List<FeriasProgramacao> feriasProgramacoes = feriasProgramacaoRepository
					.findTop10ByFuncionarioIdOrderByIdDesc(funcionario.getId());

			funcionarioResponse.setId(funcionario.getId());
			funcionarioResponse.setNome(funcionario.getNome());
			funcionarioResponse.setMatricula(funcionario.getMatricula());
			funcionarioResponse.setFeriasProgramadas(new ArrayList<>());
			for (FeriasProgramacao feriasProgramacao : feriasProgramacoes) {
				FeriasProgramacaoResponse feriasProgramacaoResponse = new FeriasProgramacaoResponse();

				FeriasProgramacaoSituacaoEnum enumSituacao = feriasProgramacao.getSituacao();
				feriasProgramacaoResponse.setId(feriasProgramacao.getId());
				feriasProgramacaoResponse.setExercicioInicio(feriasProgramacao.getExercicioInicio());
				feriasProgramacaoResponse.setExercicioFim(feriasProgramacao.getExercicioFim());
				feriasProgramacaoResponse.setSituacao(enumSituacao);
				feriasProgramacaoResponse.setSituacaoLabel(feriasProgramacao.getSituacao().getLabel());

				if (feriasProgramacao.getDataInicialUm() != null) {
					feriasProgramacaoResponse.setDataInicialUm(feriasProgramacao.getDataInicialUm());
					feriasProgramacaoResponse.setDataFinalUm(feriasProgramacao.getDataFinalUm());
				}

				if (feriasProgramacao.getDataInicialDois() != null) {
					feriasProgramacaoResponse.setDataInicialDois(feriasProgramacao.getDataInicialDois());
					feriasProgramacaoResponse.setDataFinalDois(feriasProgramacao.getDataFinalDois());
				}
				if (feriasProgramacao.getDataInicialTres() != null) {
					feriasProgramacaoResponse.setDataInicialTres(feriasProgramacao.getDataInicialTres());
					feriasProgramacaoResponse.setDataFinalTres(feriasProgramacao.getDataFinalTres());
				}

				funcionarioResponse.getFeriasProgramadas().add(feriasProgramacaoResponse);
			}

			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());

	}

	public PagedResponse<FuncionarioResponse> getAllFuncionariosToVerbasFuncionario(PagedRequest pagedRequest,
			FuncionarioFiltro funcionarioFiltro) {

		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro, pageable);

		if (funcionarios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcionarios.getNumber(), funcionarios.getSize(),
					funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();
		for (Funcionario funcionario : funcionarios) {
			List<FuncionarioVerbaResponse> verbasFuncionarioResponse = new ArrayList<FuncionarioVerbaResponse>();

			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

			funcionarioResponse.setId(funcionario.getId());
			funcionarioResponse.setNome(funcionario.getNome());
			funcionarioResponse.setMatricula(funcionario.getMatricula());

			List<FuncionarioVerba> funcVerbaList = funcionarioVerbaRepository.findByFuncionarioId(funcionario.getId());

			for (FuncionarioVerba funcionarioVerba : funcVerbaList) {
				verbasFuncionarioResponse.add(new FuncionarioVerbaResponse(funcionarioVerba));
			}

			funcionarioResponse.setVerbasFuncionario(verbasFuncionarioResponse);

			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());

	}

	public FuncionarioResponse getVerbasEFuncionarioById(Long funcionarioId) {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

		funcionarioResponse.setNome(funcionario.getNome());
		funcionarioResponse.setMatricula(funcionario.getMatricula());

		if (Objects.nonNull(funcionario.getFilial()))
			funcionarioResponse.setFilial(new EmpresaFilialResponse(funcionario.getFilial()));
		if (Objects.nonNull(funcionario.getFuncao()))
			funcionarioResponse.setFuncao(new FuncaoResponse(funcionario.getFuncao()));
		if (Objects.nonNull(funcionario.getLotacao()))
			funcionarioResponse.setLotacao(new LotacaoResponse(funcionario.getLotacao()));

		List<VerbaResponse> verbasResponse = new ArrayList<VerbaResponse>();

		List<FuncionarioVerba> funcVerbaList = funcionarioVerbaRepository.findByFuncionarioId(funcionario.getId());

		for (FuncionarioVerba verba : funcVerbaList) {
			VerbaResponse response = new VerbaResponse(verba.getVerba());
			response.setValor(verba.getValor());
			verbasResponse.add(response);
		}

		funcionarioResponse.setVerbas(verbasResponse);

		return funcionarioResponse;
	}

	/*
	 * public void createVerbasFuncionario(FuncionarioVerbaRequest
	 * funcionarioVerbaRequest) {
	 * 
	 * Funcionario funcionario =
	 * funcionarioRepository.findById(funcionarioVerbaRequest.getFuncionarioId())
	 * .orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
	 * funcionarioVerbaRequest.getFuncionarioId()));
	 * 
	 * funcionario.getVerbas().clear();
	 * 
	 * List<Verba> verbas =
	 * verbaRepository.findAllById(funcionarioVerbaRequest.getVerbasId());
	 * List<FuncionarioVerba> funcionarioVerbas = new ArrayList<FuncionarioVerba>();
	 * 
	 * removeVerbasFuncionario(funcionarioVerbaRequest);
	 * 
	 * verbas.forEach(verba -> { FuncionarioVerba fv = new FuncionarioVerba();
	 * fv.setFuncionario(funcionario); fv.setVerba(verba);
	 * 
	 * funcionarioVerbaRequest.getVerbas().forEach(verbaRequest -> { if
	 * (verbaRequest.getId() == verba.getId()) { if (verbaRequest.getValor() !=
	 * null) { fv.setValor(verbaRequest.getValor()); } } });
	 * 
	 * funcionarioVerbas.add(fv); });
	 * 
	 * funcionario.getVerbas().addAll(funcionarioVerbas);
	 * 
	 * funcionarioRepository.save(funcionario);
	 * 
	 * }
	 * 
	 * void removeVerbasFuncionario(FuncionarioVerbaRequest funcionarioVerbaRequest)
	 * { Funcionario funcionario =
	 * funcionarioRepository.findById(funcionarioVerbaRequest.getFuncionarioId())
	 * .orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
	 * funcionarioVerbaRequest.getFuncionarioId()));
	 * 
	 * 
	 * List<FuncionarioVerba> verbasDB =
	 * funcionarioVerbaRepository.findByFuncionario(funcionario);
	 * 
	 * verbasDB.forEach(vf -> { funcionarioVerbaRepository.delete(vf); }); }
	 */

	public List<FuncionarioResponse> getAllFuncionariosToFeriasProgramacaoEmAberto(String dataInicioExercicio,
			Integer matricula, String nome) {

		List<Funcionario> funcionarios = funcionarioRepository.findByNomeIgnoreCaseContainingOrMatricula(nome,
				matricula);

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();
		FeriasProgramacao feriasProgramacao = new FeriasProgramacao();
		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();

			EmpresaFilial empresaFilial = empresaFilialRepository.findById(funcionario.getFilial().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Empresa Filial", "id", funcionario.getFilial().getId()));

			Date dataExecInicio;

			try {
				dataExecInicio = new SimpleDateFormat("yyyy/MM/dd").parse(dataInicioExercicio);
				feriasProgramacao = feriasProgramacaoRepository.findByExercicioInicioByFuncionario(funcionario.getId(),
						dataExecInicio);
			} catch (ParseException e) {
				feriasProgramacao = feriasProgramacaoRepository.findByFuncionarioIdAndSituacao(funcionario.getId());
			}

			funcionarioResponse.setId(funcionario.getId());
			funcionarioResponse.setNome(funcionario.getNome());
			funcionarioResponse.setMatricula(funcionario.getMatricula());
			funcionarioResponse.setEmpresa(new EmpresaFilialResponse(empresaFilial));

			if (Objects.nonNull(feriasProgramacao)) {
				FeriasProgramacaoResponse feriasProgramacaoResponse = new FeriasProgramacaoResponse();

				feriasProgramacaoResponse.setId(feriasProgramacao.getId());
				funcionarioResponse.setFeriasProgramadas(new ArrayList<>());
				funcionarioResponse.getFeriasProgramadas().add(feriasProgramacaoResponse);
				funcionariosResponse.add(funcionarioResponse);
			}

		}

		return funcionariosResponse;

	}

	public FuncionarioResponse getFuncionarioByIdSimplificado(Long funcionarioId) {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
		funcionarioResponse.setId(funcionario.getId());
		funcionarioResponse.setNome(funcionario.getNome());
		funcionarioResponse.setMatricula(funcionario.getMatricula());
		funcionarioResponse.setDataAdmissao(funcionario.getDataAdmissao());

		if (Objects.nonNull(funcionario.getClassificacaoAto()))
			funcionarioResponse.setClassificacaoAto(new ClassificacaoAtoResponse(funcionario.getClassificacaoAto()));

		funcionarioResponse.setNumeroProcesso(funcionario.getNumeroProcesso());
		funcionarioResponse.setDataNomeacao(funcionario.getDataNomeacao());
		funcionarioResponse.setNumeroDiarioOficial(funcionario.getNumeroDiarioOficial());
		funcionarioResponse.setDataPublicacaoDiarioOficial(funcionario.getDataPublicacaoDiarioOficial());
		funcionarioResponse.setEmpresa(new EmpresaFilialResponse(funcionario.getEmpresa()));

		return funcionarioResponse;
	}

	public String getEmailById(Long id) {
		return funcionarioRepository.findEmailById(id);
	}

	public Long getCountNumberByFilial(Long filialId) {
		return funcionarioRepository.countByFilialId(filialId);
	}

	public Long getCountNumberByFilialAndLotacao(Long filialId, Long lotacaoId) {
		return funcionarioRepository.countByFilialIdAndLotacaoId(filialId, lotacaoId);
	}

	public PagedResponse<FuncionarioResponse> getAllByFilial(int page, int size, String search, Long filialId,
			String order, Long lotacaoId) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
		funcionarioFiltro.setFilialId(filialId);

		if (Utils.checkStr(search))
			funcionarioFiltro.setSearch(search);

		if (lotacaoId != null) {
			funcionarioFiltro.setLotacaoId(lotacaoId);
		}

		Page<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro, pageable);

		if (funcionarios.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), funcionarios.getNumber(), funcionarios.getSize(),
					funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
		}

		List<FuncionarioResponse> funcionariosResponse = new ArrayList<FuncionarioResponse>();
		for (Funcionario funcionario : funcionarios) {
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
			funcionarioResponse.setId(funcionario.getId());
			funcionarioResponse.setMatricula(funcionario.getMatricula());
			funcionarioResponse.setNome(funcionario.getNome());

			EmpresaFilialResponse filialResponse = new EmpresaFilialResponse();
			filialResponse.setNomeFilial(funcionario.getFilial().getNomeFilial());

			funcionarioResponse.setFilial(filialResponse);

			LotacaoResponse lotacaoResponse = new LotacaoResponse();
			lotacaoResponse.setDescricaoCompleta(
					Objects.nonNull(funcionario.getLotacao()) ? funcionario.getLotacao().getDescricaoCompleta() : "");
			lotacaoResponse.setDescricao(
					Objects.nonNull(funcionario.getLotacao()) ? funcionario.getLotacao().getDescricao() : "");
			funcionarioResponse.setLotacao(lotacaoResponse);

			ReferenciaSalarialResponse referenciaSalarialResponse = new ReferenciaSalarialResponse();

			if (Objects.nonNull(funcionario.getReferenciaSalarialCargo())) {
				ReferenciaSalarial referenciaSalarial = referenciaSalarialRepository
						.findById(funcionario.getReferenciaSalarialCargo().getId()).get();
				referenciaSalarialResponse.setValor(referenciaSalarial.getValor());
			}

			funcionarioResponse.setReferenciaSalarialResponse(referenciaSalarialResponse);

			funcionariosResponse.add(funcionarioResponse);
		}

		return new PagedResponse<>(funcionariosResponse, funcionarios.getNumber(), funcionarios.getSize(),
				funcionarios.getTotalElements(), funcionarios.getTotalPages(), funcionarios.isLast());
	}

	public List<FuncionarioResponse> getAllFuncionariosByDadosPessoais(String searchFuncionario,
			Integer searchMatricula, String searchCPF, String searchPis) {

		List<Funcionario> funcionarios = funcionarioRepository.findByDadosPessoais(searchFuncionario, searchMatricula,
				searchCPF, searchPis);
		List<FuncionarioResponse> responseList = new ArrayList<FuncionarioResponse>();

		if (!funcionarios.isEmpty()) {
			for (Funcionario funcionario : funcionarios) {
				responseList.add(new FuncionarioResponse(funcionario));
			}
		}

		return responseList;
	}

	public List<FuncionarioResponse> findFuncionarioTempoAdmissaoByFiltros(String searchFuncionario,
			Integer searchMatricula, String searchCPF, String searchPis) throws ParseException {

		List<GrupoVinculoEnum> values = new ArrayList<>();
		values.add(GrupoVinculoEnum.EFETIVO);

		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

		Date dataA = Utils.getApenasData(sd.parse("16/01/1984"));
		Date dataB = Utils.getApenasDataFinal(sd.parse("27/12/1990"));

		Instant dataInicio = dataA.toInstant();
		Instant dataFim = dataB.toInstant();

		List<Funcionario> lista = funcionarioRepository.findAdmitidosNoPeriodo(searchFuncionario, searchMatricula,
				searchCPF, searchPis, dataInicio, dataFim, values);
		List<FuncionarioResponse> responseList = new ArrayList<>();

		if (!lista.isEmpty()) {
			for (Funcionario obj : lista) {
				FuncionarioResponse response = new FuncionarioResponse(obj, Projecao.APOSENTADORIA);
				responseList.add(response);
			}
		}

		return responseList;
	}

	public String dadoBancarioConcatStr(Funcionario funcionario) {
		String dadoBancario = "";
		if (Objects.nonNull(funcionario.getBanco())) {
			dadoBancario += funcionario.getBanco().getCodigo();
		}
		if (!dadoBancario.isEmpty())
			dadoBancario += " - ";
		if (Objects.nonNull(funcionario.getAgenciaBancaria())) {
			dadoBancario += funcionario.getAgenciaBancaria().getLabel();
		} else {
			dadoBancario += funcionario.getAgencia();
		}
		return dadoBancario;
	}

	public List<FuncionarioResponse> findFuncionarioAposentadoByFiltros(String searchFuncionario,
			Integer searchMatricula, String searchCPF, String searchPis) throws ParseException {

		List<String> values = new ArrayList<>();
		values.add("APOSENTADO");
		values.add("APOSENTADA");

		List<Funcionario> lista = funcionarioRepository.findAposentados(searchFuncionario, searchMatricula, searchCPF,
				searchPis, values);
		List<FuncionarioResponse> responseList = new ArrayList<>();

		if (!lista.isEmpty()) {
			for (Funcionario obj : lista) {
				FuncionarioResponse response = new FuncionarioResponse(obj, Projecao.APOSENTADORIA);
				responseList.add(response);
			}
		}

		return responseList;
	}

	public List<FuncionarioResponse> findFuncionarioExoneradoByFiltros(String searchFuncionario,
			Integer searchMatricula, String searchCPF, String searchPis) {

		List<Funcionario> lista = funcionarioRepository.findExonerados(searchFuncionario, searchMatricula, searchCPF,
				searchPis, TipoHistoricoSituacaoFuncionalEnum.EXONERACAO);
		List<FuncionarioResponse> responseList = new ArrayList<>();

		if (!lista.isEmpty()) {
			for (Funcionario obj : lista) {
				FuncionarioResponse response = new FuncionarioResponse(obj, Projecao.CERTIDAO_EXSEGURADO);
				responseList.add(response);
			}
		}

		return responseList;
	}

	public void updateFuncionarioCertidao(@Valid FuncionarioCertidaoRequest funcionarioCertidaoRequest) {
		updateFuncionario(funcionarioCertidaoRequest.getFuncionario());
	}

	public FuncionarioResponse getFuncionarioByIdCertidaoExServidor(Long funcionarioId) {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		FuncionarioResponse funcionarioResponse = new FuncionarioResponse(funcionario, Projecao.CERTIDAO_EXSEGURADO);

		return funcionarioResponse;
	}

	public List<Funcionario> findAllBySituacaoAposentadoAndFilialIdAndLotacaoId(Long filialId, boolean isSemLotacao,
			List<Long> lotacoesId) {
		return funcionarioRepository.findAllAposentadosByFilialIdAndLotacaoId(filialId, isSemLotacao, lotacoesId);
	}

	public List<Funcionario> getFuncionariosAptosPraFolhaByFolhaPagamento(FolhaPagamento folhaPagamento) {

		FolhaCompetencia folhaCompetencia = folhaPagamento.getFolhaCompetencia();

		LocalDate dataCompetenciaFinal = LocalDate.of(folhaCompetencia.getAnoCompetencia(),
				folhaCompetencia.getMesCompetencia(), 1);

		dataCompetenciaFinal = dataCompetenciaFinal.plusMonths(1);
		dataCompetenciaFinal = dataCompetenciaFinal.minusDays(1);

		Instant dataCompetenciaFinalInstant = dataCompetenciaFinal.atStartOfDay().toInstant(ZoneOffset.UTC);

		List<Funcionario> funcByFilialAndSituacao = funcionarioRepository
				.findAptosByFilialIdAndTipoSituacaoFuncionalEntraFolha(folhaPagamento.getFilial().getId(),
						dataCompetenciaFinalInstant);

		List<Funcionario> funcionariosResult = new ArrayList<Funcionario>();
		funcByFilialAndSituacao.forEach(funcionario -> {
			if (funcionario.getTipoSituacaoFuncional().getLabel().equals("APOSENTADO")) {
				if (recadastramentoService.checkRecadastramentoEmDia(funcionario, folhaCompetencia))
					funcionariosResult.add(funcionario);
			} else {
				funcionariosResult.add(funcionario);
			}
		});
		return funcionariosResult;

	}

	private ServidorPagBloqueadoDto GetServPagBloqDtoByFunc(Funcionario funcionario, Double valorLiquido) {
		ServidorPagBloqueadoDto servidorPagBloqueadoDto = ServidorPagBloqueadoDto.builder()
				.situacaoFuncional(funcionario.getTipoSituacaoFuncional().getLabel()).nome(funcionario.getNome())
				.cpf(funcionario.getCpf()).matricula(funcionario.getMatricula().toString())
				.fundo(funcionario.getFilial().getLabel()).valorLiquido(valorLiquido)
				.dataBaseRecadastramento(funcionario.getDataBaseRecadastramento())
				.mesNascimento(Utils.getMesCompetenciaString(Utils.getMes(funcionario.getDataNascimento())))
				.telefoneContato(funcionario.getTelefonePrincipal()).build();
		return servidorPagBloqueadoDto;
	}

	public List<ServidorPagBloqueadoDto> getFuncionariosInaptosPraFolha(ServidorPagBloqueadoDto requestFilter) {

		List<ServidorPagBloqueadoDto> result = new ArrayList<ServidorPagBloqueadoDto>();

		Map<Long, ServidorPagBloqueadoDto> servPagBloqDtoMap = new HashMap<>();

		// Get da competência atual para checagem posterior do recadastramento.
		FolhaCompetenciaResponse folhaCompetenciaResponse = folhaCompetenciaService.getCompetenciaAtual();
		FolhaCompetencia folhaCompetencia = folhaCompetenciaRepository.findById(folhaCompetenciaResponse.getId())
				.orElseThrow(() -> new ResourceNotFoundException("FolhaCompetencia", "id",
						folhaCompetenciaResponse.getId()));

		LocalDate dataCompetenciaInit = LocalDate.of(folhaCompetencia.getAnoCompetencia(),
				folhaCompetencia.getMesCompetencia(), 1);

		Instant dataCompetenciaInicioInit = dataCompetenciaInit.atStartOfDay().toInstant(ZoneOffset.UTC);

		boolean todaSitFunc = false;
		if (Objects.isNull(requestFilter.getSituacaoFuncionalSelecionadas())
				|| requestFilter.getSituacaoFuncionalSelecionadas().isEmpty())
			todaSitFunc = true;

		Instant dataNascimento = null;

		if (requestFilter.getDataNascimentoSelecionada() != null) {
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date data = formato.parse(requestFilter.getDataNascimentoSelecionada());
				dataNascimento = data.toInstant();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		// Get de todos os funcionários a serem analisados por filial
		List<ServidorPagBloqueadoSummaryDto> funcByFilialIds = funcionarioRepository
				.findInaptosByFilialIdInAndSituacaoFuncIdIn(requestFilter.getFundosSelecionados(),
						requestFilter.getSituacaoFuncionalSelecionadas(), todaSitFunc,
						requestFilter.getServidorSelecionadoId(), dataNascimento);

		// Aplica filtro de funcionários apenas com pensão alimentícia.
		boolean apenasComPensAlim = false;
		if (Objects.nonNull(requestFilter.getPensaoAlimenticia()) && requestFilter.getPensaoAlimenticia()) {
			apenasComPensAlim = true;
		}

		// Varrendo a lista para checagem de problemas.
		for (ServidorPagBloqueadoSummaryDto servidorPagBloqueadoSummaryDto : funcByFilialIds) {

			Funcionario funcionario = funcionarioRepository.findById(servidorPagBloqueadoSummaryDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
							servidorPagBloqueadoSummaryDto.getId()));

			if (apenasComPensAlim)
				if (Objects.isNull(funcionario.getPensoes()) || funcionario.getPensoes().isEmpty())
					continue;

			Double valorLiquido = contrachequeRepository
					.findValorLiquidoByContrachequeId(servidorPagBloqueadoSummaryDto.getIdContracheque());

			// Checagem de problema referente a situação funcional.
			if (!funcionario.getTipoSituacaoFuncional().isEntraFolha()) {
				setServPagBloqDtoMap(servPagBloqDtoMap, funcionario, ServidorPagBloqueadoTipoEnum.SITUACAO_FUNCIONAL,
						valorLiquido);
			}

			// Checagem da data de início de situação cadastral.
			if (Objects.nonNull(funcionario.getDataInicioSituacaoFuncional())) {
				boolean problem = true;

				LocalDate initSitFuncTemp = funcionario.getDataInicioSituacaoFuncional().atZone(ZoneOffset.UTC)
						.toLocalDate();

				LocalDate sitFuncInit = LocalDate.of(initSitFuncTemp.getYear(), initSitFuncTemp.getMonthValue(), 1);
				Instant sitFuncInstantInit = sitFuncInit.atStartOfDay().toInstant(ZoneOffset.UTC);

				if (sitFuncInstantInit.isBefore(dataCompetenciaInicioInit)
						|| sitFuncInstantInit.equals(dataCompetenciaInicioInit)) {
					problem = false;
				}

				if (problem) {
					setServPagBloqDtoMap(servPagBloqDtoMap, funcionario,
							ServidorPagBloqueadoTipoEnum.SITUACAO_FUNCIONAL_DATA_INICIO, valorLiquido);
				}
			}

			// Checagem da data de fim de situação cadastral.
			if (Objects.nonNull(funcionario.getDataFimSituacaoFuncional())) {
				boolean problem = true;

				LocalDate endSitFuncTemp = funcionario.getDataFimSituacaoFuncional().atZone(ZoneOffset.UTC)
						.toLocalDate();

				LocalDate sitFuncEnd = LocalDate.of(endSitFuncTemp.getYear(), endSitFuncTemp.getMonthValue(), 1);
				Instant sitFuncInstantEnd = sitFuncEnd.atStartOfDay().toInstant(ZoneOffset.UTC);

				if (sitFuncInstantEnd.isAfter(dataCompetenciaInicioInit)
						|| sitFuncInstantEnd.equals(dataCompetenciaInicioInit)) {
					problem = false;
				}

				if (problem) {
					setServPagBloqDtoMap(servPagBloqDtoMap, funcionario,
							ServidorPagBloqueadoTipoEnum.SITUACAO_FUNCIONAL_DATA_FIM, valorLiquido);
				}
			}

			// Checagem de problema referente ao recadastramento em caso de aposentado.
			if (funcionario.getTipoSituacaoFuncional().getLabel().equals("APOSENTADO")) {
				if (!recadastramentoService.checkRecadastramentoEmDia(funcionario, folhaCompetencia)) {
					setServPagBloqDtoMap(servPagBloqDtoMap, funcionario,
							ServidorPagBloqueadoTipoEnum.RECADASTRAMENTO_PENDENTE, valorLiquido);
				}
			}
		}

		// Gerando a lista do map para retorno.
		for (Map.Entry<Long, ServidorPagBloqueadoDto> pair : servPagBloqDtoMap.entrySet()) {
			result.add(pair.getValue());
		}

		return result;

	}

	private void setServPagBloqDtoMap(Map<Long, ServidorPagBloqueadoDto> servPagBloqDtoMap, Funcionario funcionario,
			ServidorPagBloqueadoTipoEnum servidorPagBloqueadoTipoEnum, Double valorLiquido) {
		if (servPagBloqDtoMap.containsKey(funcionario.getId())) {
			ServidorPagBloqueadoDto value = servPagBloqDtoMap.get(funcionario.getId());
			value.getProblemas().add(servidorPagBloqueadoTipoEnum);
			servPagBloqDtoMap.put(funcionario.getId(), value);
		} else {
			ServidorPagBloqueadoDto value = GetServPagBloqDtoByFunc(funcionario, valorLiquido);
			value.setProblemas(new ArrayList<ServidorPagBloqueadoTipoEnum>());
			value.getProblemas().add(servidorPagBloqueadoTipoEnum);
			servPagBloqDtoMap.put(funcionario.getId(), value);
		}
	}

}
