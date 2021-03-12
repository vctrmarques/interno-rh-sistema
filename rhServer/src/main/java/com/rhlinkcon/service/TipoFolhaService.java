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
import com.rhlinkcon.model.TipoFolha;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tipoFolha.TipoFolhaRequest;
import com.rhlinkcon.payload.tipoFolha.TipoFolhaResponse;
import com.rhlinkcon.repository.TipoFolhaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TipoFolhaService {

	@Autowired
	private TipoFolhaRepository tipoFolhaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createTipoFolha(TipoFolhaRequest tipoFolhaRequest) {

		TipoFolha tipoFolha = new TipoFolha(tipoFolhaRequest);

		tipoFolhaRepository.save(tipoFolha);

	}

	public void updateTipoFolha(TipoFolhaRequest tipoFolhaRequest) {

		TipoFolha tipoFolha = tipoFolhaRepository.findById(tipoFolhaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("TipoFolha", "id", tipoFolhaRequest.getId()));

		tipoFolha.setCodigo(tipoFolhaRequest.getCodigo());
		tipoFolha.setDescricao(tipoFolhaRequest.getDescricao());

		tipoFolhaRepository.save(tipoFolha);

	}

	public void deleteTipoFolha(Long id) {
		TipoFolha tipoFolha = tipoFolhaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TipoFolha", "id", id));

		tipoFolhaRepository.delete(tipoFolha);
	}

	public PagedResponse<TipoFolhaResponse> getAllTiposFolhas(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<TipoFolha> tiposFolhas = tipoFolhaRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (tiposFolhas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tiposFolhas.getNumber(), tiposFolhas.getSize(),
					tiposFolhas.getTotalElements(), tiposFolhas.getTotalPages(), tiposFolhas.isLast());
		}

		List<TipoFolhaResponse> tiposFolhasResponse = tiposFolhas.map(tipoFolha -> {
			return new TipoFolhaResponse(tipoFolha);
		}).getContent();
		return new PagedResponse<>(tiposFolhasResponse, tiposFolhas.getNumber(), tiposFolhas.getSize(),
				tiposFolhas.getTotalElements(), tiposFolhas.getTotalPages(), tiposFolhas.isLast());

	}

	public TipoFolhaResponse getTipoFolhaById(Long tipoFolhaId) {
		TipoFolha tipoFolha = tipoFolhaRepository.findById(tipoFolhaId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoFolha", "id", tipoFolhaId));

		Usuario userCreated = usuarioRepository.findById(tipoFolha.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoFolha.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(tipoFolha.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoFolha.getUpdatedBy()));

		TipoFolhaResponse tipoFolhaResponse = new TipoFolhaResponse(tipoFolha, tipoFolha.getCreatedAt(),
				userCreated.getNome(), tipoFolha.getUpdatedAt(), userUpdated.getNome());

		return tipoFolhaResponse;
	}
	
	public List<TipoFolhaResponse> getAllTiposFolhas() {

		List<TipoFolha> tiposFolhas = tipoFolhaRepository.findAll();
		List<TipoFolhaResponse> tipoFolhasResponse = new ArrayList();

		if (!tiposFolhas.isEmpty()) {
			for (TipoFolha tipoFolha : tiposFolhas) {
				tipoFolhasResponse.add(new TipoFolhaResponse(tipoFolha));
			}
			return tipoFolhasResponse;
		}
		
		return null;
	}

}
