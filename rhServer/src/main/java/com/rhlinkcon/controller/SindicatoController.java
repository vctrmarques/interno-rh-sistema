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

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.sindicato.SindicatoRequest;
import com.rhlinkcon.payload.sindicato.SindicatoResponse;
import com.rhlinkcon.repository.SindicatoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.SindicatoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class SindicatoController {

	@Autowired
	private SindicatoRepository sindicatoRepository;
	
	@Autowired
	private SindicatoService sindicatoService;

	@GetMapping("/sindicatos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<SindicatoResponse> getSindicatos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return sindicatoService.getAllSindicatos(page, size, descricao, order);
	}
	
	@GetMapping("/sindicato/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SindicatoResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return sindicatoService.searchAllByDescricao(search);
	}

	@GetMapping("/sindicato/{sindicatoId}")
	public SindicatoResponse getSindicatoById(@PathVariable Long sindicatoId) {
		return sindicatoService.getSindicatoById(sindicatoId);
	}

	@PostMapping("/sindicato")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createSindicato(@Valid @RequestBody SindicatoRequest sindicatoRequest) {
		if (sindicatoRepository.existsByDescricao(sindicatoRequest.getDescricao())) {
			return Utils.badRequest(false, "Já existe um Sindicato com esta descrição!");
		}

		sindicatoService.createSindicato(sindicatoRequest);

		return Utils.created(true, "Sindicato criado com sucesso.");
	}

	@PutMapping("/sindicato")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateSindicato(@Valid @RequestBody SindicatoRequest sindicatoRequest) {
		if (sindicatoRepository.existsByDescricaoAndIdNot(sindicatoRequest.getDescricao(), sindicatoRequest.getId())) {
			return Utils.badRequest(false, "Já existe um Sindicato com esta descrição!!");
		}

		sindicatoService.updateSindicato(sindicatoRequest);
		
		return Utils.created(true, "Sindicato atualizado com sucesso.");
	}

	@GetMapping("/listaSindicatos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SindicatoResponse> getAllSinticatos() {
		return sindicatoService.getAllSindicatos();
	}

	@DeleteMapping("/sindicato/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteSindicato(@PathVariable("id") Long id) {
		sindicatoService.deleteSindicato(id);
		
		return Utils.deleted(true, "Sindicato deletado com sucesso.");
	}

}
