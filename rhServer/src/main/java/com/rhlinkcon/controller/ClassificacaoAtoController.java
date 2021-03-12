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

import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoRequest;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ClassificacaoAtoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ClassificacaoAtoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ClassificacaoAtoController {

	@Autowired
	private ClassificacaoAtoRepository classificacaoAtoRepository;

	@Autowired
	private ClassificacaoAtoService classificacaoAtoService;

	@GetMapping("/classificacoesAtos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ClassificacaoAtoResponse> getAllClassificacoesAtos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return classificacaoAtoService.getAllClassificacoesAtos(page, size, descricao, order);
	}

	@GetMapping("/classificacaoAto/{classificacaoAtoId}")
	public ClassificacaoAtoResponse getClassificacaoAtoById(@PathVariable Long classificacaoAtoId) {
		return classificacaoAtoService.getClassificacaoAtoById(classificacaoAtoId);
	}

	@PostMapping("/classificacaoAto")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createClassificacaoAto(@Valid @RequestBody ClassificacaoAtoRequest classificacaoAtoRequest) {
		if (classificacaoAtoRepository.existsByDescricao(classificacaoAtoRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Classificação do Ato já existe!");
		}

		classificacaoAtoService.createClassificacaoAto(classificacaoAtoRequest);

		return Utils.created(true, "Classificação do Ato criada com sucesso.");
	}

	@PutMapping("/classificacaoAto")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateClassificacaoAto(@Valid @RequestBody ClassificacaoAtoRequest classificacaoAtoRequest) {
		if (classificacaoAtoRepository.existsByDescricaoAndIdNot(classificacaoAtoRequest.getDescricao(), classificacaoAtoRequest.getId())) {
			return Utils.badRequest(false, "Esta Classificação do Ato já existe!");
		}

		classificacaoAtoService.updateClassificacaoAto(classificacaoAtoRequest);

		return Utils.created(true, "Classificação do Ato atualizada com sucesso.");
	}


	@DeleteMapping("/classificacaoAto/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteClassificacaoAto(@PathVariable("id") Long id) {
		classificacaoAtoService.deleteClassificacaoAto(id);

		return Utils.deleted(true, "Classificação de Ato deletada com sucesso.");
	}
	
	@GetMapping("/listaClassificacoesAtos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ClassificacaoAtoResponse> getAllClassificacoesAtos() {
		return classificacaoAtoService.getAllClassificacoesAtos();
	}
	
	@GetMapping("/classificacoesAtos/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ClassificacaoAtoResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return classificacaoAtoService.searchAllByDescricao(search);
	}

}
