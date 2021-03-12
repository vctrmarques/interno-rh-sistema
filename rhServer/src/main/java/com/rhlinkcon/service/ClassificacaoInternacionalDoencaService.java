package com.rhlinkcon.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.CategoriaDoenca;
import com.rhlinkcon.model.ClassificacaoInternacionalDoenca;
import com.rhlinkcon.model.SubCategoriaDoenca;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.classificacaoInternacionalDoenca.ClassificacaoInternacionalDoencaRequest;
import com.rhlinkcon.payload.classificacaoInternacionalDoenca.ClassificacaoInternacionalDoencaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CategoriaDoencaRepository;
import com.rhlinkcon.repository.ClassificacaoInternacionalDoencaRepository;
import com.rhlinkcon.repository.SubCategoriaDoencaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ClassificacaoInternacionalDoencaService {

	@Autowired
	private ClassificacaoInternacionalDoencaRepository classificacaoInternacionalDoencaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CategoriaDoencaRepository categoriaDoencaRepository;

	@Autowired
	private SubCategoriaDoencaRepository subCategoriaDoencaRepository;

	public ClassificacaoInternacionalDoencaResponse getClassificacaoInternacionalDoencaById(
			Long classificacaoInternacionalDoencaId) {
		ClassificacaoInternacionalDoenca classificacaoInternacionalDoenca = classificacaoInternacionalDoencaRepository
				.findById(classificacaoInternacionalDoencaId)
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoInternacionalDoenca", "id",
						classificacaoInternacionalDoencaId));

		ClassificacaoInternacionalDoencaResponse classificacaoInternacionalDoencaResponse = new ClassificacaoInternacionalDoencaResponse(
				classificacaoInternacionalDoenca);
		Usuario criadoPor = usuarioRepository.findById(classificacaoInternacionalDoenca.getCreatedBy()).orElseThrow(
				() -> new ResourceNotFoundException("User", "id", classificacaoInternacionalDoenca.getCreatedBy()));
		classificacaoInternacionalDoencaResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(classificacaoInternacionalDoenca.getUpdatedBy()).orElseThrow(
				() -> new ResourceNotFoundException("User", "id", classificacaoInternacionalDoenca.getUpdatedBy()));
		classificacaoInternacionalDoencaResponse.setAlteradoPor(alteradoPor.getNome());

		return classificacaoInternacionalDoencaResponse;
	}

	public PagedResponse<ClassificacaoInternacionalDoencaResponse> getAllClassificacaoInternacionalDoencas(int page,
			int size, String order, String descricao) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		Direction direction = Sort.Direction.ASC;
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ClassificacaoInternacionalDoenca> classificacaoInternacionalDoencas = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			classificacaoInternacionalDoencas = classificacaoInternacionalDoencaRepository
					.findByDescricaoIgnoreCaseContainingOrCodigoIgnoreCaseContaining(descricao,descricao, pageable);
		} else {
			classificacaoInternacionalDoencas = classificacaoInternacionalDoencaRepository.findAll(pageable);
		}

		if (classificacaoInternacionalDoencas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), classificacaoInternacionalDoencas.getNumber(),
					classificacaoInternacionalDoencas.getSize(), classificacaoInternacionalDoencas.getTotalElements(),
					classificacaoInternacionalDoencas.getTotalPages(), classificacaoInternacionalDoencas.isLast());
		}

		List<ClassificacaoInternacionalDoencaResponse> classificacaoInternacionalDoencaResponses = classificacaoInternacionalDoencas
				.map(classificacaoInternacionalDoenca -> {
					return new ClassificacaoInternacionalDoencaResponse(classificacaoInternacionalDoenca);
				}).getContent();
		return new PagedResponse<>(classificacaoInternacionalDoencaResponses,
				classificacaoInternacionalDoencas.getNumber(), classificacaoInternacionalDoencas.getSize(),
				classificacaoInternacionalDoencas.getTotalElements(), classificacaoInternacionalDoencas.getTotalPages(),
				classificacaoInternacionalDoencas.isLast());

	}

	public void deleteClassificacaoInternacionalDoenca(Long id) {
		ClassificacaoInternacionalDoenca classificacaoInternacionalDoenca = classificacaoInternacionalDoencaRepository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoInternacionalDoenca", "id", id));
		classificacaoInternacionalDoencaRepository.delete(classificacaoInternacionalDoenca);
	}

	public void createClassificacaoInternacionalDoenca(
			ClassificacaoInternacionalDoencaRequest classificacaoInternacionalDoencaRequest) {

		ClassificacaoInternacionalDoenca classificacaoInternacionalDoenca = new ClassificacaoInternacionalDoenca(classificacaoInternacionalDoencaRequest);

		setEntidades(classificacaoInternacionalDoenca, classificacaoInternacionalDoencaRequest);
		
		classificacaoInternacionalDoencaRepository.save(classificacaoInternacionalDoenca);

	}

	public void updateClassificacaoInternacionalDoenca(
			ClassificacaoInternacionalDoencaRequest classificacaoInternacionalDoencaRequest) {

		ClassificacaoInternacionalDoenca classificacaoInternacionalDoenca = classificacaoInternacionalDoencaRepository
				.findById(classificacaoInternacionalDoencaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ClassificacaoInternacionalDoenca", "id",
						classificacaoInternacionalDoencaRequest.getId()));

		classificacaoInternacionalDoenca.setCodigo(classificacaoInternacionalDoencaRequest.getCodigo());
		classificacaoInternacionalDoenca.setDescricao(classificacaoInternacionalDoencaRequest.getDescricao());

		setEntidades(classificacaoInternacionalDoenca, classificacaoInternacionalDoencaRequest);

		classificacaoInternacionalDoencaRepository.save(classificacaoInternacionalDoenca);

	}

	public void setEntidades(ClassificacaoInternacionalDoenca classificacaoInternacionalDoenca, 
			ClassificacaoInternacionalDoencaRequest classificacaoInternacionalDoencaRequest) {
		
		Long catDoencaId = classificacaoInternacionalDoencaRequest.getCategoriaDoencaId();
		if (catDoencaId != null && catDoencaId != 0) {
			CategoriaDoenca categoriaDoenca = categoriaDoencaRepository.findById(catDoencaId)
					.orElseThrow(() -> new ResourceNotFoundException("CategoriaDoenca", "id", catDoencaId));
			classificacaoInternacionalDoenca.setCategoriaDoenca(categoriaDoenca);
		}

		Long subCatDoencaId = classificacaoInternacionalDoencaRequest.getSubCategoriaDoencaId();
		if (subCatDoencaId != null && subCatDoencaId != 0) {
			SubCategoriaDoenca subCategoriaDoenca = subCategoriaDoencaRepository.findById(subCatDoencaId)
					.orElseThrow(() -> new ResourceNotFoundException("SubCategoriaDoenca", "id", subCatDoencaId));
			classificacaoInternacionalDoenca.setSubCategoriaDoenca(subCategoriaDoenca);
		}
		
	}

}
