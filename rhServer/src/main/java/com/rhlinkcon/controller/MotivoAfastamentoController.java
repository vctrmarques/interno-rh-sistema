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
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoRequest;
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoResponse;
import com.rhlinkcon.repository.MotivoAfastamentoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.MotivoAfastamentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class MotivoAfastamentoController {

	@Autowired
	private MotivoAfastamentoRepository motivoAfastamentoRepository;

	@Autowired
	private MotivoAfastamentoService motivoAfastamentoService;

	@GetMapping("/motivosAfastamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<MotivoAfastamentoResponse> getAllMotivosAfastamentos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return motivoAfastamentoService.getAllMotivosAfastamentos(page, size, descricao, order);
	}
	
	@GetMapping("/listaMotivosAfastamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<MotivoAfastamentoResponse> getAllMotivosAfastamentos() {
		return motivoAfastamentoService.getAllMotivosAfastamentos();
	}
	
	@GetMapping("/motivoAfastamento/{motivoAfastamentoId}")
	public MotivoAfastamentoResponse getMotivoAfastamentoById(@PathVariable Long motivoAfastamentoId) {
		return motivoAfastamentoService.getMotivoAfastamentoById(motivoAfastamentoId);
	}

	@PostMapping("/motivoAfastamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createMotivoAfastamento(@Valid @RequestBody MotivoAfastamentoRequest motivoAfastamentoRequest) {
		if (motivoAfastamentoRepository.existsByDescricao(motivoAfastamentoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Motivo de Afastamento já existe!");
		}

		motivoAfastamentoService.createMotivoAfastamento(motivoAfastamentoRequest);

		return Utils.created(true, "Motivo de Afastamento criado com sucesso.");
	}

	@PutMapping("/motivoAfastamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateMotivoAfastamento(@Valid @RequestBody MotivoAfastamentoRequest motivoAfastamentoRequest) {
		if (motivoAfastamentoRepository.existsByDescricaoAndIdNot(motivoAfastamentoRequest.getDescricao(), motivoAfastamentoRequest.getId())) {
			return Utils.badRequest(false, "Este Motivo de Afastamento já existe!");
		}

		motivoAfastamentoService.updateMotivoAfastamento(motivoAfastamentoRequest);
		
		return Utils.created(true, "Motivo de Afastamento atualizado com sucesso.");
	}


	@DeleteMapping("/motivoAfastamento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteMotivoAfastamento(@PathVariable("id") Long id) {
		motivoAfastamentoService.deleteMotivoAfastamento(id);
		
		return Utils.deleted(true, "Motivo de Afastamento deletado com sucesso.");
	}

}
