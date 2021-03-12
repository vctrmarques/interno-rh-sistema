package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.TipoProcessamento;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoRequest;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoResponse;
import com.rhlinkcon.repository.TipoProcessamentoRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TipoProcessamentoService {

	@Autowired
	private TipoProcessamentoRepository tipoProcessamentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private VerbaRepository verbaRepository;

	public TipoProcessamentoResponse getTipoProcessamentoById(Long tipoProcessamentoId) {
		TipoProcessamento tipoProcessamento = tipoProcessamentoRepository.findById(tipoProcessamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoProcessamento", "id", tipoProcessamentoId));

		TipoProcessamentoResponse tipoProcessamentoResponse = new TipoProcessamentoResponse(tipoProcessamento);
		Usuario criadoPor = usuarioRepository.findById(tipoProcessamento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", tipoProcessamento.getCreatedBy()));
		tipoProcessamentoResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(tipoProcessamento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", tipoProcessamento.getUpdatedBy()));
		tipoProcessamentoResponse.setAlteradoPor(alteradoPor.getNome());

		return tipoProcessamentoResponse;
	}

	public PagedResponse<TipoProcessamentoResponse> getAllTipoProcessamentoPage(int page, int size, String order,
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

		Page<TipoProcessamento> tipoProcessamentos = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			tipoProcessamentos = tipoProcessamentoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		} else {
			tipoProcessamentos = tipoProcessamentoRepository.findAll(pageable);
		}

		if (tipoProcessamentos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tipoProcessamentos.getNumber(),
					tipoProcessamentos.getSize(), tipoProcessamentos.getTotalElements(),
					tipoProcessamentos.getTotalPages(), tipoProcessamentos.isLast());
		}

		List<TipoProcessamentoResponse> tipoProcessamentoResponses = tipoProcessamentos.map(tipoProcessamento -> {
			return new TipoProcessamentoResponse(tipoProcessamento);
		}).getContent();
		return new PagedResponse<>(tipoProcessamentoResponses, tipoProcessamentos.getNumber(),
				tipoProcessamentos.getSize(), tipoProcessamentos.getTotalElements(), tipoProcessamentos.getTotalPages(),
				tipoProcessamentos.isLast());

	}

	public void deleteTipoProcessamento(Long id) {
		TipoProcessamento tipoProcessamento = tipoProcessamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TipoProcessamento", "id", id));
		tipoProcessamentoRepository.delete(tipoProcessamento);
	}

	public void createTipoProcessamento(TipoProcessamentoRequest tipoProcessamentoRequest) {
		save(tipoProcessamentoRequest);

	}

	public void updateTipoProcessamento(TipoProcessamentoRequest tipoProcessamentoRequest) {
		save(tipoProcessamentoRequest);
	}

	private void save(TipoProcessamentoRequest tipoProcessamentoRequest) {
		TipoProcessamento tipoProcessamento = new TipoProcessamento(tipoProcessamentoRequest);
		tipoProcessamento.setVerbas(new ArrayList<Verba>());

		if (tipoProcessamentoRequest.getVerbaIds() != null && !tipoProcessamentoRequest.getVerbaIds().isEmpty()) {
			tipoProcessamentoRequest.getVerbaIds().forEach(verbaId -> {
				Optional<Verba> v = verbaRepository.findById(verbaId);
				if (v.isPresent())
					tipoProcessamento.getVerbas().add(v.get());
			});
		}

		tipoProcessamentoRepository.save(tipoProcessamento);
	}

	public List<TipoProcessamentoResponse> getAllTiposProcessamentos() {

		List<TipoProcessamento> tiposProcessamentos = tipoProcessamentoRepository.findAll();
		List<TipoProcessamentoResponse> tiposProcessamentosResponse = new ArrayList<>();

		if (!tiposProcessamentos.isEmpty()) {
			for (TipoProcessamento tipoProcessamento : tiposProcessamentos) {

				tiposProcessamentosResponse.add(new TipoProcessamentoResponse(tipoProcessamento));
			}
			return tiposProcessamentosResponse;
		}

		return null;
	}

	public List<TipoProcessamentoResponse> getTipoProcessListFolhaPagByCompetenciaId(Long competenciaId) {

		List<TipoProcessamento> tiposProcessamentos = tipoProcessamentoRepository
				.getTipoProcessListFolhaPagByCompetenciaId(competenciaId);

		List<TipoProcessamentoResponse> tiposProcessamentosResponse = new ArrayList<>();

		if (!tiposProcessamentos.isEmpty()) {
			for (TipoProcessamento tipoProcessamento : tiposProcessamentos) {

				tiposProcessamentosResponse.add(new TipoProcessamentoResponse(tipoProcessamento));
			}
			return tiposProcessamentosResponse;
		}

		return null;
	}

	public List<TipoProcessamentoResponse> getAllTiposProcessamentoByDescricao(String search) {

		List<TipoProcessamento> tipos = tipoProcessamentoRepository.findByDescricaoIgnoreCaseContaining(search);
		List<TipoProcessamentoResponse> response = new ArrayList<>();

		if (!tipos.isEmpty()) {
			for (TipoProcessamento tipo : tipos) {
				response.add(new TipoProcessamentoResponse(tipo));
			}
		}

		return response;
	}

}
