package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Convenio;
import com.rhlinkcon.model.CrmCrea;
import com.rhlinkcon.model.CrmCreaEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.crmCrea.CrmCreaRequest;
import com.rhlinkcon.payload.crmCrea.CrmCreaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ConvenioRepository;
import com.rhlinkcon.repository.CrmCreaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class CrmCreaService {

	@Autowired
	private CrmCreaRepository crmCreaRepository;

	@Autowired
	private ConvenioRepository convenioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<CrmCreaResponse> getAllCrmCreas() {
		List<CrmCrea> crmCreas = crmCreaRepository.findAll();

		List<CrmCreaResponse> listCrmCreaResponse = new ArrayList<>();
		for (CrmCrea crmCrea : crmCreas) {
			listCrmCreaResponse.add(new CrmCreaResponse(crmCrea, Projecao.BASICA));
		}
		return listCrmCreaResponse;
	}

	public CrmCreaResponse getCrmCreaById(Long crmCreaId) {
		CrmCrea crmCrea = crmCreaRepository.findById(crmCreaId)
				.orElseThrow(() -> new ResourceNotFoundException("CrmCrea", "id", crmCreaId));

		CrmCreaResponse crmCreaResponse = new CrmCreaResponse(crmCrea, Projecao.COMPLETA);
		Usuario criadoPor = usuarioRepository.findById(crmCrea.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", crmCrea.getCreatedBy()));
		crmCreaResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(crmCrea.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", crmCrea.getUpdatedBy()));
		crmCreaResponse.setAlteradoPor(alteradoPor.getNome());

		return crmCreaResponse;
	}

	public PagedResponse<CrmCreaResponse> getAllCrmCreas(int page, int size, String order, String nomeConveniado) {
		validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<CrmCrea> crmCreas = null;

		if (Utils.checkStr(nomeConveniado))
			crmCreas = crmCreaRepository.findByNomeConveniadoIgnoreCaseContaining(nomeConveniado, pageable);
		else
			crmCreas = crmCreaRepository.findAll(pageable);

		if (crmCreas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), crmCreas.getNumber(), crmCreas.getSize(),
					crmCreas.getTotalElements(), crmCreas.getTotalPages(), crmCreas.isLast());
		}

		List<CrmCreaResponse> crmCreaResponses = crmCreas.map(crmCrea -> {
			return new CrmCreaResponse(crmCrea, Projecao.BASICA);
		}).getContent();
		return new PagedResponse<>(crmCreaResponses, crmCreas.getNumber(), crmCreas.getSize(),
				crmCreas.getTotalElements(), crmCreas.getTotalPages(), crmCreas.isLast());

	}

	public void deleteCrmCrea(Long id) {
		CrmCrea crmCrea = crmCreaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CrmCrea", "id", id));
		crmCreaRepository.delete(crmCrea);
	}

	public void createCrmCrea(CrmCreaRequest crmCreaRequest) {

		CrmCrea crmCrea = new CrmCrea();
		crmCrea.setCoordenadorPcmso(Utils.setBool(crmCreaRequest.isCoordenadorPcmso()));
		crmCrea.setResponsavelLtcat(Utils.setBool(crmCreaRequest.isResponsavelLtcat()));
		crmCrea.setNomeConveniado(crmCreaRequest.getNomeConveniado());
		crmCrea.setNumeroCrmCrea(crmCreaRequest.getNumeroCrmCrea());
		crmCrea.setTipo(CrmCreaEnum.getEnumByString(crmCreaRequest.getTipo()));

		if (Utils.checkList(crmCreaRequest.getConvenioIds())) {
			for (Long convenioId : crmCreaRequest.getConvenioIds()) {
				Convenio convenio = convenioRepository.findById(convenioId)
						.orElseThrow(() -> new ResourceNotFoundException("Convenio", "id", convenioId));
				crmCrea.getConvenios().add(convenio);
			}
		}

		crmCreaRepository.save(crmCrea);

	}

	public void updateCrmCrea(CrmCreaRequest crmCreaRequest) {

		CrmCrea crmCrea = crmCreaRepository.findById(crmCreaRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("CrmCrea", "id", crmCreaRequest.getId()));

		crmCrea.setCoordenadorPcmso(Utils.setBool(crmCreaRequest.isCoordenadorPcmso()));
		crmCrea.setResponsavelLtcat(Utils.setBool(crmCreaRequest.isResponsavelLtcat()));
		crmCrea.setNomeConveniado(crmCreaRequest.getNomeConveniado());
		crmCrea.setNumeroCrmCrea(crmCreaRequest.getNumeroCrmCrea());

		if(!crmCrea.getConvenios().isEmpty()) {
			crmCrea.getConvenios().removeAll(crmCrea.getConvenios());
		}
		
		if (Utils.checkList(crmCreaRequest.getConvenioIds())) {
			for (Long convenioId : crmCreaRequest.getConvenioIds()) {
				Convenio convenio = convenioRepository.findById(convenioId)
						.orElseThrow(() -> new ResourceNotFoundException("Convenio", "id", convenioId));
				crmCrea.getConvenios().add(convenio);
			}
		}

		crmCreaRepository.save(crmCrea);

	}
	
	public List<CrmCreaResponse> getAllCrmCreasPorTipo(String tipo) {
		List<CrmCrea> crmCreas = new ArrayList<>();
		List<CrmCreaResponse> crmCreaResponses = new ArrayList<>();
		crmCreas = crmCreaRepository.findAllByTipo(CrmCreaEnum.getEnumByString(tipo));

		if(!crmCreas.isEmpty()) {
			crmCreaResponses = crmCreas.stream().map(crmCrea -> {
				return new CrmCreaResponse(crmCrea, Projecao.BASICA);
			}).collect(Collectors.toList());
			return crmCreaResponses;
		}
		return crmCreaResponses;
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
