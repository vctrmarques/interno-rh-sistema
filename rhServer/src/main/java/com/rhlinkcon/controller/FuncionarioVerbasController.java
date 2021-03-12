package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioVerbaRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioVerbaResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.FuncionarioService;
import com.rhlinkcon.service.FuncionarioVerbaService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FuncionarioVerbasController {

	@Autowired
	private FuncionarioVerbaService funcionarioVerbaService;

	@Autowired
	private FuncionarioService funcionarioService;

	@GetMapping("/funcionarioVerbas/{funcionarioId}")
	public List<FuncionarioVerbaResponse> getVerbasFuncionarioById(@PathVariable Long funcionarioId) {
		return funcionarioVerbaService.getVerbasFuncionarioByFuncionarioId(funcionarioId);
	}

	@PostMapping("/funcionarioVerbas/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody FuncionarioVerbaRequest request) {

		funcionarioVerbaService.createFuncionarioVerba(request);

		return Utils.created(true, "Verbas do Funcionário criada com sucesso.");
	}

	@GetMapping("/funcionariosVerbas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncionarioResponse> getAll(PagedRequest pagedRequest, FuncionarioFiltro funcionarioFiltro) {
		return funcionarioService.getAllFuncionariosToVerbasFuncionario(pagedRequest, funcionarioFiltro);
	}

}
