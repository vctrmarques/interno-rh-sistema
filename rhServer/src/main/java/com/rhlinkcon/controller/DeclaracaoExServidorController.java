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

import com.rhlinkcon.filtro.DeclaracaoExServidorFiltro;
import com.rhlinkcon.payload.declaracaoExServidor.DeclaracaoExServidorRequest;
import com.rhlinkcon.payload.declaracaoExServidor.DeclaracaoExServidorResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.DeclaracaoExServidorService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class DeclaracaoExServidorController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";

	@Autowired
	private DeclaracaoExServidorService service;

	@GetMapping("/declaracoesExServidor")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<DeclaracaoExServidorResponse> getAll(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			DeclaracaoExServidorFiltro declaracaoExServidorFiltro) {
		return service.getAll(page, size, order, declaracaoExServidorFiltro);
	}

	@PostMapping("/declaracaoExServidor")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody DeclaracaoExServidorRequest request) {
		DeclaracaoExServidorResponse response = service.create(request);
		return Utils.created(true, "Declaração Criada com sucesso.", response);
	}

	@PutMapping("/declaracaoExServidor")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody DeclaracaoExServidorRequest request) {
		DeclaracaoExServidorResponse response = service.create(request);
		return Utils.created(true, "Declaração atualizada com sucesso.", response);
	}

	@PutMapping("/declaracaoExServidor/arquivar/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> arquivar(@PathVariable("id") Long id) {
		service.arquivar(id);
		return Utils.created(true, "Declaração atualizada com sucesso.");
	}

	@DeleteMapping("/declaracaoExServidor/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Declaração deletada com sucesso.");
	}

	@GetMapping("/declaracaoExServidor/{id}")
	public DeclaracaoExServidorResponse getById(@PathVariable Long id) {
		return service.getById(id);
	}

}
