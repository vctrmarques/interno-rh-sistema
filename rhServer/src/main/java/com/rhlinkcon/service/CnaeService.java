package com.rhlinkcon.service;

import java.util.ArrayList;
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
import com.rhlinkcon.model.Cnae;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.cnae.CnaeRequest;
import com.rhlinkcon.payload.cnae.CnaeResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CnaeRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;

@Service
public class CnaeService {

	@Autowired
	private CnaeRepository cnaeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public CnaeResponse getCnaeById(Long cnaeId) {
		Cnae cnae = cnaeRepository.findById(cnaeId)
				.orElseThrow(() -> new ResourceNotFoundException("Cnae", "id", cnaeId));

		CnaeResponse cnaeResponse = new CnaeResponse(cnae);

		Usuario criadoPor = usuarioRepository.findById(cnae.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", cnae.getCreatedBy()));
		cnaeResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(cnae.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", cnae.getUpdatedBy()));
		cnaeResponse.setAlteradoPor(alteradoPor.getNome());

		return cnaeResponse;
	}
	
	public List<CnaeResponse> searchAllByNomeOrCodigoClasse(String search) {
		List<CnaeResponse> itensResponses = new ArrayList<CnaeResponse>();
		cnaeRepository.searchByNomeSecaoOrCodigoClasse(search.toUpperCase(), search.toUpperCase())
				.forEach(item -> itensResponses.add(new CnaeResponse(item)));
		return itensResponses;
	}

	public PagedResponse<CnaeResponse> getAllCnaes(int page, int size, String order, String descricao) {
		validatePageNumberAndSize(page, size);

		// Retrieve Users
		Direction direction = Sort.Direction.ASC;
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Cnae> cnaes = null;

		if (!Objects.isNull(descricao) && !descricao.isEmpty()) {
			cnaes = cnaeRepository.findByNomeSecaoIgnoreCaseContaining(descricao, pageable);
		} else {
			cnaes = cnaeRepository.findAll(pageable);
		}

		if (cnaes.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), cnaes.getNumber(), cnaes.getSize(),
					cnaes.getTotalElements(), cnaes.getTotalPages(), cnaes.isLast());
		}

		List<CnaeResponse> cnaeResponses = cnaes.map(cnae -> {
			return new CnaeResponse(cnae);
		}).getContent();
		return new PagedResponse<>(cnaeResponses, cnaes.getNumber(), cnaes.getSize(), cnaes.getTotalElements(),
				cnaes.getTotalPages(), cnaes.isLast());

	}

	public void deleteCnae(Long id) {
		Cnae cnae = cnaeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cnae", "id", id));
		cnaeRepository.delete(cnae);

	}

	public void createCnae(CnaeRequest cnaeRequest) {
		Cnae cnae = new Cnae(cnaeRequest);
		cnaeRepository.save(cnae);

	}

	public void updateCnae(CnaeRequest cnaeRequest) {

		Cnae cnae = cnaeRepository.findById(cnaeRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cnae", "id", cnaeRequest.getId()));

		cnae.setCodigoSecao(cnaeRequest.getCodigoSecao());
		cnae.setNomeSecao(cnaeRequest.getNomeSecao());
		cnae.setCodigoClasse(cnaeRequest.getCodigoClasse());
		cnae.setNomeClasse(cnaeRequest.getNomeClasse());

		cnaeRepository.save(cnae);

	}

	private void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}
	
	public List<CnaeResponse> getAllCnaes() {

		List<Cnae> cnaes = cnaeRepository.findByOrderByNomeClasse();
		List<CnaeResponse> cnaesResponse = new ArrayList<CnaeResponse>();

		if (!cnaes.isEmpty()) {
			for (Cnae cnae : cnaes) {
				CnaeResponse cnaesResp = new CnaeResponse(cnae);

				cnaesResponse.add(cnaesResp);
			}
			return cnaesResponse;
		}
		
		return null;
	}

}
