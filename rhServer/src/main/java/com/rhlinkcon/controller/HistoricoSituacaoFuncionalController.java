package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalRequest;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.ProjectionSituacaoFuncional;
import com.rhlinkcon.service.HistoricoSituacaoFuncionalService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class HistoricoSituacaoFuncionalController {

	@Autowired
	private HistoricoSituacaoFuncionalService historicoSituacaoFuncionalService;

	@GetMapping("/historicoSituacoesFuncionais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<HistoricoSituacaoFuncionalResponse> getAllHistoricoSituacoesByFuncionarioIdAndTipoSituacaoFuncional(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "funcionarioId", defaultValue = AppConstants.DEFAULT_EMPTY) String funcionarioId,
			@RequestParam(value = "tipoSituacao", defaultValue = AppConstants.DEFAULT_EMPTY) String tipoSituacao) {
		return historicoSituacaoFuncionalService.getAllHistoricoSituacoesByFuncionarioIdAndTipoSituacaoFuncional(page,
				size, Long.parseLong(funcionarioId), tipoSituacao, order);
	}

	@GetMapping("/historicoSituacoesFuncionais/lista")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Page<ProjectionSituacaoFuncional> getAllHistoricoSituacoesByFuncionarioIdAndTipoSituacaoFuncional(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "funcionarioNome", defaultValue = AppConstants.DEFAULT_EMPTY) String funcionarioNome) {
		return historicoSituacaoFuncionalService.getAllHistoricoSituacoes(page, size, funcionarioNome, order);
	}

	@GetMapping("/listaHistoricoSituacoesFuncionais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<HistoricoSituacaoFuncionalResponse> getAllHistoricoSituacoesByFuncionarioIdAndTipoSituacaoFuncional(
			@RequestParam(value = "funcionarioId", defaultValue = AppConstants.DEFAULT_EMPTY) String funcionarioId,
			@RequestParam(value = "tipoSituacao", defaultValue = AppConstants.DEFAULT_EMPTY) String tipoSituacao) {
		return historicoSituacaoFuncionalService.getAllHistoricoSituacoesByFuncionarioIdAndTipoSituacaoFuncional(
				Long.parseLong(funcionarioId), tipoSituacao);
	}

	@GetMapping("/getPrimeiraSituacaoFuncionalByFuncionario")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public HistoricoSituacaoFuncionalResponse getPrimeiraSituacaoFuncionalByFuncionario(
			@RequestParam(value = "funcionarioId", defaultValue = AppConstants.DEFAULT_EMPTY) String funcionarioId) {
		return historicoSituacaoFuncionalService
				.getPrimeiraSituacaoFuncionalByFuncionario(Long.parseLong(funcionarioId));
	}

	@PostMapping("/historicoSituacaoFuncional")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createHistoricoSituacaoFuncional(
			@Valid @RequestBody HistoricoSituacaoFuncionalRequest historicoSituacaoFuncionalRequest) {

		historicoSituacaoFuncionalService.createHistoricoSituacaoFuncional(historicoSituacaoFuncionalRequest);

		return Utils.created(true, "Situação Funcional criado com sucesso.");
	}

	@DeleteMapping("/historicoSituacaoFuncional/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> cancelaHistoricoSituacaoFuncional(@PathVariable Long id) {
		historicoSituacaoFuncionalService.cancelaHistoricoSituacaoFuncional(id);

		return Utils.deleted(true, "Situação Funcional cancelada com sucesso.");
	}

}
