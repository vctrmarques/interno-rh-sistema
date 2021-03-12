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
import com.rhlinkcon.model.ValeTransporte;
import com.rhlinkcon.model.TipoFolha;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.valeTransporte.ValeTransporteRequest;
import com.rhlinkcon.payload.valeTransporte.ValeTransporteResponse;
import com.rhlinkcon.repository.ValeTransporteRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ValeTransporteService {

	@Autowired
	private ValeTransporteRepository valeTransporteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createValeTransporte(ValeTransporteRequest valeTransporteRequest) {

		ValeTransporte valeTransporte = new ValeTransporte(valeTransporteRequest);

		valeTransporteRepository.save(valeTransporte);

	}

	public void updateValeTransporte(ValeTransporteRequest valeTransporteRequest) {

		ValeTransporte valeTransporte = valeTransporteRepository.findById(valeTransporteRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ValeTransporte", "id", valeTransporteRequest.getId()));

		valeTransporte.setCodigo(valeTransporteRequest.getCodigo());
		valeTransporte.setValor(valeTransporteRequest.getValor());

		valeTransporteRepository.save(valeTransporte);

	}

	public void deleteValeTransporte(Long id) {
		ValeTransporte valeTransporte = valeTransporteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ValeTransporte", "id", id));

		valeTransporteRepository.delete(valeTransporte);
	}

	public PagedResponse<ValeTransporteResponse> getAllValesTransporte(int page, int size, String codigo, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ValeTransporte> valesTransporte = valeTransporteRepository.findByCodigoIgnoreCaseContaining(codigo, pageable);
		
		if (valesTransporte.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), valesTransporte.getNumber(), valesTransporte.getSize(),
					valesTransporte.getTotalElements(), valesTransporte.getTotalPages(), valesTransporte.isLast());
		}

		List<ValeTransporteResponse> valesTransporteResponse = valesTransporte.map(valeTransporte -> {
			return new ValeTransporteResponse(valeTransporte);
		}).getContent();
		return new PagedResponse<>(valesTransporteResponse, valesTransporte.getNumber(), valesTransporte.getSize(),
				valesTransporte.getTotalElements(), valesTransporte.getTotalPages(), valesTransporte.isLast());

	}

	public ValeTransporteResponse getValeTransporteById(Long valeTransporteId) {
		ValeTransporte valeTransporte = valeTransporteRepository.findById(valeTransporteId)
				.orElseThrow(() -> new ResourceNotFoundException("ValeTransporte", "id", valeTransporteId));

		Usuario userCreated = usuarioRepository.findById(valeTransporte.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", valeTransporte.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(valeTransporte.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", valeTransporte.getUpdatedBy()));

		ValeTransporteResponse valeTransporteResponse = new ValeTransporteResponse(valeTransporte, valeTransporte.getCreatedAt(),
				userCreated.getNome(), valeTransporte.getUpdatedAt(), userUpdated.getNome());

		return valeTransporteResponse;
	}
	
	public List<ValeTransporteResponse> getAllvalesTransporte() {

		List<ValeTransporte> valesTransporte = valeTransporteRepository.findAll();
		List<ValeTransporteResponse> valeTransporteResponse = new ArrayList<>();

		if (!valesTransporte.isEmpty()) {
			valesTransporte.forEach(vt -> valeTransporteResponse.add(new ValeTransporteResponse(vt)));
			return valeTransporteResponse;
		}
		
		return null;
	}

}
