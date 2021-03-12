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
import com.rhlinkcon.model.ClassificacaoAto;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoRequest;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ClassificacaoAtoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ClassificacaoAtoService {

	@Autowired
	private ClassificacaoAtoRepository classificacaoAtoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createClassificacaoAto(ClassificacaoAtoRequest classificacaoAtoRequest) {

		ClassificacaoAto classificacaoAto = new ClassificacaoAto(classificacaoAtoRequest);

		classificacaoAtoRepository.save(classificacaoAto);

	}

	public void updateClassificacaoAto(ClassificacaoAtoRequest classificacaoAtoRequest) {

		ClassificacaoAto classificacaoAto = classificacaoAtoRepository.findById(classificacaoAtoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAto", "id", classificacaoAtoRequest.getId()));

		classificacaoAto.setDescricao(classificacaoAtoRequest.getDescricao());

		classificacaoAtoRepository.save(classificacaoAto);

	}

	public void deleteClassificacaoAto(Long id) {
		ClassificacaoAto classificacaoAto = classificacaoAtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAto", "id", id));

		classificacaoAtoRepository.delete(classificacaoAto);
	}

	public PagedResponse<ClassificacaoAtoResponse> getAllClassificacoesAtos(int page, int size, String descricao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ClassificacaoAto> classificacoesAtos = classificacaoAtoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);

		if (classificacoesAtos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), classificacoesAtos.getNumber(), classificacoesAtos.getSize(),
					classificacoesAtos.getTotalElements(), classificacoesAtos.getTotalPages(), classificacoesAtos.isLast());
		}

		List<ClassificacaoAtoResponse> classificacoesAtosResponse = classificacoesAtos.map(classificacaoAto -> {
			return new ClassificacaoAtoResponse(classificacaoAto);
		}).getContent();
		return new PagedResponse<>(classificacoesAtosResponse, classificacoesAtos.getNumber(), classificacoesAtos.getSize(),
				classificacoesAtos.getTotalElements(), classificacoesAtos.getTotalPages(), classificacoesAtos.isLast());

	}

	public ClassificacaoAtoResponse getClassificacaoAtoById(Long classificacaoAtoId) {
		ClassificacaoAto classificacaoAto = classificacaoAtoRepository.findById(classificacaoAtoId)
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoAto", "id", classificacaoAtoId));

		Usuario userCreated = usuarioRepository.findById(classificacaoAto.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", classificacaoAto.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(classificacaoAto.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", classificacaoAto.getUpdatedBy()));

		ClassificacaoAtoResponse classificacaoAtoResponse = new ClassificacaoAtoResponse(classificacaoAto, classificacaoAto.getCreatedAt(),
				userCreated.getNome(), classificacaoAto.getUpdatedAt(), userUpdated.getNome());

		return classificacaoAtoResponse;
	}
	
	public List<ClassificacaoAtoResponse> getAllClassificacoesAtos() {

		List<ClassificacaoAto> classificacoesAtos = classificacaoAtoRepository.findAll();
		List<ClassificacaoAtoResponse> classificacaoAtoResponse = new ArrayList();

		if (!classificacoesAtos.isEmpty()) {
			for (ClassificacaoAto classificacaoAto : classificacoesAtos) {
				ClassificacaoAtoResponse classificacaoAtoResp = new ClassificacaoAtoResponse(classificacaoAto);

				classificacaoAtoResponse.add(classificacaoAtoResp);
			}
			return classificacaoAtoResponse;
		}
		
		return null;
	}
	
	public List<ClassificacaoAtoResponse> searchAllByDescricao(String search) {
		List<ClassificacaoAtoResponse> itensResponses = new ArrayList<ClassificacaoAtoResponse>();
		classificacaoAtoRepository.findByDescricaoIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new ClassificacaoAtoResponse(item)));
		return itensResponses;
	}

}
