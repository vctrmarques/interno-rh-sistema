package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ClasseSalarial;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.payload.faixaSalarial.FaixaSalarialRequest;
import com.rhlinkcon.payload.faixaSalarial.FaixaSalarialResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ClasseSalarialRepository;
import com.rhlinkcon.repository.GrupoSalarialRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.FaixaSalarialService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FaixaSalarialController {

	@Autowired
	private GrupoSalarialRepository grupoSalarialRepository;
	
	@Autowired
	private ClasseSalarialRepository classeSalarialRepository;

	@Autowired
	private FaixaSalarialService faixaSalarialService;

	@GetMapping("/faixasSalarais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FaixaSalarialResponse> getAllFaixasSalariais(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "funcao", defaultValue = AppConstants.DEFAULT_EMPTY) String funcao) {
		return faixaSalarialService.getAllFaixasSalariais(page, size, funcao, order);
	}
	
	@GetMapping("/faixaSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public FaixaSalarialResponse getFaixaSalarialByGrupoSalarialAndClasseSalarial(
			@RequestParam(value = "grupoSalarialId", defaultValue = AppConstants.DEFAULT_EMPTY) String grupoSalarialId,
			@RequestParam(value = "classeSalarialId", defaultValue = AppConstants.DEFAULT_EMPTY) String classeSalarialId) {
		
		GrupoSalarial grupoSalarial = grupoSalarialRepository.findById(Long.parseLong(grupoSalarialId))
				.orElseThrow(() -> new ResourceNotFoundException("GrupoSalarial", "id", Long.parseLong(grupoSalarialId)));
		
		ClasseSalarial classeSalarial = classeSalarialRepository.findById(Long.parseLong(classeSalarialId))
				.orElseThrow(() -> new ResourceNotFoundException("ClasseSalarial", "id", Long.parseLong(classeSalarialId)));
		
		
		return faixaSalarialService.getFaixaSalarialByGrupoSalarialAndClasseSalarial(grupoSalarial, classeSalarial);
	}

	@GetMapping("/faixaSalarial/{faixaSalarialId}")
	public FaixaSalarialResponse getFaixaSalarialById(@PathVariable Long faixaSalarialId) {
		return faixaSalarialService.getFaixaSalarialById(faixaSalarialId);
	}
	
	@GetMapping("/faixasSalariais/porGrupo/{grupoSalarialId}")
	public List<FaixaSalarialResponse> getFaixasFalariasPorGrupo(@PathVariable Long grupoSalarialId) {
		return faixaSalarialService.getFaixasFalariasPorGrupo(grupoSalarialId);
	}

	@PostMapping("/faixaSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createFaixaSalarial(@Valid @RequestBody FaixaSalarialRequest faixaSalarialRequest) {
//		if (faixaSalarialRepository.existsByGrupoSalarialAndClasseSalarial(faixaSalarialRequest.getGrupoSalarial().getId(),
//				faixaSalarialRequest.getClasseSalarial().getId())) {
//			return Utils.badRequest(false, "Esta Faixa Salarial já existe!");
//		}

		faixaSalarialService.createFaixaSalarial(faixaSalarialRequest);

		return Utils.created(true, "Faixa Salarial criada com sucesso.");
	}

	@PutMapping("/faixaSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFaixaSalarial(@Valid @RequestBody FaixaSalarialRequest faixaSalarialRequest) {
//		if (faixaSalarialRepository.existsByGrupoSalarialAndClasseSalarialAndIdNot(faixaSalarialRequest.getGrupoSalarial().getId()
//				 , faixaSalarialRequest.getClasseSalarial().getId(), faixaSalarialRequest.getId())) {
//			return Utils.badRequest(false, "Esta Faixa Salarial já existe!");
//		}

		faixaSalarialService.updateFaixaSalarial(faixaSalarialRequest);
		
		return Utils.created(true, "Faixa Salarial atualizada com sucesso.");
	}


	@DeleteMapping("/faixaSalarial/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteFaixaSalarial(@PathVariable("id") Long id) {
		faixaSalarialService.deleteFaixaSalarial(id);
		
		return Utils.deleted(true, "Classificação de Faixa Salarial deletada com sucesso.");
	}

}
