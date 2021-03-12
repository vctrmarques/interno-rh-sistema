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

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;

import com.rhlinkcon.model.ModeloDocumento;
import com.rhlinkcon.model.Usuario;

import com.rhlinkcon.payload.modeloDocumento.ModeloDocumentoRequest;
import com.rhlinkcon.payload.modeloDocumento.ModeloDocumentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;

import com.rhlinkcon.repository.ModeloConteudoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class ModeloConteudoService {

	@Autowired
	private ModeloConteudoRepository modeloConteudoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;



	public ModeloDocumentoResponse getModeloDocumentoById(Long modeloDocumentoId) {
		ModeloDocumento modeloDocumento = modeloConteudoRepository.findById(modeloDocumentoId)
				.orElseThrow(() -> new ResourceNotFoundException("ModeloDocumento", "id", modeloDocumentoId));

		ModeloDocumentoResponse modeloDocumentoResponse = new ModeloDocumentoResponse(modeloDocumento);
		Usuario criadoPor = usuarioRepository.findById(modeloDocumento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", modeloDocumento.getCreatedBy()));
		modeloDocumentoResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(modeloDocumento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", modeloDocumento.getUpdatedBy()));
		modeloDocumentoResponse.setAlteradoPor(alteradoPor.getNome());

		return modeloDocumentoResponse;
	}

	public PagedResponse<ModeloDocumentoResponse> getAllModeloDocumentoPage(int page, int size, String order,
			String descricao) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		Direction direction = Sort.Direction.ASC;
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ModeloDocumento> modeloDocumentos = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			modeloDocumentos = modeloConteudoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		} else {
			modeloDocumentos = modeloConteudoRepository.findAll(pageable);
		}

		if (modeloDocumentos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), modeloDocumentos.getNumber(),
					modeloDocumentos.getSize(), modeloDocumentos.getTotalElements(), modeloDocumentos.getTotalPages(),
					modeloDocumentos.isLast());
		}

		List<ModeloDocumentoResponse> modeloDocumentoResponses = modeloDocumentos.map(modeloDocumento -> {
			return new ModeloDocumentoResponse(modeloDocumento);
		}).getContent();
		return new PagedResponse<>(modeloDocumentoResponses, modeloDocumentos.getNumber(), modeloDocumentos.getSize(),
				modeloDocumentos.getTotalElements(), modeloDocumentos.getTotalPages(), modeloDocumentos.isLast());

	}

	public void deleteModeloDocumento(Long id) {
		ModeloDocumento modeloDocumento = modeloConteudoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ModeloDocumento", "id", id));
		modeloConteudoRepository.delete(modeloDocumento);
	}

	public void createModeloDocumento(ModeloDocumentoRequest modeloDocumentoRequest) {

		ModeloDocumento modeloDocumento = new ModeloDocumento();
		modeloDocumento.setDescricao(modeloDocumentoRequest.getDescricao());
		modeloDocumento.setConteudo(modeloDocumentoRequest.getConteudo());
		modeloConteudoRepository.save(modeloDocumento);

	}

	public void updateModeloDocumento(ModeloDocumentoRequest modeloDocumentoRequest) {

		ModeloDocumento modeloDocumento = modeloConteudoRepository.findById(modeloDocumentoRequest.getId())
				.orElseThrow(
						() -> new ResourceNotFoundException("ModeloDocumento", "id", modeloDocumentoRequest.getId()));

		
		modeloDocumento.setDescricao(modeloDocumentoRequest.getDescricao());
		modeloDocumento.setConteudo(modeloDocumentoRequest.getConteudo());
		modeloConteudoRepository.save(modeloDocumento);

	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

}
