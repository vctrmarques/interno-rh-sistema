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

import com.rhlinkcon.payload.centroCusto.CentroCustoRequest;
import com.rhlinkcon.payload.correcao.CorrecaoRequest;
import com.rhlinkcon.payload.correcao.CorrecaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CorrecaoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CorrecaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CorrecaoController {

	@Autowired
	private CorrecaoRepository correcaoRepository;

	@Autowired
	private CorrecaoService correcaoService;

	@GetMapping("/correcoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CorrecaoResponse> getAllCorrecoes(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nomeFilial", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeFilial) {
		return correcaoService.getAllCorrecoes(page, size, nomeFilial, order);
	}

	@GetMapping("/correcao/{correcaoId}")
	public CorrecaoResponse getCorrecaoById(@PathVariable Long correcaoId) {
		return correcaoService.getCorrecaoById(correcaoId);
	}
	
	@PostMapping("/correcao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCorrecao(@Valid @RequestBody CorrecaoRequest correcaoRequest) {
		
		correcaoService.createCorrecao(correcaoRequest);

		return Utils.created(true, "Correção criada com sucesso.");
	}

	@PutMapping("/correcao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCorrecao(@Valid @RequestBody CorrecaoRequest correcaoRequest) {

		correcaoService.updateCorrecao(correcaoRequest);
		
		return Utils.created(true, "Correção atualizada com sucesso.");
	}

	@DeleteMapping("/correcao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCorrecao(@PathVariable("id") Long id) {
		correcaoService.deleteCorrecao(id);
		
		return Utils.deleted(true, "Correção deletada com sucesso.");
	}

}
