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

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialRequest;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ReferenciaSalarialService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ReferencialSalarialController {

	@Autowired
	private ReferenciaSalarialRepository nivelSalariaRepository;

	@Autowired
	private ReferenciaSalarialService nivelSalarialService;

	@GetMapping("/listaNiveisSalariais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ReferenciaSalarialResponse> getAllReferenciasSalariais() {
		return nivelSalarialService.getAllReferenciasSalariais();
	}

	@GetMapping("/niveisSalariais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ReferenciaSalarialResponse> getAllReferenciasSalariaisPage(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return nivelSalarialService.getAllReferenciasSalariaisPage(page, size, order, descricao);
	}

	@GetMapping("/nivelSalarial/{id}")
	public ReferenciaSalarialResponse getNivelSalarialById(@PathVariable Long id) {
		return nivelSalarialService.getReferenciaSalarialById(id);
	}

	@PostMapping("/nivelSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createNivelSalarial(@Valid @RequestBody ReferenciaSalarialRequest referenciaSalarialRequest) {
		if (nivelSalariaRepository.existsByCodigo(referenciaSalarialRequest.getCodigo())) {
			return Utils.badRequest(false, "Este Nível Salarial já existe!");
		}

		nivelSalarialService.createReferenciaSalarial(referenciaSalarialRequest);

		return Utils.created(true, "Nível Salarial criado com sucesso.");
	}

	@PutMapping("/nivelSalarial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateReferenciaSalarial(@Valid @RequestBody ReferenciaSalarialRequest nivelSalarialRequest) {
		if (nivelSalariaRepository.existsByCodigoAndIdNot(nivelSalarialRequest.getCodigo(),
				nivelSalarialRequest.getId())) {
			return Utils.badRequest(false, "Este Nível Salarial já existe!");
		}

		nivelSalarialService.updateReferenciaSalarial(nivelSalarialRequest);

		return Utils.created(true, "Nível Salarial atualizado com sucesso.");
	}

	
	@DeleteMapping("/nivelSalarial/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteNivelSalarial(@PathVariable("id") Long id) {
		
		try {
			nivelSalarialService.deleteNivelSalarial(id);

			return Utils.deleted(true, "Nível Salarial deletado com sucesso.");
		} catch (Exception e) {
			return Utils.forbidden(true, "Não é possível excluir um nível salarial que possui outras informações ligadas a ele");
		}
		
	}

}
