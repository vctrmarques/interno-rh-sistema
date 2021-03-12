package com.rhlinkcon.controller;

import java.util.ArrayList;
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

import com.rhlinkcon.model.RecisaoContratoEnum;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.recisaoContrato.RecisaoContratoRequest;
import com.rhlinkcon.payload.recisaoContrato.RecisaoContratoResponse;
import com.rhlinkcon.service.RecisaoContratoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RecisaoContratoController {
	@Autowired private RecisaoContratoService recisaoContratoService;
	
	@GetMapping("/listRecisoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<RecisaoContratoResponse> getAll() {
		return recisaoContratoService.getAll();
	}
	
	@GetMapping("/recisaoContrato/{recisaoContratoId}")
	public RecisaoContratoResponse getRecisaoContratoById(@PathVariable Long recisaoContratoId) {
		return recisaoContratoService.getRecisaoContratoById(recisaoContratoId);
	}
	
	@GetMapping("/recisoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<RecisaoContratoResponse> getAllRecisoesContrato(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "matricula", defaultValue = AppConstants.DEFAULT_EMPTY) String matricula,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "status", defaultValue = AppConstants.DEFAULT_EMPTY) String status) {
		return recisaoContratoService.getAllRecisaoContrato(page, size, order, matricula, nome, status);
	}
	
	@PostMapping("/recisaoContrato")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createRecisaoContrato(@Valid @RequestBody RecisaoContratoRequest recisaoContratoRequest) {
		recisaoContratoService.createRecisaoContrato(recisaoContratoRequest);

		return Utils.created(true, "Recisão criada com sucesso.");
	}
	
	@PutMapping("/recisaoContrato")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateRecisaoContrato(@Valid @RequestBody RecisaoContratoRequest recisaoContratoRequest) {
		recisaoContratoService.updateRecisaoContrato(recisaoContratoRequest);

		return Utils.created(true, "Recisão atualizada com sucesso.");
	}
	
	@PutMapping("/cancelarRecisao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> cancelarRecisaoContrato(@Valid @RequestBody RecisaoContratoRequest recisaoContratoRequest) {
		recisaoContratoService.cancelarRecisaoContrato(recisaoContratoRequest);
		
		return Utils.created(true, "Recisão cancelada com sucesso");
	}
	
	@PutMapping("/efetivarRecisao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> efetivarRecisaoContrato(@Valid @RequestBody RecisaoContratoRequest recisaoContratoRequest) {
		recisaoContratoService.efetivarRecisaoContrato(recisaoContratoRequest);
		
		return Utils.created(true, "Recisão efetivado com sucesso");
	}
	
	@DeleteMapping("/recisaoContrato/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteRecisaoContrato(@PathVariable("id") Long id) {
		recisaoContratoService.deleteRecisaoContrato(id);

		return Utils.deleted(true, "Recisão deletada com sucesso.");
	}
	
	@GetMapping("/listStatus")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<String> getAllRecisaoContratoEnum() {
		List<String> list = new ArrayList<>();
		for (RecisaoContratoEnum recisaoContratoEnum : RecisaoContratoEnum.values()) {
			list.add(recisaoContratoEnum.getLabel());
		}
		
		return list;
	}
}
