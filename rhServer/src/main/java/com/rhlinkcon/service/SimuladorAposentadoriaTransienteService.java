package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.rhlinkcon.payload.simuladorAposentadoria.Averbacao;
import com.rhlinkcon.payload.simuladorAposentadoria.Deducao;
import com.rhlinkcon.payload.simuladorAposentadoria.OutroPeriodo;
import com.rhlinkcon.payload.simuladorAposentadoria.OutroPeriodoPublico;
import com.rhlinkcon.payload.simuladorAposentadoria.SimuladorAposentadoria;
import com.rhlinkcon.payload.simuladorAposentadoria.TempoTotal;
import com.rhlinkcon.payload.simuladorAposentadoria.TipoDataEnum;
import com.rhlinkcon.payload.simuladorAposentadoria.UtilsSimulador;
import com.rhlinkcon.util.Utils;

@Service
public class SimuladorAposentadoriaTransienteService {

	public SimuladorAposentadoria carregarDadosDeTela(SimuladorAposentadoria simulApos) {

		// Tempo total dos atributos de tempo de contribuição de cargo atual
		simulApos.setTempoContribuicaoCargoAtualTempoTotal(UtilsSimulador.periodoContribuicao(
				simulApos.getTempoContribuicaoCargoAtualInicio(), simulApos.getTempoContribuicaoCargoAtualFim()));

		// Adicionando o tempo de contribuição do cargo atual nos outros períodos de
		// contribuição na carreira atual
		OutroPeriodo outroPeriodoCargoAtual = new OutroPeriodo();
		outroPeriodoCargoAtual.setTipo(TipoDataEnum.PERIODO);
		outroPeriodoCargoAtual.setInicio(simulApos.getTempoContribuicaoCargoAtualInicio());
		outroPeriodoCargoAtual.setFim(simulApos.getTempoContribuicaoCargoAtualFim());

		if (!Utils.checkList(simulApos.getOutrosPeriodos())) {
			simulApos.setOutrosPeriodos(new ArrayList<>());
		}
		simulApos.getOutrosPeriodos().add(outroPeriodoCargoAtual);

		// Carregando dias e tempo total em cada objeto "outro período" e somando todos
		// os dias e tempo total de todos os objetos "outro periodo"
		for (OutroPeriodo outroPeriodo : simulApos.getOutrosPeriodos()) {

			if (outroPeriodo.getTipo().equals(TipoDataEnum.PERIODO)) {
				outroPeriodo.setTempoTotal(
						UtilsSimulador.periodoContribuicao(outroPeriodo.getInicio(), outroPeriodo.getFim()));
			} else {
				outroPeriodo.setTempoTotal(
						new TempoTotal(outroPeriodo.getAno(), outroPeriodo.getMes(), outroPeriodo.getDia()));
			}

			simulApos.setTempoTotalOutrosPeriodos(UtilsSimulador
					.periodoContribuicaoSomar(simulApos.getTempoTotalOutrosPeriodos(), outroPeriodo.getTempoTotal()));
		}

		// Adicionando os outros periodos nos periodos de contribuicao no serviço
		// público
		if (!Utils.checkList(simulApos.getOutrosPeriodosPublicos())) {
			simulApos.setOutrosPeriodosPublicos(new ArrayList<>());
		}
		for (OutroPeriodo outroPeriodo : simulApos.getOutrosPeriodos()) {

			OutroPeriodoPublico outroPeriodoPublico = new OutroPeriodoPublico();
			outroPeriodoPublico.setTipo(outroPeriodo.getTipo());
			outroPeriodoPublico.setInicio(outroPeriodo.getInicio());
			outroPeriodoPublico.setFim(outroPeriodo.getFim());
			outroPeriodoPublico.setAno(outroPeriodo.getAno());
			outroPeriodoPublico.setMes(outroPeriodo.getMes());
			outroPeriodoPublico.setDia(outroPeriodo.getDia());

			simulApos.getOutrosPeriodosPublicos().add(outroPeriodoPublico);

		}

		// Carregando dias e tempo total em cada objeto "outro período público" e
		// somando todos os dias e tempo total de todos os objetos "outro periodo
		// público"
		for (OutroPeriodoPublico outroPeriodoPublico : simulApos.getOutrosPeriodosPublicos()) {

//			somente contabiliza tempo que for da mesma atividade
			String atividade = outroPeriodoPublico.getAtividade();

			if (Objects.isNull(atividade) || atividade.equals(simulApos.getModalidade())) {

				if (outroPeriodoPublico.getTipo().equals(TipoDataEnum.PERIODO)) {
					outroPeriodoPublico.setTempoTotal(UtilsSimulador
							.periodoContribuicao(outroPeriodoPublico.getInicio(), outroPeriodoPublico.getFim()));
				} else {
					outroPeriodoPublico.setTempoTotal(new TempoTotal(outroPeriodoPublico.getAno(),
							outroPeriodoPublico.getMes(), outroPeriodoPublico.getDia()));
				}

				simulApos.setTempoTotalOutrosPeriodosPublicos(UtilsSimulador.periodoContribuicaoSomar(
						simulApos.getTempoTotalOutrosPeriodosPublicos(), outroPeriodoPublico.getTempoTotal()));
			}
		}

		// Carregando dias e tempo total em cada objeto "averbacao" e
		// somando todos os dias e tempo total de todos os objetos "averbacao"
		if (Utils.checkList(simulApos.getAverbacoes())) {

			for (Averbacao averbacao : simulApos.getAverbacoes()) {

				// Somente averba tempos que são da mesma modalidade, falta inserir a parte de
				// tempo concomitante;
				if (averbacao.getAtividade().equals(simulApos.getModalidade())) {

					if (averbacao.getTipo().equals(TipoDataEnum.PERIODO)) {
						averbacao.setTempoTotal(
								UtilsSimulador.periodoContribuicao(averbacao.getInicio(), averbacao.getFim()));
					} else {
						averbacao.setTempoTotal(
								new TempoTotal(averbacao.getAno(), averbacao.getMes(), averbacao.getDia()));
					}

					simulApos.setTempoTotalAverbacoes(UtilsSimulador
							.periodoContribuicaoSomar(simulApos.getTempoTotalAverbacoes(), averbacao.getTempoTotal()));
				}
			}
		}

		// Carregando dias e tempo total em cada objeto "deducao" e
		// somando todos os dias e tempo total de todos os objetos "deducao"
		if (Utils.checkList(simulApos.getDeducoes())) {
			for (Deducao deducao : simulApos.getDeducoes()) {

				if (deducao.getTipo().equals(TipoDataEnum.PERIODO)) {
					deducao.setTempoTotal(UtilsSimulador.periodoContribuicao(deducao.getInicio(), deducao.getFim()));
				} else {
					deducao.setTempoTotal(new TempoTotal(deducao.getAno(), deducao.getMes(), deducao.getDia()));
				}

				if (deducao.getContribuicao().equals("sim")) {
					simulApos.setTempoTotalDeducoesComContribuicao(UtilsSimulador.periodoContribuicaoSomar(
							simulApos.getTempoTotalDeducoesComContribuicao(), deducao.getTempoTotal()));
				} else {
					simulApos.setTempoTotalDeducoesSemContribuicao(UtilsSimulador.periodoContribuicaoSomar(
							simulApos.getTempoTotalDeducoesSemContribuicao(), deducao.getTempoTotal()));
				}

				simulApos.setTempoTotalDeducoes(UtilsSimulador
						.periodoContribuicaoSomar(simulApos.getTempoTotalDeducoes(), deducao.getTempoTotal()));
			}
		}

		simulApos.setTempoTotalContribTotais(UtilsSimulador.periodoContribuicaoSomar(
				simulApos.getTempoTotalOutrosPeriodosPublicos(), simulApos.getTempoTotalAverbacoes()));

		// Licença-Prêmio: períodos adquiridos até 31/12/1988, contados em dobro após
		// isso não deve ser contabilizado.
		simulApos.setDiasLicencasPremioFator1(simulApos.getQtdLicencaPremioFator1() * 90);
		simulApos.setTempoTotalLicencasPremioFator1(
				UtilsSimulador.getTempoTotal(simulApos.getDiasLicencasPremioFator1()));

		simulApos.setDiasLicencasPremioFator2(simulApos.getQtdLicencaPremioFator2() * 180);
		simulApos.setTempoTotalLicencasPremioFator2(
				UtilsSimulador.getTempoTotal(simulApos.getDiasLicencasPremioFator2()));

		// Somando o tempo total geral
		simulApos.setTempoTotalContribTotaisLicenInsalDeduc(UtilsSimulador.periodoContribuicaoSomar(
				simulApos.getTempoTotalOutrosPeriodosPublicos(), simulApos.getTempoTotalAverbacoes()));

		// Somando novamente com o tempo total licença prêmio fator 1
		simulApos.setTempoTotalContribTotaisLicenInsalDeduc(UtilsSimulador.periodoContribuicaoSomar(
				simulApos.getTempoTotalContribTotaisLicenInsalDeduc(), simulApos.getTempoTotalLicencasPremioFator1()));

		// Somando novamente com o tempo total licença prêmio fator 2
		simulApos.setTempoTotalContribTotaisLicenInsalDeduc(UtilsSimulador.periodoContribuicaoSomar(
				simulApos.getTempoTotalContribTotaisLicenInsalDeduc(), simulApos.getTempoTotalLicencasPremioFator2()));

		// Subtraindo o tempo total deduções
		simulApos.setTempoTotalContribTotaisLicenInsalDeduc(
				UtilsSimulador.periodoContribuicaoSubtrair(simulApos.getTempoTotalContribTotaisLicenInsalDeduc(),
						simulApos.getTempoTotalDeducoesSemContribuicao()));

		return simulApos;

	}

}
