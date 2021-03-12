package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.LegislacaoFiltroRequest;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.legislacao.AssuntoNorma;
import com.rhlinkcon.model.legislacao.DetalhamentoNorma;
import com.rhlinkcon.model.legislacao.EnteFederado;
import com.rhlinkcon.model.legislacao.Legislacao;
import com.rhlinkcon.model.legislacao.LegislacaoAnexo;
import com.rhlinkcon.model.legislacao.Norma;
import com.rhlinkcon.model.legislacao.TextoDocumento;
import com.rhlinkcon.model.legislacao.UnidadeGestora;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.legislacao.DetalhamentoNormaResponse;
import com.rhlinkcon.payload.legislacao.LegislacaoAnexoRequest;
import com.rhlinkcon.payload.legislacao.LegislacaoRequest;
import com.rhlinkcon.payload.legislacao.LegislacaoResponse;
import com.rhlinkcon.payload.legislacao.NormaResponse;
import com.rhlinkcon.repository.legislacao.AssuntoNormaRepository;
import com.rhlinkcon.repository.legislacao.DetalhamentoNormaRepository;
import com.rhlinkcon.repository.legislacao.EnteFederadoRepository;
import com.rhlinkcon.repository.legislacao.LegislacaoAnexoRepository;
import com.rhlinkcon.repository.legislacao.LegislacaoRepository;
import com.rhlinkcon.repository.legislacao.NormaRepository;
import com.rhlinkcon.repository.legislacao.TextoDocumentoRepository;
import com.rhlinkcon.repository.legislacao.UnidadeGestoraRepository;
import com.rhlinkcon.util.Utils;

@Service
public class LegislacaoService {

	@Autowired
	private LegislacaoRepository legislacaoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private NormaRepository normaRepository;

	@Autowired
	private AssuntoNormaRepository assuntoNormaRepository;

	@Autowired
	private DetalhamentoNormaRepository detalhamentoNormaRepository;

	@Autowired
	private EnteFederadoRepository enteFederadoRepository;

	@Autowired
	private UnidadeGestoraRepository unidadeGestoraRepository;

	@Autowired
	private TextoDocumentoRepository textoDocumentoRepository;

	@Autowired
	private LegislacaoAnexoRepository legislacaoAnexoRepository;

	@Autowired
	private AnexoService anexoService;

