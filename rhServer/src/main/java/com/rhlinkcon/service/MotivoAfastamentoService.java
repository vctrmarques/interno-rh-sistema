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
import com.rhlinkcon.model.MotivoAfastamento;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoRequest;
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoResponse;
import com.rhlinkcon.repository.MotivoAfastamentoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class MotivoAfastamentoService {

	@Autowired
	private MotivoAfastamentoRepository motivoAfastamentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createMotivoAfastamento(MotivoAfastamentoRequest motivoAfastamentoRequest) {

		// Creating user's account
		MotivoAfastamento motivoAfastamento = new MotivoAfastamento(motivoAfastamentoRequest);

		motivoAfastamentoRepository.save(motivoAfastamento);

	}

	public void updateMotivoAfastamento(MotivoAfastamentoRequest motivoAfastamentoRequest) {

		// Updating user's account
		MotivoAfastamento motivoAfastamento = motivoAfastamentoRepository.findById(motivoAfastamentoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("MotivoAfastamento", "id", motivoAfastamentoRequest.getId()));

		motivoAfastamento.setCodigo(motivoAfastamentoRequest.getCodigo());
		motivoAfastamento.setDescricao(motivoAfastamentoRequest.getDescricao());
		motivoAfastamento.setDisponivelParaPericia(motivoAfastamentoRequest.isDisponivelParaPericia());

		motivoAfastamentoRepository.save(motivoAfastamento);

	}

	public void deleteMotivoAfastamento(Long id) {
		MotivoAfastamento motivoAfastamento = motivoAfastamentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MotivoAfastamento", "id", id));

		motivoAfastamentoRepository.delete(motivoAfastamento);
	}

	public PagedResponse<MotivoAfastamentoResponse> getAllMotivosAfastamentos(int page, int size, String descricao, String order) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<MotivoAfastamento> motivosAfastamentos = motivoAfastamentoRepository.findByDescricaoIgnoreCaseContaining(descricao,
				pageable);

		if (motivosAfastamentos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), motivosAfastamentos.getNumber(), motivosAfastamentos.getSize(),
					motivosAfastamentos.getTotalElements(), motivosAfastamentos.getTotalPages(), motivosAfastamentos.isLast());
		}

		List<MotivoAfastamentoResponse> motivosAfastamentosResponse = motivosAfastamentos.map(motivoAfastamento -> {
			return new MotivoAfastamentoResponse(motivoAfastamento);
		}).getContent();
		return new PagedResponse<>(motivosAfastamentosResponse, motivosAfastamentos.getNumber(), motivosAfastamentos.getSize(),
				motivosAfastamentos.getTotalElements(), motivosAfastamentos.getTotalPages(), motivosAfastamentos.isLast());

	}

	public MotivoAfastamentoResponse getMotivoAfastamentoById(Long motivoAfastamentoId) {
		MotivoAfastamento motivoAfastamento = motivoAfastamentoRepository.findById(motivoAfastamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("Afastamento", "id", motivoAfastamentoId));

		Usuario userCreated = usuarioRepository.findById(motivoAfastamento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", motivoAfastamento.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(motivoAfastamento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", motivoAfastamento.getUpdatedBy()));

		MotivoAfastamentoResponse motivoAfastamentoResponse = new MotivoAfastamentoResponse(motivoAfastamento, motivoAfastamento.getCreatedAt(),
				userCreated.getNome(), motivoAfastamento.getUpdatedAt(), userUpdated.getNome());

		return motivoAfastamentoResponse;
	}
	
	public List<MotivoAfastamentoResponse> getAllMotivosAfastamentos() {

		List<MotivoAfastamento> motivosAfastamentos = motivoAfastamentoRepository.findAll();
		List<MotivoAfastamentoResponse> motivoAfastamentoResponse = new ArrayList();

		if (!motivosAfastamentos.isEmpty()) {
			for (MotivoAfastamento motivoAfastamento : motivosAfastamentos) {
				MotivoAfastamentoResponse motivAfastamentoResponse = new MotivoAfastamentoResponse(motivoAfastamento);

				motivoAfastamentoResponse.add(motivAfastamentoResponse);
			}
			return motivoAfastamentoResponse;
		}
		
		return null;
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
