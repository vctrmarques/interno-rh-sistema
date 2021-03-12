package com.rhlinkcon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.rhlinkcon.payload.treinamentoSugerido.TreinamentoSugeridoRequest;
import com.rhlinkcon.payload.treinamentoSugerido.TreinamentoSugeridoResponse;
import com.rhlinkcon.payload.treinamentoSugeridoComplemento.TreinamentoSugeridoComplementoRequest;
import com.rhlinkcon.payload.treinamentoSugeridoComplemento.TreinamentoSugeridoComplementoResponse;
import com.rhlinkcon.payload.treinamentoSugeridoValores.TreinamentoSugeridoValoresRequest;
import com.rhlinkcon.payload.treinamentoSugeridoValores.TreinamentoSugeridoValoresResponse;
import com.rhlinkcon.service.TreinamentoSugeridoComplementoService;
import com.rhlinkcon.service.TreinamentoSugeridoService;
import com.rhlinkcon.service.TreinamentoSugeridoValoresService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TreinamentoSugeridoController {
	
	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";

	@Autowired
	private TreinamentoSugeridoService treinamentoSugeridoService;
	
	@Autowired
	private TreinamentoSugeridoComplementoService treinamentoSugeridoComplementoService;
	
	@Autowired
	private TreinamentoSugeridoValoresService treinamentoSugeridoValoresService;

	@GetMapping("/treinamentoSugeridos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Page<TreinamentoSugeridoResponse> all(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String orderBy,
			@RequestParam(value = "nomeFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeFuncionario) {
		return treinamentoSugeridoService.getAll(page, size, orderBy, nomeFuncionario);
	}
	
	@GetMapping("/treinamentoSugerido/{id}")
	@PreAuthorize(AUTHORIZE)
	public TreinamentoSugeridoResponse getTreinamento(@PathVariable Long id) {
		return treinamentoSugeridoService.getTreinamento(id);
	}
	
	@GetMapping("/treinamentoSugeridoComplemento/{id}")
	@PreAuthorize(AUTHORIZE)
	public TreinamentoSugeridoComplementoResponse getComplemento(@PathVariable Long id) {
		return treinamentoSugeridoService.getComplemento(id);
	}
	
	@GetMapping("/treinamentoSugeridoValores/{id}")
	@PreAuthorize(AUTHORIZE)
	public TreinamentoSugeridoValoresResponse getValores(@PathVariable Long id) {
		return treinamentoSugeridoService.getValores(id);
	}

	@PostMapping("/treinamentoSugerido")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody TreinamentoSugeridoRequest request) {
		treinamentoSugeridoService.create(request);
		return Utils.created(true, "Treinamento Sugerido criado com sucesso.");
	}

	@PutMapping("/treinamentoSugerido")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody TreinamentoSugeridoRequest request) {
		treinamentoSugeridoService.create(request);	
		return Utils.created(true, "Treinamento Sugerido atualizado com sucesso.");
	}
	
	@PostMapping("/treinamentoSugeridoComplemento")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> createComplemento(@Valid @RequestBody TreinamentoSugeridoComplementoRequest request) {
		treinamentoSugeridoComplementoService.create(request);
		return Utils.created(true, "Treinamento Sugerido atualizado com sucesso.");
	}

	@PutMapping("/treinamentoSugeridoComplemento")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> updateComplemento(@Valid @RequestBody TreinamentoSugeridoComplementoRequest request) {
		treinamentoSugeridoComplementoService.create(request);	
		return Utils.created(true, "Treinamento Sugerido atualizado com sucesso.");
	}
	
	@PostMapping("/treinamentoSugeridoValores")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> createValores(@Valid @RequestBody TreinamentoSugeridoValoresRequest request) {
		treinamentoSugeridoValoresService.create(request);
		return Utils.created(true, "Treinamento Sugerido atualizado com sucesso.");
	}

	@PutMapping("/treinamentoSugeridoValores")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> updateValores(@Valid @RequestBody TreinamentoSugeridoValoresRequest request) {
		treinamentoSugeridoValoresService.create(request);	
		return Utils.created(true, "Treinamento Sugerido atualizado com sucesso.");
	}
	
		
	@DeleteMapping("/treinamentoSugerido/{treinamentoSuregiroId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> detele(@PathVariable Long treinamentoSuregiroId) {
		treinamentoSugeridoService.delete(treinamentoSuregiroId);
		return Utils.deleted(true, "Treinamento Sugerido removido com sucesso.");
	}

}