	public LegislacaoResponse getById(Long legislacaoId) {
		Legislacao legislacao = legislacaoRepository.findById(legislacaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Legislacao", "id", legislacaoId));

		LegislacaoResponse legislacaoResponse = new LegislacaoResponse(legislacao);
		legislacaoResponse.setCriadoPor(usuarioService.criadoPor(legislacao));
		legislacaoResponse.setAlteradoPor(usuarioService.alteradoPor(legislacao));
		return legislacaoResponse;
	}

	public List<DadoBasicoDto> getDadoBasicoList() {
		List<Legislacao> legislacaoList = legislacaoRepository.findAll();
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();

		legislacaoList.forEach(persistentId -> {
			dadoBasicoList.add(new DadoBasicoDto(persistentId));
		});

		return dadoBasicoList;
	}

	public List<NormaResponse> getNormaResponseList(String search) {
		List<NormaResponse> itensResponses = null;
		if (Utils.checkStr(search))
			itensResponses = normaRepository.getNormaResponseListByDescricao(search);
		else
			itensResponses = normaRepository.getNormaResponseList();
		return itensResponses;
	}

	public List<DadoBasicoDto> getDadoBasicoListAssuntoNorma(String search) {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();

		List<AssuntoNorma> unidadeGestoraList = null;
		if (Utils.checkStr(search))
			unidadeGestoraList = assuntoNormaRepository.findByDescricaoIgnoreCaseContaining(search);
		else
			unidadeGestoraList = assuntoNormaRepository.findAll();

		unidadeGestoraList.forEach(persistentId -> {
			dadoBasicoList.add(new DadoBasicoDto(persistentId));
		});

		return dadoBasicoList;
	}

	public List<DetalhamentoNormaResponse> searchDetalhamentoNorma(String search) {
		List<DetalhamentoNormaResponse> itensResponses = null;
		if (Utils.checkStr(search))
			itensResponses = detalhamentoNormaRepository.findComboCompleteDtoByDescricao(search);
		else
			itensResponses = detalhamentoNormaRepository.findComboCompleteDto();
		return itensResponses;
	}

	public List<DadoBasicoDto> getDadoBasicoListEnteFederado(String search) {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();

		List<EnteFederado> unidadeGestoraList = null;
		if (Utils.checkStr(search))
			unidadeGestoraList = enteFederadoRepository.findByDescricaoIgnoreCaseContaining(search);
		else
			unidadeGestoraList = enteFederadoRepository.findAll();

		unidadeGestoraList.forEach(persistentId -> {
			dadoBasicoList.add(new DadoBasicoDto(persistentId));
		});

		return dadoBasicoList;
	}

	public List<DadoBasicoDto> getDadoBasicoListUnidadeGestora(String search) {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();

		List<UnidadeGestora> unidadeGestoraList = null;
		if (Utils.checkStr(search))
			unidadeGestoraList = unidadeGestoraRepository.findByDescricaoIgnoreCaseContaining(search);
		else
			unidadeGestoraList = unidadeGestoraRepository.findAll();

		unidadeGestoraList.forEach(persistentId -> {
			dadoBasicoList.add(new DadoBasicoDto(persistentId));
		});

		return dadoBasicoList;
	}

	public List<DadoBasicoDto> getDadoBasicoListTextoDocumento(String search) {
		List<DadoBasicoDto> dadoBasicoList = new ArrayList<DadoBasicoDto>();

		List<TextoDocumento> unidadeGestoraList = null;
		if (Utils.checkStr(search))
			unidadeGestoraList = textoDocumentoRepository.findByDescricaoIgnoreCaseContaining(search);
		else
			unidadeGestoraList = textoDocumentoRepository.findAll();

		unidadeGestoraList.forEach(persistentId -> {
			dadoBasicoList.add(new DadoBasicoDto(persistentId));
		});

		return dadoBasicoList;
	}

	public PagedResponse<LegislacaoResponse> get(PagedRequest pagedRequest,
			LegislacaoFiltroRequest legislacaoFiltroRequest) {

		Pageable pageable = Utils.generatePegeableGeneric(pagedRequest);

		Page<Legislacao> legislacaos = legislacaoRepository.filtro(legislacaoFiltroRequest, pageable);

		if (legislacaos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), legislacaos.getNumber(), legislacaos.getSize(),
					legislacaos.getTotalElements(), legislacaos.getTotalPages(), legislacaos.isLast());
		}

