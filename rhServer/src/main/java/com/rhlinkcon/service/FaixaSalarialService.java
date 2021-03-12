package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ClasseSalarial;
import com.rhlinkcon.model.FaixaSalarial;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.faixaSalarial.FaixaSalarialRequest;
import com.rhlinkcon.payload.faixaSalarial.FaixaSalarialResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ClasseSalarialRepository;
import com.rhlinkcon.repository.FaixaSalarialRepository;
import com.rhlinkcon.repository.GrupoSalarialRepository;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class FaixaSalarialService {

	@Autowired
	private FaixaSalarialRepository faixaSalarialRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private GrupoSalarialRepository grupoSalarialRepository;

	@Autowired
	private ClasseSalarialRepository classeSalarialRepository;

	@Autowired
	private ReferenciaSalarialRepository nivelSalarialRepository;

	public void createFaixaSalarial(FaixaSalarialRequest faixaSalarialRequest) {

		GrupoSalarial grupoSalarial = grupoSalarialRepository.findById(faixaSalarialRequest.getGrupoSalarialId())
				.orElseThrow(() -> new ResourceNotFoundException("GrupoSalarial", "id",
						faixaSalarialRequest.getGrupoSalarialId()));

		ClasseSalarial classeSalarial = classeSalarialRepository.findById(faixaSalarialRequest.getClasseSalarialId())
				.orElseThrow(() -> new ResourceNotFoundException("ClasseSalarial", "id",
						faixaSalarialRequest.getClasseSalarialId()));

		FaixaSalarial faixaSalarial = new FaixaSalarial(faixaSalarialRequest, classeSalarial, grupoSalarial);

		if (!Objects.isNull(faixaSalarialRequest.getNiveisSalariaisIds())
				&& faixaSalarialRequest.getNiveisSalariaisIds().size() > 0) {
			faixaSalarial.setNiveisSalariais(new HashSet<>());
			for (Long itemId : faixaSalarialRequest.getNiveisSalariaisIds()) {
				ReferenciaSalarial nivelSalarial = nivelSalarialRepository.findById(itemId)
						.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id", itemId));
				faixaSalarial.getNiveisSalariais().add(nivelSalarial);
			}
		}

		faixaSalarialRepository.save(faixaSalarial);

	}

	public void updateFaixaSalarial(FaixaSalarialRequest faixaSalarialRequest) {

		FaixaSalarial faixaSalarial = faixaSalarialRepository.findById(faixaSalarialRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("FaixaSalarial", "id", faixaSalarialRequest.getId()));

		GrupoSalarial grupoSalarial = grupoSalarialRepository.findById(faixaSalarialRequest.getGrupoSalarialId())
				.orElseThrow(() -> new ResourceNotFoundException("GrupoSalarial", "id",
						faixaSalarialRequest.getGrupoSalarialId()));

		ClasseSalarial classeSalarial = classeSalarialRepository.findById(faixaSalarialRequest.getClasseSalarialId())
				.orElseThrow(() -> new ResourceNotFoundException("ClasseSalarial", "id",
						faixaSalarialRequest.getClasseSalarialId()));

		faixaSalarial.setClasseSalarial(classeSalarial);
		faixaSalarial.setGrupoSalarial(grupoSalarial);

//		List<Long> niveisSalariais = new ArrayList<Long>();
//		for (ReferenciaSalarial nivelSalarial : faixaSalarial.getNiveisSalariais()) {
//			niveisSalariais.add(nivelSalarial.getId());
//		}
		faixaSalarial.getNiveisSalariais().clear();
		if (!Objects.isNull(faixaSalarialRequest.getNiveisSalariaisIds())
				&& faixaSalarialRequest.getNiveisSalariaisIds().size() > 0) {
//			List<Long> niveisSalariaisNotDelete = new ArrayList<Long>();
			for (Long itemId : faixaSalarialRequest.getNiveisSalariaisIds()) {
				ReferenciaSalarial nivelSalarial = nivelSalarialRepository.findById(itemId)
						.orElseThrow(() -> new ResourceNotFoundException("NivelSalarial", "id", itemId));
				faixaSalarial.getNiveisSalariais().add(nivelSalarial);
//				niveisSalariaisNotDelete.add(itemId);

//				List<ReferenciaSalarial> tempList = new ArrayList<ReferenciaSalarial>(faixaSalarial.getNiveisSalariais());
//				for (ReferenciaSalarial nivelSalarial : tempList) {
//					if (!niveisSalariaisNotDelete.contains(nivelSalarial.getId())) {
//						faixaSalarial.getNiveisSalariais().remove(nivelSalarial);
//					}
//				}
			}
		}

		faixaSalarialRepository.save(faixaSalarial);

	}

	public void deleteFaixaSalarial(Long id) {
		FaixaSalarial faixaSalarial = faixaSalarialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("FaixaSalarial", "id", id));

		faixaSalarialRepository.delete(faixaSalarial);
	}

	public PagedResponse<FaixaSalarialResponse> getAllFaixasSalariais(int page, int size, String funcao, String order) {
		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		// Page<FaixaSalarial> faixasSalariais =
		// faixaSalarialRepository.findByFuncaoIgnoreCaseContaining(funcao, pageable);

		Page<FaixaSalarial> faixasSalariais = faixaSalarialRepository.findAll(pageable);

		if (faixasSalariais.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), faixasSalariais.getNumber(), faixasSalariais.getSize(),
					faixasSalariais.getTotalElements(), faixasSalariais.getTotalPages(), faixasSalariais.isLast());
		}

		List<FaixaSalarialResponse> faixasSalariaisResponse = faixasSalariais.map(faixaSalarial -> {
			return new FaixaSalarialResponse(faixaSalarial);
		}).getContent();
		return new PagedResponse<>(faixasSalariaisResponse, faixasSalariais.getNumber(), faixasSalariais.getSize(),
				faixasSalariais.getTotalElements(), faixasSalariais.getTotalPages(), faixasSalariais.isLast());

	}

	public FaixaSalarialResponse getFaixaSalarialById(Long faixaSalarialId) {
		FaixaSalarial faixaSalarial = faixaSalarialRepository.findById(faixaSalarialId)
				.orElseThrow(() -> new ResourceNotFoundException("FaixaSalarial", "id", faixaSalarialId));

		Usuario userCreated = usuarioRepository.findById(faixaSalarial.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", faixaSalarial.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(faixaSalarial.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", faixaSalarial.getUpdatedBy()));

		FaixaSalarialResponse faixaSalarialResponse = new FaixaSalarialResponse(faixaSalarial,
				faixaSalarial.getCreatedAt(), userCreated.getNome(), faixaSalarial.getUpdatedAt(),
				userUpdated.getNome());

		return faixaSalarialResponse;
	}

	public FaixaSalarialResponse getFaixaSalarialByGrupoSalarialAndClasseSalarial(GrupoSalarial grupoSalarial,
			ClasseSalarial classeSalarial) {

		FaixaSalarial faixaSalarial = faixaSalarialRepository
				.findByGrupoSalarialAndClasseSalarial(grupoSalarial, classeSalarial)
				.orElseThrow(() -> new ResourceNotFoundException("FaixaSalarial", "grupo e classe",
						grupoSalarial.getId() + " e " + classeSalarial.getId()));

		if (Objects.nonNull(faixaSalarial)) {
			Usuario userCreated = usuarioRepository.findById(faixaSalarial.getCreatedBy())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", faixaSalarial.getCreatedBy()));

			Usuario userUpdated = usuarioRepository.findById(faixaSalarial.getUpdatedBy())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", faixaSalarial.getUpdatedBy()));

			FaixaSalarialResponse faixaSalarialResponse = new FaixaSalarialResponse(faixaSalarial,
					faixaSalarial.getCreatedAt(), userCreated.getNome(), faixaSalarial.getUpdatedAt(),
					userUpdated.getNome());

			return faixaSalarialResponse;
		} else {
			return new FaixaSalarialResponse();
		}

	}

	public List<FaixaSalarialResponse> getFaixasFalariasPorGrupo(Long grupoSalarialId) {
		List<FaixaSalarial> faixas = faixaSalarialRepository.findAllByGrupoSalarialId(grupoSalarialId);
		List<FaixaSalarialResponse> respose = faixas.stream().map(faixa -> new FaixaSalarialResponse(faixa))
				.collect(Collectors.toList());
		return respose;
	}

}
