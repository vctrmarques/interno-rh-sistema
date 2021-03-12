package com.rhlinkcon.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.model.ModalidadeAposentadoriaEnum;
import com.rhlinkcon.model.RegraAposentadoria;
import com.rhlinkcon.model.TipoAposentadoriaEnum;
import com.rhlinkcon.model.TipoVigenciaEnum;
import com.rhlinkcon.payload.simuladorAposentadoria.OutroPeriodo;
import com.rhlinkcon.payload.simuladorAposentadoria.PossibilidadeAposentadoria;
import com.rhlinkcon.payload.simuladorAposentadoria.SimuladorAposentadoria;
import com.rhlinkcon.payload.simuladorAposentadoria.TempoTotal;
import com.rhlinkcon.payload.simuladorAposentadoria.UtilsSimulador;
import com.rhlinkcon.repository.regraAposentadoria.RegraAposentadoriaRepository;
import com.rhlinkcon.util.Utils;

@Service
public class SimuladorAposentadoriaService {

	@Autowired
	private RegraAposentadoriaRepository regraAposentadoriaRepository;

	@Autowired
	private RegraAposentadoriaService regraAposentadoriaService;

	@Autowired
	private SimuladorAposentadoriaTransienteService simuladorAposentadoriaTransienteService;

	public SimuladorAposentadoria simular(SimuladorAposentadoria simulApos) {

		// Carrega todos os dados transientes de tela
		simuladorAposentadoriaTransienteService.carregarDadosDeTela(simulApos);

		// Inicio do get das regras
		// Busca as regras de aposentadoria de modalidade geral
		List<RegraAposentadoria> regras = regraAposentadoriaRepository
				.findByModalidadeAposentadoria(ModalidadeAposentadoriaEnum.GERAL);

		if (!simulApos.getModalidade().equals(ModalidadeAposentadoriaEnum.GERAL.getLabel())) {
			// Busca as regras de aposentadoria de modalidade específica
			regras.addAll(regraAposentadoriaRepository.findByModalidadeAposentadoriaAndModalidadeAposentadoriaNome(
					ModalidadeAposentadoriaEnum.ESPECIFICA, simulApos.getModalidade()));
		}

		simulApos.setPossibilAposentList(new ArrayList<>());

		Date dataAposentadoriaCompulsoria = null;

		// RN004 - 2 - Valida se a data de ingresso no serviço público é maior ou igual
		// a data de vigência da regra
		for (RegraAposentadoria regraAposentadoria : regras) {

			Date vigencia = regraAposentadoria.getVigencia();
			Date ingresso = simulApos.getDataIngressoServicoPublico();

			boolean vigenciaOk = false;

			if (regraAposentadoria.getTipoVigencia().equals(TipoVigenciaEnum.IGNORAR)) {
				vigenciaOk = true;
			} else if (regraAposentadoria.getTipoVigencia().equals(TipoVigenciaEnum.ATE)
					&& (ingresso.before(vigencia) || ingresso.equals(vigencia))) {
				vigenciaOk = true;
			} else if (regraAposentadoria.getTipoVigencia().equals(TipoVigenciaEnum.ANTES)
					&& ingresso.before(vigencia)) {
				vigenciaOk = true;
			} else if (regraAposentadoria.getTipoVigencia().equals(TipoVigenciaEnum.DEPOIS)
					&& ingresso.after(vigencia)) {
				vigenciaOk = true;
			}

			if (vigenciaOk) {

				PossibilidadeAposentadoria possibilAposent = new PossibilidadeAposentadoria();

				// RN004 - 3 - a - Calculo dos anos restantes para aposentadoria, afim e
				// calcular a data futura de aposentadoria.

				// Critério de IDADE
				Date dataAposentadoria = criterioDeIdade(simulApos, regraAposentadoria);

				// Critério de TEMPO DE SERVIÇO PÚBLICO
				dataAposentadoria = criterioTempoDeServico(simulApos, regraAposentadoria, dataAposentadoria);

				// Critério de TEMPO DE CARREIRA
				dataAposentadoria = criterioTempoDeCarreira(simulApos, regraAposentadoria, dataAposentadoria);

				// Critério de TEMPO DE CARGO EFETIVO
				dataAposentadoria = criterioTempoDeCargoEfetivo(simulApos, regraAposentadoria, dataAposentadoria);

				// Critério de TEMPO DE CONTRIBUICAO
				dataAposentadoria = criterioTempoDeContribuicao(simulApos, regraAposentadoria, dataAposentadoria);

				// Seta a data para aposentadoria de acordo com os critérios anteriormente
				// analisados
				possibilAposent.setDataAposentadoria(dataAposentadoria);

				// RN004 - 3 - b - Seta o campo "modalidade", que é a concatenação da
				// modalidade, tipo de aposentadoria e tipo de regra.

				String modalidade = "Geral";
				if (!regraAposentadoria.getModalidadeAposentadoria().equals(ModalidadeAposentadoriaEnum.GERAL)) {
					modalidade = regraAposentadoria.getModalidadeAposentadoriaNome();
				}

				possibilAposent.setModalidade(modalidade + " " + regraAposentadoria.getTipoAposentadoria() + " "
						+ regraAposentadoria.getTipoRegra());
				possibilAposent.setModalidade(possibilAposent.getModalidade().toUpperCase());

				// RN004 - 3 - c - Seta o campo "Com base no fundamento legal" com a lei base da
				// regra.
				possibilAposent.setFundamentoLegal(regraAposentadoria.getLeiBase());
				possibilAposent.setArtigo(regraAposentadoria.getArtigo());
				possibilAposent.setAbonoPermanencia(regraAposentadoria.getAbonoPermanencia());

				// RN004 - 3 - d - Seta o campo "Proventos" com os proventos da regra
				possibilAposent.setProventos(regraAposentadoria.getProventos());
				possibilAposent.setReajuste(regraAposentadoria.getReajuste());

				simulApos.getPossibilAposentList().add(possibilAposent);

				// Tratamento da aposentadoria compulsória, elimminando as outras
				// possibilidades.
				if (regraAposentadoria.getTipoAposentadoria().equals(TipoAposentadoriaEnum.COMPULSORIA)) {
					boolean apenasCompuls = false;
					if (simulApos.getSexo().equals("masculino")) {
						if (regraAposentadoria.getIdadeHomem() != null)
							if (simulApos.getIdade() >= regraAposentadoria.getIdadeHomem())
								apenasCompuls = true;
					} else {
						if (regraAposentadoria.getIdadeMulher() != null)
							if (simulApos.getIdade() >= regraAposentadoria.getIdadeMulher())
								apenasCompuls = true;
					}
					if (apenasCompuls) {
						simulApos.setPossibilAposentList(new ArrayList<>());
						simulApos.getPossibilAposentList().add(possibilAposent);
						break;
					} else {
						dataAposentadoriaCompulsoria = dataAposentadoria;
					}
				}
			}
		}

		// Evita que datas de aposentadoria sejam superiores a data de aposentadoria
		// compulsória.
		if (Objects.nonNull(dataAposentadoriaCompulsoria)) {
			List<PossibilidadeAposentadoria> possibilAposentListCompCheck = new ArrayList<PossibilidadeAposentadoria>();
			for (PossibilidadeAposentadoria possibilidade : simulApos.getPossibilAposentList()) {
				if (possibilidade.getDataAposentadoria().after(dataAposentadoriaCompulsoria))
					continue;
				possibilAposentListCompCheck.add(possibilidade);
			}
			simulApos.setPossibilAposentList(new ArrayList<>());
			simulApos.getPossibilAposentList().addAll(possibilAposentListCompCheck);
		}

		// Ordenando as possibilidades
		Collections.sort(simulApos.getPossibilAposentList());

		for (PossibilidadeAposentadoria possibilidade : simulApos.getPossibilAposentList()) {
			if (possibilidade.getAbonoPermanencia() != null && possibilidade.getAbonoPermanencia()) {
				simulApos.setPossibAbonoPermanencia(possibilidade.getDataAposentadoria());
				simulApos.setPossibFundamLegal(possibilidade.getArtigo());
				break;
			}
		}

		return simulApos;
	}

