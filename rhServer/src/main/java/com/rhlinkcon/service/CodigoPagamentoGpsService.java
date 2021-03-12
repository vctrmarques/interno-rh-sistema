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
import com.rhlinkcon.model.CodigoPagamentoGps;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.codigoPagamentoGps.CodigoPagamentoGpsRequest;
import com.rhlinkcon.payload.codigoPagamentoGps.CodigoPagamentoGpsResponse;
import com.rhlinkcon.repository.CodigoPagamentoGpsRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class CodigoPagamentoGpsService {

	@Autowired
	private CodigoPagamentoGpsRepository codigoPagamentoGpsRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createCodigoPagamentoGps(CodigoPagamentoGpsRequest codigoPagamentoGpsRequest) {

		CodigoPagamentoGps codigoPagamentoGps = new CodigoPagamentoGps(codigoPagamentoGpsRequest);

		codigoPagamentoGpsRepository.save(codigoPagamentoGps);

	}

	public void updateCodigoPagamentoGps(CodigoPagamentoGpsRequest codigoPagamentoGpsRequest) {

		CodigoPagamentoGps codigoPagamentoGps = codigoPagamentoGpsRepository.findById(codigoPagamentoGpsRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("CodigoPagamentoGps", "id", codigoPagamentoGpsRequest.getId()));

		codigoPagamentoGps.setCodigo(codigoPagamentoGpsRequest.getCodigo());
		codigoPagamentoGps.setDescricao(codigoPagamentoGpsRequest.getDescricao());

		codigoPagamentoGpsRepository.save(codigoPagamentoGps);

	}

	public void deleteCodigoPagamentoGps(Long id) {
		CodigoPagamentoGps codigoPagamentoGps = codigoPagamentoGpsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CodigoPagamentoGps", "id", id));

		codigoPagamentoGpsRepository.delete(codigoPagamentoGps);
	}

	public PagedResponse<CodigoPagamentoGpsResponse> getAllCodigosPagamentosGps(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CodigoPagamentoGps> codigosPagamentosGps = codigoPagamentoGpsRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (codigosPagamentosGps.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), codigosPagamentosGps.getNumber(), codigosPagamentosGps.getSize(),
					codigosPagamentosGps.getTotalElements(), codigosPagamentosGps.getTotalPages(), codigosPagamentosGps.isLast());
		}

		List<CodigoPagamentoGpsResponse> codigosPagamentosGpsResponse = codigosPagamentosGps.map(codigoPagamentoGps -> {
			return new CodigoPagamentoGpsResponse(codigoPagamentoGps);
		}).getContent();
		return new PagedResponse<>(codigosPagamentosGpsResponse, codigosPagamentosGps.getNumber(), codigosPagamentosGps.getSize(),
				codigosPagamentosGps.getTotalElements(), codigosPagamentosGps.getTotalPages(), codigosPagamentosGps.isLast());

	}

	public CodigoPagamentoGpsResponse getCodigoPagamentoGpsById(Long codigoPagamentoGpsId) {
		CodigoPagamentoGps codigoPagamentoGps = codigoPagamentoGpsRepository.findById(codigoPagamentoGpsId)
				.orElseThrow(() -> new ResourceNotFoundException("CodigoPagamentoGps", "id", codigoPagamentoGpsId));

		Usuario userCreated = usuarioRepository.findById(codigoPagamentoGps.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", codigoPagamentoGps.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(codigoPagamentoGps.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", codigoPagamentoGps.getUpdatedBy()));

		CodigoPagamentoGpsResponse codigoPagamentoGpsResponse = new CodigoPagamentoGpsResponse(codigoPagamentoGps, codigoPagamentoGps.getCreatedAt(),
				userCreated.getNome(), codigoPagamentoGps.getUpdatedAt(), userUpdated.getNome());

		return codigoPagamentoGpsResponse;
	}
	
	public List<CodigoPagamentoGpsResponse> getAllCodigosPagamentosGps() {

		List<CodigoPagamentoGps> codigosPagamentosGps = codigoPagamentoGpsRepository.findAll();
		List<CodigoPagamentoGpsResponse> codigoPagamentoGpsResponse = new ArrayList();

		if (!codigosPagamentosGps.isEmpty()) {
			for (CodigoPagamentoGps codigoPagamentoGps : codigosPagamentosGps) {
				CodigoPagamentoGpsResponse codigoPagamentoGpsResp = new CodigoPagamentoGpsResponse(codigoPagamentoGps);

				codigoPagamentoGpsResponse.add(codigoPagamentoGpsResp);
			}
			return codigoPagamentoGpsResponse;
		}
		
		return null;
	}

}
