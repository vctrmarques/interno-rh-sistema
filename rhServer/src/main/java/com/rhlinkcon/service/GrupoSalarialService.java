package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.DataIntegretyException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.model.ClasseSalarial;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialRequest;
import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialResponse;
import com.rhlinkcon.payload.classeSalarial.ClasseSalarialRequest;
import com.rhlinkcon.repository.GrupoSalarialRepository;
import com.rhlinkcon.repository.ClasseSalarialRepository;
import com.rhlinkcon.repository.FaixaSalarialRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class GrupoSalarialService {

	@Autowired
	private GrupoSalarialRepository grupoSalarialRepository;

	@Autowired
	private ClasseSalarialRepository classeSalarialRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FaixaSalarialRepository faixaSalarialRepository;


	public void createGrupoSalarial(GrupoSalarialRequest grupoSalarialRequest) {
		GrupoSalarial grupoSalarial = new GrupoSalarial();
		grupoSalarial.setNome(grupoSalarialRequest.getNome());
		
		grupoSalarialRepository.save(grupoSalarial);

		if(!Objects.isNull(grupoSalarialRequest.getListClassesSalariais()) && grupoSalarialRequest.getListClassesSalariais().size() > 0) {
			for (ClasseSalarialRequest classeSalarialRequest : grupoSalarialRequest.getListClassesSalariais()) {
				ClasseSalarial classeSalarial = new ClasseSalarial(classeSalarialRequest, grupoSalarial);
				classeSalarialRepository.save(classeSalarial);
			}
		}
	}

	public void updateGrupoSalarial(GrupoSalarialRequest grupoSalarialRequest) {

		GrupoSalarial grupoSalarial = grupoSalarialRepository.findById(grupoSalarialRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("GrupoSalarial", "id", grupoSalarialRequest.getId()));

		grupoSalarial.setNome(grupoSalarialRequest.getNome());
		grupoSalarialRepository.save(grupoSalarial);
		List<Long> classesSalariaisId = new ArrayList<Long>();
		for (ClasseSalarial classeSalarial : grupoSalarial.getClasseSalarial()) {
			classesSalariaisId.add(classeSalarial.getId());
		}
		if(!Objects.isNull(grupoSalarialRequest.getListClassesSalariais()) && grupoSalarialRequest.getListClassesSalariais().size() > 0) {
			for (ClasseSalarialRequest classeSalarialRequest : grupoSalarialRequest.getListClassesSalariais()) {
				if(Objects.isNull(classeSalarialRequest.getId())) {
					ClasseSalarial tag = new ClasseSalarial(classeSalarialRequest, grupoSalarial);
					classeSalarialRepository.save(tag);
				}else {
					classesSalariaisId.remove(classeSalarialRequest.getId());
				}
			}
		}
		for (Long idClasseSalarial : classesSalariaisId) {
			classeSalarialRepository.deleteById(idClasseSalarial);
		}
	}

	@Transactional
	public void deleteGrupoSalarial(Long id) {
		if(faixaSalarialRepository.existsByGrupoSalarialId(id)) {
			throw new DataIntegretyException("Erro! Existem Faixas Salarias ligadas a este grupo");
		}
		
		List<ClasseSalarial> classes = classeSalarialRepository.getListClassesSalariaisIdByGrupoSalarialId(id);
		if(!Objects.isNull(classes) && classes.size() > 0) {
			for (ClasseSalarial classe : classes) {
				classeSalarialRepository.delete(classe);
			}
		}
		GrupoSalarial grupoSalarial = grupoSalarialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("GrupoSalarial", "id", id));
		grupoSalarialRepository.delete(grupoSalarial);
	}

	public PagedResponse<GrupoSalarialResponse> getAllGruposSalariais(int page, int size, String nome, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<GrupoSalarial> gruposSalariais = null;

		if (Utils.checkStr(nome) ) {
			gruposSalariais = grupoSalarialRepository.findByNomeIgnoreCaseContaining(nome, pageable);
		}else {
			gruposSalariais = grupoSalarialRepository.findAll(pageable);
		}

		if (gruposSalariais.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), gruposSalariais.getNumber(), gruposSalariais.getSize(),
					gruposSalariais.getTotalElements(), gruposSalariais.getTotalPages(), gruposSalariais.isLast());
		}

		List<GrupoSalarialResponse> gruposSalariaisResponse = gruposSalariais.map(grupoSalarial -> {
			return new GrupoSalarialResponse(grupoSalarial);
		}).getContent();
		return new PagedResponse<>(gruposSalariaisResponse, gruposSalariais.getNumber(), gruposSalariais.getSize(),
				gruposSalariais.getTotalElements(), gruposSalariais.getTotalPages(), gruposSalariais.isLast());

	}

	public GrupoSalarialResponse getGrupoSalarialById(Long grupoSalarialId) {
		GrupoSalarial grupoSalarial = grupoSalarialRepository.findById(grupoSalarialId)
				.orElseThrow(() -> new ResourceNotFoundException("GrupoSalarial", "id", grupoSalarialId));

		Usuario userCreated = usuarioRepository.findById(grupoSalarial.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", grupoSalarial.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(grupoSalarial.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", grupoSalarial.getUpdatedBy()));

		GrupoSalarialResponse grupoSalarialResponse = new GrupoSalarialResponse(grupoSalarial, grupoSalarial.getCreatedAt(),
				userCreated.getNome(), grupoSalarial.getUpdatedAt(), userUpdated.getNome());

		return grupoSalarialResponse;
	}
	
	public List<GrupoSalarialResponse> getAllGruposSalariais() {
		List<GrupoSalarial> grupoSalarialList = grupoSalarialRepository.findAll();

		List<GrupoSalarialResponse> grupoSalarialResponseList = new ArrayList<>();
		for (GrupoSalarial grupoSalarial : grupoSalarialList) {
			grupoSalarialResponseList.add(new GrupoSalarialResponse(grupoSalarial));
		}
		return grupoSalarialResponseList;
	}
	
}