	public LocalDate dataDeAposentadoria(Integer idadeParaAposentar, LocalDate dataNascimento) {
		if (Objects.nonNull(idadeParaAposentar)) {
			LocalDate dataDeAposentadoria = dataNascimento.plusYears(idadeParaAposentar);
			return dataDeAposentadoria;
		}
		return null;
	}

	private Date criterioDeIdade(SimuladorAposentadoria simulApos, RegraAposentadoria regraAposentadoria) {

		Integer idadeParaAposentar = null;

		if (simulApos.getSexo().equals("masculino")) {
			if (regraAposentadoria.getIdadeHomem() != null) {
				idadeParaAposentar = regraAposentadoria.getIdadeHomem();
			}
		} else {
			if (regraAposentadoria.getIdadeMulher() != null) {
				idadeParaAposentar = regraAposentadoria.getIdadeMulher();
			}
		}

		if (Objects.nonNull(idadeParaAposentar)) {
			Calendar c = Calendar.getInstance();
			c.setTime(simulApos.getDataNascimento());
			c.set(Calendar.YEAR, c.get(Calendar.YEAR) + (int) idadeParaAposentar);

			return c.getTime();

		} else {

			double diasRestantes = 0;

			double diasParaAposentadoria = carregarIdadeParaAposentEmDias(simulApos, regraAposentadoria);

			long diasDeIdade = UtilsSimulador
					.periodoContribuicao(simulApos.getDataNascimento(), simulApos.getTempoContribuicaoCargoAtualFim())
					.getTotalDias();

			diasRestantes = Math.floor(diasParaAposentadoria - diasDeIdade);

			Calendar c = Calendar.getInstance();
			c.setTime(simulApos.getTempoContribuicaoCargoAtualFim());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + (int) Math.floor(diasRestantes));

			return c.getTime();
		}

	}

	private int carregarIdadeParaAposentEmDias(SimuladorAposentadoria simulApos,
			RegraAposentadoria regraAposentadoria) {

		String formula = null;
		if (simulApos.getSexo().equals("masculino")) {
			formula = regraAposentadoria.getIdadeHomemFormula();
		} else {
			formula = regraAposentadoria.getIdadeMulherFormula();
		}

		int result = 0;

		try {
			formula = formula.replaceFirst("=", "");

			if (formula.contains("@diasDeIdade")) {
				long diasDeIdade = UtilsSimulador.periodoContribuicao(simulApos.getDataNascimento(),
						simulApos.getTempoContribuicaoCargoAtualFim()).getTotalDias();
				formula = formula.replace("@diasDeIdade", String.valueOf(diasDeIdade));
			}

			if (formula.contains("@tempoDeContribuicaoTotal")) {
				formula = formula.replace("@tempoDeContribuicaoTotal",
						String.valueOf(simulApos.getTempoTotalContribTotaisLicenInsalDeduc().getTotalDias()));
			}

			if (formula.contains("@tempoDeContribuicao")) {
				double tempoDeContribuicao = 0.0;
				if (simulApos.getSexo().equals("masculino")) {
					if (Objects.nonNull(regraAposentadoria.getTempoContribuicaoHomem()))
						tempoDeContribuicao = regraAposentadoria.getTempoContribuicaoHomem().doubleValue();
				} else {
					if (Objects.nonNull(regraAposentadoria.getTempoContribuicaoMulher()))
						tempoDeContribuicao = regraAposentadoria.getTempoContribuicaoMulher().doubleValue();
				}

				formula = formula.replace("@tempoDeContribuicao", String.valueOf(tempoDeContribuicao));
			}

			if (formula.contains("@tempoContribuicaoCargoAtualFim")) {
				long diffDays = UtilsSimulador
						.periodoContribuicao(simulApos.getTempoContribuicaoCargoAtualFim(), new Date()).getTotalDias();
				formula = formula.replace("@tempoContribuicaoCargoAtualFim", String.valueOf(diffDays));
			}

			if (formula.contains("@dataIngressoServicoPublico")) {
				long diffDays = UtilsSimulador
						.periodoContribuicao(simulApos.getDataIngressoServicoPublico(), new Date()).getTotalDias();
				formula = formula.replace("@dataIngressoServicoPublico", String.valueOf(diffDays));
			}

			Double resultDouble = Double.parseDouble(regraAposentadoriaService.executaFormula(formula));
			System.out.println(resultDouble);
			result = (int) Math.floor(resultDouble);
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Formula: " + formula + " erro: " + e.getMessage());
			throw new BadRequestException("A fórmula não está de acordo: " + formula, e);
		}

		return result;
	}

