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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.MotivoDesligamento;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.motivoDesligamento.MotivoDesligamentoRequest;
import com.rhlinkcon.payload.motivoDesligamento.MotivoDesligamentoResponse;
import com.rhlinkcon.repository.MotivoDesligamentoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class MotivoDesligamentoService {

	@Autowired
	private MotivoDesligamentoRepository motivoDesligamentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createMotivoDesligamento(MotivoDesligamentoRequest motivoDesligamentoRequest) {

		MotivoDesligamento motivoDesligamento = new MotivoDesligamento(motivoDesligamentoRequest);

		motivoDesligamentoRepository.save(motivoDesligamento);

	}

	public void updateMotivoDesligamento(MotivoDesligamentoRequest motivoDesligamentoRequest) {

		MotivoDesligamento motivoDesligamento = motivoDesligamentoRepository.findById(motivoDesligamentoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("MotivoDesligamento", "id", motivoDesligamentoRequest.getId()));

		motivoDesligamento.setCodigo(motivoDesligamentoRequest.getCodigo());
		motivoDesligamento.setDescricao(motivoDesligamentoRequest.getDescricao());

		motivoDesligamentoRepository.save(motivoDesligamento);

	}

	public void deleteMotivoDesligamento(Long id) {
		MotivoDesligamento motivoDesligamento = motivoDesligamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MotivoDesligamento", "id", id));

		motivoDesligamentoRepository.delete(motivoDesligamento);
	}

	public PagedResponse<MotivoDesligamentoResponse> getAllMotivosDesligamento(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<MotivoDesligamento> motivosDesligamento = motivoDesligamentoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (motivosDesligamento.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), motivosDesligamento.getNumber(), motivosDesligamento.getSize(),
					motivosDesligamento.getTotalElements(), motivosDesligamento.getTotalPages(), motivosDesligamento.isLast());
		}

		List<MotivoDesligamentoResponse> motivosDesligamentoResponse = motivosDesligamento.map(motivoDesligamento -> {
			return new MotivoDesligamentoResponse(motivoDesligamento);
		}).getContent();
		return new PagedResponse<>(motivosDesligamentoResponse, motivosDesligamento.getNumber(), motivosDesligamento.getSize(),
				motivosDesligamento.getTotalElements(), motivosDesligamento.getTotalPages(), motivosDesligamento.isLast());

	}

	public MotivoDesligamentoResponse getMotivoDesligamentoById(Long motivoDesligamentoId) {
		MotivoDesligamento motivoDesligamento = motivoDesligamentoRepository.findById(motivoDesligamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("MotivoDesligamento", "id", motivoDesligamentoId));

		Usuario userCreated = usuarioRepository.findById(motivoDesligamento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", motivoDesligamento.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(motivoDesligamento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", motivoDesligamento.getUpdatedBy()));

		MotivoDesligamentoResponse motivoDesligamentoResponse = new MotivoDesligamentoResponse(motivoDesligamento, motivoDesligamento.getCreatedAt(),
				userCreated.getNome(), motivoDesligamento.getUpdatedAt(), userUpdated.getNome());

		return motivoDesligamentoResponse;
	}
	
	public List<MotivoDesligamentoResponse> getAllMotivosDesligamento() {

		List<MotivoDesligamento> motivosDesligamento = motivoDesligamentoRepository.findAll();
		List<MotivoDesligamentoResponse> motivoDesligamentoResponse = new ArrayList();

		if (!motivosDesligamento.isEmpty()) {
			for (MotivoDesligamento motivoDesligamento : motivosDesligamento) {
				MotivoDesligamentoResponse motivDesligamentoResponse = new MotivoDesligamentoResponse(motivoDesligamento);

				motivoDesligamentoResponse.add(motivDesligamentoResponse);
			}
			return motivoDesligamentoResponse;
		}
		
		return null;
	}

}
