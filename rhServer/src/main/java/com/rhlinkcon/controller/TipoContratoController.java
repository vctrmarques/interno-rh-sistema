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
import com.rhlinkcon.payload.tipoContrato.TipoContratoRequest;
import com.rhlinkcon.payload.tipoContrato.TipoContratoResponse;
import com.rhlinkcon.repository.TipoContratoRepository;
import com.rhlinkcon.service.TipoContratoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TipoContratoController {

	@Autowired
	private TipoContratoService tipoContratoService;

	@Autowired
	private TipoContratoRepository tipoContratoRepository;

	@GetMapping("/tipoContratos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TipoContratoResponse> getAllTipoContrato(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return tipoContratoService.getAllTipoContrato(page, size, order, nome);
	}

	@GetMapping("/tipoContrato/{id}")
	public TipoContratoResponse getCboById(@PathVariable Long id) {
		return tipoContratoService.getTipoContratoById(id);
	}

	@GetMapping("/tipoContrato/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TipoContratoResponse> searchAllByNome(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return tipoContratoService.searchAllByNome(search);
	}

	@PostMapping("/tipoContrato")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTipoContrato(@Valid @RequestBody TipoContratoRequest tipoContratoRequest) {
		if (tipoContratoRepository.existsByNome(tipoContratoRequest.getNome())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		tipoContratoService.createTipoContrato(tipoContratoRequest);

		return Utils.created(true, "Tipo de Contrato criado com sucesso.");
	}

	@PutMapping("/tipoContrato")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTipoContrato(@Valid @RequestBody TipoContratoRequest tipoContratoRequest) {
		if (tipoContratoRepository.existsByNomeAndIdNot(tipoContratoRequest.getNome(), tipoContratoRequest.getId())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		tipoContratoService.updateTipoContrato(tipoContratoRequest);

		return Utils.created(true, "Tipo de Contrato atualizado com sucesso.");
	}

	@DeleteMapping("/tipoContrato/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTipoContrato(@PathVariable("id") Long id) {
		tipoContratoService.deleteTipoContrato(id);

		return Utils.deleted(true, "Tipo de Contrato deletado com sucesso.");
	}
}
