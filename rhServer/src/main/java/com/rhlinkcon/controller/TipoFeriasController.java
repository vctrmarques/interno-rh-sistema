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
import com.rhlinkcon.payload.tipoFerias.TipoFeriasRequest;
import com.rhlinkcon.payload.tipoFerias.TipoFeriasResponse;
import com.rhlinkcon.repository.TipoFeriasRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TipoFeriasService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TipoFeriasController {

	@Autowired
	private TipoFeriasRepository tipoFeriasRepository;

	@Autowired
	private TipoFeriasService tipoFeriasService;

	@GetMapping("/tiposFerias")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TipoFeriasResponse> getAllTiposFerias(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return tipoFeriasService.getAllTiposFerias(page, size, descricao, order);
	}

	@GetMapping("/tipoFerias/{tipoFeriasId}")
	public TipoFeriasResponse getTipoFeriasById(@PathVariable Long tipoFeriasId) {
		return tipoFeriasService.getTipoFeriasById(tipoFeriasId);
	}

	@PostMapping("/tipoFerias")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTipoFerias(@Valid @RequestBody TipoFeriasRequest tipoFeriasRequest) {
		if (tipoFeriasRepository.existsByDescricao(tipoFeriasRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Tipo de Férias já existe!");
		}

		tipoFeriasService.createTipoFerias(tipoFeriasRequest);

		return Utils.created(true, "Tipo de Férias criada com sucesso.");
	}

	@PutMapping("/tipoFerias")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTipoFerias(@Valid @RequestBody TipoFeriasRequest tipoFeriasRequest) {
		if (tipoFeriasRepository.existsByDescricaoAndIdNot(tipoFeriasRequest.getDescricao(), tipoFeriasRequest.getId())) {
			return Utils.badRequest(false, "Este Tipo de Férias já existe!");
		}

		tipoFeriasService.updateTipoFerias(tipoFeriasRequest);

		return Utils.created(true, "Tipo de Férias atualizada com sucesso.");
	}


	@DeleteMapping("/tipoFerias/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTipoFerias(@PathVariable("id") Long id) {
		tipoFeriasService.deleteTipoFerias(id);

		return Utils.deleted(true, "Tipo de Férias deletada com sucesso.");
	}

}
