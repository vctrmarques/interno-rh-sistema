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
import com.rhlinkcon.model.Habilidade;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.habilidade.HabilidadeRequest;
import com.rhlinkcon.payload.habilidade.HabilidadeResponse;
import com.rhlinkcon.repository.HabilidadeRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class HabilidadeService {

	@Autowired
	private HabilidadeRepository habilidadeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createHabilidade(HabilidadeRequest habilidadeRequest) {

		Habilidade habilidade = new Habilidade(habilidadeRequest);

		habilidadeRepository.save(habilidade);

	}

	public void updateHabilidade(HabilidadeRequest habilidadeRequest) {

		Habilidade habilidade = habilidadeRepository.findById(habilidadeRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Habilidade", "id", habilidadeRequest.getId()));

		habilidade.setCodigo(habilidadeRequest.getCodigo());
		habilidade.setDescricao(habilidadeRequest.getDescricao());

		habilidadeRepository.save(habilidade);

	}

	public void deleteHabilidade(Long id) {
		Habilidade habilidade = habilidadeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Habilidade", "id", id));

		habilidadeRepository.delete(habilidade);
	}

	public PagedResponse<HabilidadeResponse> getAllHabilidades(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Habilidade> habilidades = habilidadeRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (habilidades.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), habilidades.getNumber(), habilidades.getSize(),
					habilidades.getTotalElements(), habilidades.getTotalPages(), habilidades.isLast());
		}

		List<HabilidadeResponse> habilidadesResponse = habilidades.map(habilidade -> {
			return new HabilidadeResponse(habilidade);
		}).getContent();
		return new PagedResponse<>(habilidadesResponse, habilidades.getNumber(), habilidades.getSize(),
				habilidades.getTotalElements(), habilidades.getTotalPages(), habilidades.isLast());

	}

	public HabilidadeResponse getHabilidadeById(Long habilidadeId) {
		Habilidade habilidade = habilidadeRepository.findById(habilidadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Habilidade", "id", habilidadeId));

		Usuario userCreated = usuarioRepository.findById(habilidade.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", habilidade.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(habilidade.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", habilidade.getUpdatedBy()));

		HabilidadeResponse habilidadeResponse = new HabilidadeResponse(habilidade, habilidade.getCreatedAt(),
				userCreated.getNome(), habilidade.getUpdatedAt(), userUpdated.getNome());

		return habilidadeResponse;
	}
	
	public List<HabilidadeResponse> getAllHabilidades() {

		List<Habilidade> habilidades = habilidadeRepository.findAll();
		List<HabilidadeResponse> habilidadeResponse = new ArrayList<>();

		if (!habilidades.isEmpty()) {
			for (Habilidade habilidade : habilidades) {
				HabilidadeResponse habilidadeResp = new HabilidadeResponse(habilidade);

				habilidadeResponse.add(habilidadeResp);
			}
			return habilidadeResponse;
		}
		
		return null;
	}

}
