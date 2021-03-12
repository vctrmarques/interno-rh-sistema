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

import com.rhlinkcon.payload.categoriaDoenca.CategoriaDoencaRequest;
import com.rhlinkcon.payload.categoriaDoenca.CategoriaDoencaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CategoriaDoencaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CategoriaDoencaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CategoriaDoencaController {

	@Autowired
	private CategoriaDoencaRepository categoriaDoencaRepository;

	@Autowired
	private CategoriaDoencaService CategoriaDoencaService;

	@GetMapping("/categoriaDoenca/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CategoriaDoencaResponse> searchAllByCodigoOrDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return CategoriaDoencaService.searchAllByCodigoOrDescricao(search);
	}

	@GetMapping("/categoriasDoencas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CategoriaDoencaResponse> getAllCategoriaDoencas(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return CategoriaDoencaService.getAllCategoriaDoencas(page, size, order, descricao);
	}

	@GetMapping("/categoriaDoenca/{categoriaDoencaId}")
	public CategoriaDoencaResponse getCategoriaDoencaResponseById(@PathVariable Long categoriaDoencaId) {
		return CategoriaDoencaService.getCategoriaDoencaResponseById(categoriaDoencaId);
	}

	@PostMapping("/categoriaDoenca")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCategoriaDoenca(@Valid @RequestBody CategoriaDoencaRequest categoriaDoencaRequest) {
		if (categoriaDoencaRepository.existsByDescricao(categoriaDoencaRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Categoria Doença já existe!");
		}

		CategoriaDoencaService.createCategoriaDoenca(categoriaDoencaRequest);

		return Utils.created(true, "Categoria Doença criada com sucesso.");
	}

	@PutMapping("/categoriaDoenca")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCategoriaDoenca(@Valid @RequestBody CategoriaDoencaRequest categoriaDoencaRequest) {
		if (categoriaDoencaRepository.existsByDescricaoAndIdNot(categoriaDoencaRequest.getDescricao(),
				categoriaDoencaRequest.getId())) {
			return Utils.badRequest(false, "Esta área de Formação já existe!");
		}

		CategoriaDoencaService.updateCategoriaDoenca(categoriaDoencaRequest);

		return Utils.created(true, "Categoria Doença atualizada com sucesso.");
	}

	@DeleteMapping("/categoriaDoenca/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCategoriaDoenca(@PathVariable("id") Long id) {
		CategoriaDoencaService.deleteCategoriaDoenca(id);

		return Utils.deleted(true, "Categoria Doença deletada com sucesso.");
	}

}
