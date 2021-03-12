package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.RegraAposentadoriaFiltro;
import com.rhlinkcon.model.ModalidadeAposentadoriaEnum;
import com.rhlinkcon.model.RegraAposentadoria;
import com.rhlinkcon.model.TipoVigenciaEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.regraAposentadoria.RegraAposentadoriaRequest;
import com.rhlinkcon.payload.regraAposentadoria.RegraAposentadoriaResponse;
import com.rhlinkcon.repository.regraAposentadoria.RegraAposentadoriaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class RegraAposentadoriaService {

	@Autowired
	private RegraAposentadoriaRepository regraAposentadoriaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public RegraAposentadoriaResponse getRegraAposentadoriaById(Long regraAposentadoriaId) {
		RegraAposentadoria regraAposentadoria = regraAposentadoriaRepository.findById(regraAposentadoriaId)
				.orElseThrow(() -> new ResourceNotFoundException("RegraAposentadoria", "id", regraAposentadoriaId));

		RegraAposentadoriaResponse regraAposentadoriaResponse = new RegraAposentadoriaResponse(regraAposentadoria,
				Projecao.COMPLETA);
		Usuario criadoPor = usuarioRepository.findById(regraAposentadoria.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", regraAposentadoria.getCreatedBy()));
		regraAposentadoriaResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(regraAposentadoria.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", regraAposentadoria.getUpdatedBy()));
		regraAposentadoriaResponse.setAlteradoPor(alteradoPor.getNome());

		return regraAposentadoriaResponse;
	}

	public List<String> findAllModalidadesAposentadorias() {
		List<String> list = new ArrayList<String>();
		list.add(ModalidadeAposentadoriaEnum.GERAL.getLabel());
		List<RegraAposentadoria> modalidades = regraAposentadoriaRepository
				.findByModalidadeAposentadoriaAndModalidadeAposentadoriaNomeNotNull(
						ModalidadeAposentadoriaEnum.ESPECIFICA);
		for (RegraAposentadoria regraAposentadoria : modalidades) {
			if (!list.contains(regraAposentadoria.getModalidadeAposentadoriaNome())) {
				list.add(regraAposentadoria.getModalidadeAposentadoriaNome());
			}
		}
		return list;
	}

	private boolean validacaoFormula(String formula) {
//		validar se as chaves estão batendo com as variáveis dos objetos
//		throw new BadRequestException("A fórmula não está de acordo: " + formula, e);
		return true;
	}

	public String executaFormula(String formula) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		try {
			formula = setVariables(engine, formula);
			String result = engine.eval(formula).toString();
			System.out.println("Formula: " + formula);
			System.out.println("Resultado: " + result);
			return result;
		} catch (Exception e) {
			System.out.println("Formula: " + formula + " erro: " + e.getMessage());
			throw new BadRequestException("A fórmula não está de acordo: " + formula, e);
		}

	}

	private String setVariables(ScriptEngine engine, String formula) {
		String[] lines = initBreakLines(formula);
		StringBuilder sb = new StringBuilder();
		for (String linha : lines) {
			while (linha.contains("variavel(")) {
				int indice = linha.indexOf("variavel(");
				String valueObject = linha.substring(indice, linha.indexOf(")", indice) + 1);
				String entidade = valueObject.replace("variavel(", "");
				entidade = entidade.replace(")", "");
				engine.put(entidade, null);
				linha = linha.replace(valueObject, entidade);
			}
			sb.append(linha);
		}
		return sb.toString();
	}

	private String[] initBreakLines(String value) {
		return value.split("\n");
	}

	public PagedResponse<RegraAposentadoriaResponse> getAllRegraAposentadorias(int page, int size, String order,
			RegraAposentadoriaFiltro filtro) {
		validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<RegraAposentadoria> regraAposentadorias = regraAposentadoriaRepository.filtro(filtro, pageable);

		if (regraAposentadorias.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), regraAposentadorias.getNumber(),
					regraAposentadorias.getSize(), regraAposentadorias.getTotalElements(),
					regraAposentadorias.getTotalPages(), regraAposentadorias.isLast());
		}

		List<RegraAposentadoriaResponse> regraAposentadoriaResponses = regraAposentadorias.map(regraAposentadoria -> {
			Projecao projecao = Projecao.BASICA;
			return new RegraAposentadoriaResponse(regraAposentadoria, projecao);
		}).getContent();
		return new PagedResponse<>(regraAposentadoriaResponses, regraAposentadorias.getNumber(),
				regraAposentadorias.getSize(), regraAposentadorias.getTotalElements(),
				regraAposentadorias.getTotalPages(), regraAposentadorias.isLast());

	}

	public void deleteRegraAposentadoria(Long id) {
		RegraAposentadoria regraAposentadoria = regraAposentadoriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("RegraAposentadoria", "id", id));
		regraAposentadoriaRepository.delete(regraAposentadoria);
	}

	public void createRegraAposentadoria(RegraAposentadoriaRequest regraAposentadoriaRequest) {

		RegraAposentadoria regraAposentadoria = new RegraAposentadoria();

		regraAposentadoria.setModalidadeAposentadoria(regraAposentadoriaRequest.getModalidadeAposentadoria());
		if (regraAposentadoriaRequest.getModalidadeAposentadoria().equals(ModalidadeAposentadoriaEnum.ESPECIFICA)) {
			String nome = regraAposentadoriaRequest.getModalidadeAposentadoriaNome();
			if (nome == null || nome.isEmpty()) {
				throw new BadRequestException("O nome da modalidade específica é requerida.");
			} else {
				regraAposentadoria.setModalidadeAposentadoriaNome(nome);
			}
		} else if (regraAposentadoriaRequest.getModalidadeAposentadoria().equals(ModalidadeAposentadoriaEnum.GERAL)) {
			regraAposentadoria.setModalidadeAposentadoriaNome(null);
		}

		regraAposentadoria.setTipoAposentadoria(regraAposentadoriaRequest.getTipoAposentadoria());
		regraAposentadoria.setTipoRegra(regraAposentadoriaRequest.getTipoRegra());
		regraAposentadoria.setLeiBase(regraAposentadoriaRequest.getLeiBase());
		regraAposentadoria.setVigencia(regraAposentadoriaRequest.getVigencia());
		regraAposentadoria.setTempoServicoPublico(regraAposentadoriaRequest.getTempoServicoPublico());

		regraAposentadoria.setTempoCargoEfetivo(regraAposentadoriaRequest.getTempoCargoEfetivo());

		regraAposentadoria.setTempoCarreira(regraAposentadoriaRequest.getTempoCarreira());

		if (regraAposentadoriaRequest.getTempoContribuicaoHomem().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getTempoContribuicaoHomem())) {
				regraAposentadoria
						.setTempoContribuicaoHomemFormula(regraAposentadoriaRequest.getTempoContribuicaoHomem());
				regraAposentadoria.setTempoContribuicaoHomem(null);
			}
		} else {
			try {
				Integer tempoContribuicaoHomemInt = Integer
						.parseInt(regraAposentadoriaRequest.getTempoContribuicaoHomem());
				regraAposentadoria.setTempoContribuicaoHomem(tempoContribuicaoHomemInt);
				regraAposentadoria.setTempoContribuicaoHomemFormula(null);
			} catch (Exception e) {
				throw new BadRequestException(
						"A tempoContribuicao homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}

		if (regraAposentadoriaRequest.getTempoContribuicaoMulher().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getTempoContribuicaoMulher())) {
				regraAposentadoria
						.setTempoContribuicaoMulherFormula(regraAposentadoriaRequest.getTempoContribuicaoMulher());
				regraAposentadoria.setTempoContribuicaoMulher(null);
			}
		} else {
			try {
				Integer tempoContribuicaoMulherInt = Integer
						.parseInt(regraAposentadoriaRequest.getTempoContribuicaoMulher());
				regraAposentadoria.setTempoContribuicaoMulher(tempoContribuicaoMulherInt);
				regraAposentadoria.setTempoContribuicaoMulherFormula(null);
			} catch (Exception e) {
				throw new BadRequestException(
						"A tempoContribuicao homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}

		regraAposentadoria.setPedagio(regraAposentadoriaRequest.getPedagio());

		if (regraAposentadoriaRequest.getIdadeHomem().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getIdadeHomem())) {
				regraAposentadoria.setIdadeHomemFormula(regraAposentadoriaRequest.getIdadeHomem());
				regraAposentadoria.setIdadeHomem(null);
			}
		} else {
			try {
				Integer idadeHomemInt = Integer.parseInt(regraAposentadoriaRequest.getIdadeHomem());
				regraAposentadoria.setIdadeHomem(idadeHomemInt);
				regraAposentadoria.setIdadeHomemFormula(null);
			} catch (Exception e) {
				throw new BadRequestException("A idade homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}
		regraAposentadoria.setPedagio(Utils.setBool(regraAposentadoriaRequest.getPedagio()));

		if (regraAposentadoriaRequest.getIdadeMulher().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getIdadeMulher())) {
				regraAposentadoria.setIdadeMulherFormula(regraAposentadoriaRequest.getIdadeMulher());
				regraAposentadoria.setIdadeMulher(null);
			}
		} else {
			try {
				Integer idadeMulherInt = Integer.parseInt(regraAposentadoriaRequest.getIdadeMulher());
				regraAposentadoria.setIdadeMulher(idadeMulherInt);
				regraAposentadoria.setIdadeMulherFormula(null);
			} catch (Exception e) {
				throw new BadRequestException("A idade homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}

		regraAposentadoria.setLicencaPremio(Utils.setBool(regraAposentadoriaRequest.getLicencaPremio()));
		regraAposentadoria.setAbonoPermanencia(Utils.setBool(regraAposentadoriaRequest.getAbonoPermanencia()));
		regraAposentadoria.setArtigo(regraAposentadoriaRequest.getArtigo());

		regraAposentadoria.setProventos(regraAposentadoriaRequest.getProventos());
		regraAposentadoria.setReajuste(regraAposentadoriaRequest.getReajuste());

		regraAposentadoria
				.setTipoVigencia(TipoVigenciaEnum.getEnumByString(regraAposentadoriaRequest.getTipoVigencia()));
		regraAposentadoria.setTempoServicoEmPlenoExercicio(
				Utils.setBool(regraAposentadoriaRequest.getTempoServicoEmPlenoExercicio()));

		regraAposentadoriaRepository.save(regraAposentadoria);

	}

	public void updateRegraAposentadoria(RegraAposentadoriaRequest regraAposentadoriaRequest) {

		RegraAposentadoria regraAposentadoria = regraAposentadoriaRepository.findById(regraAposentadoriaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("RegraAposentadoria", "id",
						regraAposentadoriaRequest.getId()));

		regraAposentadoria.setModalidadeAposentadoria(regraAposentadoriaRequest.getModalidadeAposentadoria());

		if (regraAposentadoriaRequest.getModalidadeAposentadoria().equals(ModalidadeAposentadoriaEnum.ESPECIFICA)) {
			String nome = regraAposentadoriaRequest.getModalidadeAposentadoriaNome();
			if (nome == null || nome.isEmpty()) {
				throw new BadRequestException("O nome da modalidade específica é requerida.");
			} else {
				regraAposentadoria.setModalidadeAposentadoriaNome(nome);
			}
		} else if (regraAposentadoriaRequest.getModalidadeAposentadoria().equals(ModalidadeAposentadoriaEnum.GERAL)) {
			regraAposentadoria.setModalidadeAposentadoriaNome(null);
		}

		regraAposentadoria.setTipoAposentadoria(regraAposentadoriaRequest.getTipoAposentadoria());
		regraAposentadoria.setTipoRegra(regraAposentadoriaRequest.getTipoRegra());
		regraAposentadoria.setLeiBase(regraAposentadoriaRequest.getLeiBase());
		regraAposentadoria.setVigencia(regraAposentadoriaRequest.getVigencia());
		regraAposentadoria.setTempoServicoPublico(regraAposentadoriaRequest.getTempoServicoPublico());

		regraAposentadoria.setTempoCargoEfetivo(regraAposentadoriaRequest.getTempoCargoEfetivo());

		regraAposentadoria.setTempoCarreira(regraAposentadoriaRequest.getTempoCarreira());

		if (regraAposentadoriaRequest.getTempoContribuicaoHomem().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getTempoContribuicaoHomem())) {
				regraAposentadoria
						.setTempoContribuicaoHomemFormula(regraAposentadoriaRequest.getTempoContribuicaoHomem());
				regraAposentadoria.setTempoContribuicaoHomem(null);
			}
		} else {
			try {
				Integer tempoContribuicaoHomemInt = Integer
						.parseInt(regraAposentadoriaRequest.getTempoContribuicaoHomem());
				regraAposentadoria.setTempoContribuicaoHomem(tempoContribuicaoHomemInt);
				regraAposentadoria.setTempoContribuicaoHomemFormula(null);
			} catch (Exception e) {
				throw new BadRequestException(
						"A tempoContribuicao homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}

		if (regraAposentadoriaRequest.getTempoContribuicaoMulher().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getTempoContribuicaoMulher())) {
				regraAposentadoria
						.setTempoContribuicaoMulherFormula(regraAposentadoriaRequest.getTempoContribuicaoMulher());
				regraAposentadoria.setTempoContribuicaoMulher(null);
			}
		} else {
			try {
				Integer tempoContribuicaoMulherInt = Integer
						.parseInt(regraAposentadoriaRequest.getTempoContribuicaoMulher());
				regraAposentadoria.setTempoContribuicaoMulher(tempoContribuicaoMulherInt);
				regraAposentadoria.setTempoContribuicaoMulherFormula(null);
			} catch (Exception e) {
				throw new BadRequestException(
						"A tempoContribuicao homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}

		regraAposentadoria.setPedagio(regraAposentadoriaRequest.getPedagio());
		regraAposentadoria.setPedagio(Utils.setBool(regraAposentadoriaRequest.getPedagio()));

		if (regraAposentadoriaRequest.getIdadeHomem().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getIdadeHomem())) {
				regraAposentadoria.setIdadeHomemFormula(regraAposentadoriaRequest.getIdadeHomem());
				regraAposentadoria.setIdadeHomem(null);
			}
		} else {
			try {
				Integer idadeHomemInt = Integer.parseInt(regraAposentadoriaRequest.getIdadeHomem());
				regraAposentadoria.setIdadeHomem(idadeHomemInt);
				regraAposentadoria.setIdadeHomemFormula(null);
			} catch (Exception e) {
				throw new BadRequestException("A idade homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}

		if (regraAposentadoriaRequest.getIdadeMulher().startsWith("=")) {
			if (validacaoFormula(regraAposentadoriaRequest.getIdadeMulher())) {
				regraAposentadoria.setIdadeMulherFormula(regraAposentadoriaRequest.getIdadeMulher());
				regraAposentadoria.setIdadeMulher(null);
			}
		} else {
			try {
				Integer idadeMulherInt = Integer.parseInt(regraAposentadoriaRequest.getIdadeMulher());
				regraAposentadoria.setIdadeMulher(idadeMulherInt);
				regraAposentadoria.setIdadeMulherFormula(null);
			} catch (Exception e) {
				throw new BadRequestException("A idade homem não está de acordo. Não é um valor inteiro válido.", e);
			}
		}

		regraAposentadoria.setLicencaPremio(regraAposentadoriaRequest.getLicencaPremio());
		regraAposentadoria.setAbonoPermanencia(regraAposentadoriaRequest.getAbonoPermanencia());
		regraAposentadoria.setArtigo(regraAposentadoriaRequest.getArtigo());

		regraAposentadoria.setProventos(regraAposentadoriaRequest.getProventos());
		regraAposentadoria.setReajuste(regraAposentadoriaRequest.getReajuste());
		regraAposentadoria
				.setTipoVigencia(TipoVigenciaEnum.getEnumByString(regraAposentadoriaRequest.getTipoVigencia()));
		regraAposentadoria.setTempoServicoEmPlenoExercicio(
				Utils.setBool(regraAposentadoriaRequest.getTempoServicoEmPlenoExercicio()));

		regraAposentadoriaRepository.save(regraAposentadoria);

	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

}
