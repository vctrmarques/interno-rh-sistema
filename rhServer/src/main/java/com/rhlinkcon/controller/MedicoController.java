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

import com.rhlinkcon.filtro.MedicoFiltro;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.especialidadeMedica.EspecialidadeMedicaDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.medico.MedicoDto;
import com.rhlinkcon.payload.medico.MedicoRequest;
import com.rhlinkcon.payload.medico.MedicoResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.MedicoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class MedicoController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','MEDICO_GESTAO')";
	
	@Autowired
	private MedicoService service;

	
	@GetMapping("/medicos")
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<MedicoResponse> getAllMedicos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			MedicoFiltro filtro) {
		return service.getAllMedicos(page, size, filtro, order);
	}
	
	@PostMapping("/medico")
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<?> create(@Valid @RequestBody MedicoRequest request) {
		return service.create(request);
	}
	
	@GetMapping("/medico/{medicoId}")
	public MedicoResponse getMedicoById(@PathVariable Long medicoId) {
		return service.getMedicoById(medicoId);
	}
	
	@PutMapping("/medico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateMedico(@Valid @RequestBody MedicoRequest request) {
		return service.updateMedico(request);
	}

	@DeleteMapping("/medico/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteMedico(@PathVariable("id") Long id) {
		service.deleteMedico(id);
		return Utils.deleted(true, "Médico deletado com sucesso.");
	}
	
	@GetMapping("/medico/search")
	public List<DadoBasicoDto> getList(@RequestParam(value = "search", required = true) String search) {
		return service.getDadoBasicoList(search);
	}
	
	@GetMapping("/medicoEspecialiade/search")
	public List<EspecialidadeMedicaDto> getMedicoNome(@RequestParam(value = "nome", required = true) String nome) {
		return service.getMedicoNome(nome);
	}


	
}
