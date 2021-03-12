package com.rhlinkcon.controller;

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

import com.rhlinkcon.payload.categoriaEconomica.CategoriaEconomicaRequest;
import com.rhlinkcon.payload.categoriaEconomica.CategoriaEconomicaResponse;
import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoRequest;
import com.rhlinkcon.payload.classificacaoAgenteNocivo.ClassificacaoAgenteNocivoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ClassificacaoAgenteNocivoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ClassificacaoAgenteNocivoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ClassificacaoAgenteNocivoController {

	@Autowired
	private ClassificacaoAgenteNocivoRepository classificacaoAgenteNocivoRepository;

	@Autowired
	private ClassificacaoAgenteNocivoService classificacaoAgenteNocivoService;

	@GetMapping("/classificacoesAgentesNocivos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ClassificacaoAgenteNocivoResponse> getClassificacoesAgentesNocivos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return classificacaoAgenteNocivoService.getAllClassificacoesAgentesNocivos(page, size, descricao, order);
	}

	@GetMapping("/classificacaoAgenteNocivo/{agenteNocivoId}")
	public ClassificacaoAgenteNocivoResponse getClassifcacaoAgenteNocivoById(@PathVariable Long agenteNocivoId) {
		return classificacaoAgenteNocivoService.getClassificacaoAgenteNocivoById(agenteNocivoId);
	}

	@PostMapping("/classificacaoAgenteNocivo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createClassificacaoAgenteNocivo(@Valid @RequestBody ClassificacaoAgenteNocivoRequest agenteNocivoRequest) {
		if (classificacaoAgenteNocivoRepository.existsByDescricao(agenteNocivoRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Classificação de Agente Nocivo já existe!");
		}

		classificacaoAgenteNocivoService.createClassificacaoAgenteNocivo(agenteNocivoRequest);

		return Utils.created(true, "Classificação de Agente Nocivo criada com sucesso.");
	}

	@PutMapping("/classificacaoAgenteNocivo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateClassificacaoAgenteNocivo(@Valid @RequestBody ClassificacaoAgenteNocivoRequest agenteNocivoRequest) {
		if (classificacaoAgenteNocivoRepository.existsByDescricaoAndIdNot(agenteNocivoRequest.getDescricao(), agenteNocivoRequest.getId())) {
			return Utils.badRequest(false, "Esta Classificação de Agente Nocivo já existe!");
		}

		classificacaoAgenteNocivoService.updateClassificacaoAgenteNocivo(agenteNocivoRequest);
		
		return Utils.created(true, "Classificação de Agente Nocivo atualizada com sucesso.");
	}


	@DeleteMapping("/classificacaoAgenteNocivo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteClassificacaoAgenteNocivo(@PathVariable("id") Long id) {
		classificacaoAgenteNocivoService.deleteClassificacaoAgenteNocivo(id);
		
		return Utils.deleted(true, "Classificação de Agente Nocivo deletada com sucesso.");
	}

}
