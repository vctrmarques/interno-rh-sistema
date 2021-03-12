
package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.NivelSalarialHistorico;
import com.rhlinkcon.model.NivelSalarialHistoricoOrigemAjusteEnum;
import com.rhlinkcon.model.SimuladorNivelSalarial;
import com.rhlinkcon.model.SimuladorNivelSalarialAcordo;
import com.rhlinkcon.model.SimuladorNivelSalarialValores;
import com.rhlinkcon.model.SituacaoSimuladorNivelSalarialEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;
import com.rhlinkcon.payload.simuladorNivelSalarial.*;
import com.rhlinkcon.repository.SimuladorNivelSalarialRepository;
import com.rhlinkcon.repository.SimuladorNivelSalarialValoresRepository;
import com.rhlinkcon.repository.SimuladorNivelSalarialAcordoRepository;
import com.rhlinkcon.repository.NivelSalarialHistoricoRepository;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class SimuladorNivelSalarialService {

	@Autowired
	private SimuladorNivelSalarialRepository simuladorNivelSalarialRepository;

	@Autowired
	private SimuladorNivelSalarialValoresRepository simuladorNivelSalarialValoresRepository;

	@Autowired
	private SimuladorNivelSalarialAcordoRepository simuladorNivelSalarialAcordoRepository;

	@Autowired
	private ReferenciaSalarialRepository nivelSalariaRepository;

	@Autowired
	private NivelSalarialHistoricoRepository nivelSalarialHistoricoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public PagedResponse<SimuladorNivelSalarialResponse> getAllNivelSalarialPage(int page, int size, String order,
			String descricao) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		Direction direction = Sort.Direction.ASC;
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<SimuladorNivelSalarial> simuladorNivelSalarials = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			simuladorNivelSalarials = simuladorNivelSalarialRepository.findByDescricaoIgnoreCaseContaining(descricao,
					pageable);
		} else {
			simuladorNivelSalarials = simuladorNivelSalarialRepository.findAll(pageable);
		}

		if (simuladorNivelSalarials.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), simuladorNivelSalarials.getNumber(),
					simuladorNivelSalarials.getSize(), simuladorNivelSalarials.getTotalElements(),
					simuladorNivelSalarials.getTotalPages(), simuladorNivelSalarials.isLast());
		}

		List<SimuladorNivelSalarialResponse> simuladorNivelSalarialResponses = simuladorNivelSalarials
				.map(simuladorNivelSalarial -> {
					SimuladorNivelSalarialResponse simuladorNivelSalarialResponse = new SimuladorNivelSalarialResponse(
							simuladorNivelSalarial);

					simuladorNivelSalarialResponse.setQuantidadeNiveis(
							simuladorNivelSalarialValoresRepository.countBySimulador(simuladorNivelSalarial));
					return simuladorNivelSalarialResponse;
				}).getContent();
		return new PagedResponse<>(simuladorNivelSalarialResponses, simuladorNivelSalarials.getNumber(),
				simuladorNivelSalarials.getSize(), simuladorNivelSalarials.getTotalElements(),
				simuladorNivelSalarials.getTotalPages(), simuladorNivelSalarials.isLast());
	}

	public List<ReferenciaSalarialResponse> getNiveisCompetencia(Long simuladorNivelSalarialId, String mes, String ano) {

		List<BigInteger> listaNotInBigInteger = simuladorNivelSalarialRepository
				.findSimuladorCompetencia(simuladorNivelSalarialId, mes, ano);
		List<Long> listaNotIn = new ArrayList<Long>();
		if (listaNotInBigInteger.size() > 0) {
			for (BigInteger id : listaNotInBigInteger) {
				listaNotIn.add(id.longValue());
			}
		}

		List<ReferenciaSalarial> listaNiveis = new ArrayList<ReferenciaSalarial>();
		List<ReferenciaSalarialResponse> retorno = new ArrayList<ReferenciaSalarialResponse>();
		if (listaNotIn.size() > 0) {
			listaNiveis = nivelSalariaRepository.findByIdNotIn(listaNotIn);
		} else {
			listaNiveis = nivelSalariaRepository.findAll();
		}

		for (ReferenciaSalarial nivelSalarial : listaNiveis) {
			retorno.add(new ReferenciaSalarialResponse(nivelSalarial));
		}

		return retorno;
	}

	public SimuladorNivelSalarialResponse getSimuladorNivelSalarialById(Long id) {

		SimuladorNivelSalarial simulador = simuladorNivelSalarialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("SimuladorNivelSalarial", "id", id));

		SimuladorNivelSalarialAcordo acordo = simuladorNivelSalarialAcordoRepository.findBySimulador(simulador);
		SimuladorNivelSalarialAcordoResponse acordoResponse = new SimuladorNivelSalarialAcordoResponse();
		if (acordo != null) {
			acordoResponse = new SimuladorNivelSalarialAcordoResponse(acordo);
		}

		List<SimuladorNivelSalarialValores> listaValores = simuladorNivelSalarialValoresRepository
				.findBySimulador(simulador);
		List<SimuladorNivelSalarialValoresResponse> listaSimuladorNivelSalarialValoresResponse = new ArrayList<SimuladorNivelSalarialValoresResponse>();
		if (listaValores.size() > 0) {
			listaValores.forEach((SimuladorNivelSalarialValores simuladorNivelSalarialValores) -> {
				SimuladorNivelSalarialValoresResponse simuladorNivelSalarialValoresResponse = new SimuladorNivelSalarialValoresResponse(
						simuladorNivelSalarialValores);
				listaSimuladorNivelSalarialValoresResponse.add(simuladorNivelSalarialValoresResponse);
			});
		}

		SimuladorNivelSalarialResponse simuladorNivelSalarialResponse = new SimuladorNivelSalarialResponse(simulador);
		simuladorNivelSalarialResponse.setAcordo(acordoResponse);
		simuladorNivelSalarialResponse.setSimuladorNivelSalarialValoresList(listaSimuladorNivelSalarialValoresResponse);

		Usuario criadoPor = usuarioRepository.findById(simulador.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", simulador.getCreatedBy()));
		simuladorNivelSalarialResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(simulador.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", simulador.getUpdatedBy()));
		simuladorNivelSalarialResponse.setAlteradoPor(alteradoPor.getNome());
		return simuladorNivelSalarialResponse;
	}

	public void createSimuladorNivelSalarial(SimuladorNivelSalarialRequest simuladorNivelSalarialRequest) {
		SimuladorNivelSalarial simulador = new SimuladorNivelSalarial();

		simulador.populate(simuladorNivelSalarialRequest);

		simuladorNivelSalarialRepository.save(simulador);

		// Valores nivel salarial
		if (Utils.checkList(simuladorNivelSalarialRequest.getSimuladorNivelSalarialValoresList())) {
			this.saveValores(simulador, simuladorNivelSalarialRequest.getSimuladorNivelSalarialValoresList());
		}

		// Acordo
		if (simuladorNivelSalarialRequest.getAcordo() != null) {
			this.saveAcodro(simulador, simuladorNivelSalarialRequest.getAcordo());
		}
	}

	public void updateSimuladorNivelSalarial(SimuladorNivelSalarialRequest simuladorNivelSalarialRequest) {

		SimuladorNivelSalarial simulador = simuladorNivelSalarialRepository
				.findById(simuladorNivelSalarialRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("SimuladorNivelSalarial", "id",
						simuladorNivelSalarialRequest.getId()));

		simulador.populate(simuladorNivelSalarialRequest);

		simuladorNivelSalarialRepository.save(simulador);

		// Valores nivel salarial
		this.deleteValores(simulador);
		if (Utils.checkList(simuladorNivelSalarialRequest.getSimuladorNivelSalarialValoresList())) {
			this.saveValores(simulador, simuladorNivelSalarialRequest.getSimuladorNivelSalarialValoresList());
		}

		// Acordo
		this.deleteAcordo(simulador);
		if (simuladorNivelSalarialRequest.getAcordo() != null) {
			this.saveAcodro(simulador, simuladorNivelSalarialRequest.getAcordo());
		}

	}

	public void deleteSimuladorNivelSalarial(Long id) {
		SimuladorNivelSalarial simulador = simuladorNivelSalarialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("SimuladorNivelSalarial", "id", id));

		this.deleteValores(simulador);
		this.deleteAcordo(simulador);
		simuladorNivelSalarialRepository.delete(simulador);
	}

	private void deleteValores(SimuladorNivelSalarial simulador) {
		List<SimuladorNivelSalarialValores> lista = simuladorNivelSalarialValoresRepository.findBySimulador(simulador);
		if (lista.size() > 0) {
			lista.forEach((SimuladorNivelSalarialValores simuladorNivelSalarialValores) -> {
				simuladorNivelSalarialValoresRepository.delete(simuladorNivelSalarialValores);
			});
		}
	}

	private void deleteAcordo(SimuladorNivelSalarial simulador) {
		SimuladorNivelSalarialAcordo acordo = simuladorNivelSalarialAcordoRepository.findBySimulador(simulador);

		if (acordo != null) {
			simuladorNivelSalarialAcordoRepository.delete(acordo);
		}
	}

	private void saveValores(SimuladorNivelSalarial simulador,
			List<SimuladorNivelSalarialValoresRequest> listaSimuladorValoresRequest) {
		listaSimuladorValoresRequest.forEach((SimuladorNivelSalarialValoresRequest simuladorValoresRequest) -> {

			SimuladorNivelSalarialValores simuladorValores = new SimuladorNivelSalarialValores();

			simuladorValores.setValorAjustado(simuladorValoresRequest.getValorAjustado());
			simuladorValores.setValorRetroativo(simuladorValoresRequest.getValorRetroativo());
			simuladorValores.setSimulador(simulador);

			ReferenciaSalarial nivelSalarial = nivelSalariaRepository.findById(simuladorValoresRequest.getNivelSalarialId())
					.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id",
							simuladorValoresRequest.getNivelSalarialId()));

			simuladorValores.setNivelSalarial(nivelSalarial);

			simuladorNivelSalarialValoresRepository.save(simuladorValores);

		});
	}

	private void saveAcodro(SimuladorNivelSalarial simulador, SimuladorNivelSalarialAcordoRequest acordoRequest) {
		SimuladorNivelSalarialAcordo acordo = new SimuladorNivelSalarialAcordo();
		acordo.populate(acordoRequest);
		acordo.setSimulador(simulador);
		simuladorNivelSalarialAcordoRepository.save(acordo);
	}

	private void verificaSimuladorProgramdo() {
		// aplicando simuladores programados
		Instant instant = Instant.now();
		Instant diaAnterior = instant.minus(Duration.ofDays(1));
		Instant diaPosterior = instant.plus(Duration.ofDays(1));

		List<SimuladorNivelSalarial> listaSimulador = simuladorNivelSalarialRepository
				.findByDataCompetenciaAfterAndDataCompetenciaBeforeAndProgramarAjusteAndSituacao(diaAnterior,
						diaPosterior, true, SituacaoSimuladorNivelSalarialEnum.PROGRAMADO);

		if (listaSimulador.size() > 0) {
			for (SimuladorNivelSalarial simulador : listaSimulador) {

				List<SimuladorNivelSalarialValores> listaValores = simuladorNivelSalarialValoresRepository
						.findBySimulador(simulador);

				if (listaValores.size() > 0) {
					listaValores.forEach((SimuladorNivelSalarialValores simuladorNivelSalarialValores) -> {

						NivelSalarialHistorico historico = new NivelSalarialHistorico();

						ReferenciaSalarial nivelSalarial = simuladorNivelSalarialValores.getNivelSalarial();

						historico.setValorOriginal(nivelSalarial.getValor());

						nivelSalarial.setValor(simuladorNivelSalarialValores.getValorAjustado());
						nivelSalariaRepository.save(nivelSalarial);

						historico.setNivelSalarial(nivelSalarial);
						historico.setValorAjustado(simuladorNivelSalarialValores.getValorAjustado());
						historico.setValorRetroativo(simuladorNivelSalarialValores.getValorRetroativo());
						historico.setOrigemAjuste(NivelSalarialHistoricoOrigemAjusteEnum.AJUSTE_PROGRAMADO);
						historico.setDataAjuste(new Date());
						historico.setSimulacao(simulador);

						nivelSalarialHistoricoRepository.save(historico);

					});
				}

				simulador.setSituacao(SituacaoSimuladorNivelSalarialEnum.APLICADO);
				simuladorNivelSalarialRepository.save(simulador);

			}
		}

	}

	private void excluirNaoProgramados() {

		// excluindo simuladores nao programados com mais de 3 meses
		Instant instant = Instant.now();
		Instant diaPosterior = instant.minus(Duration.ofDays(90));

		List<SimuladorNivelSalarial> listaSimulador = simuladorNivelSalarialRepository
				.findByDataCompetenciaBeforeAndSituacao(diaPosterior,
						SituacaoSimuladorNivelSalarialEnum.NAO_PROGRAMADO);

		if (listaSimulador.size() > 0) {
			for (SimuladorNivelSalarial simulador : listaSimulador) {
				this.deleteSimuladorNivelSalarial(simulador.getId());
			}
		}

	}

	public void executarJob() {
		this.excluirNaoProgramados();
		this.verificaSimuladorProgramdo();
	}

}
