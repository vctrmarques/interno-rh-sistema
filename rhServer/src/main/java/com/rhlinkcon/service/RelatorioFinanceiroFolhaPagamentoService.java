package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.RHServiceException;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AnexoPastaEnum;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.Lancamento;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.RelatorioFinanceiroFolhaPagamento;
import com.rhlinkcon.model.RelatorioFinanceiroFolhaPagamentoHistorico;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.model.StatusRelatorioFinanceiroEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.ExportRelatorioFinanceiroFuncionarioPensionista;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.ExportRelatorioFinanceiroSituacaoFuncional;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.RelatorioFinanceiroFolhaPagamentoRequest;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.RelatorioFinanceiroFolhaPagamentoResponse;
import com.rhlinkcon.report.service.RelatorioFinanceiroPdfReportService;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.FolhaCompetenciaRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.RelatorioFinanceiroFolhaPagamentoHistoricoRepository;
import com.rhlinkcon.repository.RelatorioFinanceiroFolhaPagamentoRepository;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.contracheque.ContrachequeRepository;
import com.rhlinkcon.repository.folhaPagamento.FolhaPagamentoRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioFinanceiroFolhaPagamentoService {

	@Autowired
	private RelatorioFinanceiroFolhaPagamentoRepository relatorioFinancRepository;

	@Autowired
	private RelatorioFinanceiroFolhaPagamentoHistoricoRepository relatorioFinancHistoricoRepository;

	@Autowired
	private FolhaCompetenciaRepository folhaCompetenciaRepository;

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;

	@Autowired
	private LotacaoRepository lotacaoRepository;

	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	@Autowired
	private AnexoService anexoService;

	@Autowired
	private FolhaPagamentoRepository folhaPagamentoRepository;

	@Autowired
	private ContrachequeRepository contrachequeRepository;

	@Autowired
	private RelatorioFinanceiroPdfReportService pdfReportService;

	@Autowired
	private FuncionarioService funcionarioService;

	public RelatorioFinanceiroFolhaPagamentoResponse getById(Long id) {
		RelatorioFinanceiroFolhaPagamento relatorioFinanc = relatorioFinancRepository.getOne(id);

		return new RelatorioFinanceiroFolhaPagamentoResponse(relatorioFinanc, Projecao.COMPLETA);
	}

	public RelatorioFinanceiroFolhaPagamentoResponse create(
			RelatorioFinanceiroFolhaPagamentoRequest relatorioFinancRequest) {
		StatusRelatorioFinanceiroEnum statusRelatorioFinanceiroEnum = StatusRelatorioFinanceiroEnum
				.getEnumByString(relatorioFinancRequest.getStatus());
		RelatorioFinanceiroFolhaPagamento relatorioFinanc = new RelatorioFinanceiroFolhaPagamento();
		if (Objects.nonNull(statusRelatorioFinanceiroEnum)) {

			boolean exists = folhaPagamentoRepository
					.existsByFolhaCompetenciaId(relatorioFinancRequest.getCompetencia());
			if (!exists) {
				throw new BadRequestException(
						"Não é possível criar relatório para competetência sem folha de pagamento.!");
			}

			boolean existsNaoConcluido = folhaPagamentoRepository.existsByFolhaCompetenciaIdAndSituacaoNot(
					relatorioFinancRequest.getCompetencia(), SituacaoProcessamentoEnum.CONCLUIDO);
			if (existsNaoConcluido) {
				throw new BadRequestException(
						"Não é possível criar relatório para competetência com folhas de pagamento não concluídas.!");
			}

			if (statusRelatorioFinanceiroEnum.equals(StatusRelatorioFinanceiroEnum.PREVIA)) {
				Optional<FolhaCompetencia> competencia = folhaCompetenciaRepository
						.findFirstByFimCompetenciaIsNullOrderByAnoCompetenciaDescMesCompetenciaDesc();
				if (competencia.isPresent()
						&& competencia.get().getId().equals(relatorioFinancRequest.getCompetencia())) {

					relatorioFinanc.setFolhaCompetencia(competencia.get());
					relatorioFinanc.setDescricao(
							relatorioFinancRequest.getDescricao() + Utils.getHashStringLocalDateTimeNow());
					Set<SituacaoFuncional> listSituacaoFuncionais = relatorioFinancRequest.getSituacoesFuncionais()
							.stream().map(situacaoId -> situacaoFuncionalRepository.getOne(situacaoId))
							.collect(Collectors.toSet());
					relatorioFinanc.setSituacoesFuncionais(listSituacaoFuncionais);

					Set<EmpresaFilial> listFiliais = relatorioFinancRequest.getFiliais().stream()
							.map(filialId -> empresaFilialRepository.getOne(filialId)).collect(Collectors.toSet());
					relatorioFinanc.setFiliais(listFiliais);

					Set<Lotacao> listLotacoes = relatorioFinancRequest.getLotacoes().stream()
							.map(lotId -> lotacaoRepository.getOne(lotId)).collect(Collectors.toSet());
					relatorioFinanc.setLotacoes(listLotacoes);

					// Carregando dependencias da criação do anexo
					byte[] content = createRelatorio(statusRelatorioFinanceiroEnum, relatorioFinanc);
					String pasta = AnexoPastaEnum.RELATORIO_FINANCEIRO_FOLHA_PAGAMENTO.getPasta();
					String fileName = relatorioFinanc.getDescricao() + ".pdf";
					String contentType = "application/pdf";

					// Criando anexo
					Anexo anexo = anexoService.createAnexo(content, fileName, contentType, pasta, null);

					// Gravando o anexo no objeto principal
					relatorioFinanc.setAnexo(anexo);

					// Salvando
					relatorioFinancRepository.save(relatorioFinanc);

				}
			} else {
				relatorioFinanc.setFolhaCompetencia(
						folhaCompetenciaRepository.findById(relatorioFinancRequest.getCompetencia()).get());
				relatorioFinanc
						.setDescricao(relatorioFinancRequest.getDescricao() + Utils.getHashStringLocalDateTimeNow());
				Set<SituacaoFuncional> listSituacaoFuncionais = relatorioFinancRequest.getSituacoesFuncionais().stream()
						.map(situacaoId -> situacaoFuncionalRepository.getOne(situacaoId)).collect(Collectors.toSet());
				relatorioFinanc.setSituacoesFuncionais(listSituacaoFuncionais);

				Set<EmpresaFilial> listFiliais = relatorioFinancRequest.getFiliais().stream()
						.map(filialId -> empresaFilialRepository.getOne(filialId)).collect(Collectors.toSet());
				relatorioFinanc.setFiliais(listFiliais);

				Set<Lotacao> listLotacoes = relatorioFinancRequest.getLotacoes().stream()
						.map(lotId -> lotacaoRepository.getOne(lotId)).collect(Collectors.toSet());
				relatorioFinanc.setLotacoes(listLotacoes);

				// Carregando dependencias da criação do anexo
				byte[] content = createRelatorio(statusRelatorioFinanceiroEnum, relatorioFinanc);
				String pasta = AnexoPastaEnum.RELATORIO_FINANCEIRO_FOLHA_PAGAMENTO.getPasta();
				String fileName = relatorioFinanc.getDescricao() + ".pdf";
				String contentType = "application/pdf";

				// Criando anexo
				Anexo anexo = anexoService.createAnexo(content, fileName, contentType, pasta, null);

				// Gravando o anexo no objeto principal
				relatorioFinanc.setAnexo(anexo);

				// Salvando
				relatorioFinancRepository.save(relatorioFinanc);

			}

			RelatorioFinanceiroFolhaPagamentoHistorico historico = new RelatorioFinanceiroFolhaPagamentoHistorico();
			historico.setRelatorioFinanceiroFolhaPagamento(relatorioFinanc);
			historico.setStatus(statusRelatorioFinanceiroEnum);
			relatorioFinancHistoricoRepository.save(historico);
			relatorioFinanc.setHistoricoAtual(historico);
			relatorioFinancRepository.save(relatorioFinanc);
		}
		relatorioFinanc = relatorioFinancRepository.findById(relatorioFinanc.getId()).get();

		return new RelatorioFinanceiroFolhaPagamentoResponse(relatorioFinanc, Projecao.BASICA);
	}

	public byte[] createRelatorio(StatusRelatorioFinanceiroEnum statusRelatorioFinanceiroEnum,
			RelatorioFinanceiroFolhaPagamento relatorioFinanc) {

		List<ExportRelatorioFinanceiroSituacaoFuncional> list = montarDadosExportarRelatorio(
				statusRelatorioFinanceiroEnum, relatorioFinanc);

		return pdfReportService.gerarPdf(relatorioFinanc, list);

	}

	public RelatorioFinanceiroFolhaPagamentoResponse alterarSituacao(Long relatorioFinanceiroId, String statusString) {
		RelatorioFinanceiroFolhaPagamento relatorioFinanc = relatorioFinancRepository.getOne(relatorioFinanceiroId);

		if (!relatorioFinanc.getHistoricoAtual().getStatus().equals(StatusRelatorioFinanceiroEnum.SALVO)) {
			RelatorioFinanceiroFolhaPagamentoHistorico historico = new RelatorioFinanceiroFolhaPagamentoHistorico();
			historico.setRelatorioFinanceiroFolhaPagamento(relatorioFinanc);
			historico.setStatus(StatusRelatorioFinanceiroEnum.getEnumByString(statusString));
			relatorioFinancHistoricoRepository.save(historico);
			relatorioFinanc.setHistoricoAtual(historico);
			relatorioFinancRepository.save(relatorioFinanc);
		} else {
			throw new ServiceException("A situação do relatório não pode estar como Calculado.");
		}

		return new RelatorioFinanceiroFolhaPagamentoResponse(relatorioFinanc, Projecao.BASICA);
	}

	public RelatorioFinanceiroFolhaPagamentoResponse inserirSituacaoSalvo(Long id) {
		RelatorioFinanceiroFolhaPagamento relatorioFinanc = relatorioFinancRepository.getOne(id);
		if (relatorioFinanc.getHistoricoAtual().getStatus().equals(StatusRelatorioFinanceiroEnum.CALCULADA)) {
			RelatorioFinanceiroFolhaPagamentoHistorico historico = new RelatorioFinanceiroFolhaPagamentoHistorico();
			historico.setRelatorioFinanceiroFolhaPagamento(relatorioFinanc);
			historico.setStatus(StatusRelatorioFinanceiroEnum.SALVO);
			relatorioFinancHistoricoRepository.save(historico);
			relatorioFinanc.setHistoricoAtual(historico);
			relatorioFinancRepository.save(relatorioFinanc);
		} else {
			throw new ServiceException("A situação do relatório precisa estar como Calculado.");
		}
		return new RelatorioFinanceiroFolhaPagamentoResponse(relatorioFinanc, Projecao.BASICA);
	}

	public RelatorioFinanceiroFolhaPagamentoResponse update(
			RelatorioFinanceiroFolhaPagamentoRequest relatorioFinancRequest) {

		return new RelatorioFinanceiroFolhaPagamentoResponse();
	}

	@Transactional
	public void delete(Long id) {
		RelatorioFinanceiroFolhaPagamento relatorioFinanc = relatorioFinancRepository.getOne(id);

		if (!relatorioFinanc.getHistoricoAtual().getStatus().equals(StatusRelatorioFinanceiroEnum.SALVO)) {
			Long anexoId = relatorioFinanc.getAnexo().getId();
			relatorioFinanc.setLotacoes(null);
			relatorioFinanc.setSituacoesFuncionais(null);
			relatorioFinanc.setFiliais(null);
			relatorioFinanc.setHistoricoAtual(null);
			relatorioFinancRepository.save(relatorioFinanc);
			relatorioFinancHistoricoRepository.deleteByRelatorioFinanceiroFolhaPagamentoId(id);
			relatorioFinancRepository.deleteById(id);
			anexoService.deleteAnexo(anexoId);
		} else {
			throw new RHServiceException("Você não pode apagar um Relatório com Situação SALVO.");
		}
	}

	public List<RelatorioFinanceiroFolhaPagamentoResponse> getRelatorioFinanceirosNaoSalvo() {
		List<StatusRelatorioFinanceiroEnum> listSituacao = new ArrayList<StatusRelatorioFinanceiroEnum>();
		listSituacao.add(StatusRelatorioFinanceiroEnum.PREVIA);
		listSituacao.add(StatusRelatorioFinanceiroEnum.CALCULADA);
		List<RelatorioFinanceiroFolhaPagamentoResponse> list = new ArrayList<RelatorioFinanceiroFolhaPagamentoResponse>();
		List<RelatorioFinanceiroFolhaPagamento> listRelatorios = relatorioFinancRepository
				.findByHistoricoAtualStatusIn(listSituacao);
		if (!listRelatorios.isEmpty()) {
			list = listRelatorios.stream()
					.map(relatorio -> new RelatorioFinanceiroFolhaPagamentoResponse(relatorio, Projecao.BASICA))
					.collect(Collectors.toList());
		}
		return list;
	}

	public List<RelatorioFinanceiroFolhaPagamentoResponse> getRelatorioFinanceirosSalvo(Long competenciaId) {
		List<StatusRelatorioFinanceiroEnum> listSituacao = new ArrayList<StatusRelatorioFinanceiroEnum>();
		listSituacao.add(StatusRelatorioFinanceiroEnum.SALVO);
		List<RelatorioFinanceiroFolhaPagamentoResponse> list = new ArrayList<RelatorioFinanceiroFolhaPagamentoResponse>();
		List<RelatorioFinanceiroFolhaPagamento> listRelatorios = relatorioFinancRepository
				.findByHistoricoAtualStatusInAndFolhaCompetenciaId(listSituacao, competenciaId);
		if (!listRelatorios.isEmpty()) {
			list = listRelatorios.stream()
					.map(relatorio -> new RelatorioFinanceiroFolhaPagamentoResponse(relatorio, Projecao.BASICA))
					.collect(Collectors.toList());
		}
		return list;
	}

	public List<ExportRelatorioFinanceiroSituacaoFuncional> montarDadosExportarRelatorio(
			StatusRelatorioFinanceiroEnum statusRelatorioFinanceiroEnum,
			RelatorioFinanceiroFolhaPagamento relatorioFinanc) {
		List<FolhaPagamento> list = folhaPagamentoRepository.findAllByFolhaCompetenciaIdAndFilialIdIn(
				relatorioFinanc.getFolhaCompetencia().getId(), relatorioFinanc.getFiliaisId());
		List<ExportRelatorioFinanceiroSituacaoFuncional> listagemRelatorio = new ArrayList<ExportRelatorioFinanceiroSituacaoFuncional>();
		for (FolhaPagamento folhaPagamento : list) {
			for (SituacaoFuncional sf : relatorioFinanc.getSituacoesFuncionais()) {
				List<Contracheque> listContrachequesFuncionarios = contrachequeRepository
						.findByFolhaPagamentoIdAndFuncionarioIsNotNullAndTipoSituacaoFuncionalAndLotacaoIn(
								folhaPagamento.getId(), sf.getDescricao(), relatorioFinanc.getLotacoesString());
				if (!listContrachequesFuncionarios.isEmpty()) {
					// Registrando informações da filial por Situação Funcional pt 1
					ExportRelatorioFinanceiroSituacaoFuncional exportRelatorioFinanceiroSituacaoFuncional = new ExportRelatorioFinanceiroSituacaoFuncional();
					exportRelatorioFinanceiroSituacaoFuncional.setStatus(statusRelatorioFinanceiroEnum);
					exportRelatorioFinanceiroSituacaoFuncional.setCnpj(folhaPagamento.getFilial().getCnpj());
					exportRelatorioFinanceiroSituacaoFuncional.setFilial(folhaPagamento.getFilial().getNomeFilial());
					exportRelatorioFinanceiroSituacaoFuncional
							.setFolhaPagamento(new FolhaPagamentoResponse(folhaPagamento));
					exportRelatorioFinanceiroSituacaoFuncional.setSituacaoFuncional(sf.getDescricao());

					for (Contracheque contracheque : listContrachequesFuncionarios) {
						// Registrando informações do funcionário
						ExportRelatorioFinanceiroFuncionarioPensionista exportRelatorioFinanc = new ExportRelatorioFinanceiroFuncionarioPensionista();
						exportRelatorioFinanc.setNome(contracheque.getNome());
						exportRelatorioFinanc.setMatricula(contracheque.getMatricula().toString());
						exportRelatorioFinanc.setCargo(contracheque.getCargoEfetivo());
						if (Objects.nonNull(contracheque.getFuncionario().getFuncao()))
							exportRelatorioFinanc.setFuncao(contracheque.getFuncionario().getFuncao().getNome());
						if (Objects.nonNull(contracheque.getFuncionario().getTipoSituacaoFuncional()))
							exportRelatorioFinanc.setSituacaoFuncional(
									contracheque.getFuncionario().getTipoSituacaoFuncional().getDescricao());
						exportRelatorioFinanc.setDadoBancario(
								funcionarioService.dadoBancarioConcatStr(contracheque.getFuncionario()));
						exportRelatorioFinanc.setLotacao(contracheque.getLotacao());
						exportRelatorioFinanc.setTotalDescontos(contracheque.getValorDesconto());
						exportRelatorioFinanc.setTotalProventos(contracheque.getTotalProventos());
						exportRelatorioFinanc.setTotalLiquido(contracheque.getValorLiquido());
						for (Lancamento lancamento : contracheque.getLancamentos()) {
							String key = lancamento.getVerba().getCodigo() + " - "
									+ lancamento.getVerba().getDescricaoResumida();
							if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
								exportRelatorioFinanc.getListProventos().put(key, lancamento.getValor());
								// Registrando informações da filial por Situação Funcional pt 3
								if (exportRelatorioFinanceiroSituacaoFuncional.getListProventos().containsKey(key)) {
									exportRelatorioFinanceiroSituacaoFuncional.getListProventos().put(key,
											exportRelatorioFinanceiroSituacaoFuncional.getListProventos().get(key)
													+ lancamento.getValor());
								} else {
									exportRelatorioFinanceiroSituacaoFuncional.getListProventos().put(key,
											lancamento.getValor());
								}

							} else if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.DESCONTO)) {
								exportRelatorioFinanc.getListDescontos().put(key, lancamento.getValor());
								// Registrando informações da filial por Situação Funcional pt 4
								if (exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().containsKey(key)) {
									exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().put(key,
											exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().get(key)
													+ lancamento.getValor());
								} else {
									exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().put(key,
											lancamento.getValor());
								}
							}

						}

						exportRelatorioFinanceiroSituacaoFuncional.getListFuncionarioPensionistas()
								.add(exportRelatorioFinanc);

						// Registrando informações da filial por Situação Funcional pt 5
						exportRelatorioFinanceiroSituacaoFuncional.setTotalFuncionarios(
								exportRelatorioFinanceiroSituacaoFuncional.getTotalFuncionarios() + 1);
						exportRelatorioFinanceiroSituacaoFuncional
								.setTotalDescontos(exportRelatorioFinanceiroSituacaoFuncional.getTotalDescontos()
										+ contracheque.getValorDesconto());
						exportRelatorioFinanceiroSituacaoFuncional
								.setTotalProventos(exportRelatorioFinanceiroSituacaoFuncional.getTotalProventos()
										+ contracheque.getTotalProventos());
						exportRelatorioFinanceiroSituacaoFuncional
								.setTotalLiquido(exportRelatorioFinanceiroSituacaoFuncional.getTotalLiquido()
										+ contracheque.getValorLiquido());
					}
					listagemRelatorio.add(exportRelatorioFinanceiroSituacaoFuncional);
				}
			}

