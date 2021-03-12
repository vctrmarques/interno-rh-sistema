package com.rhlinkcon.controller;

import com.rhlinkcon.payload.funcao.FuncaoRequest;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.service.FuncaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FuncaoController {

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private FuncaoService funcaoService;

	@GetMapping("/funcoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncaoResponse> getAllFuncoes(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return funcaoService.getAllFuncoes(page, size, order, nome);
	}

	@GetMapping("/listaFuncoes")
//	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncaoResponse> getAllFuncoes() {
		return funcaoService.getAllFuncoes();
	}

	@GetMapping("/funcao/{funcaoId}")
	public FuncaoResponse getFuncaoById(@PathVariable Long funcaoId) {
		return funcaoService.getFuncaoById(funcaoId);
	}

	@PutMapping("/extinguirFuncao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> extingueFuncaoById(@Valid @RequestBody FuncaoRequest funcaoRequest) {
		funcaoService.extingueFuncao(funcaoRequest);
		return Utils.created(true, "Função extinta com sucesso.");
	}

	@PostMapping("/funcao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createFuncao(@Valid @RequestBody FuncaoRequest funcaoRequest) {
		if (funcaoRepository.existsByNome(funcaoRequest.getNome())) {
			return Utils.badRequest(false, "Esta Função já existe!");
		}
		funcaoService.createFuncao(funcaoRequest);
		return Utils.created(true, "Função criada com sucesso.");
	}

	@PutMapping("/funcao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFuncao(@Valid @RequestBody FuncaoRequest funcaoRequest) {
		if (funcaoRepository.existsByNomeAndIdNot(funcaoRequest.getNome(),
				funcaoRequest.getId())) {
			return Utils.badRequest(false, "Esta Função já existe!");
		}
		funcaoService.updateFuncao(funcaoRequest);
		return Utils.created(true, "Função atualizada com sucesso.");
	}

	@DeleteMapping("/funcao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteFuncao(@PathVariable("id") Long id) {
		funcaoService.deleteFuncao(id);
		return Utils.deleted(true, "Função deletada com sucesso.");
	}

	@GetMapping("/funcao/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncaoResponse> getAllFuncoesFindByNome(@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return funcaoService.getAllFuncoesFindByNome(search);
	}
}