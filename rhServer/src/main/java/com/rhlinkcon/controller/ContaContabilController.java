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

import com.rhlinkcon.payload.contaContabil.ContaContabilRequest;
import com.rhlinkcon.payload.contaContabil.ContaContabilResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.ContaContabilService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ContaContabilController {

	@Autowired
	private ContaContabilService contaContabilService;

	@GetMapping("/contasContabeis")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ContaContabilResponse> getAllContasContabeis(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return contaContabilService.getAllContasContabeis(page, size, order, descricao);
	}
	
	@GetMapping("/contasContabeis/porTipo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ContaContabilResponse> getAllContasContabeisPorTipo(
			@RequestParam(value = "valor", defaultValue = AppConstants.DEFAULT_EMPTY) Integer valor,
			@RequestParam(value = "tipo", defaultValue = AppConstants.DEFAULT_EMPTY) String tipo) {
		return contaContabilService.getAllContasContabeisPorTipo(valor, tipo);
	}

	@GetMapping("/listaContasContabeis")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ContaContabilResponse> getAllContasContabeis() {
		return contaContabilService.getAllContasContabeis();
	}

	@GetMapping("/contaContabil/{contaContabilId}")
	public ContaContabilResponse getContaContabilById(@PathVariable Long contaContabilId) {
		return contaContabilService.getContaContabilById(contaContabilId);
	}
	
	@PostMapping("/contaContabil")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createContaContabil(@Valid @RequestBody ContaContabilRequest contaContabilRequest) {

		contaContabilService.createContaContabil(contaContabilRequest);

		return Utils.created(true, "Conta Contábil criada com sucesso.");
	}

	@PutMapping("/contaContabil")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateContaContabil(@Valid @RequestBody ContaContabilRequest contaContabilRequest) {

		contaContabilService.updateContaContabil(contaContabilRequest);
		
		return Utils.created(true, "Conta Contábil atualizada com sucesso.");
	}

	@DeleteMapping("/contaContabil/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteContaContabil(@PathVariable("id") Long id) {
		contaContabilService.deleteContaContabil(id);

		return Utils.deleted(true, "Conta Contábil deletada com sucesso.");
	}

}
