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

import com.rhlinkcon.payload.entidadeExame.EntidadeExameRequest;
import com.rhlinkcon.payload.entidadeExame.EntidadeExameResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.EntidadeExameService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class EntidadeExameController {

	@Autowired
	private EntidadeExameService entidadeExameService;

	@GetMapping("/entidadeExames")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EntidadeExameResponse> getAllEntidadeExames(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "razaoSocial", defaultValue = AppConstants.DEFAULT_EMPTY) String razaoSocial) {
		return entidadeExameService.getAllEntidadeExames(page, size, order, razaoSocial);
	}

	@GetMapping("/listaEntidadeExames")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EntidadeExameResponse> getAllEntidadeExames() {
		return entidadeExameService.getAllEntidadeExames();
	}

	@GetMapping("/entidadeExame/{entidadeExameId}")
	public EntidadeExameResponse getEntidadeExameById(@PathVariable Long entidadeExameId) {
		return entidadeExameService.getEntidadeExameById(entidadeExameId);
	}

	@PostMapping("/entidadeExame")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createEntidadeExame(@Valid @RequestBody EntidadeExameRequest entidadeExameRequest) {

		entidadeExameService.createEntidadeExame(entidadeExameRequest);

		return Utils.created(true, "EntidadeExame criado com sucesso.");
	}

	@PutMapping("/entidadeExame")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateEntidadeExame(@Valid @RequestBody EntidadeExameRequest entidadeExameRequest) {

		entidadeExameService.updateEntidadeExame(entidadeExameRequest);

		return Utils.created(true, "EntidadeExame atualizado com sucesso.");
	}

	@DeleteMapping("/entidadeExame/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteEntidadeExame(@PathVariable("id") Long id) {
		entidadeExameService.deleteEntidadeExame(id);

		return Utils.deleted(true, "EntidadeExame deletado com sucesso.");
	}

}
