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
import com.rhlinkcon.payload.responsavelLegal.ResponsavelLegalRequest;
import com.rhlinkcon.payload.responsavelLegal.ResponsavelLegalResponse;
import com.rhlinkcon.repository.ResponsavelLegalRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ResponsavelLegalService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ResponsavelLegalController {

	@Autowired
	private ResponsavelLegalRepository responsavelLegalRepository;

	@Autowired
	private ResponsavelLegalService responsavelLegalService;

	@GetMapping("/responsaveisLegais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ResponsavelLegalResponse> getAllResponsaveisLegais(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return responsavelLegalService.getAllResponsaveisLegais(page, size, nome, order);
	}

	@GetMapping("/responsavelLegal/{responsavelLegalId}")
	public ResponsavelLegalResponse getResponsavelLegalById(@PathVariable Long responsavelLegalId) {
		return responsavelLegalService.getResponsavelLegalById(responsavelLegalId);
	}

	@PostMapping("/responsavelLegal")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createResponsavelLegal(@Valid @RequestBody ResponsavelLegalRequest responsavelLegalRequest) {
		if (responsavelLegalRepository.existsByNome(responsavelLegalRequest.getNome())) {
			return Utils.badRequest(false, "Já existe um Responsavel Legal com este nome!");
		}
		
		responsavelLegalService.createResponsavelLegal(responsavelLegalRequest);

		return Utils.created(true, "Responsavel Legal criada com sucesso.");
	}

	@PutMapping("/responsavelLegal")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateResponsavelLegal(@Valid @RequestBody ResponsavelLegalRequest responsavelLegalRequest) {
		if (responsavelLegalRepository.existsByNomeAndIdNot(responsavelLegalRequest.getNome(), responsavelLegalRequest.getId())) {
			return Utils.badRequest(false, "Já existe um Responsavel Legal com esta descrição!!");
		}
		
		responsavelLegalService.updateResponsavelLegal(responsavelLegalRequest);
		
		return Utils.created(true, "Responsavel Legal atualizado com sucesso.");
	}


	@DeleteMapping("/responsavelLegal/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteResponsavelLegal(@PathVariable("id") Long id) {
		responsavelLegalService.deleteResponsavelLegal(id);
		
		return Utils.deleted(true, "Responsavel Legal deletada com sucesso.");
	}
	
	@GetMapping("/responsavelLegal/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ResponsavelLegalResponse> searchByNome(@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return responsavelLegalService.findByNome(search);
	}

}
