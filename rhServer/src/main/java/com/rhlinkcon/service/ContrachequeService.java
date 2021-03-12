package com.rhlinkcon.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.ContrachequeFiltro;
import com.rhlinkcon.filtro.FichaFinanceiraFiltro;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Lancamento;
import com.rhlinkcon.model.ParametroGlobal;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.model.StatusFolhaEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFuncionarioCountDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.ProjecaoSomatorioValores;
import com.rhlinkcon.payload.batimentoFolhaPagamento.ProjecaoSomatorioValoresFiliais;
import com.rhlinkcon.payload.contracheque.ContrachequeRecalcularDto;
import com.rhlinkcon.payload.contracheque.ContrachequeRequest;
import com.rhlinkcon.payload.contracheque.ContrachequeResponse;
import com.rhlinkcon.payload.dirf.ProjecaoDirfBeneficiario;
import com.rhlinkcon.payload.fichaFinanceira.FichaFinanceiraResponse;
import com.rhlinkcon.payload.folhaCompetencia.FolhaCompetenciaResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lancamento.LancamentoResponse;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoFilialDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.payload.verba.VerbaValorCompetenciaResponse;
import com.rhlinkcon.repository.FolhaCompetenciaRepository;
import com.rhlinkcon.repository.contracheque.ContrachequeRepository;
import com.rhlinkcon.repository.folhaPagamento.FolhaPagamentoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.lancamento.LancamentoRepository;
import com.rhlinkcon.repository.parametroGlobal.ParametroGlobalRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ContrachequeService {

	@Autowired
	private ContrachequeRepository contrachequeRepository;
	@Autowired
	private FolhaCompetenciaRepository folhaCompetenciaRepository;
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private LancamentoService lancamentoService;
	@Autowired
	private FolhaPagamentoRepository fPagamentoRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private ParametroGlobalRepository parametroGlobalRepository;
	@Autowired
	private DependenteService dependenteService;
	@Autowired
	private ExecutorService executorService;
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private PensionistaService pensionistaService;

	public PagedResponse<ContrachequeResponse> getPagedList(PagedRequest pagedRequest,
			ContrachequeFiltro requestFilter) {

		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<Contracheque> lista = contrachequeRepository.filtro(requestFilter, pageable);

		if (lista.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), lista.getNumber(), lista.getSize(),
					lista.getTotalElements(), lista.getTotalPages(), lista.isLast());
		}

		List<ContrachequeResponse> listaResponse = new ArrayList<ContrachequeResponse>();
		for (Contracheque fv : lista) {
			listaResponse.add(new ContrachequeResponse(fv));
		}

		return new PagedResponse<>(listaResponse, lista.getNumber(), lista.getSize(), lista.getTotalElements(),
				lista.getTotalPages(), lista.isLast());

	}

	public List<String> findDistinctTipoSituacaoFuncionalByFolhaPagamentoId(Long folhaPagamentoId) {
		return contrachequeRepository.findDistinctTipoSituacaoFuncionalByFolhaPagamentoId(folhaPagamentoId);
	}

	public PagedResponse<FuncionarioResponse> retornoFuncionarioForaDaFolha(int page, int size, Long folhaPagamentoId,
			Long filialId, String nome, String dataAdmissao, Long lotacaoId, String order) throws ParseException {
		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Funcionario> list = null;
		if (nome.equals("")) {
			nome = "%";
			list = contrachequeRepository.findByIdNotIn(folhaPagamentoId, filialId, nome, pageable);
		} else {
			list = contrachequeRepository.findByIdNotIn(folhaPagamentoId, filialId, nome, pageable);

		}

		if (lotacaoId != null && (dataAdmissao != null && !dataAdmissao.equals(""))) {
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

			Date dataA = Utils.getApenasData(sd.parse(dataAdmissao));

			Instant dataInicio = dataA.toInstant();
			list = contrachequeRepository.findByIdNotIn(folhaPagamentoId, filialId, nome, lotacaoId, dataInicio,
					pageable);

		}

		if (lotacaoId != null) {
			list = contrachequeRepository.findByIdNotIn(folhaPagamentoId, filialId, nome, lotacaoId, pageable);
		}

		if (dataAdmissao != null) {
			if (!dataAdmissao.equals("") && !dataAdmissao.equals("Invalid date")) {
				SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

				Date dataA = Utils.getApenasData(sd.parse(dataAdmissao));

				Instant dataInicio = dataA.toInstant();
				list = contrachequeRepository.findByIdNotIn(folhaPagamentoId, filialId, nome, dataInicio, pageable);
			}
		}

		if (list.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), list.getNumber(), list.getSize(),
					list.getTotalElements(), list.getTotalPages(), list.isLast());
		}
		List<FuncionarioResponse> response = list.stream().map(e -> new FuncionarioResponse(e))
				.collect(Collectors.toList());

		return new PagedResponse<>(response, list.getNumber(), list.getSize(), list.getTotalElements(),
				list.getTotalPages(), list.isLast());
	}

	public void recalcularContracheque(ContrachequeRecalcularDto request) {

		// Pegando a folha de pagamento pelo primeiro contracheque da lista, já que a
		// folha é única para todos os contracheques
		Contracheque contrachequeZero = contrachequeRepository.findById(request.getContrachequeIds().get(0))
				.orElseThrow(
						() -> new ResourceNotFoundException("Contracheque", "id", request.getContrachequeIds().get(0)));

		// Pegando a folha de pagamento comum a todos os contracheques a serem
		// recalculados
		FolhaPagamento folhaPagamento = contrachequeZero.getFolhaPagamento();

		// Efetuando validação de status da folha
		if (folhaPagamento.getStatus().equals(StatusFolhaEnum.BLOQUEADO))
			throw new BadRequestException(
					"Não é possível recalcular contracheques de uma folha de pagamento bloqueada.");

		// Efetuando validação da Situação da folha
		if (folhaPagamento.getSituacao().equals(SituacaoProcessamentoEnum.PENDENTE))
			throw new BadRequestException(
					"Não é possível recalcular contracheques de uma folha de pagamento com outro recalculo em andamento.");

		// Atualizando a situação da folha para Pendente, situaçao de início de
		// recalculo / processamento.
		folhaPagamento.setSituacao(SituacaoProcessamentoEnum.PENDENTE);
		fPagamentoRepository.save(folhaPagamento);

		// Busca de verbas automáticas do tipo de processamento da folha
//		List<Verba> verbasAutomaticas = tipoProcessamentoRepository
//				.findVerbasById(folhaPagamento.getTipoProcessamento().getId());

		// Iniciando processamento assíncrono.
		Runnable runnable = () -> {

			// Varrendos os contracheques a serem recalculados
			for (Long contrachequeId : request.getContrachequeIds()) {

				Contracheque contracheque = contrachequeRepository.findById(contrachequeId)
						.orElseThrow(() -> new ResourceNotFoundException("Contracheque", "id", contrachequeId));

				try {
					// Deletando as verbas / lançamentos para processar novamente o contracheque
					lancamentoRepository.deleteByContracheque(contracheque);

					// Preencher demais dados do contracheque
					atualizarDadosContracheque(contracheque);

					// Processa cada contracheque por vez
//					lancamentoService.processarLancamentos(contracheque, verbasAutomaticas);
					lancamentoService.processarLancamentos(contracheque);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			// Atualiza a situação de processamento da folha.
			boolean existProblem = contrachequeRepository.existsByFolhaPagamentoIdAndSituacaoNot(folhaPagamento.getId(),
					SituacaoProcessamentoEnum.CONCLUIDO);

			if (existProblem)
				folhaPagamento.setSituacao(SituacaoProcessamentoEnum.FALHO);
			else
				folhaPagamento.setSituacao(SituacaoProcessamentoEnum.CONCLUIDO);

			fPagamentoRepository.save(folhaPagamento);
		};
		executorService.submit(runnable);

	}

	/*
	 * O método não finalizado. Falta implementar a interação da verbasDesconto
	 * seguindo o mesmo raciocinio de verbasProventos. O método é usado para
	 * calcular a ficha financeira no contexto do ano inteiro.
	 */

	public FichaFinanceiraResponse listaFolhaPagamentoPorFuncionarioIdEPeriodo(Long funcionarioId,
			FichaFinanceiraFiltro fichaFinanceiraFiltro) {

		List<Contracheque> listContrachequesFuncionario = new ArrayList<Contracheque>();
		List<FolhaCompetencia> listCompetencias = new ArrayList<FolhaCompetencia>();
		List<String> listCompetenciasString = new ArrayList<String>();
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));
		FichaFinanceiraResponse fichaFinanceiraResponse = new FichaFinanceiraResponse();
		fichaFinanceiraResponse.setFuncionario(
				new FuncionarioResponse(funcionario, funcionario.getLotacao(), funcionario.getVinculo()));

		if (Objects.nonNull(fichaFinanceiraFiltro.getAno())) {
			listCompetencias = folhaCompetenciaRepository.findAllByAnoCompetencia(fichaFinanceiraFiltro.getAno());
			listCompetenciasString.addAll(Utils.periodoString(fichaFinanceiraFiltro.getAno()));
			listCompetenciasString.add("13/" + fichaFinanceiraFiltro.getAno().toString());
		} else {
			listCompetenciasString.addAll(Utils.periodoString(fichaFinanceiraFiltro.getPeriodoInicial(),
					fichaFinanceiraFiltro.getPeriodoFinal()));
			listCompetencias = folhaCompetenciaRepository.findByPeriodoString(listCompetenciasString);
			listCompetenciasString.add("13/" + Utils.getAno(fichaFinanceiraFiltro.getPeriodoFinal()).toString());
		}

		if (Objects.nonNull(listCompetencias) && !listCompetencias.isEmpty()) {

			listContrachequesFuncionario = contrachequeRepository.listByFuncionarioIdAndFolhaCompetencias(funcionarioId,
					listCompetencias);

			fichaFinanceiraResponse.setListFolhaCompetenciaResponse(listCompetenciasString.stream()
					.map(fc -> new FolhaCompetenciaResponse(fc)).collect(Collectors.toList()));
			List<Verba> verbasProventos = new ArrayList<>();
			List<Verba> verbasDescontos = new ArrayList<Verba>();
			List<Verba> verbasOutros = new ArrayList<Verba>();
			fichaFinanceiraResponse.setVerbasDescontos(new ArrayList<>());
			fichaFinanceiraResponse.setVerbasProventos(new ArrayList<>());
			fichaFinanceiraResponse.setVerbasOutros(new ArrayList<VerbaResponse>());
			fichaFinanceiraResponse.setValorBruto(0.0);
			fichaFinanceiraResponse.setValorDesconto(0.0);
			fichaFinanceiraResponse.setValorLiquido(0.0);

			ParametroGlobal parametroGlobal = parametroGlobalRepository
					.findByChaveAndAtivo(ParametroGlobalChaveEnum.FOLHA_13_SALARIO, true)
					.orElseThrow(() -> new ResourceNotFoundException("Parametro Global", "nome", "FOLHA_13_SALARIO"));

			List<Contracheque> fPCcheque13Salario = contrachequeRepository
					.findByFuncionarioIdAndFolhaPagamentoTipoProcessamentoId(funcionarioId,
							Long.valueOf(parametroGlobal.getValor()));

			if (Objects.nonNull(fPCcheque13Salario) && !fPCcheque13Salario.isEmpty()) {

			}

			for (Contracheque contracheque : listContrachequesFuncionario) {
				for (Lancamento verbaContracheque : contracheque.getLancamentos()) {
					if (verbaContracheque.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
						if (!verbasProventos.stream()
								.filter(v -> v.getId().equals(verbaContracheque.getVerba().getId())).findFirst()
								.isPresent()) {
							verbasProventos.add(new Verba(verbaContracheque.getVerba()));
							VerbaResponse verba = new VerbaResponse(verbaContracheque.getVerba());
							verba.setListMesesValores(new ArrayList<VerbaValorCompetenciaResponse>());

							verba.getListMesesValores()
									.add(new VerbaValorCompetenciaResponse(contracheque.getFolhaPagamento()
											.getFolhaCompetencia().getMesAnoCompetencia(),
											verbaContracheque.getValor()));
							fichaFinanceiraResponse.getVerbasProventos().add(verba);
						} else {
							Integer indexLocalizacao = getListVerbaResponseIndexByIdInList(
									fichaFinanceiraResponse.getVerbasProventos(), verbaContracheque.getVerba().getId());
							if (indexLocalizacao > -1) {
								fichaFinanceiraResponse.getVerbasProventos().get(indexLocalizacao).getListMesesValores()
										.add(new VerbaValorCompetenciaResponse(contracheque.getFolhaPagamento()
												.getFolhaCompetencia().getMesAnoCompetencia(),
												verbaContracheque.getValor()));
							}
						}
					} else if (verbaContracheque.getVerba().getTipoVerba().equals(TipoVerba.DESCONTO)) {
						if (!verbasDescontos.stream()
								.filter(v -> v.getId().equals(verbaContracheque.getVerba().getId())).findFirst()
								.isPresent()) {
							verbasDescontos.add(new Verba(verbaContracheque.getVerba()));
							VerbaResponse verba = new VerbaResponse(verbaContracheque.getVerba());
							verba.setListMesesValores(new ArrayList<VerbaValorCompetenciaResponse>());
							verba.getListMesesValores()
									.add(new VerbaValorCompetenciaResponse(contracheque.getFolhaPagamento()
											.getFolhaCompetencia().getMesAnoCompetencia(),
											verbaContracheque.getValor()));
							fichaFinanceiraResponse.getVerbasDescontos().add(verba);
						} else {
							Integer indexLocalizacao = getListVerbaResponseIndexByIdInList(
									fichaFinanceiraResponse.getVerbasDescontos(), verbaContracheque.getVerba().getId());
							if (indexLocalizacao > -1) {
								fichaFinanceiraResponse.getVerbasDescontos().get(indexLocalizacao).getListMesesValores()
										.add(new VerbaValorCompetenciaResponse(contracheque.getFolhaPagamento()
												.getFolhaCompetencia().getMesAnoCompetencia(),
												verbaContracheque.getValor()));
							}
						}
					} else if (verbaContracheque.getVerba().getTipoVerba().equals(TipoVerba.OUTROS)) {
						if (!verbasOutros.stream().filter(v -> v.getId().equals(verbaContracheque.getVerba().getId()))
								.findFirst().isPresent()) {
							verbasOutros.add(new Verba(verbaContracheque.getVerba()));
							VerbaResponse verba = new VerbaResponse(verbaContracheque.getVerba());
							verba.setListMesesValores(new ArrayList<VerbaValorCompetenciaResponse>());
							verba.getListMesesValores()
									.add(new VerbaValorCompetenciaResponse(contracheque.getFolhaPagamento()
											.getFolhaCompetencia().getMesAnoCompetencia(),
											verbaContracheque.getValor()));
							fichaFinanceiraResponse.getVerbasOutros().add(verba);
						} else {
							Integer indexLocalizacao = getListVerbaResponseIndexByIdInList(
									fichaFinanceiraResponse.getVerbasOutros(), verbaContracheque.getVerba().getId());
							if (indexLocalizacao > -1) {
								fichaFinanceiraResponse.getVerbasOutros().get(indexLocalizacao).getListMesesValores()
										.add(new VerbaValorCompetenciaResponse(contracheque.getFolhaPagamento()
												.getFolhaCompetencia().getMesAnoCompetencia(),
												verbaContracheque.getValor()));
							}
						}
					}
				}
				fichaFinanceiraResponse
						.setValorBruto(fichaFinanceiraResponse.getValorBruto() + contracheque.getValorBruto());
				fichaFinanceiraResponse
						.setValorLiquido(fichaFinanceiraResponse.getValorLiquido() + contracheque.getValorLiquido());
				fichaFinanceiraResponse
						.setValorDesconto(fichaFinanceiraResponse.getValorDesconto() + contracheque.getValorDesconto());
			}

			for (FolhaCompetenciaResponse fcr : fichaFinanceiraResponse.getListFolhaCompetenciaResponse()) {
				for (VerbaResponse vpr : fichaFinanceiraResponse.getVerbasProventos()) {
					vpr.getListMesesValores().stream().filter(mv -> mv.getMesAno().equals(fcr.getMesAno())).findFirst()
							.ifPresent(
									mv2 -> fcr.setValorTotalProventos(fcr.getValorTotalProventos() + mv2.getValor()));
				}

				for (VerbaResponse vdr : fichaFinanceiraResponse.getVerbasDescontos()) {
					vdr.getListMesesValores().stream().filter(mv -> mv.getMesAno().equals(fcr.getMesAno())).findFirst()
							.ifPresent(
									mv2 -> fcr.setValorTotalDescontos(fcr.getValorTotalDescontos() + mv2.getValor()));
				}
				fcr.setValorTotalLiquido(fcr.getValorTotalProventos() - fcr.getValorTotalDescontos());
			}

		} else {
			throw new ResourceNotFoundException("Competência", "Período",
					Objects.nonNull(fichaFinanceiraFiltro.getAno()) ? fichaFinanceiraFiltro.getAno().toString()
							: Utils.periodoString(fichaFinanceiraFiltro.getPeriodoInicial(),
									fichaFinanceiraFiltro.getPeriodoFinal()));
		}
		return fichaFinanceiraResponse;
	}

	public FichaFinanceiraResponse listaFolhaPagamentoPorFuncionarioIdEFolhaCompetenciaId(Long funcionarioId,
			Long folhaCompetenciaId) {
		List<Contracheque> listContrachequesFuncionario = contrachequeRepository
				.findByFuncionarioIdAndFolhaPagamentoFolhaCompetenciaId(funcionarioId, folhaCompetenciaId);
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		FichaFinanceiraResponse fichaFinanceiraResponse = new FichaFinanceiraResponse();
		fichaFinanceiraResponse.setFuncionario(
				new FuncionarioResponse(funcionario, funcionario.getLotacao(), funcionario.getVinculo()));
		fichaFinanceiraResponse.setLancamentosDescontos(new ArrayList<>());
		fichaFinanceiraResponse.setLancamentosVantagens(new ArrayList<>());
		fichaFinanceiraResponse.setLancamentosOutros(new ArrayList<>());
		fichaFinanceiraResponse.setValorBruto(0.0);
		fichaFinanceiraResponse.setValorDesconto(0.0);
		fichaFinanceiraResponse.setValorLiquido(0.0);
		if (Objects.nonNull(listContrachequesFuncionario) && !listContrachequesFuncionario.isEmpty()) {
			for (Contracheque contracheque : listContrachequesFuncionario) {
				for (Lancamento verbaContracheque : contracheque.getLancamentos()) {
					if (verbaContracheque.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
						fichaFinanceiraResponse.getLancamentosVantagens()
								.add(new LancamentoResponse(verbaContracheque));
					} else if (verbaContracheque.getVerba().getTipoVerba().equals(TipoVerba.DESCONTO)) {
						fichaFinanceiraResponse.getLancamentosDescontos()
								.add(new LancamentoResponse(verbaContracheque));
					} else if (verbaContracheque.getVerba().getTipoVerba().equals(TipoVerba.OUTROS)) {
						fichaFinanceiraResponse.getLancamentosOutros().add(new LancamentoResponse(verbaContracheque));
					}
				}
				fichaFinanceiraResponse
						.setValorBruto(fichaFinanceiraResponse.getValorBruto() + contracheque.getValorBruto());
				fichaFinanceiraResponse
						.setValorLiquido(fichaFinanceiraResponse.getValorLiquido() + contracheque.getValorLiquido());
				fichaFinanceiraResponse
						.setValorDesconto(fichaFinanceiraResponse.getValorDesconto() + contracheque.getValorDesconto());
			}
		}
		return fichaFinanceiraResponse;
	}

	public synchronized void processarContracheques(Long folhaPagamentoId) {

		// Iniciando criação de contracheques e posterior processamento assíncrono.

		FolhaPagamento folhaPagamento = fPagamentoRepository.findById(folhaPagamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("FolhaPagamento", "id", folhaPagamentoId));

		// Busca de verbas automáticas para serem usadas no processamento
//		List<Verba> verbasAutomaticas = tipoProcessamentoRepository
//				.findVerbasById(folhaPagamento.getTipoProcessamento().getId());

		// Busca os funcionários para criar os contracheques.
		List<Funcionario> funcionarios = funcionarioService
				.getFuncionariosAptosPraFolhaByFolhaPagamento(folhaPagamento);

		List<Long> funcionarioIds = new ArrayList<Long>();

		for (Funcionario funcionario : funcionarios) {

			funcionarioIds.add(funcionario.getId());

			boolean exists = contrachequeRepository.existsByFolhaPagamentoIdAndFuncionarioId(folhaPagamento.getId(),
					funcionario.getId());

			Contracheque contrachequeFunc = null;
			if (!exists) {
				contrachequeFunc = new Contracheque();
				contrachequeFunc.setFuncionario(funcionario);
				contrachequeFunc.setFolhaPagamento(folhaPagamento);

			} else {
				contrachequeFunc = contrachequeRepository
						.findByFolhaPagamentoIdAndFuncionarioId(folhaPagamento.getId(), funcionario.getId()).get();

				// Deletando as verbas / lançamentos para processar novamente o contracheque
				lancamentoRepository.deleteByContracheque(contrachequeFunc);
			}

			// Preencher demais dados do contracheque
			atualizarDadosContracheque(contrachequeFunc);

			// Processa o contracheque do funcionário
//				lancamentoService.processarLancamentos(contrachequeFunc, verbasAutomaticas);
			lancamentoService.processarLancamentos(contrachequeFunc);

		}

		// Apagando os contracheques de funcionários que não estão na lista
		List<Contracheque> contrachFuncToDelete;

		if (funcionarioIds.isEmpty())
			contrachFuncToDelete = contrachequeRepository.findByFolhaPagamentoAndFuncionarioIsNotNull(folhaPagamento);
		else
			contrachFuncToDelete = contrachequeRepository.findByFolhaPagamentoAndFuncionarioIdNotIn(folhaPagamento,
					funcionarioIds);

		if (Objects.nonNull(contrachFuncToDelete)) {
			for (Contracheque contrachequeFuncDelete : contrachFuncToDelete) {
				lancamentoRepository.deleteByContracheque(contrachequeFuncDelete);
				contrachequeRepository.delete(contrachequeFuncDelete);
			}
		}

		// Busca os Pensionistas aptos para criar os contracheques.
		List<Pensionista> pensionistas = pensionistaService
				.getPensionistasAptosPraFolhaByFolhaPagamento(folhaPagamento);

		List<Long> pensionistasId = new ArrayList<Long>();

		for (int i = 0; i < pensionistas.size(); i++) {

			Pensionista pensionista = pensionistas.get(i);

			pensionistasId.add(pensionista.getId());

			Contracheque contrachequePens = null;

			boolean exists = contrachequeRepository.existsByFolhaPagamentoIdAndPensionistaId(folhaPagamento.getId(),
					pensionista.getId());

			if (!exists) {
				contrachequePens = new Contracheque();
				contrachequePens.setPensionista(pensionista);
				contrachequePens.setFolhaPagamento(folhaPagamento);

			} else {
				contrachequePens = contrachequeRepository
						.findByFolhaPagamentoIdAndPensionistaId(folhaPagamento.getId(), pensionista.getId()).get();

				// Deletando as verbas / lançamentos para processar novamente o contracheque
				lancamentoRepository.deleteByContracheque(contrachequePens);
			}

			// Preencher demais dados do contracheque
			atualizarDadosContracheque(contrachequePens);

			// Processa o contracheque do pensionista
//			lancamentoService.processarLancamentos(contrachequePens, verbasAutomaticas);
			lancamentoService.processarLancamentos(contrachequePens);

		}

		// Apagando os contracheques de pensionistas que não estão na lista
		List<Contracheque> contrachPensToDelete;

		if (pensionistasId.isEmpty())
			contrachPensToDelete = contrachequeRepository.findByFolhaPagamentoAndPensionistaIsNotNull(folhaPagamento);
		else
			contrachPensToDelete = contrachequeRepository.findByFolhaPagamentoAndPensionistaIdNotIn(folhaPagamento,
					pensionistasId);

		if (contrachPensToDelete.size() > 0) {
			for (Contracheque contrachequePens : contrachPensToDelete) {
				lancamentoRepository.deleteByContracheque(contrachequePens);
				contrachequeRepository.delete(contrachequePens);
			}
		}

		// Seta situação de processamento da folha.
		boolean existProblem = contrachequeRepository.existsByFolhaPagamentoIdAndSituacaoNot(folhaPagamento.getId(),
				SituacaoProcessamentoEnum.CONCLUIDO);

		if (existProblem)
			folhaPagamento.setSituacao(SituacaoProcessamentoEnum.FALHO);
		else
			folhaPagamento.setSituacao(SituacaoProcessamentoEnum.CONCLUIDO);

		fPagamentoRepository.save(folhaPagamento);

	}

	private void atualizarDadosContracheque(Contracheque contracheque) {

		contracheque.setSituacao(SituacaoProcessamentoEnum.PENDENTE);

		if (Objects.nonNull(contracheque.getFuncionario())) {

			// Atualiza as informações de dependentes do funcionário
			dependenteService.atualizarDependente(contracheque.getFuncionario());

			contracheque.setNome(contracheque.getFuncionario().getNome());
			contracheque.setMatricula(contracheque.getFuncionario().getMatricula());
			if (!Objects.isNull(contracheque.getFuncionario().getLotacao()))
				contracheque.setLotacao(contracheque.getFuncionario().getLotacao().getDescricao());
			if (!Objects.isNull(contracheque.getFuncionario().getMunicipio()))
				contracheque.setMunicipio(contracheque.getFuncionario().getMunicipio().getDescricao());
			contracheque.setDataAdmissao(contracheque.getFuncionario().getDataAdmissao());
			if (!Objects.isNull(contracheque.getFuncionario().getCargo()))
				contracheque.setCargoEfetivo(contracheque.getFuncionario().getCargo().getNome());
			contracheque.setDataNascimento(contracheque.getFuncionario().getDataNascimento());
			if (!Objects.isNull(contracheque.getFuncionario().getFuncao()))
				contracheque.setCargoFuncao(contracheque.getFuncionario().getFuncao().getDescricao());
//			contracheque.setNivel(contracheque.getFuncionario().get);
//			contracheque.setReferencia(contracheque.getFuncionario().getReferencia());
			if (!Objects.isNull(contracheque.getFuncionario().getVinculo()))
				contracheque.setVinculo(contracheque.getFuncionario().getVinculo().getDescricao());
			if (!Objects.isNull(contracheque.getFuncionario().getTipoSituacaoFuncional()))
				contracheque.setTipoSituacaoFuncional(
						contracheque.getFuncionario().getTipoSituacaoFuncional().getDescricao());
			contracheque.setIdentidade(contracheque.getFuncionario().getIdentidade());
			contracheque.setCpf(contracheque.getFuncionario().getCpf());
			if (!Objects.isNull(contracheque.getFuncionario().getFilial()))
				contracheque.setOrgaoPagador(contracheque.getFuncionario().getFilial().getNomeFilial());
			contracheque.setDepSf(contracheque.getFuncionario().getNumeroDependentesSalarioFamilia());
			contracheque.setDepIr(contracheque.getFuncionario().getNumeroDependentesImpostoRenda());
			if (!Objects.isNull(contracheque.getFuncionario().getCargaHoraria()))
				contracheque.setCargaHoraria(contracheque.getFuncionario().getCargaHoraria().getLabel());

		} else {

			contracheque.setNome(contracheque.getPensionista().getNome());
			contracheque.setMatricula(contracheque.getPensionista().getMatricula());
			contracheque.setIdentidade(contracheque.getPensionista().getIdentidade());
			contracheque.setCpf(contracheque.getPensionista().getCpf());
			contracheque.setTipoSituacaoFuncional("PENSIONISTA");
			contracheque.setDataAdmissao(contracheque.getPensionista().getPensaoPagamento().getDataPrimeiroPagamento()
					.atStartOfDay(ZoneId.systemDefault()).toInstant());
			contracheque.setDataNascimento(
					contracheque.getPensionista().getDataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant());
//			if (!Objects.isNull(pensionista.getLotacao()))
//				contracheque.setLotacao(pensionista.getLotacao().getDescricao());
//			if (!Objects.isNull(pensionista.getMunicipio()))
//				contracheque.setMunicipio(pensionista.getMunicipio().getDescricao());
//			if (!Objects.isNull(pensionista.getCargo()))
//				contracheque.setCargoEfetivo(pensionista.getCargo().getNome());
//			contracheque.setNivel(pensionista.get);
//			contracheque.setCargoFuncao(pensionista.getFuncao().getDescricao());
//			contracheque.setReferencia(pensionista.getReferencia());
//			if (!Objects.isNull(pensionista.getVinculo()))
//				contracheque.setVinculo(pensionista.getVinculo().getDescricao());
//			if (!Objects.isNull(pensionista.getFilial()))
//				contracheque.setOrgaoPagador(pensionista.getFilial().getNomeFilial());
//			contracheque.setDepSf(pensionista.getNumeroDependentesSalarioFamilia());
//			contracheque.setDepIr(pensionista.getNumeroDependentesImpostoRenda());
//			if (!Objects.isNull(pensionista.getCargaHoraria()))
//				contracheque.setCargaHoraria(pensionista.getCargaHoraria().getLabel());
		}

		// Salvar contracheque
		contrachequeRepository.save(contracheque);

	}

	public void deleteContracheque(Long contrachequeId) {

		// Busca o contracheque para exclusão
		Contracheque contracheque = contrachequeRepository.findById(contrachequeId)
				.orElseThrow(() -> new ResourceNotFoundException("Contracheque", "id", contrachequeId));

		// Pegando a folha de pagamento do contracheque para análise de status e
		// situação
		FolhaPagamento folhaPagamento = contracheque.getFolhaPagamento();

		// Efetuando validação de status da folha
		if (folhaPagamento.getStatus().equals(StatusFolhaEnum.BLOQUEADO))
			throw new BadRequestException(
					"Não é possível recalcular contracheques de uma folha de pagamento bloqueada.");

		// Efetuando validação da Situação da folha
		if (folhaPagamento.getSituacao().equals(SituacaoProcessamentoEnum.PENDENTE))
			throw new BadRequestException(
					"Não é possível recalcular contracheques de uma folha de pagamento com outro recalculo em andamento.");

		// Apaga o contracheque em questão
		lancamentoRepository.deleteByContracheque(contracheque);
		contrachequeRepository.delete(contracheque);

		// Atualizando o número de processamentos da folha, diminuindo o contracheque
		// que acabou de ser excluído
		folhaPagamento.setProcessamentos(folhaPagamento.getProcessamentos() - 1);
		fPagamentoRepository.save(folhaPagamento);
	}

	public List<ContrachequeResponse> listaTodosPorFolhaIdNaoPaginado(ContrachequeFiltro filtro) {
		List<Contracheque> lista = contrachequeRepository.filtro(filtro);
		List<ContrachequeResponse> listaResponse = new ArrayList<ContrachequeResponse>();
		for (Contracheque fv : lista) {
			ContrachequeResponse funcionarioResponse = new ContrachequeResponse(fv);

			funcionarioResponse.setLancamentosVantagens(new ArrayList<LancamentoResponse>());
			funcionarioResponse.setLancamentosDescontos(new ArrayList<LancamentoResponse>());
			funcionarioResponse.setLancamentosOutros(new ArrayList<LancamentoResponse>());

			for (Lancamento lancamento : fv.getLancamentos()) {
				if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.OUTROS))
					funcionarioResponse.getLancamentosOutros().add(new LancamentoResponse(lancamento));
				else if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM))
					funcionarioResponse.getLancamentosVantagens().add(new LancamentoResponse(lancamento));
				else
					funcionarioResponse.getLancamentosDescontos().add(new LancamentoResponse(lancamento));
			}
			listaResponse.add(funcionarioResponse);
		}

		return listaResponse;
	}

	public List<Contracheque> listaTodosPorFolhaId(Long id) {
		return contrachequeRepository.findByFolhaPagamentoId(id);
	}

	// Relatório Gerencial Por Filial
	public Page<RelatorioGerencialDto> getRelGerencialFilialByTipoProcessAndFolhaCompId(Long tipoProcessamentoId,
			Long folhaCompetenciaId, Pageable pageable) {
		return contrachequeRepository.getRelGerencialFilialByTipoProcessAndFolhaCompId(tipoProcessamentoId,
				folhaCompetenciaId, pageable);
	}

	public List<RelatorioGerencialDto> getRelGerencialFilialByTipoProcessAndFolhaCompId(Long tipoProcessamentoId,
			Long folhaCompetenciaId) {
		return contrachequeRepository.getRelGerencialFilialByTipoProcessAndFolhaCompId(tipoProcessamentoId,
				folhaCompetenciaId);
	}

	public RelatorioGerencialDto getRelGerencialFilialByTipoProcessAndFolhaCompIdAndFilialId(Long tipoProcessamentoId,
			Long folhaCompetenciaId, Long filialId) {
		return contrachequeRepository.getRelGerencialFilialByTipoProcessAndFolhaCompIdAndFilialId(tipoProcessamentoId,
				folhaCompetenciaId, filialId);
	}

	// Relatório Gerencial Por Cargo
	public Page<RelatorioGerencialDto> getRelGerencialCargoByFolhaPagamentoId(Long folhaPagamentoId, String lotacao,
			Pageable pageable) {
		return contrachequeRepository.getRelGerencialCargoByFolhaPagamentoId(folhaPagamentoId, lotacao, pageable);
	}

	public List<RelatorioGerencialDto> getRelGerencialCargoByFolhaPagamentoId(Long folhaPagamentoId, String lotacao) {
		return contrachequeRepository.getRelGerencialCargoByFolhaPagamentoId(folhaPagamentoId, lotacao);
	}

	public RelatorioGerencialDto getRelGerencialCargoByFolhaPagamentoIdAndCargo(Long folhaPagamentoId, String lotacao,
			String cargoEfetivo) {
		return contrachequeRepository.getRelGerencialCargoByFolhaPagamentoIdAndCargo(folhaPagamentoId, lotacao,
				cargoEfetivo);
	}

	// Relatório Gerencial Por Lotação
	public Page<RelatorioGerencialDto> getRelGerencialLotacaoByFolhaPagamentoId(Long folhaPagamentoId,
			Pageable pageable) {
		return contrachequeRepository.getRelGerencialLotacaoByFolhaPagamentoId(folhaPagamentoId, pageable);
	}

	public List<RelatorioGerencialDto> getRelGerencialLotacaoByFolhaPagamentoId(Long folhaPagamentoId) {
		return contrachequeRepository.getRelGerencialLotacaoByFolhaPagamentoId(folhaPagamentoId);
	}

	public RelatorioGerencialDto getRelGerencialLotacaoByFolhaPagamentoIdAndLotacao(Long folhaPagamentoId,
			String lotacao) {
		return contrachequeRepository.getRelGerencialLotacaoByFolhaPagamentoIdAndLotacao(folhaPagamentoId, lotacao);
	}

	// Relatório Gerencial Por Função
	public Page<RelatorioGerencialDto> getRelGerencialFuncaoByFolhaPagamentoId(Long folhaPagamentoId, String lotacao,
			Pageable pageable) {
		return contrachequeRepository.getRelGerencialFuncaoByFolhaPagamentoId(folhaPagamentoId, lotacao, pageable);
	}

	public List<RelatorioGerencialDto> getRelGerencialFuncaoByFolhaPagamentoId(Long folhaPagamentoId, String lotacao) {
		return contrachequeRepository.getRelGerencialFuncaoByFolhaPagamentoId(folhaPagamentoId, lotacao);
	}

	public RelatorioGerencialDto getRelGerencialFuncaoByFolhaPagamentoIdAndFuncao(Long folhaPagamentoId, String lotacao,
			String cargoFuncao) {
		return contrachequeRepository.getRelGerencialFuncaoByFolhaPagamentoIdAndFuncao(folhaPagamentoId, lotacao,
				cargoFuncao);
	}

	// Relatório Gerencial Por Vínculo
	public Page<RelatorioGerencialDto> getRelGerencialVinculoByFolhaPagamentoId(Long folhaPagamentoId, String lotacao,
			Pageable pageable) {
		return contrachequeRepository.getRelGerencialVinculoByFolhaPagamentoId(folhaPagamentoId, lotacao, pageable);
	}

	public List<RelatorioGerencialDto> getRelGerencialVinculoByFolhaPagamentoId(Long folhaPagamentoId, String lotacao) {
		return contrachequeRepository.getRelGerencialVinculoByFolhaPagamentoId(folhaPagamentoId, lotacao);
	}

	public RelatorioGerencialDto getRelGerencialVinculoByFolhaPagamentoIdAndVinculo(Long folhaPagamentoId,
			String lotacao, String vinculo) {
		return contrachequeRepository.getRelGerencialVinculoByFolhaPagamentoIdAndVinculo(folhaPagamentoId, lotacao,
				vinculo);
	}

	// Relatório Gerencial Por Funcionário
	public Page<RelatorioGerencialDto> getRelGerencialFuncionarioByFolhaPagamentoId(Long folhaPagamentoId,
			String cargoEfetivo, String cargoFuncao, String lotacao, Pageable pageable) {
		return contrachequeRepository.getRelGerencialFuncionarioByFolhaPagamentoId(folhaPagamentoId, cargoEfetivo,
				cargoFuncao, lotacao, pageable);
	}

	public List<RelatorioGerencialDto> getRelGerencialFuncionarioByFolhaPagamentoId(Long folhaPagamentoId,
			String cargoEfetivo, String cargoFuncao, String lotacao) {
		return contrachequeRepository.getRelGerencialFuncionarioByFolhaPagamentoId(folhaPagamentoId, cargoEfetivo,
				cargoFuncao, lotacao);
	}

	public RelatorioGerencialDto getRelGerencialFuncionarioByFolhaPagamentoIdAndFuncionarioId(Long folhaPagamentoId,
			String cargoEfetivo, String cargoFuncao, String lotacao, Long funcionarioId) {
		return contrachequeRepository.getRelGerencialFuncionarioByFolhaPagamentoIdAndFuncionarioId(folhaPagamentoId,
				cargoEfetivo, cargoFuncao, lotacao, funcionarioId);
	}

	// Relatório Gerencial - Somatório
	public RelatorioGerencialDto getRelGerencialFilialByTipoProcessAndFolhaCompIdSomatorio(Long tipoProcessamentoId,
			Long folhaCompetenciaId) {
		return contrachequeRepository.getRelGerencialFilialByTipoProcessAndFolhaCompIdSomatorio(tipoProcessamentoId,
				folhaCompetenciaId);
	}

	public RelatorioGerencialDto getRelGerencialByFolhaPagamentoIdSomatorio(Long folhaPagamentoId, String cargoEfetivo,
			String cargoFuncao, String lotacao, String vinculo) {
		return contrachequeRepository.getRelGerencialByFolhaPagamentoIdSomatorio(folhaPagamentoId, cargoEfetivo,
				cargoFuncao, lotacao, vinculo);
	}

	public Integer getListVerbaResponseIndexByIdInList(List<VerbaResponse> listverbasResponse, Long id) {
		for (VerbaResponse verbaResponse : listverbasResponse) {
			if (verbaResponse.getId().equals(id)) {
				return listverbasResponse.indexOf(verbaResponse);
			}
		}
		return -1;
	}

	public ProjecaoSomatorioValores getSomatorioValoresOrgao(Integer mes, Integer ano, Long tipoProcessamentoId) {
		return contrachequeRepository.getSomatorioValoresOrgao(mes, ano, tipoProcessamentoId);
	}

	public List<ProjecaoSomatorioValoresFiliais> getSomatorioValoresFiliais(Integer mes, Integer ano,
			Long tipoProcessamentoId) {
		return contrachequeRepository.getSomatorioValoresFiliais(mes, ano, tipoProcessamentoId);
	}

	public List<ContrachequeResponse> getAllByFiltroCompetencia(Long competenciaId, Long tipoProcessamentoId, Long id) {
		List<Contracheque> lista = contrachequeRepository
				.findAllByFolhaPagamentoFolhaCompetenciaIdAndFolhaPagamentoTipoProcessamentoIdAndFolhaPagamentoFilialId(
						competenciaId, tipoProcessamentoId, id);

		List<ContrachequeResponse> listaResponse = new ArrayList<>();
		for (Contracheque f : lista) {
			listaResponse.add(new ContrachequeResponse(f));
		}
		return listaResponse;
	}

	public List<Contracheque> getAllByFiltroCompetenciaNoResponse(Long competenciaId, Long tipoProcessamentoId,
			Long id) {
		return contrachequeRepository
				.findAllByFolhaPagamentoFolhaCompetenciaIdAndFolhaPagamentoTipoProcessamentoIdAndFolhaPagamentoFilialId(
						competenciaId, tipoProcessamentoId, id);
	}

	public List<ProjecaoDirfBeneficiario> getProjecaoDirfFuncionarioContracheque(Integer anoBase, Long filialId,
			Long funcionarioId) {
		return contrachequeRepository.getProjecaoDirfFuncionarioContracheque(anoBase, filialId, funcionarioId);
	}

	public List<ProjecaoDirfBeneficiario> getProjecaoDirfFuncionarioPensionista(Integer anoBase, Long filialId,
			String query) {
		return contrachequeRepository.getProjecaoDirfFuncionarioPensionista(anoBase, filialId, query);
	}

	public List<BatimentoFuncionarioCountDto> getCountTotalFuncionariosPorLotacao(Long competenciaId,
			Long tipoProcessamentoId, Long filialId) {
		return contrachequeRepository.getCountTotalFuncionariosPorLotacao(competenciaId, tipoProcessamentoId, filialId);
	}

	public List<BatimentoFuncionarioCountDto> getCountTotalFuncionariosPorSituacao(Long competenciaId,
			Long tipoProcessamentoId, Long filialId) {
		return contrachequeRepository.getCountTotalFuncionariosPorSituacao(competenciaId, tipoProcessamentoId,
				filialId);
	}

	public List<RelatorioFolhaPagamentoResumoFilialDto> getDadosContrachequeByFiltros(ArrayList<Long> filialList,
			ArrayList<String> situacaoFuncionalList, Integer ano, Integer competencia, Long tipoProcessamentoId) {

		if (situacaoFuncionalList != null) {
			return contrachequeRepository.getDadosContrachequeByFiltros(filialList, situacaoFuncionalList, ano,
					competencia, tipoProcessamentoId);
		} else {
			return contrachequeRepository.getDadosContrachequeByFiltros(filialList, ano, competencia,
					tipoProcessamentoId);
		}

	}

	public void adicionarFuncionario(ContrachequeRequest request) {

		FolhaPagamento folha = fPagamentoRepository.findById(request.getFolhaPagamentoId())
				.orElseThrow(() -> new ResourceNotFoundException("Folha", "id", request.getFolhaPagamentoId()));

		if (folha.getStatus().equals(StatusFolhaEnum.BLOQUEADO))
			throw new ServiceException("Não é possível incluir um funcionário em uma folha de pagamento bloqueada.");

		// Efetuando validação da Situação da folha
		if (folha.getSituacao().equals(SituacaoProcessamentoEnum.PENDENTE))
			throw new BadRequestException(
					"Não é possível incluir um funcionário em uma folha de pagamento com outro recalculo em andamento.");

		if (!request.getFuncionariosId().isEmpty()) {

			List<Funcionario> funcionarios = funcionarioRepository.findAllById(request.getFuncionariosId());

			// Busca de verbas automáticas para processamento.

			List<Long> contrachequeIds = new ArrayList<Long>();

			// Iniciando criação ou busca de contracheques e posterior processamento.
			for (Funcionario funcionario : funcionarios) {

				if (!funcionario.getFilial().getId().equals(folha.getFilial().getId())) {
					throw new ServiceException(
							"Não é possível incluir um funcionário que não seja da mesma filial da folha de pagamento.");
				}

				boolean exists = contrachequeRepository.existsByFolhaPagamentoIdAndFuncionarioId(folha.getId(),
						funcionario.getId());

				Contracheque contrachequeFunc = new Contracheque();
				if (!exists) {
					contrachequeFunc.setFuncionario(funcionario);
					contrachequeFunc.setFolhaPagamento(folha);
					contrachequeRepository.save(contrachequeFunc);

				} else {
					contrachequeFunc = contrachequeRepository
							.findByFolhaPagamentoIdAndFuncionarioId(folha.getId(), funcionario.getId()).get();

				}

				contrachequeIds.add(contrachequeFunc.getId());

			}

			// Chama a rotina de recalculo por contracheque, que da conta do processamento.
			recalcularContracheque(ContrachequeRecalcularDto.builder().contrachequeIds(contrachequeIds).build());

		}

	}

}