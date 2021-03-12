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
import com.rhlinkcon.payload.subCategoriaDoenca.SubCategoriaDoencaRequest;
import com.rhlinkcon.payload.subCategoriaDoenca.SubCategoriaDoencaResponse;
import com.rhlinkcon.repository.SubCategoriaDoencaRepository;
import com.rhlinkcon.service.SubCategoriaDoencaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class SubCategoriaDoencaController {

	@Autowired
	private SubCategoriaDoencaRepository subCategoriaDoencaRepository;

	@Autowired
	private SubCategoriaDoencaService subCategoriaDoencaService;

	@GetMapping("/subCategoriaDoenca/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<SubCategoriaDoencaResponse> searchByCodigoOrDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return subCategoriaDoencaService.searchByCodigoOrDescricao(search);
	}

	@GetMapping("/subCategoriaDoencas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<SubCategoriaDoencaResponse> getAllSubCategoriaDoencas(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return subCategoriaDoencaService.getAllSubCategoriaDoencas(page, size, order, descricao);
	}

	@GetMapping("/subCategoriaDoenca/{subCategoriaDoencaId}")
	public SubCategoriaDoencaResponse getSubCategoriaDoencaById(@PathVariable Long subCategoriaDoencaId) {
		return subCategoriaDoencaService.getSubCategoriaDoencaById(subCategoriaDoencaId);
	}

	@PostMapping("/subCategoriaDoenca")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createSubCategoriaDoenca(
			@Valid @RequestBody SubCategoriaDoencaRequest subCategoriaDoencaRequest) {
		if (subCategoriaDoencaRepository.existsByDescricao(subCategoriaDoencaRequest.getDescricao())) {
			return Utils.badRequest(false, "A descricao já está em uso!");
		}

		if (subCategoriaDoencaRepository.existsByCodigo(subCategoriaDoencaRequest.getCodigo())) {
			return Utils.badRequest(false, "O código já está em uso!");
		}

		subCategoriaDoencaService.createSubCategoriaDoenca(subCategoriaDoencaRequest);

		return Utils.created(true, "SubCategoriaDoenca criado com sucesso.");
	}

	@PutMapping("/subCategoriaDoenca")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateSubCategoriaDoenca(
			@Valid @RequestBody SubCategoriaDoencaRequest subCategoriaDoencaRequest) {
		if (subCategoriaDoencaRepository.existsByDescricaoAndIdNot(subCategoriaDoencaRequest.getDescricao(),
				subCategoriaDoencaRequest.getId())) {
			return Utils.badRequest(false, "A descricao já está em uso!");
		}

		if (subCategoriaDoencaRepository.existsByCodigoAndIdNot(subCategoriaDoencaRequest.getCodigo(),
				subCategoriaDoencaRequest.getId())) {
			return Utils.badRequest(false, "O código já está em uso!");
		}

		subCategoriaDoencaService.updateSubCategoriaDoenca(subCategoriaDoencaRequest);

		return Utils.created(true, "SubCategoriaDoenca atualizado com sucesso.");
	}

	@DeleteMapping("/subCategoriaDoenca/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteSubCategoriaDoenca(@PathVariable("id") Long id) {
		subCategoriaDoencaService.deleteSubCategoriaDoenca(id);

		return Utils.deleted(true, "SubCategoriaDoenca deletado com sucesso.");
	}

}
