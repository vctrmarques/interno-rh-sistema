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
import com.rhlinkcon.payload.naturezaFuncao.NaturezaFuncaoRequest;
import com.rhlinkcon.payload.naturezaFuncao.NaturezaFuncaoResponse;
import com.rhlinkcon.repository.NaturezaFuncaoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.NaturezaFuncaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class NaturezaFuncaoController {
	
	@Autowired
	private NaturezaFuncaoService naturezaFuncaoService;
	
	@Autowired
	private NaturezaFuncaoRepository naturezaFuncaoRepository;
	
	@GetMapping("/naturezasFuncoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<NaturezaFuncaoResponse> getAllNaturezasFuncoes(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return naturezaFuncaoService.getAllNaturezasFuncoes(page, size, descricao, order);
	}

	@GetMapping("/naturezaFuncao/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<NaturezaFuncaoResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return naturezaFuncaoService.searchAllByDescricao(search);
	}
	
	@GetMapping("/naturezaFuncao/{id}")
	public NaturezaFuncaoResponse getNaturezaFuncaoById(@PathVariable Long id) {
		return naturezaFuncaoService.getNaturezaFuncaoById(id);
	}

	@PostMapping("/naturezaFuncao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createNaturezaFuncao(@Valid @RequestBody NaturezaFuncaoRequest naturezaFuncaoRequest) {
		if (naturezaFuncaoRepository.existsByDescricao(naturezaFuncaoRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Natureza da Função já existe!");
		}
		
		naturezaFuncaoService.createNaturezaFuncao(naturezaFuncaoRequest);

		return Utils.created(true, "Natureza da Função criada com sucesso.");
	}

	@PutMapping("/naturezaFuncao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateNaturezaJuridica(@Valid @RequestBody NaturezaFuncaoRequest naturezaFuncaoRequest) {
		if (naturezaFuncaoRepository.existsByDescricaoAndIdNot(naturezaFuncaoRequest.getDescricao(), naturezaFuncaoRequest.getId())) {
			return Utils.badRequest(false, "Esta Natureza da Função já existe!");
		}

		naturezaFuncaoService.updateNaturezaFuncao(naturezaFuncaoRequest);

		return Utils.created(true, "Natureza da Função atualizada com sucesso.");
	}


	@DeleteMapping("/naturezaFuncao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteNaturezaFuncao(@PathVariable("id") Long id) {
		naturezaFuncaoService.deleteNaturezaFuncao(id);

		return Utils.deleted(true, "Natureza da Função deletada com sucesso.");
	}

	@GetMapping("/listaNaturezaFuncoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<NaturezaFuncaoResponse> getAllNaturezaFuncoes() {
		return naturezaFuncaoService.getAllNaturezaFuncoes();
	}
}
