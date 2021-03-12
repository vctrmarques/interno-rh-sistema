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

import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoRequest;
import com.rhlinkcon.payload.contaContabilSimples.ContaContabilSimplesRequest;
import com.rhlinkcon.payload.contaContabilSimples.ContaContabilSimplesResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ContaContabilSimplesRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ContaContabilSimplesService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ContaContabilSimplesController {

	@Autowired
	private ContaContabilSimplesRepository contaContabilSimplesRepository;

	@Autowired
	private ContaContabilSimplesService contaContabilSimplesService;

	@GetMapping("/contasContabeisSimples")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ContaContabilSimplesResponse> getAllContasContabeisSimples(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return contaContabilSimplesService.getAllContasContabeisSimples(page, size, descricao, order);
	}

	@GetMapping("/contaContabilSimples/{contaContabilId}")
	public ContaContabilSimplesResponse getClassifcacaoAgenteNocivoById(@PathVariable Long contaContabilId) {
		return contaContabilSimplesService.getContaContabilSimplesById(contaContabilId);
	}

	@PostMapping("/contaContabilSimples")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createContaContabilSimples(@Valid @RequestBody ContaContabilSimplesRequest contaContabilRequest) {
		contaContabilSimplesService.createContaContabilSimples(contaContabilRequest);
		return Utils.created(true, "Conta Contábil criada com sucesso.");
	}

	@PutMapping("/contaContabilSimples")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateContaContabilSimples(@Valid @RequestBody ContaContabilSimplesRequest contaContabilRequest) {
		if (contaContabilSimplesRepository.existsByDescricaoAndIdNot(contaContabilRequest.getDescricao(), contaContabilRequest.getId())) {
			return Utils.badRequest(false, "Esta Conta Contábil já existe!");
		}

		contaContabilSimplesService.updateContaContabilSimples(contaContabilRequest);
		
		return Utils.created(true, "Conta Contábil atualizada com sucesso.");
	}


	@DeleteMapping("/contaContabilSimples/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteContaContabilSimple(@PathVariable("id") Long id) {
		contaContabilSimplesService.deleteContaContabilSimples(id);
		
		return Utils.deleted(true, "Conta Contábil Simples deletada com sucesso.");
	}

}
