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

import com.rhlinkcon.payload.exame.ExameRequest;
import com.rhlinkcon.payload.exame.ExameResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ExameRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ExameService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ExameController {

	@Autowired
	private ExameRepository exameRepository;

	@Autowired
	private ExameService exameService;

	@GetMapping("/exame/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ExameResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return exameService.searchAllByDescricaol(search);
	}

	@GetMapping("/exames")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ExameResponse> getAllExames(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return exameService.getAllExames(page, size, descricao, order);
	}

	@GetMapping("/exame/{exameId}")
	public ExameResponse getExameById(@PathVariable Long exameId) {
		return exameService.getExameById(exameId);
	}

	@PostMapping("/exame")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createExame(@Valid @RequestBody ExameRequest exameRequest) {
		if (exameRepository.existsByDescricao(exameRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Exame já existe!");
		}

		exameService.createExame(exameRequest);

		return Utils.created(true, "Exame criado com sucesso.");
	}

	@PutMapping("/exame")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateExamea(@Valid @RequestBody ExameRequest exameRequest) {
		if (exameRepository.existsByDescricaoAndIdNot(exameRequest.getDescricao(), exameRequest.getId())) {
			return Utils.badRequest(false, "Este Exame já existe!");
		}

		exameService.updateExame(exameRequest);

		return Utils.created(true, "Exame atualizado com sucesso.");
	}

	@DeleteMapping("/exame/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteExame(@PathVariable("id") Long id) {
		exameService.deleteExame(id);

		return Utils.deleted(true, "Classificação de Exame deletada com sucesso.");
	}

}
