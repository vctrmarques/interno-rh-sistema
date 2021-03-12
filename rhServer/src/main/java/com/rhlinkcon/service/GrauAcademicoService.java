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

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.GrauAcademico;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.grauAcademico.GrauAcademicoRequest;
import com.rhlinkcon.payload.grauAcademico.GrauAcademicoResponse;
import com.rhlinkcon.payload.areaFormacao.AreaFormacaoResponse;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.GrauAcademicoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class GrauAcademicoService {

	@Autowired
	private GrauAcademicoRepository grauAcademicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createGrauAcademico(GrauAcademicoRequest grauAcademicoRequest) {

		GrauAcademico grauAcademico = new GrauAcademico(grauAcademicoRequest);

		grauAcademicoRepository.save(grauAcademico);

	}

	public void updateGrauAcademico(GrauAcademicoRequest grauAcademicoRequest) {

		GrauAcademico grauAcademico = grauAcademicoRepository.findById(grauAcademicoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("GrauAcademico", "id", grauAcademicoRequest.getId()));

		grauAcademico.setNome(grauAcademicoRequest.getNome());

		grauAcademicoRepository.save(grauAcademico);

	}

	public void deleteGrauAcademico(Long id) {
		GrauAcademico grauAcademico = grauAcademicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("GrauAcademico", "id", id));

		grauAcademicoRepository.delete(grauAcademico);
	}

	public List<GrauAcademicoResponse> searchByNome(String search) {
		List<GrauAcademicoResponse> itensResponses = new ArrayList<GrauAcademicoResponse>();
		
		grauAcademicoRepository.findByNomeIgnoreCaseContaining(search)
				.forEach(item -> itensResponses.add(new GrauAcademicoResponse(item)));
		return itensResponses;
	}
	
	public PagedResponse<GrauAcademicoResponse> getAllGrausAcademicos(int page, int size, String nome, String order) {
		validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<GrauAcademico> graus = grauAcademicoRepository.findByNomeIgnoreCaseContaining(nome, pageable);

		if (graus.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), graus.getNumber(), graus.getSize(),
					graus.getTotalElements(), graus.getTotalPages(), graus.isLast());
		}

		List<GrauAcademicoResponse> grausResponse = graus.map(grauAcademico -> {
			return new GrauAcademicoResponse(grauAcademico);
		}).getContent();
		return new PagedResponse<>(grausResponse, graus.getNumber(), graus.getSize(), graus.getTotalElements(),
				graus.getTotalPages(), graus.isLast());

	}

	public GrauAcademicoResponse getGrauAcademicoById(Long grauAcademicoId) {
		GrauAcademico grauAcademico = grauAcademicoRepository.findById(grauAcademicoId)
				.orElseThrow(() -> new ResourceNotFoundException("GrauAcademico", "id", grauAcademicoId));

		Usuario userCreated = usuarioRepository.findById(grauAcademico.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", grauAcademico.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(grauAcademico.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", grauAcademico.getUpdatedBy()));

		GrauAcademicoResponse grauAcademicoResponse = new GrauAcademicoResponse(grauAcademico, grauAcademico.getCreatedAt(), 
											userCreated.getNome(), grauAcademico.getUpdatedAt(), userUpdated.getNome());

		return grauAcademicoResponse;
	}
	
	public List<GrauAcademicoResponse> getAllGrausAcademicos() {

		List<GrauAcademico> grausAcademicos = grauAcademicoRepository.findAll();
		List<GrauAcademicoResponse> grauAcademicoResponse = new ArrayList();

		if (!grausAcademicos.isEmpty()) {
			for (GrauAcademico grauAcademico : grausAcademicos) {
				GrauAcademicoResponse grauAcademicoResp = new GrauAcademicoResponse(grauAcademico);

				grauAcademicoResponse.add(grauAcademicoResp);
			}
			return grauAcademicoResponse;
		}
		
		return null;
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
