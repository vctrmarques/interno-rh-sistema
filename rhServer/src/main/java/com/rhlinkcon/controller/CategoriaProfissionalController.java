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

import com.rhlinkcon.payload.categoriaProfissional.CategoriaProfissionalRequest;
import com.rhlinkcon.payload.categoriaProfissional.CategoriaProfissionalResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CategoriaProfissionalRepository;
import com.rhlinkcon.service.CategoriaProfissionalService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CategoriaProfissionalController {

	@Autowired
	private CategoriaProfissionalRepository categoriaProfissionalRepository;

	@Autowired
	private CategoriaProfissionalService categoriaProfissionalService;

	@GetMapping("/categoriasProfissionais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CategoriaProfissionalResponse> getAllCategoriasProfissionais(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return categoriaProfissionalService.getAllCategoriasProfissionais(page, size, order, descricao);
	}
	
	@GetMapping("/categoriaProfissional/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CategoriaProfissionalResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return categoriaProfissionalService.searchAllByDescricao(search);
	}

	@GetMapping("/categoriaProfissional/{categoriaProfissionalId}")
	public CategoriaProfissionalResponse getCategoriaProfissionalById(@PathVariable Long categoriaProfissionalId) {
		return categoriaProfissionalService.getCategoriaProfissionalById(categoriaProfissionalId);
	}

	@PostMapping("/categoriaProfissional")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCategoriaProfissional(
			@Valid @RequestBody CategoriaProfissionalRequest categoriaProfissionalRequest) {
		if (categoriaProfissionalRepository.existsByDescricao(categoriaProfissionalRequest.getDescricao())) {
			return Utils.badRequest(false, "A descrição já está em uso!");
		}

		categoriaProfissionalService.createCategoriaProfissional(categoriaProfissionalRequest);

		return Utils.created(true, "Categoria Profissional criada com sucesso.");
	}

	@PutMapping("/categoriaProfissional")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCategoriaProfissional(
			@Valid @RequestBody CategoriaProfissionalRequest categoriaProfissionalRequest) {
		if (categoriaProfissionalRepository.existsByDescricaoAndIdNot(categoriaProfissionalRequest.getDescricao(),
				categoriaProfissionalRequest.getId())) {
			return Utils.badRequest(false, "A descrição já está em uso!");
		}

		categoriaProfissionalService.updateCategoriaProfissional(categoriaProfissionalRequest);

		return Utils.created(true, "Categoria Profissional atualizada com sucesso.");
	}

	@DeleteMapping("/categoriaProfissional/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCategoriaProfissional(@PathVariable("id") Long id) {
		categoriaProfissionalService.deleteCategoriaProfissional(id);

		return Utils.deleted(true, "Categoria Profissional deletada com sucesso.");
	}
	
	@GetMapping("/listaCategoriasProfissionais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CategoriaProfissionalResponse> getAllCategoriasProfissionais() {
		return categoriaProfissionalService.getAllCategoriasProfissionais();
	}

}
