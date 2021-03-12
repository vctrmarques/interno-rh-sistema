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

import com.rhlinkcon.filtro.ArrecadacaoIndiceFiltro;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.ArrecadacaoIndiceAnoPeriodicidadeDto;
import com.rhlinkcon.service.ArrecadacaoIndiceDto;
import com.rhlinkcon.service.ArrecadacaoIndiceService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/arrecadacaoIndice")
public class ArrecadacaoIndiceController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ROLE_ARRECADACAO_INDICE_GESTAO')";
	private static final String AUTHORIZECADASTRAR = "hasAnyRole('ADMIN','ROLE_ARRECADACAO_INDICE_CADASTRAR')";
	private static final String AUTHORIZEATUALIZAR = "hasAnyRole('ADMIN','ROLE_ARRECADACAO_INDICE_ATUALIZAR')";
	private static final String AUTHORIZEEXCLUIR = "hasAnyRole('ADMIN','ROLE_ARRECADACAO_INDICE_EXCLUIR')";
	private static final String AUTHORIZEVISUALIZAR = "hasAnyRole('ADMIN','ROLE_ARRECADACAO_INDICE_VISUALIZAR')";
	
	@Autowired
	private ArrecadacaoIndiceService service;
	
	@GetMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<ArrecadacaoIndiceDto> getPagedList(PagedRequest pagedRequest, ArrecadacaoIndiceFiltro filtro) {
		return service.getPagedList(pagedRequest, filtro);
	}
	
	@PostMapping
	@PreAuthorize(AUTHORIZECADASTRAR)
	public ResponseEntity<?> create(@Valid @RequestBody ArrecadacaoIndiceDto dto) {
		return service.createIndice(dto);
	}
	
	@GetMapping("/ano/search")
	public List<ArrecadacaoIndiceAnoPeriodicidadeDto> getAnoList(@RequestParam(value = "search", required = true) String search) {
		return service.getAnoList(search);
	}	
	
	@GetMapping("/indice/search")
	public List<ArrecadacaoIndiceDto> getIndiceList(@RequestParam(value = "search", required = true) String indice) {
		return service.getIndiceList(indice);
	}	
	
	@GetMapping("/{id}")
	@PreAuthorize(AUTHORIZEVISUALIZAR)
	public ArrecadacaoIndiceDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@PutMapping
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public ResponseEntity<?> updateIndice(@Valid @RequestBody ArrecadacaoIndiceDto dto) {
		return service.updateIndice(dto);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(AUTHORIZEEXCLUIR)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Índice deletado com sucesso.");
	}
	
}
