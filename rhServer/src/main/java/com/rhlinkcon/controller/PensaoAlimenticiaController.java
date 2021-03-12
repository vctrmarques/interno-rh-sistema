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

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.pensaoAlimenticia.PensaoAlimenticiaDto;
import com.rhlinkcon.payload.pensaoAlimenticia.PensaoAlimenticiaFuncionarioDto;
import com.rhlinkcon.payload.pensaoAlimenticia.PensaoAlimenticiaResponsavelDto;
import com.rhlinkcon.service.PensaoAlimenticiaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/pensaoAlimenticia")
public class PensaoAlimenticiaController {

	@Autowired
	private PensaoAlimenticiaService service;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','PENSAO_ALIMENTICIA_CADASTRAR')")
	public ResponseEntity<?> create(@Valid @RequestBody PensaoAlimenticiaDto request) {
		service.create(request);
		return Utils.created(true, "Pensão Alimentícia inserida com sucesso.");
	}

	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN','PENSAO_ALIMENTICIA_ATUALIZAR')")
	public ResponseEntity<?> update(@Valid @RequestBody PensaoAlimenticiaDto request) {
		service.update(request);
		return Utils.created(true, "Pensão Alimentícia atualizada com sucesso.");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','PENSAO_ALIMENTICIA_EXCLUIR')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);

		return Utils.deleted(true, "Pensão Alimentícia excluída com sucesso.");
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','PENSAO_ALIMENTICIA_VISUALIZAR','PENSAO_ALIMENTICIA_CADASTRAR','PENSAO_ALIMENTICIA_ATUALIZAR','PENSAO_ALIMENTICIA_EXCLUIR')")
	public PensaoAlimenticiaDto getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','PENSAO_ALIMENTICIA_VISUALIZAR','PENSAO_ALIMENTICIA_CADASTRAR','PENSAO_ALIMENTICIA_ATUALIZAR','PENSAO_ALIMENTICIA_EXCLUIR')")
	public PagedResponse<PensaoAlimenticiaDto> getPagedList(PagedRequest pagedRequest,
			PensaoAlimenticiaDto requestFilter) {
		return service.getPagedList(pagedRequest, requestFilter);
	}

	@GetMapping("/search")
	public List<DadoBasicoDto> getDadoBasicoList(PensaoAlimenticiaDto requestFilter) {
		return service.getDadoBasicoList(requestFilter);
	}

	@GetMapping("/funcionario/search")
	public List<PensaoAlimenticiaFuncionarioDto> funcionarioSearch(@RequestParam(value = "search") String search) {
		return service.funcionarioSearch(search);
	}

	@GetMapping("/responsavel/search")
	public List<PensaoAlimenticiaResponsavelDto> responsavelSearch(@RequestParam(value = "search") String search) {
		return service.responsavelSearch(search);
	}

	@GetMapping("/funcionarios")
	public PagedResponse<FuncionarioResponse> getFuncionariosPensoes(PagedRequest pagedRequest,
			@RequestParam(value = "nomeFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeFuncionario) {
		return service.getFuncionariosPensoes(pagedRequest, nomeFuncionario);
	}

}
