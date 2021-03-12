package com.rhlinkcon.controller;

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

import com.rhlinkcon.payload.acidenteTrabalho.AcidenteTrabalhoRequest;
import com.rhlinkcon.payload.acidenteTrabalho.AcidenteTrabalhoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AcidenteTrabalhoRepository;
import com.rhlinkcon.service.AcidenteTrabalhoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AcidenteTrabalhoController {

	@Autowired
	private AcidenteTrabalhoRepository acidenteTrabalhoRepository;

	@Autowired
	private AcidenteTrabalhoService acidenteTrabalhoService;

	@GetMapping("/acidenteTrabalhos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<AcidenteTrabalhoResponse> getAllAcidentesDeTrabalho(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return acidenteTrabalhoService.getAllAcidentesDeTrabalho(page, size, order, nome);
	}

	@GetMapping("/acidenteTrabalho/{acidenteTrabalhoId}")
	public AcidenteTrabalhoResponse getAcidenteTrabalhoById(@PathVariable Long acidenteTrabalhoId) {
		return acidenteTrabalhoService.getAcidenteTrabalhoById(acidenteTrabalhoId);
	}

	@PostMapping("/acidenteTrabalho")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createAcidenteTrabalho(
			@Valid @RequestBody AcidenteTrabalhoRequest acidenteTrabalhoRequest) {
		if (acidenteTrabalhoRepository.existsByAviso(acidenteTrabalhoRequest.getAviso())) {
			return Utils.badRequest(false, "O aviso já está em uso!");
		}

		acidenteTrabalhoService.createAcidenteTrabalho(acidenteTrabalhoRequest);

		return Utils.created(true, "Acidente de trabalho criado com sucesso.");
	}

	@PutMapping("/acidenteTrabalho")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateAcidenteTrabalho(
			@Valid @RequestBody AcidenteTrabalhoRequest acidenteTrabalhoRequest) {
		if (acidenteTrabalhoRepository.existsByAvisoAndIdNot(acidenteTrabalhoRequest.getAviso(),
				acidenteTrabalhoRequest.getId())) {
			return Utils.badRequest(false, "O aviso já está em uso!");
		}

		acidenteTrabalhoService.updateAcidenteTrabalho(acidenteTrabalhoRequest);

		return Utils.created(true, "Acidente de trabalho atualizado com sucesso.");
	}

	@DeleteMapping("/acidenteTrabalho/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteAcidenteTrabalho(@PathVariable("id") Long id) {
		acidenteTrabalhoService.deleteAcidenteTrabalho(id);

		return Utils.deleted(true, "Acidente de trabalho deletado com sucesso.");
	}

}
