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

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.requisito.RequisitoRequest;
import com.rhlinkcon.payload.requisito.RequisitoResponse;
import com.rhlinkcon.repository.RequisitoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.RequisitoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RequisitoController {

	@Autowired
	private RequisitoRepository requisitoRepository;

	@Autowired
	private RequisitoService requisitoService;

	@GetMapping("/requisitos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<RequisitoResponse> getHistoricosContabeis(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return requisitoService.getAllRequisitos(page, size, descricao, order);
	}
	
	@GetMapping("/requisito/{requisitoId}")
	public RequisitoResponse getRequisitoById(@PathVariable Long requisitoId) {
		return requisitoService.getRequisitoById(requisitoId);
	}

	@PostMapping("/requisito")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createRequisito(@Valid @RequestBody RequisitoRequest requisitoRequest) {
		if (requisitoRepository.existsByDescricao(requisitoRequest.getDescricao())) {
			return Utils.badRequest(false, "Requisito já existe!");
		}

		requisitoService.createRequisito(requisitoRequest);;

		return Utils.created(true, "Requisito criado com sucesso.");
	}

	@PutMapping("/requisito")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateRequisito(@Valid @RequestBody RequisitoRequest requisitoRequest) {
		if (requisitoRepository.existsByDescricaoAndIdNot(requisitoRequest.getDescricao(), requisitoRequest.getId())) {
			return Utils.badRequest(false, "Requisito já existe!");
		}

		requisitoService.updateRequisito(requisitoRequest);

		return Utils.created(true, "Requisito atualizado com sucesso.");
	}


	@DeleteMapping("/requisito/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteRequisito(@PathVariable("id") Long id) {
		requisitoService.deleteRequisito(id);
		
		return Utils.deleted(true, "Histórico Contábil deletado com sucesso.");
	}

}
