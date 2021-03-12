package com.rhlinkcon.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import com.rhlinkcon.model.SituacaoProcessoEnum;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.EnumDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.processo.ProcessoRequest;
import com.rhlinkcon.payload.processo.ProcessoResponse;
import com.rhlinkcon.service.EnumService;
import com.rhlinkcon.service.ProcessoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ProcessoController {
	@Autowired
	private ProcessoService processoService;
	@Autowired
	private EnumService enumService;

	@GetMapping("/processos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ProcessoResponse> getAllProcessos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nomeFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeFuncionario) {
		return processoService.getAllProcessos(page, size, order, nomeFuncionario);
	}

	@GetMapping("/processo/funcionarios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncionarioResponse> getFuncionarioComProcessos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nomeFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeFuncionario) {
		return processoService.getUsuarioComProcessos(page, size, order, nomeFuncionario);
	}

	@GetMapping("/processo/{processoId}")
	public ProcessoResponse getProcessoById(@PathVariable Long processoId) {
		return processoService.getProcessoById(processoId);
	}

	@PostMapping("/processo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createProcesso(@Valid @RequestBody ProcessoRequest processoRequest) {
		processoService.createProcesso(processoRequest);
		return Utils.created(true, "Processo criado com sucesso.");
	}

	@PutMapping("/processo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateProcesso(@Valid @RequestBody ProcessoRequest processoRequest) {
		processoService.createProcesso(processoRequest);
		return Utils.created(true, "Processo atualizado com sucesso.");
	}

	@DeleteMapping("/processo/remover-anexo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteAnexo(@PathVariable("id") Long id) {
		processoService.deleteAnexo(id);
		return Utils.deleted(true, "Anexo deletadoo com sucesso.");
	}

	@DeleteMapping("/processo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteProcesso(@PathVariable("id") Long id) {
		processoService.deleteProcesso(id);
		return Utils.deleted(true, "Processo deletado com sucesso.");
	}
}
