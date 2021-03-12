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
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.especialidadeMedica.EspecialidadeMedicaDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.EspecialidadeMedicaService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/especialidadeMedica")
public class EspecialidadeMedicaController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ESPECIALIDADE_MEDICA_GESTAO')";
	
	@Autowired
	private EspecialidadeMedicaService service;

	@PostMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<?> create(@Valid @RequestBody EspecialidadeMedicaDto request) {
		service.create(request);
		return Utils.created(true, "Especialidade Médica criado com sucesso.");
	}

	@PutMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<?> update(@Valid @RequestBody EspecialidadeMedicaDto request) {
		service.update(request);
		return Utils.created(true, "Especialidade Médica atualizado com sucesso.");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Especialidade Médica deletado com sucesso.");
	}

	@GetMapping("/{id}")
	@PreAuthorize(AUTHORIZEGESTAO)
	public EspecialidadeMedicaDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}

	@GetMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<EspecialidadeMedicaDto> getPagedList(PagedRequest pagedRequest, EspecialidadeMedicaDto requestFilter) {
		return service.getPagedList(pagedRequest, requestFilter);
	}

	@GetMapping("/search")
	public List<EspecialidadeMedicaDto> getList(EspecialidadeMedicaDto requestFilter) {
		return service.getList(requestFilter);
	}
	
	@GetMapping("/clinicoGeral")
	public EspecialidadeMedicaDto getEspecialidadeMedicaClinicoGeral() {
		return service.getEspecialidadeMedicaClinicoGeral();
	}
}