//	private Date criterioDeIdade(SimuladorAposentadoria simulApos, RegraAposentadoria regraAposentadoria) {
//		double diasRestantes = 0;
//
//		double diasParaAposentadoria = carregarIdadeParaAposentEmDias(simulApos, regraAposentadoria);
//
//		long diasDeIdade = UtilsSimulador
//				.periodoContribuicao(simulApos.getDataNascimento(), simulApos.getTempoContribuicaoCargoAtualFim())
//				.getTotalDias();
//
//		diasRestantes = Math.floor(diasParaAposentadoria - diasDeIdade);
//
//		Calendar c = Calendar.getInstance();
//		c.setTime(simulApos.getTempoContribuicaoCargoAtualFim());
//		c.set(Calendar.DATE, c.get(Calendar.DATE) + (int) Math.floor(diasRestantes));
//
//		return c.getTime();
//	}

//	private double carregarIdadeParaAposentEmDias(SimuladorAposentadoria simulApos,
//			RegraAposentadoria regraAposentadoria) {
//
//		String formula = null;
//
//		if (simulApos.getSexo().equals("masculino")) {
//			if (regraAposentadoria.getIdadeHomem() != null) {
//				return UtilsSimulador.carregarAnosEmDias(regraAposentadoria.getIdadeHomem());
//			} else {
//				formula = regraAposentadoria.getIdadeHomemFormula();
//			}
//
//		} else {
//			if (regraAposentadoria.getIdadeMulher() != null) {
//				return UtilsSimulador.carregarAnosEmDias(regraAposentadoria.getIdadeMulher());
//			} else {
//				formula = regraAposentadoria.getIdadeMulherFormula();
//			}
//		}
//
//		int result = 0;
//
//		try {
//			formula = formula.replaceFirst("=", "");
//
//			if (formula.contains("@diasDeIdade")) {
//				long diasDeIdade = UtilsSimulador.periodoContribuicao(simulApos.getDataNascimento(),
//						simulApos.getTempoContribuicaoCargoAtualFim()).getTotalDias();
//				formula = formula.replace("@diasDeIdade", String.valueOf(diasDeIdade));
//			}
//
//			if (formula.contains("@tempoDeContribuicaoTotal")) {
//				formula = formula.replace("@tempoDeContribuicaoTotal",
//						String.valueOf(simulApos.getTempoTotalContribTotais().getTotalDias()));
//			}
//
//			if (formula.contains("@tempoDeContribuicao")) {
//				double tempoDeContribuicao = carregarTempoDeContribuicao(regraAposentadoria, simulApos);
//				formula = formula.replace("@tempoDeContribuicao", String.valueOf(tempoDeContribuicao));
//			}
//
//			if (formula.contains("@tempoContribuicaoCargoAtualFim")) {
//				long diffDays = UtilsSimulador
//						.periodoContribuicao(simulApos.getTempoContribuicaoCargoAtualFim(), new Date()).getTotalDias();
//				formula = formula.replace("@tempoContribuicaoCargoAtualFim", String.valueOf(diffDays));
//			}
//
//			if (formula.contains("@dataIngressoServicoPublico")) {
//				long diffDays = UtilsSimulador
//						.periodoContribuicao(simulApos.getDataIngressoServicoPublico(), new Date()).getTotalDias();
//				formula = formula.replace("@dataIngressoServicoPublico", String.valueOf(diffDays));
//			}
//
//			Double resultDouble = Double.parseDouble(regraAposentadoriaService.executaFormula(formula));
//			System.out.println(resultDouble);
//			result = (int) Math.floor(resultDouble);
//			System.out.println(result);
//		} catch (Exception e) {
//			System.out.println("Formula: " + formula + " erro: " + e.getMessage());
//			throw new BadRequestException("A fórmula não está de acordo: " + formula, e);
//		}
//
//		return result;
//	}

	private Date criterioTempoDeServico(SimuladorAposentadoria simulApos, RegraAposentadoria regraAposentadoria,
			Date dataAposentadoria) {

		// Calculando a quantidade de dias trabalhados desde o ingresso no serviço
		// publico até hoje
		TempoTotal tempoServicoPublicoTrabalhado = simulApos.getTempoTotalOutrosPeriodosPublicos();

		// Somando o tempo total de outros serviços públicos com o tempo total licença
		// prêmio fator 1
		tempoServicoPublicoTrabalhado = UtilsSimulador.periodoContribuicaoSomar(tempoServicoPublicoTrabalhado,
				simulApos.getTempoTotalLicencasPremioFator1());

		// Somando o tempo total de outros serviços públicos com o tempo total licença
		// prêmio fator 2
		tempoServicoPublicoTrabalhado = UtilsSimulador.periodoContribuicaoSomar(tempoServicoPublicoTrabalhado,
				simulApos.getTempoTotalLicencasPremioFator2());

		// Subtraindo o tempo total de outros serviços públicos com o tempo total
		// deduções
		tempoServicoPublicoTrabalhado = UtilsSimulador.periodoContribuicaoSubtrair(tempoServicoPublicoTrabalhado,
				simulApos.getTempoTotalDeducoesSemContribuicao());

		// Transformando o tempo de serviço publico da regra de anos para dias
		int tempoServicoPublicoDias = UtilsSimulador.carregarAnosEmDias(regraAposentadoria.getTempoServicoPublico());

		long diasRestantes = tempoServicoPublicoDias - tempoServicoPublicoTrabalhado.getTotalDias();

		// Descobrindo quantos dias faltam
		Calendar c2 = Calendar.getInstance();
		c2.setTime(simulApos.getTempoContribuicaoCargoAtualFim());
		c2.set(Calendar.DATE, c2.get(Calendar.DATE) + Math.toIntExact(diasRestantes));

		if (c2.getTime().after(dataAposentadoria)) {
			dataAposentadoria = c2.getTime();
		}
		return dataAposentadoria;
	}

	private Date criterioTempoDeCarreira(SimuladorAposentadoria simulApos, RegraAposentadoria regraAposentadoria,
			Date dataAposentadoria) {

		// Calculando a quantidade de dias trabalhados
		long tempoDeCarreiraTrabalhadoDias = 0;

		if (Utils.checkList(simulApos.getOutrosPeriodos())) {
			for (OutroPeriodo outroPeriodoRequest : simulApos.getOutrosPeriodos()) {
				tempoDeCarreiraTrabalhadoDias += outroPeriodoRequest.getTempoTotal().getTotalDias();
			}
		}

		// Transformando o tempo de serviço publico da regra de anos para dias
		int tempoDeCarreiraDias = UtilsSimulador.carregarAnosEmDias(regraAposentadoria.getTempoCarreira());

		long diasRestantes = 0;
		diasRestantes = tempoDeCarreiraDias - tempoDeCarreiraTrabalhadoDias;

		// Descobrindo quantos dias faltam
		Calendar c2 = Calendar.getInstance();
		c2.setTime(simulApos.getTempoContribuicaoCargoAtualFim());
		c2.set(Calendar.DATE, c2.get(Calendar.DATE) + Math.toIntExact(diasRestantes));

		if (c2.getTime().after(dataAposentadoria)) {
			dataAposentadoria = c2.getTime();
		}
		return dataAposentadoria;
	}

	private Date criterioTempoDeCargoEfetivo(SimuladorAposentadoria simulApos, RegraAposentadoria regraAposentadoria,
			Date dataAposentadoria) {

		// Calculando a quantidade de dias trabalhados
		long tempoDeCargoEfetivoTrabalhadoDias = UtilsSimulador
				.periodoContribuicao(simulApos.getTempoContribuicaoCargoAtualInicio(),
						simulApos.getTempoContribuicaoCargoAtualFim())
				.getTotalDias();

		// Transformando o tempo de serviço publico da regra de anos para dias
		int tempoCargoEfetivoDias = UtilsSimulador.carregarAnosEmDias(regraAposentadoria.getTempoCargoEfetivo());

		long diasRestantes = 0;
		diasRestantes = tempoCargoEfetivoDias - tempoDeCargoEfetivoTrabalhadoDias;

		// Descobrindo quantos dias faltam
		Calendar c2 = Calendar.getInstance();
		c2.setTime(simulApos.getTempoContribuicaoCargoAtualFim());
		c2.set(Calendar.DATE, c2.get(Calendar.DATE) + Math.toIntExact(diasRestantes));

		if (c2.getTime().after(dataAposentadoria)) {
			dataAposentadoria = c2.getTime();
		}
		return dataAposentadoria;
	}

	private Date criterioTempoDeContribuicao(SimuladorAposentadoria simulApos, RegraAposentadoria regraAposentadoria,
			Date dataAposentadoria) {

		double tempoDeContribuicao = 0.0;
		if (simulApos.getSexo().equals("masculino")) {
			if (Objects.nonNull(regraAposentadoria.getTempoContribuicaoHomem()))
				tempoDeContribuicao = regraAposentadoria.getTempoContribuicaoHomem().doubleValue();
		} else {
			if (Objects.nonNull(regraAposentadoria.getTempoContribuicaoMulher()))
				tempoDeContribuicao = regraAposentadoria.getTempoContribuicaoMulher().doubleValue();
		}

		Double resultDouble = tempoDeContribuicao * 365.25;
		int tempoDeContribuicaoDias = (int) Math.floor(resultDouble);

		long diasRestantes = 0;
		if (regraAposentadoria.getTempoServicoEmPlenoExercicio()) {
			diasRestantes = tempoDeContribuicaoDias
					- simulApos.getTempoTotalContribTotaisLicenInsalDeduc().getTotalDias();

			if (Objects.nonNull(simulApos.getTempoTotalDeducoesComContribuicao()))
				diasRestantes += simulApos.getTempoTotalDeducoesComContribuicao().getTotalDias();
		} else {
			diasRestantes = tempoDeContribuicaoDias
					- simulApos.getTempoTotalContribTotaisLicenInsalDeduc().getTotalDias();
		}

		// Descobrindo quantos dias faltam
		Calendar c2 = Calendar.getInstance();
		c2.setTime(simulApos.getTempoContribuicaoCargoAtualFim());
		c2.set(Calendar.DATE, c2.get(Calendar.DATE) + Math.toIntExact(diasRestantes));

		if (regraAposentadoria.getPedagio()) {
			TempoTotal pedagioOutroPer = UtilsSimulador.periodoContribuicao(regraAposentadoria.getVigencia(),
					c2.getTime());
			Double pedagio = pedagioOutroPer.getTotalDias() * 0.2;
			c2.set(Calendar.DATE, c2.get(Calendar.DATE) + Math.toIntExact(pedagio.longValue()));
		}

		if (c2.getTime().after(dataAposentadoria)) {
			dataAposentadoria = c2.getTime();
		}
		return dataAposentadoria;
	}

}
