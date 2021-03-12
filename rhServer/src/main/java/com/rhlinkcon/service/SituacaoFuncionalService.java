package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.CausaAfastamento;
import com.rhlinkcon.model.ModalidadeAfastamentoEnum;
import com.rhlinkcon.model.MotivoAfastamento;
import com.rhlinkcon.model.MotivoDesligamento;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.TipoAfastamentoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalRequest;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;
import com.rhlinkcon.repository.CausaAfastamentoRepository;
import com.rhlinkcon.repository.MotivoAfastamentoRepository;
import com.rhlinkcon.repository.MotivoDesligamentoRepository;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class SituacaoFuncionalService {

	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	@Autowired
	private MotivoAfastamentoRepository motivoAfastamentoRepository;

	@Autowired
	private MotivoDesligamentoRepository motivoDesligamentoRepository;

	@Autowired
	private CausaAfastamentoRepository causaAfastamentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createSituacaoFuncional(SituacaoFuncionalRequest situacaoFuncionalRequest) {

		// Creating user's account
		SituacaoFuncional situacaoFuncional = new SituacaoFuncional(situacaoFuncionalRequest);

		setEntidades(situacaoFuncional, situacaoFuncionalRequest);

		situacaoFuncionalRepository.save(situacaoFuncional);

	}

	public void updateSituacaoFuncional(SituacaoFuncionalRequest situacaoFuncionalRequest) {

		// Updating user's account
		SituacaoFuncional situacaoFuncional = situacaoFuncionalRepository.findById(situacaoFuncionalRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("Afastamento", "id", situacaoFuncionalRequest.getId()));

		situacaoFuncional.setCodigo(situacaoFuncionalRequest.getCodigo());
		situacaoFuncional.setDescricao(situacaoFuncionalRequest.getDescricao());
		situacaoFuncional.setRais(situacaoFuncionalRequest.getRais());
		situacaoFuncional.setRaisSituacao(situacaoFuncionalRequest.getRaisSituacao());
		situacaoFuncional.setQtdDias(situacaoFuncionalRequest.getQtdDias());
		situacaoFuncional.setEntraFolha(situacaoFuncionalRequest.isEntraFolha());

		if (Strings.isNotEmpty(situacaoFuncionalRequest.getTipo()))
			situacaoFuncional.setTipo(TipoAfastamentoEnum.getEnumByString(situacaoFuncionalRequest.getTipo()));
		else
			situacaoFuncional.setTipo(null);
		if (Strings.isNotEmpty(situacaoFuncionalRequest.getModalidade()))
			situacaoFuncional
					.setModalidade(ModalidadeAfastamentoEnum.getEnumByString(situacaoFuncionalRequest.getModalidade()));
		else
			situacaoFuncional.setModalidade(null);
		setEntidades(situacaoFuncional, situacaoFuncionalRequest);

		situacaoFuncionalRepository.save(situacaoFuncional);

	}

	public void deleteSituacaoFuncional(Long id) {
		SituacaoFuncional situacaoFuncional = situacaoFuncionalRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CentroCusto", "id", id));

		situacaoFuncionalRepository.delete(situacaoFuncional);
	}

	public PagedResponse<SituacaoFuncionalResponse> getAllSituacaoFuncional(int page, int size, String descricao,
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

		Page<SituacaoFuncional> situacaoFuncionals = situacaoFuncionalRepository
				.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (situacaoFuncionals.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), situacaoFuncionals.getNumber(),
					situacaoFuncionals.getSize(), situacaoFuncionals.getTotalElements(),
					situacaoFuncionals.getTotalPages(), situacaoFuncionals.isLast());
		}

		List<SituacaoFuncionalResponse> afastamentosResponse = situacaoFuncionals.map(afastamento -> {
			return new SituacaoFuncionalResponse(afastamento);
		}).getContent();
		return new PagedResponse<>(afastamentosResponse, situacaoFuncionals.getNumber(), situacaoFuncionals.getSize(),
				situacaoFuncionals.getTotalElements(), situacaoFuncionals.getTotalPages(), situacaoFuncionals.isLast());

	}

	public SituacaoFuncionalResponse getSituacaoFuncionalById(Long afastamentoId) {
		SituacaoFuncional situacaoFuncional = situacaoFuncionalRepository.findById(afastamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("Afastamento", "id", afastamentoId));

		Usuario userCreated = usuarioRepository.findById(situacaoFuncional.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", situacaoFuncional.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(situacaoFuncional.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", situacaoFuncional.getUpdatedBy()));

		SituacaoFuncionalResponse situacaoFuncionalResponse = new SituacaoFuncionalResponse(situacaoFuncional,
				situacaoFuncional.getCreatedAt(), userCreated.getNome(), situacaoFuncional.getUpdatedAt(),
				userUpdated.getNome());

		return situacaoFuncionalResponse;
	}

	public void setEntidades(SituacaoFuncional situacaoFuncional, SituacaoFuncionalRequest situacaoFuncionalRequest) {
		if (Objects.nonNull(situacaoFuncionalRequest.getMotivoAfastamentoId())) {
			MotivoAfastamento motivoAfastamento = motivoAfastamentoRepository
					.findById(situacaoFuncionalRequest.getMotivoAfastamentoId())
					.orElseThrow(() -> new ResourceNotFoundException("MotivoAfastamento", "id",
							situacaoFuncionalRequest.getMotivoAfastamentoId()));

			situacaoFuncional.setMotivoAfastamento(motivoAfastamento);
		} else
			situacaoFuncional.setMotivoAfastamento(null);

		if (situacaoFuncionalRequest.getMotivoDesligamentoId() != null) {
			MotivoDesligamento motivoDesligamento = motivoDesligamentoRepository
					.findById(situacaoFuncionalRequest.getMotivoDesligamentoId())
					.orElseThrow(() -> new ResourceNotFoundException("MotivoDesligamento", "id",
							situacaoFuncionalRequest.getMotivoDesligamentoId()));

			situacaoFuncional.setMotivoDesligamento(motivoDesligamento);
		} else
			situacaoFuncional.setMotivoDesligamento(null);

		if (situacaoFuncionalRequest.getCausaAfastamentoId() != null) {
			CausaAfastamento causaAfastamento = causaAfastamentoRepository
					.findById(situacaoFuncionalRequest.getCausaAfastamentoId())
					.orElseThrow(() -> new ResourceNotFoundException("CausaAfastamento", "id",
							situacaoFuncionalRequest.getCausaAfastamentoId()));

			situacaoFuncional.setCausaAfastamento(causaAfastamento);
		} else
			situacaoFuncional.setCausaAfastamento(null);

	}

	public List<SituacaoFuncionalResponse> searchAllByDescricao(String search) {
		List<SituacaoFuncionalResponse> itensResponses = new ArrayList<SituacaoFuncionalResponse>();
		situacaoFuncionalRepository.findByDescricaoIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new SituacaoFuncionalResponse(item)));
		return itensResponses;
	}

	public List<SituacaoFuncionalResponse> getAllSituacoesFuncionais() {

		List<SituacaoFuncional> situacaoFuncionals = situacaoFuncionalRepository.findAll();
		List<SituacaoFuncionalResponse> afastamentosResponse = new ArrayList<>();

		if (!situacaoFuncionals.isEmpty()) {
			for (SituacaoFuncional situacaoFuncional : situacaoFuncionals) {

				afastamentosResponse.add(new SituacaoFuncionalResponse(situacaoFuncional));
			}
			return afastamentosResponse;
		}

		return null;
	}

	public List<SituacaoFuncionalResponse> getSituacoesFuncionaisTipoAfastamento() {
		List<SituacaoFuncional> situacaoFuncionals = situacaoFuncionalRepository
				.findByTipo(TipoAfastamentoEnum.AFASTAMENTO);
		List<SituacaoFuncionalResponse> afastamentosResponse = new ArrayList<>();

		if (!situacaoFuncionals.isEmpty()) {
			for (SituacaoFuncional situacaoFuncional : situacaoFuncionals) {

				afastamentosResponse.add(new SituacaoFuncionalResponse(situacaoFuncional));
			}
			return afastamentosResponse;
		}

		return null;
	}

	public List<SituacaoFuncionalResponse> listSituaFuncionalEntraFolha(boolean entraFolha) {
		List<SituacaoFuncional> list = situacaoFuncionalRepository.findByEntraFolha(entraFolha);
		List<SituacaoFuncionalResponse> listResponses = new ArrayList<SituacaoFuncionalResponse>();
		if (!list.isEmpty()) {
			listResponses = list.stream().map(sf -> new SituacaoFuncionalResponse(sf, Projecao.BASICA))
					.collect(Collectors.toList());
		}
		return listResponses;
	}

	public List<DadoBasicoDto> getSituacoesFuncionaisList(String search, Boolean entraFolha) {
		List<DadoBasicoDto> itensResponses = null;

		if (entraFolha != null) {
			if (Utils.checkStr(search))
				itensResponses = situacaoFuncionalRepository
						.findByDescricaoIgnoreCaseContainingAndEntraFolha(search, entraFolha).stream()
						.map(e -> new DadoBasicoDto(e)).collect(Collectors.toList());
			else
				itensResponses = situacaoFuncionalRepository.findByEntraFolha(entraFolha).stream()
						.map(e -> new DadoBasicoDto(e)).collect(Collectors.toList());
		} else {
			if (Utils.checkStr(search))
				itensResponses = situacaoFuncionalRepository.findByDescricaoIgnoreCaseContaining(search).stream()
						.map(e -> new DadoBasicoDto(e)).collect(Collectors.toList());
			else
				itensResponses = situacaoFuncionalRepository.findAll().stream().map(e -> new DadoBasicoDto(e))
						.collect(Collectors.toList());
		}

		return itensResponses;
	}

}
