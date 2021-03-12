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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.transferenciaPrevidenciaFuncionario.TransferenciaPrevidenciaFuncionarioRequest;
import com.rhlinkcon.payload.transferenciaPrevidenciaFuncionario.TransferenciaPrevidenciaFuncionarioResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TransferenciaPrevidenciaFuncionarioService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TransferenciaPrevidenciaFuncionarioController {
	
	@Autowired
	private TransferenciaPrevidenciaFuncionarioService transferenciaPrevidenciaFuncionarioService;
	
	
	@GetMapping("/transferenciasPrevidenciaFuncionarios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TransferenciaPrevidenciaFuncionarioResponse> getAll(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return transferenciaPrevidenciaFuncionarioService.getAll(page, size, descricao, order);
	}
	
	@PostMapping("/transferenciasPrevidenciaFuncionarios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody TransferenciaPrevidenciaFuncionarioRequest request) {
		transferenciaPrevidenciaFuncionarioService.create(request);
		return Utils.created(true, "Funcionário transferido com sucesso.");
	}
	
	@GetMapping("/transferenciasPrevidenciaFuncionarios/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public TransferenciaPrevidenciaFuncionarioResponse getById(@PathVariable Long id) {
		return transferenciaPrevidenciaFuncionarioService.getById(id);
	}
	
	@GetMapping("/exSegurado/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TransferenciaPrevidenciaFuncionarioResponse> searchExSeguradoByNome(@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return transferenciaPrevidenciaFuncionarioService.findExSeguradoByNome(search);
	}
	
	@GetMapping("/exSeguradoAposentado/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TransferenciaPrevidenciaFuncionarioResponse> searchExSeguradoAposentadoByNome(
			@RequestParam(value = "searchFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String searchFuncionario,
			@RequestParam(value = "searchMatricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer searchMatricula,
			@RequestParam(value = "searchCPF", defaultValue = AppConstants.DEFAULT_EMPTY) String searchCPF,
			@RequestParam(value = "searchPis", defaultValue = AppConstants.DEFAULT_EMPTY) String searchPis) {
		return transferenciaPrevidenciaFuncionarioService.findExSeguradoByFiltro(searchFuncionario, searchMatricula, searchCPF, searchPis);
	}


}
