package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.CausaAfastamento;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.causaAfastamento.CausaAfastamentoRequest;
import com.rhlinkcon.payload.causaAfastamento.CausaAfastamentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CausaAfastamentoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@Service
public class CausaAfastamentoService {

	@Autowired
	private CausaAfastamentoRepository causaAfastamentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<CausaAfastamentoResponse> getAllCausasAfastamento() {

		List<CausaAfastamento> causasAfastamento = causaAfastamentoRepository.findAll();
		List<CausaAfastamentoResponse> causaAfastamentoResponse = new ArrayList();

		if (!causasAfastamento.isEmpty()) {
			for (CausaAfastamento causaAfastamento : causasAfastamento) {
				CausaAfastamentoResponse causaAfastResponse = new CausaAfastamentoResponse(causaAfastamento);

				causaAfastamentoResponse.add(causaAfastResponse);
			}
			return causaAfastamentoResponse;
		}

		return null;
	}

	public void createCausaAfastamento(CausaAfastamentoRequest causaAfastamentoRequest) {

		CausaAfastamento causaAfastamento = new CausaAfastamento(causaAfastamentoRequest);

		causaAfastamentoRepository.save(causaAfastamento);

	}

	public void updateCausaAfastamento(CausaAfastamentoRequest causaAfastamentoRequest) {

		CausaAfastamento causaAfastamento = causaAfastamentoRepository.findById(causaAfastamentoRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("CausaAfastamento", "id", causaAfastamentoRequest.getId()));

		causaAfastamento.setCodigo(causaAfastamentoRequest.getCodigo());
		causaAfastamento.setDescricao(causaAfastamentoRequest.getDescricao());

		causaAfastamentoRepository.save(causaAfastamento);

	}

	public void deleteCausaAfastamento(Long id) {
		CausaAfastamento causaAfastamento = causaAfastamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CausaAfastamento", "id", id));

		causaAfastamentoRepository.delete(causaAfastamento);
	}

	public PagedResponse<CausaAfastamentoResponse> getAllCausasAfastamento(int page, int size, String descricao,
			String order) {
		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CausaAfastamento> causasAfastamento = causaAfastamentoRepository
				.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (causasAfastamento.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), causasAfastamento.getNumber(),
					causasAfastamento.getSize(), causasAfastamento.getTotalElements(),
					causasAfastamento.getTotalPages(), causasAfastamento.isLast());
		}

		List<CausaAfastamentoResponse> causasAfastamentoResponse = causasAfastamento.map(causaAfastamento -> {
			return new CausaAfastamentoResponse(causaAfastamento);
		}).getContent();
		return new PagedResponse<>(causasAfastamentoResponse, causasAfastamento.getNumber(),
				causasAfastamento.getSize(), causasAfastamento.getTotalElements(), causasAfastamento.getTotalPages(),
				causasAfastamento.isLast());

	}

	public CausaAfastamentoResponse getCausaAfastamentoById(Long causaAfastamentoId) {
		CausaAfastamento causaAfastamento = causaAfastamentoRepository.findById(causaAfastamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("CausaAfastamento", "id", causaAfastamentoId));

		Usuario userCreated = usuarioRepository.findById(causaAfastamento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", causaAfastamento.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(causaAfastamento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", causaAfastamento.getUpdatedBy()));

		CausaAfastamentoResponse causaAfastamentoResponse = new CausaAfastamentoResponse(causaAfastamento,
				causaAfastamento.getCreatedAt(), userCreated.getNome(), causaAfastamento.getUpdatedAt(),
				userUpdated.getNome());

		return causaAfastamentoResponse;
	}

}
