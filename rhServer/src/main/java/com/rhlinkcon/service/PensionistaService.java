package com.rhlinkcon.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.filtro.PensionistaFiltro;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.PensaoPrevidenciariaPagamento;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.pensionista.PensaoPrevidenciariaPagamentoRequest;
import com.rhlinkcon.payload.pensionista.PensionistaRequest;
import com.rhlinkcon.payload.pensionista.PensionistaResponse;
import com.rhlinkcon.payload.pensionistaVerba.PensionistaVerbaResponse;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoDto;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoSummaryDto;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoTipoEnum;
import com.rhlinkcon.repository.FolhaCompetenciaRepository;
import com.rhlinkcon.repository.PensaoPrevidenciariaPagamentoRepository;
import com.rhlinkcon.repository.PensionistaVerbaRepository;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.contracheque.ContrachequeRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.pensionista.PensionistaRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class PensionistaService {

	@Autowired
	private PensionistaRepository pensionistaRepository;

	@Autowired
	private PensaoPrevidenciariaPagamentoRepository repositoryPagamento;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PensaoPrevidenciariaPagamentoRepository pensaoPrevidenciariaPagamentoRepository;

	@Autowired
	private PensionistaVerbaRepository pensionistaVerbaRepository;

	@Autowired
	private RecadastramentoService recadastramentoService;

	@Autowired
	private FolhaCompetenciaService folhaCompetenciaService;

	@Autowired
	private FolhaCompetenciaRepository folhaCompetenciaRepository;

	@Autowired
	private ContrachequeRepository contrachequeRepository;

	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	public List<FuncionarioResponse> findExSeguradoByNome(String search) {
		List<Long> sitFuncIds = new ArrayList<>();

		situacaoFuncionalRepository.findByDescricaoIgnoreCaseContaining("FALECIMENTO").forEach(item -> {
			sitFuncIds.add(item.getId());
		});
		situacaoFuncionalRepository.findByDescricaoIgnoreCaseContaining("FALECIDO").forEach(item -> {
			sitFuncIds.add(item.getId());
		});
		situacaoFuncionalRepository.findByDescricaoIgnoreCaseContaining("FALECIDA").forEach(item -> {
			sitFuncIds.add(item.getId());
		});

		FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
		if (Utils.checkStr(search))
			funcionarioFiltro.setSearch(search);
		funcionarioFiltro.setTipoSituacaoFuncionalIds(sitFuncIds);

		List<Funcionario> funcionarios = funcionarioRepository.filtro(funcionarioFiltro);

		List<FuncionarioResponse> funcionarioResponses = new ArrayList<FuncionarioResponse>();

		if (Objects.nonNull(funcionarios) && funcionarios.size() > 0) {
			funcionarios.forEach(funcionarioEncontrado -> {

				FuncionarioResponse funcResponse = new FuncionarioResponse(funcionarioEncontrado.getId(),
						funcionarioEncontrado.getMatricula(),
						new EmpresaFilialResponse(funcionarioEncontrado.getEmpresa()), funcionarioEncontrado.getNome());
				funcResponse.setCpf(funcionarioEncontrado.getCpf());
				funcionarioResponses.add(funcResponse);

			});

		}

		return funcionarioResponses;
	}

	@Transactional
	public void create(PensionistaRequest request, PensaoPrevidenciariaPagamentoRequest requestPagamento) {
		PensaoPrevidenciariaPagamento ppp = new PensaoPrevidenciariaPagamento(requestPagamento);
		repositoryPagamento.save(ppp);
		request.setPensaoPagamentoId(ppp.getId());
		Pensionista pp = new Pensionista(request);
		pensionistaRepository.save(pp);
	}

	@Transactional
	public void create(PensionistaRequest request) {
		Pensionista pp = new Pensionista(request);
		pensionistaRepository.save(pp);
	}

	public PensionistaResponse getPensaoPrevidencia(Long id) {
		Pensionista pp = pensionistaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pensionista", "id", id));
		PensionistaResponse ppr = new PensionistaResponse(pp, Projecao.COMPLETA);
		ppr.setAlteradoPor(usuarioService.alteradoPor(pp));
		ppr.setCriadoPor(usuarioService.criadoPor(pp));
		return ppr;
	}

	public PagedResponse<PensionistaResponse> getAll(int page, int size, String searchFuncionario,
			String searchPensionista, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		PensionistaFiltro pensionistaFiltro = new PensionistaFiltro();
		if (Utils.checkStr(searchFuncionario))
			pensionistaFiltro.setSearchFuncionario(searchFuncionario);
		if (Utils.checkStr(searchPensionista))
			pensionistaFiltro.setSearchPensionista(searchPensionista);

		Page<Pensionista> pensoes = pensionistaRepository.filtro(pensionistaFiltro, pageable);

		if (pensoes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), pensoes.getNumber(), pensoes.getSize(),
					pensoes.getTotalElements(), pensoes.getTotalPages(), pensoes.isLast());
		}

		List<PensionistaResponse> transferenciasResponse = pensoes.map(item -> {
			return new PensionistaResponse(item, Projecao.BASICA);
		}).getContent();
		return new PagedResponse<>(transferenciasResponse, pensoes.getNumber(), pensoes.getSize(),
				pensoes.getTotalElements(), pensoes.getTotalPages(), pensoes.isLast());
	}

	public PagedResponse<PensionistaResponse> getAllPensionisaToVerbasPensionista(PagedRequest pagedRequest,
			PensionistaFiltro pensionistaFiltro) {

		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<Pensionista> pensionistas = pensionistaRepository.filtro(pensionistaFiltro, pageable);

		if (pensionistas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), pensionistas.getNumber(), pensionistas.getSize(),
					pensionistas.getTotalElements(), pensionistas.getTotalPages(), pensionistas.isLast());
		}

		List<PensionistaResponse> pensionistasResponse = new ArrayList<PensionistaResponse>();
		for (Pensionista pensionista : pensionistas) {
			List<PensionistaVerbaResponse> verbasPensionistaResponse = new ArrayList<PensionistaVerbaResponse>();

			PensionistaResponse pensionistaResponse = new PensionistaResponse();

			pensionistaResponse.setId(pensionista.getId());
			pensionistaResponse.setNome(pensionista.getNome());
			pensionistaResponse.setMatricula(pensionista.getMatricula());
			pensionistaResponse.setFuncionarioNome(pensionista.getFuncionario().getNome());

			List<PensionistaVerba> pensVerbaList = pensionistaVerbaRepository.findByPensionistaId(pensionista.getId());
			for (PensionistaVerba verba : pensVerbaList) {
				PensionistaVerbaResponse pensVerbaResponse = new PensionistaVerbaResponse(verba);
				verbasPensionistaResponse.add(pensVerbaResponse);
			}

			pensionistaResponse.setVerbas(verbasPensionistaResponse);

			pensionistasResponse.add(pensionistaResponse);
		}

		return new PagedResponse<>(pensionistasResponse, pensionistas.getNumber(), pensionistas.getSize(),
				pensionistas.getTotalElements(), pensionistas.getTotalPages(), pensionistas.isLast());

	}

	public void update(PensionistaRequest pensionistaRequest) {
		Pensionista pensionista = new Pensionista(pensionistaRequest);
		pensionistaRepository.save(pensionista);
		pensaoPrevidenciariaPagamentoRepository.save(pensionistaRequest.getPensaoPagamento());
	}

	public List<Pensionista> findAllByFuncionarioFilialIdAndFuncionarioLotacaoId(Long filialId, boolean isSemLotacao,
			List<Long> lotacoesId, Boolean status) {
		return pensionistaRepository.findAllByFuncionarioFilialIdAndFuncionarioLotacaoId(filialId, isSemLotacao,
				lotacoesId, status);
	}

	public List<Pensionista> getPensionistasAptosPraFolhaByFolhaPagamento(FolhaPagamento folhaPagamento) {

		FolhaCompetencia folhaCompetencia = folhaPagamento.getFolhaCompetencia();

		LocalDate dataCompetenciaFinal = LocalDate.of(folhaCompetencia.getAnoCompetencia(),
				folhaCompetencia.getMesCompetencia(), 1);

		dataCompetenciaFinal = dataCompetenciaFinal.plusMonths(1);
		dataCompetenciaFinal = dataCompetenciaFinal.minusDays(1);

		List<Pensionista> pensByFilialIdAndStatus = pensionistaRepository
				.findAptosByFilialIdAndStatus(folhaPagamento.getFilial().getId(), dataCompetenciaFinal);

		List<Pensionista> pensionistasResult = new ArrayList<Pensionista>();
		pensByFilialIdAndStatus.forEach(pensionista -> {
			if (recadastramentoService.checkRecadastramentoEmDia(pensionista, folhaCompetencia))
				pensionistasResult.add(pensionista);
		});
		return pensionistasResult;

	}

	private ServidorPagBloqueadoDto GetServPagBloqDtoByFunc(Pensionista pensionista, Double valorLiquido) {
		ServidorPagBloqueadoDto servidorPagBloqueadoDto = ServidorPagBloqueadoDto.builder()
				.situacaoFuncional("PENSIONISTA").nome(pensionista.getNome()).cpf(pensionista.getCpf())
				.matricula(pensionista.getMatricula().toString())
				.fundo(pensionista.getFuncionario().getFilial().getLabel()).valorLiquido(valorLiquido)
				.dataBaseRecadastramento(pensionista.getDataBaseRecadastramento())
				.mesNascimento(Utils.getMesCompetenciaString(pensionista.getDataNascimento().getMonthValue()))
				.telefoneContato(pensionista.getTelefoneFixo()).build();
		return servidorPagBloqueadoDto;
	}

	public List<ServidorPagBloqueadoDto> getPensionistasInaptosPraFolha(ServidorPagBloqueadoDto requestFilter) {

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

		LocalDate dataNascimento = null;

		if (requestFilter.getDataNascimentoSelecionada() != null) {
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date data = formato.parse(requestFilter.getDataNascimentoSelecionada());
				dataNascimento = data.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		// Get de todos os pensionistas a serem analisados por filial
		List<ServidorPagBloqueadoSummaryDto> pensByFilialIds = pensionistaRepository.findInaptosByFilialIdAndStatus(
				requestFilter.getFundosSelecionados(), requestFilter.getSituacaoFuncionalSelecionadas(), todaSitFunc,
				requestFilter.getServidorSelecionadoId(), dataNascimento);

		// Varrendo a lista para checagem de problemas.
		for (ServidorPagBloqueadoSummaryDto servidorPagBloqueadoSummaryDto : pensByFilialIds) {

			Pensionista pensionista = pensionistaRepository.findById(servidorPagBloqueadoSummaryDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Pensionista", "id",
							servidorPagBloqueadoSummaryDto.getId()));

			Double valorLiquido = contrachequeRepository
					.findValorLiquidoByContrachequeId(servidorPagBloqueadoSummaryDto.getIdContracheque());

			// Checagem de problema referente a situação funcional.
			if (!pensionista.isStatus()) {
				setServPagBloqDtoMap(servPagBloqDtoMap, pensionista, ServidorPagBloqueadoTipoEnum.PENSIONISTA_INATIVO,
						valorLiquido);
			}

			// Checagem da data de início de primeiro pagamento
			if (Objects.nonNull(pensionista.getPensaoPagamento().getDataPrimeiroPagamento())) {
				boolean problem = true;

				LocalDate initSitPensTemp = pensionista.getPensaoPagamento().getDataPrimeiroPagamento();

				LocalDate sitPensInit = LocalDate.of(initSitPensTemp.getYear(), initSitPensTemp.getMonthValue(), 1);
				Instant sitPensInstantInit = sitPensInit.atStartOfDay().toInstant(ZoneOffset.UTC);

				if (sitPensInstantInit.isBefore(dataCompetenciaInicioInit)
						|| sitPensInstantInit.equals(dataCompetenciaInicioInit)) {
					problem = false;
				}

				if (problem) {
					setServPagBloqDtoMap(servPagBloqDtoMap, pensionista,
							ServidorPagBloqueadoTipoEnum.DATA_PRIMEIRO_PAGAMENTO_PENS, valorLiquido);
				}
			}

			// Checagem da data de fim de situação cadastral.
			if (Objects.nonNull(pensionista.getPensaoPagamento().getDataFimBeneficio())) {
				boolean problem = true;

				LocalDate endSitPensTemp = pensionista.getPensaoPagamento().getDataFimBeneficio();

				LocalDate sitPensEnd = LocalDate.of(endSitPensTemp.getYear(), endSitPensTemp.getMonthValue(), 1);
				Instant sitPensInstantEnd = sitPensEnd.atStartOfDay().toInstant(ZoneOffset.UTC);

				if (sitPensInstantEnd.isAfter(dataCompetenciaInicioInit)
						|| sitPensInstantEnd.equals(dataCompetenciaInicioInit)) {
					problem = false;
				}

				if (problem) {
					setServPagBloqDtoMap(servPagBloqDtoMap, pensionista,
							ServidorPagBloqueadoTipoEnum.DATA_FINAL_PAGAMENTO_PENS, valorLiquido);
				}
			}

			// Checagem de problema referente ao recadastramento em caso de aposentado.
			if (!recadastramentoService.checkRecadastramentoEmDia(pensionista, folhaCompetencia)) {
				setServPagBloqDtoMap(servPagBloqDtoMap, pensionista,
						ServidorPagBloqueadoTipoEnum.RECADASTRAMENTO_PENDENTE, valorLiquido);
			}
		}

		// Gerando a lista do map para retorno.
		for (Map.Entry<Long, ServidorPagBloqueadoDto> pair : servPagBloqDtoMap.entrySet()) {
			result.add(pair.getValue());
		}

		return result;

	}

	private void setServPagBloqDtoMap(Map<Long, ServidorPagBloqueadoDto> servPagBloqDtoMap, Pensionista pensionista,
			ServidorPagBloqueadoTipoEnum servidorPagBloqueadoTipoEnum, Double valorLiquido) {
		if (servPagBloqDtoMap.containsKey(pensionista.getId())) {
			ServidorPagBloqueadoDto value = servPagBloqDtoMap.get(pensionista.getId());
			value.getProblemas().add(servidorPagBloqueadoTipoEnum);
			servPagBloqDtoMap.put(pensionista.getId(), value);
		} else {
			ServidorPagBloqueadoDto value = GetServPagBloqDtoByFunc(pensionista, valorLiquido);
			value.setProblemas(new ArrayList<ServidorPagBloqueadoTipoEnum>());
			value.getProblemas().add(servidorPagBloqueadoTipoEnum);
			servPagBloqDtoMap.put(pensionista.getId(), value);
		}
	}

}