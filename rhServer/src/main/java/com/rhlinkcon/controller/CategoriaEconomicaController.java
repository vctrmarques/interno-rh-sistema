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

import com.rhlinkcon.payload.atividade.AtividadeRequest;
import com.rhlinkcon.payload.categoriaEconomica.CategoriaEconomicaRequest;
import com.rhlinkcon.payload.categoriaEconomica.CategoriaEconomicaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CategoriaEconomicaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CategoriaEconomicaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CategoriaEconomicaController {

	@Autowired
	private CategoriaEconomicaRepository categoriaEconomicaRepository;

	@Autowired
	private CategoriaEconomicaService categoriaEconomicaService;

	@GetMapping("/categoriasEconomicas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CategoriaEconomicaResponse> getCategoriasEconomicas(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return categoriaEconomicaService.getAllCategoriasEconomicas(page, size, descricao, order);
	}

	@GetMapping("/categoriaEconomica/{categoriaEconomicaId}")
	public CategoriaEconomicaResponse getCategoriaEconomicaById(@PathVariable Long categoriaEconomicaId) {
		return categoriaEconomicaService.getCategoriaEconomicaById(categoriaEconomicaId);
	}

	@PostMapping("/categoriaEconomica")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCategoriaEconomica(@Valid @RequestBody CategoriaEconomicaRequest categoriaEconomicaRequest) {
		if (categoriaEconomicaRepository.existsByDescricao(categoriaEconomicaRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Categoria Economica já existe!");
		}

		categoriaEconomicaService.createCategoriaEconomica(categoriaEconomicaRequest);

		return Utils.created(true, "Categoria Econômica criada com sucesso.");
	}

	@PutMapping("/categoriaEconomica")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCategoriaEconomica(@Valid @RequestBody CategoriaEconomicaRequest categoriaEconomicaRequest) {
		if (categoriaEconomicaRepository.existsByDescricaoAndIdNot(categoriaEconomicaRequest.getDescricao(), categoriaEconomicaRequest.getId())) {
			return Utils.badRequest(false, "Esta área de Formação já existe!");
		}

		categoriaEconomicaService.updateCategoriaEconomica(categoriaEconomicaRequest);
		
		return Utils.created(true, "Categoria Econômica atualizada com sucesso.");
	}


	@DeleteMapping("/categoriaEconomica/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCategoriaEconomica(@PathVariable("id") Long id) {
		categoriaEconomicaService.deleteCategoriaEconomica(id);

		return Utils.deleted(true, "Categoria Econômica deletada com sucesso.");
	}

}
