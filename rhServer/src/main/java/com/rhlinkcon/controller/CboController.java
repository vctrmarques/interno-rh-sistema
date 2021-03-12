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

import com.rhlinkcon.payload.cbo.CboDto;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.CboService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/cbo")
public class CboController {

	@Autowired
	private CboService service;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','CBOS_CADASTRAR')")
	public ResponseEntity<?> create(@Valid @RequestBody CboDto request) {
		service.create(request);
		return Utils.created(true, "CBO criado com sucesso.");
	}

	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN','CBOS_ATUALIZAR')")
	public ResponseEntity<?> update(@Valid @RequestBody CboDto request) {
		service.update(request);
		return Utils.created(true, "CBO atualizado com sucesso.");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CBOS_EXCLUIR')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "CBO deletado com sucesso.");
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CBOS_VISUALIZAR','CBOS_CADASTRAR','CBOS_ATUALIZAR','CBOS_EXCLUIR')")
	public CboDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','CBOS_VISUALIZAR','CBOS_CADASTRAR','CBOS_ATUALIZAR','CBOS_EXCLUIR')")
	public PagedResponse<CboDto> getPagedList(PagedRequest pagedRequest, CboDto requestFilter) {
		return service.getPagedList(pagedRequest, requestFilter);
	}

	@GetMapping("/search")
	public List<CboDto> getList(CboDto requestFilter) {
		return service.getList(requestFilter);
	}

}
