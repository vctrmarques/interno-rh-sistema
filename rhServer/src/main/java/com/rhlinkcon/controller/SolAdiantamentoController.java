package com.rhlinkcon.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.SolAdiantamento.SolAdiantamentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.SolAdiantamentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class SolAdiantamentoController {

	@Autowired
	private SolAdiantamentoService solAdiantamentoService;
	
	@GetMapping("/solAdiantamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<SolAdiantamentoResponse> getSolAdiantamentos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "mesAdiantamento", defaultValue = AppConstants.DEFAULT_EMPTY) String mesAdiantamento) {
		return solAdiantamentoService.getAllSolAdiantamentos(page, size, order, mesAdiantamento);
	}
	
	@GetMapping("/listaSolAdiantamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SolAdiantamentoResponse> getAllSolAdiantamentos() {
		return solAdiantamentoService.getAllSolAdiantamentos();
	}
	
	@GetMapping("/solAdiantamento/{solAdiantamentoId}")
	public SolAdiantamentoResponse getSolAdiantamentoById(@PathVariable Long solAdiantamentoId) {
		return solAdiantamentoService.getSolAdiantamentoById(solAdiantamentoId);
	}
	
	@DeleteMapping("/solAdiantamento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteSolAdiantamento(@PathVariable("id") Long id) {
		solAdiantamentoService.deleteSolAdiantamento(id);

		return Utils.deleted(true, "Solicitação de adiantamento deletada com sucesso.");
	}

}
