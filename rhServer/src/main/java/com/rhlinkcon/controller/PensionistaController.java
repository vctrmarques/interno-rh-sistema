package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.pensionista.PensionistaCreateRequest;
import com.rhlinkcon.payload.pensionista.PensionistaRequest;
import com.rhlinkcon.payload.pensionista.PensionistaResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.PensionistaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class PensionistaController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";

	@Autowired
	private PensionistaService service;

	@GetMapping("/pensoesPrevidenciarias")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<PensionistaResponse> getAll(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "searchFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String searchFuncionario,
			@RequestParam(value = "searchPensionista", defaultValue = AppConstants.DEFAULT_EMPTY) String searchPensionista) {
		return service.getAll(page, size, searchFuncionario, searchPensionista, order);
	}

	@PostMapping("/pensaoPrevidenciaria")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody PensionistaCreateRequest requestCreate) {
		service.create(requestCreate.getPensaoPrevidenciaria(), requestCreate.getPensaoPrevidenciariaPagamento());
		return Utils.created(true, "Pensão Criada com sucesso.");
	}

	@PutMapping("/pensaoPrevidenciaria")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody PensionistaRequest request) {
		service.update(request);
		return Utils.created(true, "Pensão atualizada com sucesso.");
	}

	@GetMapping("/pensaoPrevidenciaria/{id}")
	@PreAuthorize(AUTHORIZE)
	public PensionistaResponse getPensaoPrevidencia(@PathVariable Long id) {
		return service.getPensaoPrevidencia(id);
	}

	@GetMapping("/pensao/exSegurado/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncionarioResponse> searchExSeguradoByNome(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return service.findExSeguradoByNome(search);
	}

}
