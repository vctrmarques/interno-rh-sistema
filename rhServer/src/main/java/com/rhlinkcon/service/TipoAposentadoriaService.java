package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.TipoAposentadoria;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.tipoAposentadoria.TipoAposentadoriaRequest;
import com.rhlinkcon.payload.tipoAposentadoria.TipoAposentadoriaResponse;
import com.rhlinkcon.repository.TipoAposentadoriaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class TipoAposentadoriaService {
	
	@Autowired
	private TipoAposentadoriaRepository tipoAposentadoriaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public PagedResponse<TipoAposentadoriaResponse> getAllTiposAposentadoria(int page, int size, String descricao,
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

		Page<TipoAposentadoria> tiposAposentadorias = tipoAposentadoriaRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (tiposAposentadorias.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), tiposAposentadorias.getNumber(), tiposAposentadorias.getSize(),
					tiposAposentadorias.getTotalElements(), tiposAposentadorias.getTotalPages(), tiposAposentadorias.isLast());
		}

		List<TipoAposentadoriaResponse> tiposAposentadoriasResponse = tiposAposentadorias.map(tipoFolha -> {
			return new TipoAposentadoriaResponse(tipoFolha);
		}).getContent();
		return new PagedResponse<>(tiposAposentadoriasResponse, tiposAposentadorias.getNumber(), tiposAposentadorias.getSize(),
				tiposAposentadorias.getTotalElements(), tiposAposentadorias.getTotalPages(), tiposAposentadorias.isLast());
	}

	public TipoAposentadoriaResponse getTipoAposentadoriaById(Long tiposAposentadoriaId) {
		TipoAposentadoria tipoAposentadoria = tipoAposentadoriaRepository.findById(tiposAposentadoriaId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoAposentadoria", "id", tiposAposentadoriaId));

		Usuario userCreated = usuarioRepository.findById(tipoAposentadoria.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoAposentadoria.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(tipoAposentadoria.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", tipoAposentadoria.getUpdatedBy()));

		TipoAposentadoriaResponse tipoAposentadoriaResponse = new TipoAposentadoriaResponse(tipoAposentadoria, tipoAposentadoria.getCreatedAt(),
				userCreated.getNome(), tipoAposentadoria.getUpdatedAt(), userUpdated.getNome());

		return tipoAposentadoriaResponse;
	}

	public boolean existsByDescricao(String descricao) {
		return tipoAposentadoriaRepository.existsByDescricao(descricao);	
	}

	public void createTipoAposentadoria(@Valid TipoAposentadoriaRequest tipoAposentadoriaRequest) {
		TipoAposentadoria tipoAposentadoria = new TipoAposentadoria(tipoAposentadoriaRequest);
		tipoAposentadoriaRepository.save(tipoAposentadoria);
	}

	public boolean existsByDescricaoAndIdNot(String descricao, Long id) {
		return tipoAposentadoriaRepository.existsByDescricaoAndIdNot(descricao, id);
	}

	public void updateTipoAposentadoria(@Valid TipoAposentadoriaRequest tipoAposentadoriaRequest) {
		TipoAposentadoria tipoAposentadoria = tipoAposentadoriaRepository.findById(tipoAposentadoriaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("TipoAposentadoria", "id", tipoAposentadoriaRequest.getId()));

		tipoAposentadoria.setCodigo(tipoAposentadoriaRequest.getCodigo());
		tipoAposentadoria.setDescricao(tipoAposentadoriaRequest.getDescricao());

		tipoAposentadoriaRepository.save(tipoAposentadoria);
		
	}

	public void deleteTipoAposentadoria(Long id) {
		TipoAposentadoria tipoAposentadoria = tipoAposentadoriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TipoAposentadoria", "id", id));

		tipoAposentadoriaRepository.delete(tipoAposentadoria);
	}

	public List<TipoAposentadoriaResponse> getAllTiposAposentadorias() {

		List<TipoAposentadoria> tipos = tipoAposentadoriaRepository.findAll();
		List<TipoAposentadoriaResponse> tiposReponse = new ArrayList<TipoAposentadoriaResponse>();

		if (!tipos.isEmpty()) {
			for (TipoAposentadoria tipoAposentadoria : tipos) {
				tiposReponse.add(new TipoAposentadoriaResponse(tipoAposentadoria));
			}
			return tiposReponse;
		}
		
		return null;
	}

}
