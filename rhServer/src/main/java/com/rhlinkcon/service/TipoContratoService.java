package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.TipoContrato;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tipoContrato.TipoContratoRequest;
import com.rhlinkcon.payload.tipoContrato.TipoContratoResponse;
import com.rhlinkcon.repository.TipoContratoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class TipoContratoService {

	@Autowired
	private TipoContratoRepository tipoContratoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public TipoContratoResponse getTipoContratoById(Long id) {
		TipoContrato tipoContrato = tipoContratoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TipoContrato", "id", id));

		TipoContratoResponse tipoContratoResponse = new TipoContratoResponse(tipoContrato);
		Usuario criadoPor = usuarioRepository.findById(tipoContrato.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", tipoContrato.getCreatedBy()));
		tipoContratoResponse.setCriadoPor(criadoPor.getNome());
		tipoContratoResponse.setCriadoEm(tipoContrato.getCreatedAt());
		Usuario alteradoPor = usuarioRepository.findById(tipoContrato.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", tipoContrato.getUpdatedBy()));
		tipoContratoResponse.setAlteradoPor(alteradoPor.getNome());
		tipoContratoResponse.setAlteradoEm(criadoPor.getUpdatedAt());

		return tipoContratoResponse;
	}

	public List<TipoContratoResponse> searchAllByNome(String search) {
		List<TipoContratoResponse> itensResponses = new ArrayList<TipoContratoResponse>();
		tipoContratoRepository.findByNomeIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new TipoContratoResponse(item)));
		return itensResponses;
	}

	public PagedResponse<TipoContratoResponse> getAllTipoContrato(int page, int size, String order, String nome) {
		validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<TipoContrato> tiposContratos = null;

		if (!Objects.isNull(nome) && !nome.isEmpty()) {
			tiposContratos = tipoContratoRepository.findByNomeIgnoreCaseContaining(nome, pageable);
		} else {
			tiposContratos = tipoContratoRepository.findAll(pageable);
		}

		if (tiposContratos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tiposContratos.getNumber(), tiposContratos.getSize(),
					tiposContratos.getTotalElements(), tiposContratos.getTotalPages(), tiposContratos.isLast());
		}

		List<TipoContratoResponse> tipoContratosResponses = tiposContratos.map(tipoContrato -> {
			return new TipoContratoResponse(tipoContrato);
		}).getContent();
		return new PagedResponse<>(tipoContratosResponses, tiposContratos.getNumber(), tiposContratos.getSize(),
				tiposContratos.getTotalElements(), tiposContratos.getTotalPages(), tiposContratos.isLast());

	}

	public void deleteTipoContrato(Long id) {
		TipoContrato tipoContrato = tipoContratoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TipoContrato", "id", id));
		tipoContratoRepository.delete(tipoContrato);
	}

	public void createTipoContrato(TipoContratoRequest tipoContratoRequest) {

		TipoContrato tipoContrato = new TipoContrato();
		tipoContrato.setNome(tipoContratoRequest.getNome());
		tipoContratoRepository.save(tipoContrato);

	}

	public void updateTipoContrato(TipoContratoRequest tipoContratoRequest) {

		TipoContrato tipoContrato = tipoContratoRepository.findById(tipoContratoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("TipoContrato", "id", tipoContratoRequest.getId()));

		tipoContrato.setNome(tipoContratoRequest.getNome());

		tipoContratoRepository.save(tipoContrato);

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
