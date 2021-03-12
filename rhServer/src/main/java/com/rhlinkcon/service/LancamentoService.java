package com.rhlinkcon.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.AppException;
import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Aliquota;
import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.IdentificacaoVerbaEnum;
import com.rhlinkcon.model.ItemFormula;
import com.rhlinkcon.model.ItemFormulaTipoEnum;
import com.rhlinkcon.model.Lancamento;
import com.rhlinkcon.model.ParametroGlobal;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.model.PensaoAlimenticia;
import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.model.TipoIncidenciaPrincipalPensaoEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoVerbaDto;
import com.rhlinkcon.payload.contracheque.ContrachequeResponse;
import com.rhlinkcon.payload.dirf.DirfValoresDto;
import com.rhlinkcon.payload.lancamento.LancamentoManualRequest;
import com.rhlinkcon.payload.lancamento.LancamentoResponse;
import com.rhlinkcon.payload.lancamento.LancamentoVerbaManualRequest;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoProventosDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;
import com.rhlinkcon.repository.FuncionarioVerbaRepository;
import com.rhlinkcon.repository.PensaoAlimenticiaRepository;
import com.rhlinkcon.repository.PensionistaVerbaRepository;
import com.rhlinkcon.repository.TipoProcessamentoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.contracheque.ContrachequeRepository;
import com.rhlinkcon.repository.lancamento.LancamentoRepository;
import com.rhlinkcon.util.MotorCalculoAttribute;
import com.rhlinkcon.util.Utils;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private ContrachequeRepository contrachequeRepository;
	@Autowired
	private FuncionarioVerbaRepository funcionarioVerbaRepository;
	@Autowired
	private VerbaRepository verbaRepository;
	@Autowired
	private PensionistaVerbaRepository pensionistaVerbaRepository;
	@Autowired
	private PensaoAlimenticiaRepository pensaoAlimenticiaRepository;
	@Autowired
	private AliquotaService aliquotaService;
	@Autowired
	private ParametroGlobalService parametroGlobalService;
	@Autowired
	private TipoProcessamentoRepository tipoProcessamentoRepository;

	private List<FuncionarioVerba> funcionarioVerbaList;

	private List<PensaoAlimenticia> pensaoAlimenticiaList;

	private List<PensionistaVerba> pensionistaVerbaList;

	private Map<Long, Double> verbasTotaisMap;

	private Map<Long, String> verbasFeedbackMap;

	private List<Lancamento> lancamentos;

	public ContrachequeResponse findContrachequeByContrachequeId(Long contrachequeId) {

		ContrachequeResponse response = null;

		// Busca o contracheque do funcionário e folha em questão
		Optional<Contracheque> contrachequeOpt = contrachequeRepository.findById(contrachequeId);

		// Checa se foi retornado contracheque para a folha e funcionário em questão
		if (contrachequeOpt.isPresent()) {
			Contracheque contracheque = contrachequeOpt.get();

			response = new ContrachequeResponse(contracheque);
			response.setLancamentosVantagens(new ArrayList<LancamentoResponse>());
			response.setLancamentosDescontos(new ArrayList<LancamentoResponse>());
			response.setLancamentosOutros(new ArrayList<LancamentoResponse>());

			for (Lancamento lancamento : contracheque.getLancamentos()) {
				if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.OUTROS))
					response.getLancamentosOutros().add(new LancamentoResponse(lancamento));
				else if (lancamento.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM))
					response.getLancamentosVantagens().add(new LancamentoResponse(lancamento));
				else
					response.getLancamentosDescontos().add(new LancamentoResponse(lancamento));
			}

		}
		return response;

	}

	public void adicionarLancamentoVerbaManual(LancamentoManualRequest request) {

		// Validando o valor máximo das verbas
		for (LancamentoVerbaManualRequest verbaManual : request.getVerbaManualList()) {

			Verba verba = verbaRepository.findById(verbaManual.getVerbaId())
					.orElseThrow(() -> new ResourceNotFoundException("Verba", "id", verbaManual.getVerbaId()));

			if (verba.getValorMaximo() != null && verba.getValorMaximo() > 0
					&& verbaManual.getValor() > verba.getValorMaximo()) {
				throw new BadRequestException(
						"A verba " + verba.getDescricaoVerba() + " teve o valor máximo ultrapassado.");
			}
		}

		// TODO ANALISAR
		// Processamento dos funcionários da folha em questão
//		LancamentoRequest lancamentoRequest = new LancamentoRequest();
//		lancamentoRequest.setFolhaPagamentoId(request.getFolhaPagamentoId());
//
//		lancamentoRequest.setFuncionariosId(new ArrayList<Long>());
//		lancamentoRequest.getFuncionariosId().addAll(request.getFuncionariosId());
//
//		lancamentoRequest.setVerbaManualList(new ArrayList<LancamentoVerbaManualRequest>());
//		lancamentoRequest.getVerbaManualList().addAll(request.getVerbaManualList());
//
//		contrachequeService.adicionarFuncionario(lancamentoRequest);

	}

	// TODO REFACTORY
