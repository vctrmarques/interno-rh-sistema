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

import com.rhlinkcon.payload.esocial.EsocialRequest;
import com.rhlinkcon.payload.esocial.EsocialResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EsocialRepository;
import com.rhlinkcon.service.EsocialService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class EsocialController {
	
	@Autowired
	private EsocialService esocialService;
	
	@Autowired
	private EsocialRepository esocialRepository;
	
	@GetMapping("/esocials")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EsocialResponse> getAllEsocial(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return esocialService.getAllEsocial(page, size, order, descricao);
	}

	@GetMapping("/esocial/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EsocialResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return esocialService.searchAllByDescricao(search);
	}
	
	@GetMapping("/esocial/{id}")
	public EsocialResponse getById(@PathVariable Long id) {
		return esocialService.getById(id);
	}

	@PostMapping("/esocial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody EsocialRequest esocialRequest) {
		if (esocialRepository.existsByDescricao(esocialRequest.getDescricao())) {
			return Utils.badRequest(false, "A descrição já está em uso!");
		}

		esocialService.create(esocialRequest);

		return Utils.created(true, "Esocial criado com sucesso.");
	}

	@PutMapping("/esocial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> update(@Valid @RequestBody EsocialRequest esocialRequest) {
		if (esocialRepository.existsByDescricaoAndIdNot(esocialRequest.getDescricao(), esocialRequest.getId())) {
			return Utils.badRequest(false, "A descrição já está em uso!");
		}

		esocialService.update(esocialRequest);

		return Utils.created(true, "Esocial atualizado com sucesso.");
	}

	@DeleteMapping("/esocial/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		esocialService.delete(id);

		return Utils.deleted(true, "Esocial deletado com sucesso.");
	}

}
