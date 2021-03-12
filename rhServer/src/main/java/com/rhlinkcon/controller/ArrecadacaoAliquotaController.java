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

import com.rhlinkcon.filtro.ArrecadacaoAliquotaFiltro;
import com.rhlinkcon.payload.arrecadacaoAliquota.ArrecadacaoAliquotaDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.ArrecadacaoAliquotaService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/arrecadacaoAliquota")
public class ArrecadacaoAliquotaController {
	
	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ARRECADACAO_ALIQUOTA_GESTAO')";
	
	@Autowired
	private ArrecadacaoAliquotaService service;

	@PostMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<?> create(@Valid @RequestBody ArrecadacaoAliquotaDto dto) {
		service.create(dto);
		return Utils.created(true, "Arrecadação Aliquota criado com sucesso.");
	}

	@PutMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<?> update(@Valid @RequestBody ArrecadacaoAliquotaDto dto) {
		service.update(dto);
		return Utils.created(true, "Arrecadação Aliquota atualizado com sucesso.");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(AUTHORIZEGESTAO)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Arrecadação Aliquota deletado com sucesso.");
	}

	@GetMapping("/{id}")
	@PreAuthorize(AUTHORIZEGESTAO)
	public ArrecadacaoAliquotaDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}

	@GetMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<ArrecadacaoAliquotaDto> getPagedList(PagedRequest pagedRequest, ArrecadacaoAliquotaFiltro requestFilter) {
		return service.getPagedList(pagedRequest, requestFilter);
	}

	@GetMapping("/search")
	@PreAuthorize(AUTHORIZEGESTAO)
	public List<ArrecadacaoAliquotaDto> getList(ArrecadacaoAliquotaFiltro requestFilter) {
		return service.getList(requestFilter);
	}
	
}