//	public void removerVerbaFolhaPagamento(AdicionarVerbaFolhaPagamentoRequest request) {
//		lancamentoRepository.deleteById(request.getFolhaPagamentoFuncionarioVerbaId());
//
//		List<Funcionario> funcionarios = new ArrayList<>();
//		funcionarios = funcionarioRepository.findAllById(request.getFuncionariosId());
//		processarFolhaIndividual(funcionarios, request.getFolhaPagamentoId(), new ArrayList<Verba>());
//
//	}

	// List<Verba> verbasAutomaticas
	public void processarLancamentos(Contracheque contracheque) {

		// Zerando os valores bruto, desconto e líquido do contracheque.
		contracheque.setValorBruto(0D);
		contracheque.setValorDesconto(0D);
		contracheque.setValorLiquido(0D);

		// Busca de verbas automáticas do tipo de processamento da folha
		List<Verba> verbasAutomaticas = tipoProcessamentoRepository
				.findVerbasById(contracheque.getFolhaPagamento().getTipoProcessamento().getId());

		// Lista de lançamentos do contracheque.
		lancamentos = new ArrayList<Lancamento>();

		// Map de todas as verbas, de funcionário e automáticas.
		verbasTotaisMap = new HashMap<>();

		// Map de todas as verbas e suas fórmulas traduzidas, para armazenar no atributo
		// feedback do contracheque.
		verbasFeedbackMap = new HashMap<>();

		funcionarioVerbaList = new ArrayList<FuncionarioVerba>();
		pensaoAlimenticiaList = new ArrayList<PensaoAlimenticia>();
		pensionistaVerbaList = new ArrayList<PensionistaVerba>();

		// Busca verbas do Funcionário
		if (Objects.nonNull(contracheque.getFuncionario())) {
			funcionarioVerbaList = funcionarioVerbaRepository
					.findByFuncionarioId(contracheque.getFuncionario().getId());
			// Busca de pensão alimentícia do funcionário - Alimentandos.
			Optional<List<PensaoAlimenticia>> pensaoAlimenticiaByFuncionario = pensaoAlimenticiaRepository
					.findByFuncionario(contracheque.getFuncionario());
			if (pensaoAlimenticiaByFuncionario.isPresent())
				pensaoAlimenticiaList = pensaoAlimenticiaByFuncionario.get();
		} else {
			// Busca de verbas de Pensionsitas
			pensionistaVerbaList = pensionistaVerbaRepository
					.findByPensionistaId(contracheque.getPensionista().getId());
		}

		// Preenche o Map de todas as verbas
		funcionarioVerbaList.forEach(verbaFunc -> {
			verbasTotaisMap.put(verbaFunc.getVerba().getId(), null);
		});
		pensionistaVerbaList.forEach(verbaPen -> {
			verbasTotaisMap.put(verbaPen.getVerba().getId(), null);
		});
		verbasAutomaticas.forEach(verbaAut -> {
			if (!verbasTotaisMap.containsKey(verbaAut.getId()))
				verbasTotaisMap.put(verbaAut.getId(), null);
		});

		List<LancamentoVerbaManualRequest> verbaManualList = new ArrayList<LancamentoVerbaManualRequest>();

		// Aplica as regras de verba manual adicionada, colocando-as na lista de
		// funcionário verba apra serem processadas
		if (Objects.nonNull(verbaManualList) && !verbaManualList.isEmpty()) {
			verbaManualList.forEach(verbaManual -> {

				boolean verbaManualInexistente = true;
				for (FuncionarioVerba funcionarioVerba : funcionarioVerbaList) {
					if (funcionarioVerba.getId() == verbaManual.getVerbaId()) {
						funcionarioVerba.setValor(verbaManual.getValor());
						funcionarioVerba.setVerbaManual(true);
						verbaManualInexistente = false;
						verbasTotaisMap.put(funcionarioVerba.getVerba().getId(), verbaManual.getValor());
						break;
					}
				}
				for (Verba verbaAutomatica : verbasAutomaticas) {
					if (verbaAutomatica.getId() == verbaManual.getVerbaId()) {
						verbaAutomatica.setValor(verbaManual.getValor());
						verbaAutomatica.setVerbaManual(true);
						verbaManualInexistente = false;
						verbasTotaisMap.put(verbaAutomatica.getId(), verbaManual.getValor());
						break;
					}
				}
				// Caso não seja encontrada a verba na lista de verbas de funcionário nem verbas
				// automáticas, a verba será adicionada a lista de verbas de funcionário para
				// fins de lançamento manual.
				if (verbaManualInexistente) {
					FuncionarioVerba verbaFuncionarioManual = new FuncionarioVerba();
					verbaFuncionarioManual.setFuncionario(contracheque.getFuncionario());
					Verba verba = verbaRepository.findById(verbaManual.getVerbaId()).get();
					verbaFuncionarioManual.setVerba(verba);
					verbaFuncionarioManual.setValor(verbaManual.getValor());
					verbaFuncionarioManual.setTipoValor(verba.getTipoValor());
					verbaFuncionarioManual.setVerbaManual(true);
					verbaFuncionarioManual.setRecorrencia(verba.getRecorrencia());
					funcionarioVerbaList.add(verbaFuncionarioManual);
					verbasTotaisMap.put(verbaFuncionarioManual.getVerba().getId(), verbaManual.getValor());
				}
			});
		}

		// Inicia o processamento de verbas de pensão alimentícia do funcionário
		for (PensaoAlimenticia pensaoAlimenticia : pensaoAlimenticiaList) {

			if (verbasTotaisMap.containsKey(pensaoAlimenticia.getVerba().getId())) {
				try {

					Lancamento lancamento = new Lancamento();
					lancamento.setContracheque(contracheque);
					lancamento.setVerba(pensaoAlimenticia.getVerba());
					lancamento.setPensaoAlimenticia(pensaoAlimenticia);
					lancamento.setValorReferencia(Double.parseDouble(pensaoAlimenticia.getNumeroProcessoPagamento()));

					if (pensaoAlimenticia.getTipoValor().equals(TipoValorEnum.MOEDA)) {
						lancamento.setValor(Utils.roundValue(pensaoAlimenticia.getValor()));

					} else if (pensaoAlimenticia.getTipoValor().equals(TipoValorEnum.PERCENTUAL)) {

						if (pensaoAlimenticia.getTipoIncidenciaPrincipalPensao()
								.equals(TipoIncidenciaPrincipalPensaoEnum.SALARIO_BRUTO))

							lancamento.setValor(
									getCalculoValor(pensaoAlimenticia.getValor(), contracheque.getValorBruto()));

						else if (pensaoAlimenticia.getTipoIncidenciaPrincipalPensao()
								.equals(TipoIncidenciaPrincipalPensaoEnum.SALARIO_LIQUIDO))

							lancamento.setValor(
									getCalculoValor(pensaoAlimenticia.getValor(), contracheque.getValorLiquido()));

						else if (pensaoAlimenticia.getTipoIncidenciaPrincipalPensao()
								.equals(TipoIncidenciaPrincipalPensaoEnum.SALARIO_MINIMO)) {

							ParametroGlobal paramGlobal = parametroGlobalService
									.getParametroByChaveAndAtivo(ParametroGlobalChaveEnum.SALARIO_MINIMO, true);

							lancamento.setValor(getCalculoValor(pensaoAlimenticia.getValor(),
									Double.parseDouble(paramGlobal.getValor())));

						}

					} else {

						ParametroGlobal paramGlobal = parametroGlobalService
								.getParametroByChaveAndAtivo(ParametroGlobalChaveEnum.SALARIO_MINIMO, true);

						lancamento.setValor(Utils
								.roundValue(pensaoAlimenticia.getValor() * Double.parseDouble(paramGlobal.getValor())));

					}

					lancamento.setVerbaEspecifica(true);

					if (lancamento.getValor() != null && lancamento.getValor() > new Double(0.0)) {
						Double verbaValorMap = verbasTotaisMap.get(lancamento.getVerba().getId());
						if (Objects.isNull(verbaValorMap))
							verbaValorMap = 0D;
						verbaValorMap += lancamento.getValor();
						verbasTotaisMap.put(lancamento.getVerba().getId(), verbaValorMap);
						contracheque.setValorDesconto(contracheque.getValorDesconto() + verbaValorMap);
						lancamentos.add(lancamento);
					}

				} catch (Exception e) {
					e.printStackTrace();
					contracheque.setSituacao(SituacaoProcessamentoEnum.FALHO);
					break;
				}
			}

		}

		// Verbas de vantagem serão processadas primeiro, pois elas podems ser usadas
		// como parametros em outros tipos de verbas.

		// Inicia o processamento de verbas de funcionário de vantagem
		for (FuncionarioVerba funcionarioVerba : funcionarioVerbaList) {
			try {

				if (funcionarioVerba.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
					Lancamento lancamento = new Lancamento();
					lancamento.setContracheque(contracheque);
					lancamento.setVerba(funcionarioVerba.getVerba());
					lancamento.setFuncionarioVerba(funcionarioVerba);

					if (funcionarioVerba.getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA))
						lancamento.setNumeroParcela(funcionarioVerba.getParcelasPagas() + 1);

					Double valor = getValorDaVerba(contracheque, lancamento.getVerba());

					lancamento.setValorReferencia(lancamento.getVerba().getReferencia());

					if (Objects.nonNull(valor) && valor > 0D) {
						lancamento.setValor(valor);
						contracheque.setValorBruto(contracheque.getValorBruto() + valor);
						lancamentos.add(lancamento);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				contracheque.setSituacao(SituacaoProcessamentoEnum.FALHO);
				break;
			}
		}

		// Inicia o processamento de verbas de pensionista de vantagem
		for (PensionistaVerba pensionistaVerba : pensionistaVerbaList) {
			try {

				if (pensionistaVerba.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
					Lancamento lancamento = new Lancamento();
					lancamento.setContracheque(contracheque);
					lancamento.setVerba(pensionistaVerba.getVerba());
					lancamento.setPensionistaVerba(pensionistaVerba);

					if (pensionistaVerba.getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA))
						lancamento.setNumeroParcela(pensionistaVerba.getParcelasPagas() + 1);

					Double valor = getValorDaVerba(contracheque, lancamento.getVerba());

					lancamento.setValorReferencia(lancamento.getVerba().getReferencia());

					if (Objects.nonNull(valor) && valor > 0D) {
						lancamento.setValor(valor);
						contracheque.setValorBruto(contracheque.getValorBruto() + valor);
						lancamentos.add(lancamento);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				contracheque.setSituacao(SituacaoProcessamentoEnum.FALHO);
				break;
			}
		}

		// Inicia o processamento de verbas automáticas de vantagem
		for (Verba verbaAutomatica : verbasAutomaticas) {
			try {

				if (verbaAutomatica.getTipoVerba().equals(TipoVerba.VANTAGEM))
					// Impede que verbas já lançadas manualmente sejam lançadas novamente.
					if (!existeVerbaManualLancada(verbaAutomatica)) {

						Lancamento lancamento = new Lancamento();
						lancamento.setContracheque(contracheque);
						lancamento.setVerba(verbaAutomatica);

						// TODO ANALISAR VERBA MANUAL LIST
						// lancamento.setAdicionadoManualmente(isVerbaManual(verba, verbaManualList));
						Double valor = getValorDaVerba(contracheque, lancamento.getVerba());
						lancamento.setValorReferencia(lancamento.getVerba().getReferencia());

						if (Objects.nonNull(valor) && valor > 0D) {
							lancamento.setValor(valor);
							contracheque.setValorBruto(contracheque.getValorBruto() + valor);
							lancamentos.add(lancamento);
						}

					}

			} catch (Exception e) {
				e.printStackTrace();
				contracheque.setSituacao(SituacaoProcessamentoEnum.FALHO);
				break;
			}
		}

		// Inicia o processamento das demais verbas de funcionário
		for (FuncionarioVerba funcionarioVerba : funcionarioVerbaList) {
			try {

				if (!funcionarioVerba.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
					Lancamento lancamento = new Lancamento();
					lancamento.setContracheque(contracheque);
					lancamento.setVerba(funcionarioVerba.getVerba());
					lancamento.setFuncionarioVerba(funcionarioVerba);

					if (funcionarioVerba.getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA))
						lancamento.setNumeroParcela(funcionarioVerba.getParcelasPagas() + 1);

					Double valor = getValorDaVerba(contracheque, lancamento.getVerba());

					lancamento.setValorReferencia(lancamento.getVerba().getReferencia());

					if (Objects.nonNull(valor) && valor > 0D) {
						lancamento.setValor(valor);
						if (funcionarioVerba.getVerba().getTipoVerba().equals(TipoVerba.DESCONTO))
							contracheque.setValorDesconto(contracheque.getValorDesconto() + valor);
						lancamentos.add(lancamento);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				contracheque.setSituacao(SituacaoProcessamentoEnum.FALHO);
				break;
			}
		}

		// Inicia o processamento das demais verbas de pensionista
		for (PensionistaVerba pensionistaVerba : pensionistaVerbaList) {
			try {

				if (!pensionistaVerba.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM)) {
					Lancamento lancamento = new Lancamento();
					lancamento.setContracheque(contracheque);
					lancamento.setVerba(pensionistaVerba.getVerba());
					lancamento.setPensionistaVerba(pensionistaVerba);

					if (pensionistaVerba.getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA))
						lancamento.setNumeroParcela(pensionistaVerba.getParcelasPagas() + 1);

					Double valor = getValorDaVerba(contracheque, lancamento.getVerba());

					lancamento.setValorReferencia(lancamento.getVerba().getReferencia());

					if (Objects.nonNull(valor) && valor > 0D) {
						lancamento.setValor(valor);
						if (pensionistaVerba.getVerba().getTipoVerba().equals(TipoVerba.DESCONTO))
							contracheque.setValorDesconto(contracheque.getValorDesconto() + valor);
						lancamentos.add(lancamento);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				contracheque.setSituacao(SituacaoProcessamentoEnum.FALHO);
				break;
			}
		}

		// Inicia o processamento das demais verbas automáticas
		for (Verba verbaAutomatica : verbasAutomaticas) {
			try {

				if (!verbaAutomatica.getTipoVerba().equals(TipoVerba.VANTAGEM))
					// Impede que verbas já lançadas manualmente sejam lançadas novamente.
					if (!existeVerbaManualLancada(verbaAutomatica)) {

						Lancamento lancamento = new Lancamento();
						lancamento.setContracheque(contracheque);
						lancamento.setVerba(verbaAutomatica);

						// TODO ANALISAR VERBA MANUAL LIST
						// lancamento.setAdicionadoManualmente(isVerbaManual(verba, verbaManualList));
						Double valor = getValorDaVerba(contracheque, lancamento.getVerba());
						lancamento.setValorReferencia(lancamento.getVerba().getReferencia());

						if (Objects.nonNull(valor) && valor > 0D) {
							lancamento.setValor(valor);
							if (verbaAutomatica.getTipoVerba().equals(TipoVerba.DESCONTO))
								contracheque.setValorDesconto(contracheque.getValorDesconto() + valor);
							lancamentos.add(lancamento);
						}

					}

			} catch (Exception e) {
				e.printStackTrace();
				contracheque.setSituacao(SituacaoProcessamentoEnum.FALHO);
				break;
			}
		}

		try {
			// Lançamentos são salvos na base.
			lancamentoRepository.saveAll(lancamentos);

			// Caso uma falha não tenha ocorrido, a situação de Concluído é gravada.
			if (!contracheque.getSituacao().equals(SituacaoProcessamentoEnum.FALHO)) {
				contracheque.setSituacao(SituacaoProcessamentoEnum.CONCLUIDO);
			}

			// Gravando os feedbacks.
			String feedback = "";
			for (Long key : verbasFeedbackMap.keySet()) {
				String valor = verbasFeedbackMap.get(key);
				feedback += valor + "\n";
			}
			contracheque.setFeedback(feedback);

			// Salvando o contracheque
			contrachequeRepository.save(contracheque);

		} catch (Exception e2) {
			throw new ServiceException(e2.getMessage());
		}

	}

	private Double getValorDaVerba(Contracheque contracheque, Verba verba) throws Exception {

		// Inicia o processamento de verbas de incidencia, se houver.
		if (verba.getIncidencias() != null) {
			for (Verba verbaInc : verba.getIncidencias()) {

				if (verbasTotaisMap.containsKey(verbaInc.getId())) {

					Double valorInc = getValorDaVerba(contracheque, verbaInc);

					if (Objects.isNull(valorInc) || valorInc.equals(0D))
						continue;

					Double totalProventos = verba.getTotalProventosIncidentes();
					Double totalDescontos = verba.getTotalDescontosIncidentes();
					if (verbaInc.getTipoVerba().equals(TipoVerba.VANTAGEM)) {
						totalProventos += valorInc;
						verba.setTotalProventosIncidentes(totalProventos);
					} else {
						totalDescontos += valorInc;
						verba.setTotalDescontosIncidentes(totalDescontos);
					}

				}
			}
		}

		// Inicia o processamento da verba principal.

		Double valor = 0D;

		if (verbasTotaisMap.containsKey(verba.getId())) {
			if (Objects.nonNull(verbasTotaisMap.get(verba.getId()))) {
				Double verbaValorMap = verbasTotaisMap.get(verba.getId());
				valor = Utils.roundValue(verbaValorMap);

			} else {

				String resultado = translate(contracheque, verba);

				valor = Utils.roundValue(Double.valueOf(resultado));

				if (valor != null && valor < 0D)
					valor = 0D;

				if (verba.getFaixaAliquota() != null) {
					FaixaEnum faixaEnum = verba.getFaixaAliquota();

					if (Objects.nonNull(faixaEnum) && Objects.nonNull(valor)) {
						Aliquota aliquota = aliquotaService.getAliquotaByFaixaAndValorReferencia(valor, faixaEnum);
						if (Objects.nonNull(aliquota)) {
							if (aliquota.getAliquota() != null && aliquota.getAliquota() > 0d) {
								valor = ((aliquota.getAliquota() / 100) * valor) - aliquota.getDeducao();
								verba.setReferencia(aliquota.getAliquota());
							}
						}
					}
				}

				if (Objects.nonNull(contracheque.getFuncionario())) {

					FuncionarioVerba funcionarioVerba = getFuncionarioVerbaByVerba(verba);

					if (Objects.nonNull(funcionarioVerba))
						valor = aplicaVerbaManual(verba, valor, funcionarioVerba.getTipoValor(),
								funcionarioVerba.getValor());

				} else {

					PensionistaVerba pensionistaVerba = getPensionistaVerbaByVerba(verba);

					if (Objects.nonNull(pensionistaVerba))
						valor = aplicaVerbaManual(verba, valor, pensionistaVerba.getTipoValor(),
								pensionistaVerba.getValor());

				}

				verbasTotaisMap.put(verba.getId(), valor);

			}

		}

		return valor;

	}

	private Double aplicaVerbaManual(Verba verba, Double valor, TipoValorEnum tipoValor, Double valorManual) {

		if (tipoValor.equals(TipoValorEnum.MOEDA)) {
			return Utils.roundValue(valorManual);

		} else if (tipoValor.equals(TipoValorEnum.PERCENTUAL)) {
			if (verba.hasFormula())
				return getCalculoValor(valorManual, valor);
			else
				return getCalculoValor(valorManual, verba.getReferencia());

		} else {
			if (verba.hasFormula())
				return Utils.roundValue(valorManual * valor);
			else
				return Utils.roundValue(valorManual * verba.getReferencia());

		}

		// TODO ANALISAR
//		lancamento.setVerbaEspecifica(true);

	}

	public String translate(Contracheque contracheque, Verba verba) throws Exception {

		String formulaTraduzida = "";

		if (verba.getFormulas() != null) {

			for (ItemFormula itemFormula : verba.getFormulas()) {

				if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.ATRIBUTO)) {

					Object v = null;

					// Atributos de Contracheque, Funcionário ou Pensionista.
					v = getObjectValue(contracheque, itemFormula.getValor());
					if (Objects.nonNull(v)) {
						formulaTraduzida += v.toString();
						continue;
					}

					// Atributos de Verba
					if (Objects.isNull(v)) {
						v = getObjectValue(verba, itemFormula.getValor());
						if (Objects.nonNull(v)) {
							formulaTraduzida += v.toString();
							continue;
						}
					}

					// Atributos de Parametro Global
					if (Objects.isNull(v)) {
						ParametroGlobalChaveEnum paramGlobalEnum = ParametroGlobalChaveEnum
								.getEnumByString(itemFormula.getValor());

						if (Objects.nonNull(paramGlobalEnum)) {
							ParametroGlobal paramGlobal = parametroGlobalService
									.getParametroByChaveAndAtivo(paramGlobalEnum, true);

							if (Objects.nonNull(paramGlobal)) {
								v = paramGlobal.getValor();
								if (Objects.nonNull(v)) {
									formulaTraduzida += v.toString();
									continue;
								}
							}
						}
					}

				} else if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.CODIGO)) {
					formulaTraduzida += itemFormula.getValor();

				} else if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.TEXTO)) {
					formulaTraduzida += "'" + itemFormula.getValor() + "'";

				} else if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.NUMERAL)) {
					formulaTraduzida += itemFormula.getValor();

				} else if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.DATA)) {

					String dateString = itemFormula.getValor();

					String pattern = "yyyy-MM-dd";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					String date = simpleDateFormat.format(Date.from(Utils.stringDateToStartInstant(dateString)));

					formulaTraduzida += " new Date('" + date + "').getTime() ";

				} else if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.ROTINA)) {

					String formulaTraduzidaRotina = null;

					if (verbasTotaisMap.containsKey(itemFormula.getRotina().getId())) {

						if (Objects.nonNull(verbasTotaisMap.get(itemFormula.getRotina().getId()))) {
							formulaTraduzidaRotina = verbasTotaisMap.get(itemFormula.getRotina().getId()).toString();
							formulaTraduzida += formulaTraduzidaRotina;

						} else {
							formulaTraduzidaRotina = getValorDaVerba(contracheque, itemFormula.getRotina()).toString();

							formulaTraduzida += formulaTraduzidaRotina;

						}
					} else {

						// Rotina que não está na lista de verbas automáticas ou verbas de funcionário /
						// pensionista tem o valor zerado
						formulaTraduzida += "0.0";
					}
				}
			}
		}

		String descVerba = verba.getDescricaoVerba();

		if (formulaTraduzida.isEmpty()) {
			String log = "VERBA: " + descVerba + ". RESULTADO: 0.0. FÓRMULA: " + formulaTraduzida;
//			System.out.println(log);
			verbasFeedbackMap.put(verba.getId(), log);
			return "0.0";
		}

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");

		Object resultado = engine.eval(formulaTraduzida);
		String log = "VERBA: " + descVerba + ". RESULTADO: " + resultado.toString() + ". FÓRMULA: " + formulaTraduzida;
		verbasFeedbackMap.put(verba.getId(), log);
