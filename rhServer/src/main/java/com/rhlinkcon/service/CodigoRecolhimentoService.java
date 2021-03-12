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
import com.rhlinkcon.model.CodigoRecolhimento;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.codigoRecolhimento.CodigoRecolhimentoRequest;
import com.rhlinkcon.payload.codigoRecolhimento.CodigoRecolhimentoResponse;
import com.rhlinkcon.repository.CodigoRecolhimentoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CodigoRecolhimentoService {

	@Autowired
	private CodigoRecolhimentoRepository codigoRecolhimentoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createCodigoRecolhimento(CodigoRecolhimentoRequest codigoRecolhimentoRequest) {

		CodigoRecolhimento codigoRecolhimento = new CodigoRecolhimento(codigoRecolhimentoRequest);

		codigoRecolhimentoRepository.save(codigoRecolhimento);

	}

	public void updateCodigoRecolhimento(CodigoRecolhimentoRequest codigoRecolhimentoRequest) {

		CodigoRecolhimento codigoRecolhimento = codigoRecolhimentoRepository.findById(codigoRecolhimentoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("CodigoRecolhimento", "id", codigoRecolhimentoRequest.getId()));

		codigoRecolhimento.setCodigo(codigoRecolhimentoRequest.getCodigo());
		codigoRecolhimento.setDescricao(codigoRecolhimentoRequest.getDescricao());

		codigoRecolhimentoRepository.save(codigoRecolhimento);

	}

	public void deleteCodigoRecolhimento(Long id) {
		CodigoRecolhimento codigoRecolhimento = codigoRecolhimentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CodigoRecolhimento", "id", id));

		codigoRecolhimentoRepository.delete(codigoRecolhimento);
	}

	public PagedResponse<CodigoRecolhimentoResponse> getAllCodigosRecolhimento(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CodigoRecolhimento> codigosRecolhimento = codigoRecolhimentoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (codigosRecolhimento.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), codigosRecolhimento.getNumber(), codigosRecolhimento.getSize(),
					codigosRecolhimento.getTotalElements(), codigosRecolhimento.getTotalPages(), codigosRecolhimento.isLast());
		}

		List<CodigoRecolhimentoResponse> codigosRecolhimentoResponse = codigosRecolhimento.map(codigoRecolhimento -> {
			return new CodigoRecolhimentoResponse(codigoRecolhimento);
		}).getContent();
		return new PagedResponse<>(codigosRecolhimentoResponse, codigosRecolhimento.getNumber(), codigosRecolhimento.getSize(),
				codigosRecolhimento.getTotalElements(), codigosRecolhimento.getTotalPages(), codigosRecolhimento.isLast());

	}

	public CodigoRecolhimentoResponse getCodigoRecolhimentoById(Long codigoRecolhimentoId) {
		CodigoRecolhimento codigoRecolhimento = codigoRecolhimentoRepository.findById(codigoRecolhimentoId)
				.orElseThrow(() -> new ResourceNotFoundException("CodigoRecolhimento", "id", codigoRecolhimentoId));

		Usuario userCreated = usuarioRepository.findById(codigoRecolhimento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", codigoRecolhimento.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(codigoRecolhimento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", codigoRecolhimento.getUpdatedBy()));

		CodigoRecolhimentoResponse codigoRecolhimentoResponse = new CodigoRecolhimentoResponse(codigoRecolhimento, codigoRecolhimento.getCreatedAt(),
				userCreated.getNome(), codigoRecolhimento.getUpdatedAt(), userUpdated.getNome());

		return codigoRecolhimentoResponse;
	}
	
	public List<CodigoRecolhimentoResponse> getAllCodigosRecolhimento() {

		List<CodigoRecolhimento> codigosRecolhimento = codigoRecolhimentoRepository.findAll();
		List<CodigoRecolhimentoResponse> codigoRecolhimentoResponse = new ArrayList();

		if (!codigosRecolhimento.isEmpty()) {
			for (CodigoRecolhimento codigoRecolhimento : codigosRecolhimento) {
				CodigoRecolhimentoResponse codigoRecolhimentoResp = new CodigoRecolhimentoResponse(codigoRecolhimento);

				codigoRecolhimentoResponse.add(codigoRecolhimentoResp);
			}
			return codigoRecolhimentoResponse;
		}
		
		return null;
	}

}
