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

import com.rhlinkcon.payload.BasicRequest;
import com.rhlinkcon.payload.declaracaoAposentadoria.DeclaracaoAposentadoriaRequest;
import com.rhlinkcon.payload.declaracaoAposentadoria.DeclaracaoAposentadoriaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.DeclaracaoAposentadoriaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class DeclaracaoAposentadoriaController {

	@Autowired
	private DeclaracaoAposentadoriaService service;

	@GetMapping("/declaracoesAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN','DECLARACAO_PARA_APOSENTADOS_VISUALIZAR','DECLARACAO_PARA_APOSENTADOS_CADASTRAR','DECLARACAO_PARA_APOSENTADOS_ATUALIZAR','DECLARACAO_PARA_APOSENTADOS_EXCLUIR')")
	public PagedResponse<DeclaracaoAposentadoriaResponse> getAll(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao,
			@RequestParam(value = "descricaoNumero", defaultValue = AppConstants.DEFAULT_EMPTY) String numero,
			@RequestParam(value = "descricaoAno", defaultValue = AppConstants.DEFAULT_EMPTY) String ano) {
		return service.getAll(page, size, descricao, numero, ano, order);
	}

	@GetMapping("/declaracaoAposentadoria/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','DECLARACAO_PARA_APOSENTADOS_VISUALIZAR','DECLARACAO_PARA_APOSENTADOS_CADASTRAR','DECLARACAO_PARA_APOSENTADOS_ATUALIZAR','DECLARACAO_PARA_APOSENTADOS_EXCLUIR')")
	public DeclaracaoAposentadoriaResponse getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@PostMapping("/declaracaoAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN','DECLARACAO_PARA_APOSENTADOS_CADASTRAR')")
	public ResponseEntity<?> create(@Valid @RequestBody DeclaracaoAposentadoriaRequest request) {
		DeclaracaoAposentadoriaResponse objResponse = service.create(request);
		return Utils.created(true, "Declaração criada com sucesso.", objResponse);
	}

	@PutMapping("/declaracaoAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN','DECLARACAO_PARA_APOSENTADOS_ATUALIZAR')")
	public ResponseEntity<?> update(@Valid @RequestBody DeclaracaoAposentadoriaRequest request) {
		DeclaracaoAposentadoriaResponse objResponse = service.update(request);
		return Utils.created(true, "Declaração atualizada com sucesso.", objResponse);
	}

	@DeleteMapping("/declaracaoAposentadoria/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','DECLARACAO_PARA_APOSENTADOS_EXCLUIR')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Declaração deletada com sucesso.");
	}

	@PostMapping("/declaracaoAposentadoria/retificar")
	@PreAuthorize("hasAnyRole('ADMIN','DECLARACAO_PARA_APOSENTADOS_ATUALIZAR','DECLARACAO_PARA_APOSENTADOS_CADASTRAR')")
	public ResponseEntity<?> retificar(@Valid @RequestBody BasicRequest basicRequest) {
		DeclaracaoAposentadoriaResponse objResponse = service.retificar(basicRequest.getId());
		return Utils.created(true, "Declaração retificada com sucesso.", objResponse);
	}

	@DeleteMapping("/declaracaoAposentadoria/remover-anexo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','DECLARACAO_PARA_APOSENTADOS_EXCLUIR')")
	public ResponseEntity<?> deleteAnexo(@PathVariable("id") Long id) {
		service.deleteAnexo(id);
		return Utils.deleted(true, "Declaração deletada com sucesso.");
	}

}
