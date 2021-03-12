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

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.processoFuncao.ProcessoFuncaoRequest;
import com.rhlinkcon.payload.processoFuncao.ProcessoFuncaoResponse;
import com.rhlinkcon.repository.ProcessoFuncaoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ProcessoFuncaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ProcessoFuncaoController {

	@Autowired
	private ProcessoFuncaoRepository processoFuncaoRepository;

	@Autowired
	private ProcessoFuncaoService processoFuncaoService;

	@GetMapping("/processosFuncoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ProcessoFuncaoResponse> getAllProcessosFuncoes(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return processoFuncaoService.getAllProcessosFuncoes(page, size, descricao, order);
	}
	
	@GetMapping("/processoFuncao/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ProcessoFuncaoResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return processoFuncaoService.searchAllByDescricao(search);
	}
	
	@GetMapping("/processoFuncao/{processoFuncaoId}")
	public ProcessoFuncaoResponse getProcessoFuncaoById(@PathVariable Long processoFuncaoId) {
		return processoFuncaoService.getProcessoFuncaoById(processoFuncaoId);
	}

	@PostMapping("/processoFuncao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createProcessoFuncao(@Valid @RequestBody ProcessoFuncaoRequest processoFuncaoRequest) {
		if (processoFuncaoRepository.existsByDescricao(processoFuncaoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Processo de Função já existe!");
		}

		processoFuncaoService.createProcessoFuncao(processoFuncaoRequest);

		return Utils.created(true, "Processo de Função criado com sucesso.");
	}

	@PutMapping("/processoFuncao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateProcessoFuncao(@Valid @RequestBody ProcessoFuncaoRequest processoFuncaoRequest) {
		if (processoFuncaoRepository.existsByDescricaoAndIdNot(processoFuncaoRequest.getDescricao(), processoFuncaoRequest.getId())) {
			return Utils.badRequest(false, "Este Processo de Função já existe!");
		}

		processoFuncaoService.updateProcessoFuncao(processoFuncaoRequest);
		
		return Utils.created(true, "Processo de Função atualizado com sucesso.");
	}


	@DeleteMapping("/processoFuncao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteProcessoFuncao(@PathVariable("id") Long id) {
		processoFuncaoService.deleteProcessoFuncao(id);
		
		return Utils.deleted(true, "Processo de Função deletada com sucesso.");
	}
	
	@GetMapping("/listaProcessosFuncoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ProcessoFuncaoResponse> getAllProcessosFuncoes() {
		return processoFuncaoService.getAllProcessosFuncoes();
	}

}
