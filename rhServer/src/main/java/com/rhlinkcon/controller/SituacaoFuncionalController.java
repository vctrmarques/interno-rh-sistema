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
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalRequest;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.SituacaoFuncionalService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class SituacaoFuncionalController {

	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;

	@Autowired
	private SituacaoFuncionalService situacaoFuncionalService;

	@GetMapping("/situacoesFuncionais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<SituacaoFuncionalResponse> getAllSituacaoFuncional(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return situacaoFuncionalService.getAllSituacaoFuncional(page, size, descricao, order);
	}
	
	@GetMapping("/situacaoFuncional/{situacaoFuncionalId}")
	public SituacaoFuncionalResponse getSituacaoFuncionalById(@PathVariable Long situacaoFuncionalId) {
		return situacaoFuncionalService.getSituacaoFuncionalById(situacaoFuncionalId);
	}

	@PostMapping("/situacaoFuncional")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createSituacaoFuncional(@Valid @RequestBody SituacaoFuncionalRequest situacaoFuncionalRequest) {
		if (situacaoFuncionalRepository.existsByDescricao(situacaoFuncionalRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Situação Funcional já existe!");
		}

		situacaoFuncionalService.createSituacaoFuncional(situacaoFuncionalRequest);

		return Utils.created(true, "Situação Funcional criada com sucesso.");
	}

	@PutMapping("/situacaoFuncional")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateSituacaoFuncional(@Valid @RequestBody SituacaoFuncionalRequest situacaoFuncionalRequest) {
		if (situacaoFuncionalRepository.existsByDescricaoAndIdNot(situacaoFuncionalRequest.getDescricao(), situacaoFuncionalRequest.getId())) {
			return Utils.badRequest(false, "Este Situação Funcional já existe!");
		}

		situacaoFuncionalService.updateSituacaoFuncional(situacaoFuncionalRequest);
		
		return Utils.created(true, "Situação Funcional atualizada com sucesso.");
	}


	@DeleteMapping("/situacaoFuncional/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteSituacaoFuncional(@PathVariable("id") Long id) {
		situacaoFuncionalService.deleteSituacaoFuncional(id);
		
		return Utils.deleted(true, "Situação Funcional deletada com sucesso.");
	}
	
	@GetMapping("/situacaoFuncional/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SituacaoFuncionalResponse> searchAllByDescricaoAndTipoCategoria(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return situacaoFuncionalService.searchAllByDescricao(search);
	}
	
	@GetMapping("/listaSituacoesFuncionais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SituacaoFuncionalResponse> getAllSituacoesFuncionais() {
		return situacaoFuncionalService.getAllSituacoesFuncionais();
	}
	
	@GetMapping("/listaSituacoesFuncionais/afastamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SituacaoFuncionalResponse> getSituacoesFuncionaisTipoAfastamento() {
		return situacaoFuncionalService.getSituacoesFuncionaisTipoAfastamento();
	}
	
	@GetMapping("/listaSituacoesFuncionais/entraFolha/{entraFolha}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SituacaoFuncionalResponse> getSituacoesFuncionaisEntraFolha(@PathVariable("entraFolha") boolean entraFolha) {
		return situacaoFuncionalService.listSituaFuncionalEntraFolha(entraFolha);
	}

}
