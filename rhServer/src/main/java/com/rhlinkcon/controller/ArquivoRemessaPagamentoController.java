package com.rhlinkcon.controller;

import java.io.IOException;
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

import com.rhlinkcon.filtro.ArquivoRemessaPagamentoFiltro;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaHistoricoSituacaoResponse;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaPagamentoRequest;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaPagamentoResponse;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaRelatorioResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.ArquivoRemessaHistoricoSituacaoService;
import com.rhlinkcon.service.ArquivoRemessaPagamentoService;
import com.rhlinkcon.service.FolhaPagamentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ArquivoRemessaPagamentoController {
	
	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private ArquivoRemessaPagamentoService service;
	
	@Autowired
	private FolhaPagamentoService folhaService;
	
	@Autowired
	private ArquivoRemessaHistoricoSituacaoService historicoService;
	
	@GetMapping("/arquivosRemessaPagamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ArquivoRemessaPagamentoResponse> getAll(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			ArquivoRemessaPagamentoFiltro arquivoRemessaFiltro) {
		return service.getAll(page, size, order, arquivoRemessaFiltro);
	}
	
	@PostMapping("/arquivoRemessaPagamento")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody ArquivoRemessaPagamentoRequest request) {
		service.create(request);
		return Utils.created(true, "Remessa realizada com sucesso.");
	}
	
	@PutMapping("/arquivoRemessaPagamento")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody ArquivoRemessaPagamentoRequest request) {
		service.update(request);
		return Utils.created(true, "Remessa atualizada com sucesso.");
	}
	
	@DeleteMapping("/arquivoRemessaPagamento/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Remessa removida com sucesso.");
	}
	
	@GetMapping("/arquivoRemessaPagamento/folhaPagamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FolhaPagamentoResponse> getAllbyFiltros(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "processamento", defaultValue = AppConstants.DEFAULT_EMPTY) Long processamentoId,
			@RequestParam(value = "filial", defaultValue = AppConstants.DEFAULT_EMPTY) Long filialId) {
		return folhaService.getAllbyFiltros(page, size, order, processamentoId, filialId);
	}
	
	@GetMapping("/arquivoRemessaPagamento/historicoSituacoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ArquivoRemessaHistoricoSituacaoResponse> getAll(
			@RequestParam(value = "arquivoRemessaId", defaultValue = AppConstants.DEFAULT_EMPTY) Long arquivoRemessaId) {
		return historicoService.getAllbyArquivoRemessaId(arquivoRemessaId);
	}
	
	@GetMapping("/arquivoRemessaPagamento/relatorioLiquidos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ArquivoRemessaRelatorioResponse getRelatorio(
			@RequestParam(value = "arquivoRemessaId", defaultValue = AppConstants.DEFAULT_EMPTY) Long arquivoRemessaId) {
		return service.getRelatoriobyArquivoRemessaId(arquivoRemessaId);
	}
	
	@GetMapping("/arquivoRemessaPagamento/arquivo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AnexoResponse> getAllOrCreate(
			@RequestParam(value = "arquivoRemessaId", defaultValue = AppConstants.DEFAULT_EMPTY) Long arquivoRemessaId) {
		try {
			return service.getAllOrCreateByArquivoRemessaId(arquivoRemessaId);
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	@GetMapping("/arquivoRemessaPagamento/{id}")
	public ArquivoRemessaPagamentoResponse getById(@PathVariable Long id) {
		return service.getById(id);
	}
	
}
