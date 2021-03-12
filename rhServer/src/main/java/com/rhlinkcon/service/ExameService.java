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
import com.rhlinkcon.model.CategoriaExameEnum;
import com.rhlinkcon.model.Exame;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.exame.ExameRequest;
import com.rhlinkcon.payload.exame.ExameResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ExameRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ExameService {

	@Autowired
	private ExameRepository exameRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createExame(ExameRequest exameRequest) {

		Exame exame = new Exame(exameRequest);

		exameRepository.save(exame);

	}

	public void updateExame(ExameRequest exameRequest) {

		Exame exame = exameRepository.findById(exameRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Exame", "id", exameRequest.getId()));

		exame.setCodigo(exameRequest.getCodigo());
		exame.setDescricao(exameRequest.getDescricao());

		if (!exameRequest.getCategoria().isEmpty())
			exame.setCategoria(CategoriaExameEnum.getEnumByString(exameRequest.getCategoria()));

		exameRepository.save(exame);

	}

	public void deleteExame(Long id) {
		Exame exame = exameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exame", "id", id));

		exameRepository.delete(exame);
	}

	public List<ExameResponse> searchAllByDescricaol(String search) {
		List<ExameResponse> itensResponses = new ArrayList<ExameResponse>();
		exameRepository.findByDescricaoIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new ExameResponse(item)));
		return itensResponses;
	}

	public PagedResponse<ExameResponse> getAllExames(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Exame> exames = exameRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (exames.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), exames.getNumber(), exames.getSize(),
					exames.getTotalElements(), exames.getTotalPages(), exames.isLast());
		}

		List<ExameResponse> examesResponse = exames.map(exame -> {
			return new ExameResponse(exame);
		}).getContent();
		return new PagedResponse<>(examesResponse, exames.getNumber(), exames.getSize(), exames.getTotalElements(),
				exames.getTotalPages(), exames.isLast());

	}

	public ExameResponse getExameById(Long exameId) {
		Exame exame = exameRepository.findById(exameId)
				.orElseThrow(() -> new ResourceNotFoundException("Exame", "id", exameId));

		Usuario userCreated = usuarioRepository.findById(exame.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", exame.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(exame.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", exame.getUpdatedBy()));

		ExameResponse exameResponse = new ExameResponse(exame, exame.getCreatedAt(), userCreated.getNome(),
				exame.getUpdatedAt(), userUpdated.getNome());

		return exameResponse;
	}

}
