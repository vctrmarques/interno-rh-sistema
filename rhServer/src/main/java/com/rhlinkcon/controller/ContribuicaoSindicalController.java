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

import com.rhlinkcon.payload.contribuicaoSindical.ContribuicaoSindicalRequest;
import com.rhlinkcon.payload.contribuicaoSindical.ContribuicaoSindicalResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ContribuicaoSindicalService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ContribuicaoSindicalController {
	
	@Autowired
	private ContribuicaoSindicalService contribuicaoSindicalService;
	
	@GetMapping("/contribuicoesSindicais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ContribuicaoSindicalResponse> getAllContribuicoesSindicais(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "matricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer matricula) {
		return contribuicaoSindicalService.getAllContribuicoesSindicais(page, size, nome, matricula, order);
	}
	
	@GetMapping("/contribuicaoSindical/{contribuicaoId}")
	public ContribuicaoSindicalResponse getContribuicaoById(@PathVariable Long contribuicaoId) {
		return contribuicaoSindicalService.getContribuicaoById(contribuicaoId);
	}
	
	@DeleteMapping("/contribuicaoSindical/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteContribuicaoSindical(@PathVariable("id") Long id) {
		contribuicaoSindicalService.deleteContribuicaoSindical(id);
		
		return Utils.deleted(true, "Contribuição Sindical deletada com sucesso.");
	}
	
	@PostMapping("/contribuicaoSindical")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createContribuicaoSindical(@Valid @RequestBody ContribuicaoSindicalRequest contribuicaoSindicalRequest) {
		contribuicaoSindicalService.createContribuicaoSindical(contribuicaoSindicalRequest);
		return Utils.created(true, "Contribuição criada com sucesso.");
	}
	
	@PutMapping("/contribuicaoSindical")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateContribuicaoSindical(@Valid @RequestBody ContribuicaoSindicalRequest contribuicaoSindicalRequest) {

		contribuicaoSindicalService.updateContribuicaoSindical(contribuicaoSindicalRequest);
		
		return Utils.created(true, "Contribuicão Sindical atualizada com sucesso.");
	}
	
	@GetMapping("/contribuicoesSindicais/funcionario/{funcionarioId}")
	public List<ContribuicaoSindicalResponse> getAllContribuicaoByFuncionario(@PathVariable Long funcionarioId) {
		return contribuicaoSindicalService.getAllContribuicaoByFuncionario(funcionarioId);
	}

}