//		System.out.println(log);

		return resultado.toString();

	}

	private Object getObjectValue(Object objetoValores, String valueObject) throws AppException {
		try {

			if (Objects.isNull(objetoValores))
				return null;

			List<Method> methods = Arrays.asList(objetoValores.getClass().getDeclaredMethods());
			Method choosenMethod = null;
			for (Method method : methods) {
				if (method.isAnnotationPresent(MotorCalculoAttribute.class)) {
					MotorCalculoAttribute attribute = method.getAnnotation(MotorCalculoAttribute.class);
					if (attribute.name().equals(valueObject)) {
						choosenMethod = method;
						break;
					}
				}
			}

			if (null != choosenMethod) {
				return choosenMethod.invoke(objetoValores);
			}

			return null;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new AppException(e.getMessage(), e);
		}

	}

//	private boolean isVerbaManual(Verba verba, List<LancamentoVerbaManualRequest> verbaManualList) {
//		boolean result = false;
//		if (Objects.nonNull(verbaManualList) && !verbaManualList.isEmpty()) {
//			for (LancamentoVerbaManualRequest verbaManual : verbaManualList) {
//				if (verba.getId() == verbaManual.getVerbaId()) {
//					result = true;
//					break;
//				}
//			}
//		}
//		return result;
//	}

	private FuncionarioVerba getFuncionarioVerbaByVerba(Verba verba) {
		for (FuncionarioVerba funcionarioVerba : funcionarioVerbaList) {
			if (funcionarioVerba.getVerba().getId().equals(verba.getId()))
				return funcionarioVerba;
		}
		return null;
	}

	private PensionistaVerba getPensionistaVerbaByVerba(Verba verba) {
		for (PensionistaVerba pensionistaVerba : pensionistaVerbaList) {
			if (pensionistaVerba.getVerba().getId().equals(verba.getId()))
				return pensionistaVerba;
		}
		return null;
	}

	private Double getCalculoValor(Double valor, Double referencia) {
		return Utils.roundValue((referencia * valor) / 100);
	}

	private boolean existeVerbaManualLancada(Verba verbaAutomatica) {
		for (Lancamento lancamento : lancamentos) {
			if (lancamento.getVerba().getId().equals(verbaAutomatica.getId()))
				return true;
		}
		return false;
	}

	public Double dirfSomaValorVerbasFuncionarioByFiltros(Integer anoBase, Long filialId, Long funcionarioId,
			List<String> codigos) {
		return lancamentoRepository.somaValorVerbasFuncionarioByFiltros(anoBase, filialId, funcionarioId, codigos);
	}

	public List<RelatorioGerencialDto> getRelGerencialResumoByFolhaPagamentoId(Long folhaPagamentoId,
			String cargoEfetivo, String cargoFuncao, Long funcionarioId, String lotacao) {
		return lancamentoRepository.getRelGerencialResumoByFolhaPagamentoId(folhaPagamentoId, cargoEfetivo, cargoFuncao,
				funcionarioId, lotacao);
	}

	public List<DirfValoresDto> dirfSomaValorMesVerbasFuncionarioByFiltros(Integer anoBase, Long filialId,
			Long funcionarioId, List<String> codigos) {
		return lancamentoRepository.somaValorVerbasMesFuncionarioByFiltros(anoBase, filialId, funcionarioId, codigos);
	}

	public List<BatimentoVerbaDto> getBatimentoVerba(Long competenciaId, Long tipoProcessamentoId, Long filialId,
			TipoVerba tipo, Long situacaoId, Long lotacaoId) {
		return lancamentoRepository.getBatimentoVerbaByFiltros(competenciaId, tipoProcessamentoId, filialId, tipo,
				situacaoId, lotacaoId);
	}

	public List<RelatorioFolhaPagamentoResumoProventosDto> listContrachequesByVerba(Long folhaPagamentoId,
			String situacaoFuncional, IdentificacaoVerbaEnum identificacaoVerba) {
		return lancamentoRepository.listContrachequesByVerba(folhaPagamentoId, situacaoFuncional, identificacaoVerba);
	}

}
