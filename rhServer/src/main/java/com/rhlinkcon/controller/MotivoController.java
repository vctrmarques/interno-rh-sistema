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
import com.rhlinkcon.payload.motivo.MotivoRequest;
import com.rhlinkcon.payload.motivo.MotivoResponse;
import com.rhlinkcon.repository.MotivoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.MotivoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class MotivoController {

	@Autowired
	private MotivoRepository motivoRepository;

	@Autowired
	private MotivoService motivoService;

	@GetMapping("/motivos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<MotivoResponse> getMotivos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return motivoService.getAllMotivos(page, size, descricao, order);
	}
	
	@GetMapping("/motivo/{motivoId}")
	public MotivoResponse getMotivoById(@PathVariable Long motivoId) {
		return motivoService.getMotivoById(motivoId);
	}

	@PostMapping("/motivo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createMotivo(@Valid @RequestBody MotivoRequest motivoRequest) {
		if (motivoRepository.existsByDescricao(motivoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Motivo já existe!");
		}

		motivoService.createMotivo(motivoRequest);

		return Utils.created(true, "Motivo criado com sucesso.");
	}

	@PutMapping("/motivo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateMotivo(@Valid @RequestBody MotivoRequest motivoRequest) {
		if (motivoRepository.existsByDescricaoAndIdNot(motivoRequest.getDescricao(), motivoRequest.getId())) {
			return Utils.badRequest(false, "Este Motivo já existe!");
		}

		motivoService.updateMotivo(motivoRequest);
		
		return Utils.created(true, "Motivo atualizado com sucesso.");
	}


	@DeleteMapping("/motivo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteMotivo(@PathVariable("id") Long id) {
		motivoService.deleteMotivo(id);
		
		return Utils.deleted(true, "Motivo deletado com sucesso.");
	}
	
	@GetMapping("/listaMotivos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<MotivoResponse> getAllMotivos() {
		return motivoService.getAllMotivos();
	}
	

}
