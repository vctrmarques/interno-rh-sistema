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
import com.rhlinkcon.payload.motivoDesligamento.MotivoDesligamentoRequest;
import com.rhlinkcon.payload.motivoDesligamento.MotivoDesligamentoResponse;
import com.rhlinkcon.repository.MotivoDesligamentoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.MotivoDesligamentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class MotivoDesligamentoController {

	@Autowired
	private MotivoDesligamentoRepository motivoDesligamentoRepository;

	@Autowired
	private MotivoDesligamentoService motivoDesligamentoService;

	@GetMapping("/listaMotivosDesligamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<MotivoDesligamentoResponse> getAllMotivosDesligamento() {
		return motivoDesligamentoService.getAllMotivosDesligamento();
	}

	@GetMapping("/motivosDesligamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<MotivoDesligamentoResponse> getAllMotivosDesligamento(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return motivoDesligamentoService.getAllMotivosDesligamento(page, size, descricao, order);
	}

	@GetMapping("/motivoDesligamento/{motivoDesligamentoId}")
	public MotivoDesligamentoResponse getMotivoDesligamentoById(@PathVariable Long motivoDesligamentoId) {
		return motivoDesligamentoService.getMotivoDesligamentoById(motivoDesligamentoId);
	}

	@PostMapping("/motivoDesligamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createMotivoDesligamento(@Valid @RequestBody MotivoDesligamentoRequest motivoDesligamentoRequest) {
		if (motivoDesligamentoRepository.existsByDescricao(motivoDesligamentoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Motivo de Desligamento já existe!");
		}

		motivoDesligamentoService.createMotivoDesligamento(motivoDesligamentoRequest);

		return Utils.created(true, "Motivo de Desligamento criado com sucesso.");
	}

	@PutMapping("/motivoDesligamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateMotivoDesligamento(@Valid @RequestBody MotivoDesligamentoRequest motivoDesligamentoRequest) {
		if (motivoDesligamentoRepository.existsByDescricaoAndIdNot(motivoDesligamentoRequest.getDescricao(), motivoDesligamentoRequest.getId())) {
			return Utils.badRequest(false, "Este Motivo de Desligamento já existe!");
		}

		motivoDesligamentoService.updateMotivoDesligamento(motivoDesligamentoRequest);

		return Utils.created(true, "Motivo de Desligamento atualizado com sucesso.");
	}

	@DeleteMapping("/motivoDesligamento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteMotivoDesligamento(@PathVariable("id") Long id) {
		motivoDesligamentoService.deleteMotivoDesligamento(id);

		return Utils.deleted(true, "Classificação de Motivo de Desligamento deletada com sucesso.");
	}

}
