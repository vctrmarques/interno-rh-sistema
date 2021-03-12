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

import com.rhlinkcon.payload.classificacaoInternacionalDoenca.ClassificacaoInternacionalDoencaRequest;
import com.rhlinkcon.payload.classificacaoInternacionalDoenca.ClassificacaoInternacionalDoencaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ClassificacaoInternacionalDoencaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ClassificacaoInternacionalDoencaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ClassificacaoInternacionalDoencaController {

	@Autowired
	private ClassificacaoInternacionalDoencaRepository classificacaoInternacionalDoencaRepository;

	@Autowired
	private ClassificacaoInternacionalDoencaService ClassificacaoInternacionalDoencaService;

	@GetMapping("/classificacaoInternacionalDoencas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ClassificacaoInternacionalDoencaResponse> getAllClassificacaoInternacionalDoencas(
			@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return ClassificacaoInternacionalDoencaService.getAllClassificacaoInternacionalDoencas(page, size, order,
				descricao);
	}

	@GetMapping("/classificacaoInternacionalDoenca/{classificacaoInternacionalDoencaId}")
	public ClassificacaoInternacionalDoencaResponse getClassificacaoInternacionalDoencasById(
			@PathVariable Long classificacaoInternacionalDoencaId) {
		return ClassificacaoInternacionalDoencaService
				.getClassificacaoInternacionalDoencaById(classificacaoInternacionalDoencaId);
	}

	@PostMapping("/classificacaoInternacionalDoenca")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createClassificacaoInternacionalDoenca(
			@Valid @RequestBody ClassificacaoInternacionalDoencaRequest classificacaoInternacionalDoencaRequest) {
		if (classificacaoInternacionalDoencaRepository
				.existsByDescricao(classificacaoInternacionalDoencaRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Classificação Internacional de Doença já existe!");
		}

		ClassificacaoInternacionalDoencaService
				.createClassificacaoInternacionalDoenca(classificacaoInternacionalDoencaRequest);

		return Utils.created(true, "Classificação Internacional de Doença criada com sucesso.");
	}

	@PutMapping("/classificacaoInternacionalDoenca")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateClassificacaoInternacionalDoenca(
			@Valid @RequestBody ClassificacaoInternacionalDoencaRequest classificacaoInternacionalDoencaRequest) {
		if (classificacaoInternacionalDoencaRepository.existsByDescricaoAndIdNot(
				classificacaoInternacionalDoencaRequest.getDescricao(),
				classificacaoInternacionalDoencaRequest.getId())) {
			return Utils.badRequest(false, "Esta Classificação Internacional de Doença já existe!");
		}

		ClassificacaoInternacionalDoencaService
				.updateClassificacaoInternacionalDoenca(classificacaoInternacionalDoencaRequest);

		return Utils.created(true, "Classificação Internacional de Doença atualizada com sucesso.");
	}

	@DeleteMapping("/classificacaoInternacionalDoenca/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteClassificacaoInternacionalDoenca(@PathVariable("id") Long id) {
		ClassificacaoInternacionalDoencaService.deleteClassificacaoInternacionalDoenca(id);

		return Utils.deleted(true, "Classificação Internacional de Doença deletada com sucesso.");
	}

}
