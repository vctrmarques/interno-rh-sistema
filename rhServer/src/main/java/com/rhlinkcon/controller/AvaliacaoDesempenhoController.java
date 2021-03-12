package com.rhlinkcon.controller;

import com.rhlinkcon.filtro.AvaliacaoDesempenhoFiltro;
import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoRequest;
import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoResponse;
import com.rhlinkcon.payload.generico.EnumDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.AvaliacaoDesempenhoService;
import com.rhlinkcon.service.EnumService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AvaliacaoDesempenhoController {

	@Autowired
	private AvaliacaoDesempenhoService avaliacaoDesempenhoService;

	@Autowired
	private EnumService enumService;

	@GetMapping("/avaliacaoDesempenho")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<AvaliacaoDesempenhoResponse> list(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return avaliacaoDesempenhoService.getAll(page, size, order, new AvaliacaoDesempenhoFiltro(nome));
	}

	@GetMapping("/avaliacaoDesempenho/{id}")
	public AvaliacaoDesempenhoResponse get(@PathVariable Long id) {
		return avaliacaoDesempenhoService.getById(id);
	}

	@PostMapping("/avaliacaoDesempenho")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody AvaliacaoDesempenhoRequest avaliacaoDesempenhoRequest) {
		if (avaliacaoDesempenhoService.existsByNome(avaliacaoDesempenhoRequest.getNome())) {
			return Utils.badRequest(false, "Já existe uma avaliação com este nome.");
		}
		if (avaliacaoDesempenhoService.existsByCodAvaliacao(avaliacaoDesempenhoRequest.getCodAvaliacao())) {
			return Utils.badRequest(false, "Já existe uma avaliação com este código.");
		}
		avaliacaoDesempenhoService.create(avaliacaoDesempenhoRequest);
		return Utils.created(true, "Avaliação criada com sucesso.");
	}

	@PutMapping("/avaliacaoDesempenho")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> update(@Valid @RequestBody AvaliacaoDesempenhoRequest avaliacaoDesempenhoRequest) {
		if (avaliacaoDesempenhoService.existsByNomeAndIdNot(avaliacaoDesempenhoRequest.getNome(), avaliacaoDesempenhoRequest.getId())) {
			return Utils.badRequest(false, "Já existe uma avaliação com este nome.");
		}
		if (avaliacaoDesempenhoService.existsByCodAvaliacaoAndIdNot(avaliacaoDesempenhoRequest.getCodAvaliacao(), avaliacaoDesempenhoRequest.getId())) {
			return Utils.badRequest(false, "Já existe uma avaliação com este código.");
		}
		avaliacaoDesempenhoService.update(avaliacaoDesempenhoRequest);
		return Utils.created(true, "Avaliação atualizada com sucesso.");
	}

	@DeleteMapping("/avaliacaoDesempenho/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		avaliacaoDesempenhoService.delete(id);
		return Utils.deleted(true, "Avaliação excluída com sucesso.");
	}

	@GetMapping("/avaliacaoDesempenho/relatorio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AvaliacaoDesempenhoResponse> relatorio() {
		return avaliacaoDesempenhoService.getAll();
	}

}