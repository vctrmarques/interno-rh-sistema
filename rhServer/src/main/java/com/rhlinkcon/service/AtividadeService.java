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
import com.rhlinkcon.model.Atividade;
import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.atividade.AtividadeRequest;
import com.rhlinkcon.payload.atividade.AtividadeResponse;
import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AtividadeRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createAtividade(AtividadeRequest atividadeRequest) {

		// Creating user's account
		Atividade atividade = new Atividade(atividadeRequest);

		atividadeRepository.save(atividade);

	}

	public void updateAtividade(AtividadeRequest atividadeRequest) {

		// Updating user's account
		Atividade atividade = atividadeRepository.findById(atividadeRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Atividade", "id", atividadeRequest.getId()));

		atividade.setCodigo(atividadeRequest.getCodigo());
		atividade.setDescricao(atividadeRequest.getDescricao());
		atividade.setObservacao(atividadeRequest.getObservacao());

		atividadeRepository.save(atividade);

	}

	public void deleteAtividade(Long id) {
		Atividade atividade = atividadeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Atividade", "id", id));

		atividadeRepository.delete(atividade);
	}

	public PagedResponse<AtividadeResponse> getAllAtividades(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Atividade> atividades = atividadeRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (atividades.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), atividades.getNumber(), atividades.getSize(),
					atividades.getTotalElements(), atividades.getTotalPages(), atividades.isLast());
		}

		List<AtividadeResponse> atividadeResponses = atividades.map(atividade -> {
			return new AtividadeResponse(atividade);
		}).getContent();
		return new PagedResponse<>(atividadeResponses, atividades.getNumber(), atividades.getSize(), atividades.getTotalElements(),
				atividades.getTotalPages(), atividades.isLast());

	}

	public AtividadeResponse getAtividadeById(Long atividadeId) {
		Atividade atividade = atividadeRepository.findById(atividadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Atividade", "id", atividadeId));

		Usuario userCreated = usuarioRepository.findById(atividade.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", atividade.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(atividade.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", atividade.getUpdatedBy()));

		AtividadeResponse atividadeResponse = new AtividadeResponse(atividade, atividade.getCreatedAt(), 
											userCreated.getNome(), atividade.getUpdatedAt(), userUpdated.getNome());

		return atividadeResponse;
	}

	public List<AtividadeResponse> getAllAtividades() {

		List<Atividade> atividades = atividadeRepository.findAll();
		List<AtividadeResponse> atividadesResponse = new ArrayList<>();

		if (!atividades.isEmpty()) {
			for (Atividade atividade: atividades) {
				atividadesResponse.add(new AtividadeResponse(atividade));
			}
			return atividadesResponse;
		}

		return null;
	}	

}