//			Será necessário adicionar uma flag para inserir ou não os pensionistas no relatório pois eles não são situações funcionais
//			por enquanto todos os pensionistas estão sendo inseridos.

			List<Contracheque> listContrachequesPensionistas = contrachequeRepository
					.findByFolhaPagamentoIdAndPensionistaIsNotNullAndLotacaoIn(folhaPagamento.getId(),
							relatorioFinanc.getLotacoesString());

			if (!listContrachequesPensionistas.isEmpty()) {
				// Registrando informações da filial pensionista pt 1
				ExportRelatorioFinanceiroSituacaoFuncional exportRelatorioFinanceiroSituacaoFuncional = new ExportRelatorioFinanceiroSituacaoFuncional();
				exportRelatorioFinanceiroSituacaoFuncional.setStatus(statusRelatorioFinanceiroEnum);
				exportRelatorioFinanceiroSituacaoFuncional.setCnpj(folhaPagamento.getFilial().getCnpj());
				exportRelatorioFinanceiroSituacaoFuncional.setFilial(folhaPagamento.getFilial().getNomeFilial());
				exportRelatorioFinanceiroSituacaoFuncional
						.setFolhaPagamento(new FolhaPagamentoResponse(folhaPagamento));
				exportRelatorioFinanceiroSituacaoFuncional.setSituacaoFuncional("PENSIONISTA");

				for (Contracheque contracheque : listContrachequesPensionistas) {
					// Registrando informações do pensionista
					ExportRelatorioFinanceiroFuncionarioPensionista exportRelatorioFinanc = new ExportRelatorioFinanceiroFuncionarioPensionista();
					exportRelatorioFinanc.setNome(contracheque.getNome());
					exportRelatorioFinanc.setMatricula(contracheque.getMatricula().toString());
					exportRelatorioFinanc.setCargo(contracheque.getCargoEfetivo());
					exportRelatorioFinanc.setFuncao(contracheque.getCargoFuncao());
					exportRelatorioFinanc.setLotacao(contracheque.getLotacao());
					exportRelatorioFinanc.setTotalDescontos(contracheque.getValorDesconto());
					exportRelatorioFinanc.setTotalProventos(contracheque.getTotalProventos());
					exportRelatorioFinanc.setTotalLiquido(contracheque.getValorLiquido());
					for (Lancamento lancamento : contracheque.getLancamentos()) {
						String key = lancamento.getVerba().getCodigo() + " - "
								+ lancamento.getVerba().getDescricaoResumida();
						if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
							exportRelatorioFinanc.getListProventos().put(key, lancamento.getValor());
							// Registrando informações da filial pensionista pt 3
							if (exportRelatorioFinanceiroSituacaoFuncional.getListProventos().containsKey(key)) {
								exportRelatorioFinanceiroSituacaoFuncional.getListProventos().put(key,
										exportRelatorioFinanceiroSituacaoFuncional.getListProventos().get(key)
												+ lancamento.getValor());
							} else {
								exportRelatorioFinanceiroSituacaoFuncional.getListProventos().put(key,
										lancamento.getValor());
							}

						} else if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.DESCONTO)) {
							exportRelatorioFinanc.getListDescontos().put(key, lancamento.getValor());
							// Registrando informações da filial pensionista pt 4
							if (exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().containsKey(key)) {
								exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().put(key,
										exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().get(key)
												+ lancamento.getValor());
							} else {
								exportRelatorioFinanceiroSituacaoFuncional.getListDescontos().put(key,
										lancamento.getValor());
							}
						}

					}

					exportRelatorioFinanceiroSituacaoFuncional.getListFuncionarioPensionistas()
							.add(exportRelatorioFinanc);

					// Registrando informações da filial por pensionista pt 5
					exportRelatorioFinanceiroSituacaoFuncional.setTotalFuncionarios(
							exportRelatorioFinanceiroSituacaoFuncional.getTotalFuncionarios() + 1);
					exportRelatorioFinanceiroSituacaoFuncional
							.setTotalDescontos(exportRelatorioFinanceiroSituacaoFuncional.getTotalDescontos()
									+ contracheque.getValorDesconto());
					exportRelatorioFinanceiroSituacaoFuncional
							.setTotalProventos(exportRelatorioFinanceiroSituacaoFuncional.getTotalProventos()
									+ contracheque.getTotalProventos());
					exportRelatorioFinanceiroSituacaoFuncional
							.setTotalLiquido(exportRelatorioFinanceiroSituacaoFuncional.getTotalLiquido()
									+ contracheque.getValorLiquido());
				}
				listagemRelatorio.add(exportRelatorioFinanceiroSituacaoFuncional);
			}
		}

		return listagemRelatorio;
	}

}
