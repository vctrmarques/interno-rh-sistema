package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
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

import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.recadastramento.ProjectionRecadastramentoResponse;
import com.rhlinkcon.payload.recadastramento.RecadastramentoHistoricoLigacaoRequest;
import com.rhlinkcon.payload.recadastramento.RecadastramentoHistoricoLigacaoResponse;
import com.rhlinkcon.payload.recadastramento.RecadastramentoRequest;
import com.rhlinkcon.payload.recadastramento.RecadastramentoResponse;
import com.rhlinkcon.payload.recadastramento.RecadastroRequest;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.RecadastramentoHistoricoLigacaoService;
import com.rhlinkcon.service.RecadastramentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RecadastramentoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";

	@Autowired
	private RecadastramentoService service;

	@Autowired
	private RecadastramentoHistoricoLigacaoService historicoLigacaoService;

	@GetMapping("/recadastramentos")
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<ProjectionRecadastramentoResponse> getAll(PagedRequest pagedRequest,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao,
			@RequestParam(value = "fundo", defaultValue = AppConstants.DEFAULT_EMPTY) List<String> fundo,
			@RequestParam(value = "tipo", defaultValue = AppConstants.DEFAULT_EMPTY) String tipo) {
		return service.getAll(pagedRequest, descricao, fundo, tipo);
	}

	@GetMapping("/recadastramento/historicoLigacoes")
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<RecadastramentoHistoricoLigacaoResponse> getAllHistoricoLigacoes(
			@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "funcionarioId", defaultValue = AppConstants.DEFAULT_EMPTY) String funcionarioId) {
		return service.getAllHistoricoLigacoes(page, size, funcionarioId, order);
	}

	@GetMapping("/recadastramento/historico")
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<RecadastramentoResponse> getAllHistorico(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "funcionarioId", defaultValue = AppConstants.DEFAULT_EMPTY) String funcionarioId,
			@RequestParam(value = "data", defaultValue = AppConstants.DEFAULT_EMPTY) String data) {
		return service.getAllHistorico(page, size, funcionarioId, data, order);
	}

	@PostMapping("/recadastramento")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> create(@Valid @RequestBody RecadastramentoRequest request) {
		service.create(request);
		return Utils.created(true, "Recadastramento realizado com sucesso.");
	}

	@PostMapping("/recadastramento/novo")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> createNovo(@Valid @RequestBody RecadastroRequest request) {
		try {
			Long recadastramentoId = service.novo(request);
			return Utils.created(true, "Dados carregados com sucesso", recadastramentoId);
		} catch (ServiceException e) {
			return Utils.badRequest(false, e.getMessage());
		}
	}

	@PutMapping("/recadastramento")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> update(@Valid @RequestBody RecadastramentoRequest request) {
		service.update(request);
		return Utils.created(true, "Recadastramento realizado com sucesso.");
	}

	@DeleteMapping("/recadastramento/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Recadastramento removido com sucesso.");
	}

	@DeleteMapping("/recadastramento/remover-anexo/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> deleteAnexo(@PathVariable("id") Long id) {
		service.deleteAnexo(id);
		return Utils.deleted(true, "Anexo removido com sucesso.");
	}

	@GetMapping("/recadastramento/{id}")
	public RecadastramentoResponse getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@GetMapping("/recadastramento/visualizar/{id}")
	public RecadastramentoResponse getByIdVisualizar(@PathVariable Long id) {
		return service.getByIdVisualizar(id);
	}

	@PostMapping("/historicoLigacao")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> createHistoricoLigacao(
			@Valid @RequestBody RecadastramentoHistoricoLigacaoRequest request) {
		historicoLigacaoService.create(request);
		return Utils.created(true, "Observação adicionada com sucesso.");
	}

	@DeleteMapping("/historicoLigacao/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> deleteHistoricoLigacao(@PathVariable("id") Long id) {
		historicoLigacaoService.delete(id);
		return Utils.deleted(true, "Item removido com sucesso.");
	}
}