		List<LegislacaoResponse> legislacaoResponses = legislacaos.map(legislacao -> {
			return new LegislacaoResponse(legislacao);
		}).getContent();
		return new PagedResponse<>(legislacaoResponses, legislacaos.getNumber(), legislacaos.getSize(),
				legislacaos.getTotalElements(), legislacaos.getTotalPages(), legislacaos.isLast());

	}

	public void delete(Long id) {
		Legislacao legislacao = legislacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Legislacao", "id", id));

		if (legislacaoRepository.existsByPessoalLegislacao(legislacao))
			throw new BadRequestException(
					"Não é possível excluir essa Legislação / Norma, pois ela é regulamentada por outra Legislação / Norma.");

		// Exclusão dos anexos.
		for (LegislacaoAnexo legislacaoAnexo : legislacao.getAnexos()) {
			Long anexoToDeleteId = legislacaoAnexo.getAnexo().getId();
			legislacaoAnexoRepository.delete(legislacaoAnexo);
			anexoService.deleteAnexo(anexoToDeleteId);
		}

		legislacaoRepository.delete(legislacao);
	}

	public void create(LegislacaoRequest legislacaoRequest) {

		if (legislacaoRepository.existsByNumeroNormaAndEnteFederadoIdAndNormaId(legislacaoRequest.getNumeroNorma(),
				legislacaoRequest.getEnteFederado().getId(), legislacaoRequest.getNorma().getId()))
			throw new BadRequestException("Por favor, verifique os dados inseridos. Já existe uma normal / "
					+ "lei, que possui o mesmo número, tipo de ente federado e tipo de normal preenchidos por você!");

		Norma norma = normaRepository.findById(legislacaoRequest.getNorma().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Norma", "id", legislacaoRequest.getNorma().getId()));

		DetalhamentoNorma detalhamentoNorma = detalhamentoNormaRepository
				.findById(legislacaoRequest.getDetalhamentoNorma().getId())
				.orElseThrow(() -> new ResourceNotFoundException("DetalhamentoNorma", "id",
						legislacaoRequest.getDetalhamentoNorma().getId()));

		// RN03.2
		if (norma.getCodigo().equals(10)) {
			if (legislacaoRequest.getAnoNorma() > 1988)
				throw new BadRequestException("Legislação com ano " + legislacaoRequest.getAnoNorma()
						+ " é superior a 1988. A regra 181 do Colare não permite a criação desta Legislação.");
		}

		// RN03.3
		if (norma.getCodigo().equals(5)) {
			if (!legislacaoRequest.getNumeroNorma().equals(legislacaoRequest.getAnoNorma()))
				throw new BadRequestException("Legislação com número e ano diferentes. "
						+ "  A regra 186 do Colare não permite a criação desta Legislação.");
		}

		// RN04.2
		Integer codigoNorma = norma.getCodigo();
		if (codigoNorma.equals(11) || codigoNorma.equals(14) || codigoNorma.equals(15) || codigoNorma.equals(16)
				|| codigoNorma.equals(17)) {

			Integer codigoDetalha = detalhamentoNorma.getCodigo();
			if (codigoDetalha.equals(1) || codigoDetalha.equals(2) || codigoDetalha.equals(3) || codigoDetalha.equals(6)
					|| codigoDetalha.equals(8) || codigoDetalha.equals(9) || codigoDetalha.equals(10)) {
				throw new BadRequestException(
						"Legislação com detalhamento de norma inválida para o tipo de norma selecionada. "
								+ "  A regra 158 do Colare não permite a criação desta Legislação.");
			}
		}

		Legislacao legislacao = new Legislacao();

		legislacao.setNumeroNorma(legislacaoRequest.getNumeroNorma());
		legislacao.setAnoNorma(legislacaoRequest.getAnoNorma());
		legislacao.setEmentaNorma(legislacaoRequest.getEmentaNorma());
		legislacao.setInicioEfeitoFinanceiro(legislacaoRequest.getInicioEfeitoFinanceiro());
		legislacao.setPublicacao(legislacaoRequest.getPublicacao());
		legislacao.setInicioVigencia(legislacaoRequest.getInicioVigencia());
		legislacao.setFimVigencia(legislacaoRequest.getFimVigencia());

		legislacao.setEnteFederado(new EnteFederado(legislacaoRequest.getEnteFederado().getId()));
		legislacao.setNorma(norma);
		legislacao.setDetalhamentoNorma(detalhamentoNorma);
		legislacao.setAssuntoNorma(new AssuntoNorma(legislacaoRequest.getAssuntoNorma().getId()));
		legislacao.setUnidadeGestora(new UnidadeGestora(legislacaoRequest.getUnidadeGestora().getId()));

		if (Objects.nonNull(legislacaoRequest.getPessoalLegislacao())) {
			Legislacao legislacaoPessoal = legislacaoRepository
					.findById(legislacaoRequest.getPessoalLegislacao().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Legislacao", "id",
							legislacaoRequest.getPessoalLegislacao().getId()));
			legislacao.setPessoalLegislacao(legislacaoPessoal);
		}

		legislacaoRepository.save(legislacao);

		for (LegislacaoAnexoRequest legislacaoAnexoRequest : legislacaoRequest.getLegislacaoAnexoList()) {
			LegislacaoAnexo legislacaoAnexo = new LegislacaoAnexo();
			legislacaoAnexo.setLegislacao(legislacao);
			legislacaoAnexo.setAnexo(new Anexo(legislacaoAnexoRequest.getAnexo().getId()));
			legislacaoAnexo.setTextoDocumento(new TextoDocumento(legislacaoAnexoRequest.getTextoDocumento().getId()));

			legislacaoAnexoRepository.save(legislacaoAnexo);

		}

	}

	public void update(LegislacaoRequest legislacaoRequest) {

		Legislacao legislacao = legislacaoRepository.findById(legislacaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Legislacao", "id", legislacaoRequest.getId()));

		if (legislacaoRepository.existsByNumeroNormaAndEnteFederadoIdAndNormaIdAndIdNot(
				legislacaoRequest.getNumeroNorma(), legislacaoRequest.getEnteFederado().getId(),
				legislacaoRequest.getNorma().getId(), legislacao.getId()))
			throw new BadRequestException("Por favor, verifique os dados inseridos. Já existe uma normal / "
					+ "lei, que possui o mesmo número, tipo de ente federado e tipo de normal preenchidos por você!");

		legislacao.setNumeroNorma(legislacaoRequest.getNumeroNorma());
		legislacao.setAnoNorma(legislacaoRequest.getAnoNorma());
		legislacao.setEmentaNorma(legislacaoRequest.getEmentaNorma());
		legislacao.setInicioEfeitoFinanceiro(legislacaoRequest.getInicioEfeitoFinanceiro());
		legislacao.setPublicacao(legislacaoRequest.getPublicacao());
		legislacao.setInicioVigencia(legislacaoRequest.getInicioVigencia());
		legislacao.setFimVigencia(legislacaoRequest.getFimVigencia());

		legislacao.setEnteFederado(new EnteFederado(legislacaoRequest.getEnteFederado().getId()));
		legislacao.setNorma(new Norma(legislacaoRequest.getNorma().getId()));
		legislacao.setDetalhamentoNorma(new DetalhamentoNorma(legislacaoRequest.getDetalhamentoNorma().getId()));
		legislacao.setAssuntoNorma(new AssuntoNorma(legislacaoRequest.getAssuntoNorma().getId()));
		legislacao.setUnidadeGestora(new UnidadeGestora(legislacaoRequest.getUnidadeGestora().getId()));

		if (Objects.nonNull(legislacaoRequest.getPessoalLegislacao())) {
			Legislacao legislacaoPessoal = legislacaoRepository
					.findById(legislacaoRequest.getPessoalLegislacao().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Legislacao", "id",
							legislacaoRequest.getPessoalLegislacao().getId()));
			legislacao.setPessoalLegislacao(legislacaoPessoal);
		} else {
			legislacao.setPessoalLegislacao(null);
		}

		legislacaoRepository.save(legislacao);

		// Apaga anexos excluídos
		Optional<List<LegislacaoAnexo>> legislacaoAnexoListOpt = legislacaoAnexoRepository.findByLegislacao(legislacao);

		if (legislacaoAnexoListOpt.isPresent()) {

			for (LegislacaoAnexo legislacaoAnexo : legislacaoAnexoListOpt.get()) {
				boolean toDelete = true;

				for (LegislacaoAnexoRequest legislacaoAnexoRequest : legislacaoRequest.getLegislacaoAnexoList()) {
					if (Objects.nonNull(legislacaoAnexoRequest.getId()) && legislacaoAnexoRequest.getId() != 0) {
						if (legislacaoAnexo.getId().equals(legislacaoAnexoRequest.getId())) {
							toDelete = false;
							break;
						}
					} else {
						continue;
					}
				}

				if (toDelete) {
					Long anexoToDelete = legislacaoAnexo.getAnexo().getId();
					legislacaoAnexoRepository.deleteById(legislacaoAnexo.getId());
					anexoService.deleteAnexo(anexoToDelete);
				}
			}
		}

		// Cria os novos anexos inseridos.
		for (LegislacaoAnexoRequest legislacaoAnexoRequest : legislacaoRequest.getLegislacaoAnexoList()) {

			if (Objects.isNull(legislacaoAnexoRequest.getId()) || legislacaoAnexoRequest.getId() == 0) {
				LegislacaoAnexo legislacaoAnexo = new LegislacaoAnexo();
				legislacaoAnexo.setLegislacao(legislacao);
				legislacaoAnexo.setAnexo(new Anexo(legislacaoAnexoRequest.getAnexo().getId()));
				legislacaoAnexo
						.setTextoDocumento(new TextoDocumento(legislacaoAnexoRequest.getTextoDocumento().getId()));

				legislacaoAnexoRepository.save(legislacaoAnexo);
			}

		}

	}

}
