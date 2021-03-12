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

import com.rhlinkcon.payload.atividade.AtividadeRequest;
import com.rhlinkcon.payload.atividade.AtividadeResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AtividadeRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.AtividadeService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AtividadeController {

	@Autowired
	private AtividadeRepository atividadeRepository;

	@Autowired
	private AtividadeService atividadeService;

	@GetMapping("/atividades")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<AtividadeResponse> getAtividades(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return atividadeService.getAllAtividades(page, size, descricao, order);
	}

	@GetMapping("/atividade/{atividadeId}")
	public AtividadeResponse getAtividadeById(@PathVariable Long atividadeId) {
		return atividadeService.getAtividadeById(atividadeId);
	}

	@PostMapping("/atividade")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createAtividade(@Valid @RequestBody AtividadeRequest atividadeRequest) {
		if (atividadeRepository.existsByCodigo(atividadeRequest.getCodigo())) {
			return Utils.badRequest(false, "Esta Atividade já existe!");
		}
		
		if (atividadeRepository.existsByDescricao(atividadeRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Atividade já existe!");
		}

		atividadeService.createAtividade(atividadeRequest);

		return Utils.created(true, "Atividade criada com sucesso.");
	}

	@PutMapping("/atividade")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateAtividade(@Valid @RequestBody AtividadeRequest atividadeRequest) {
		if (atividadeRepository.existsByCodigoAndIdNot(atividadeRequest.getCodigo(), atividadeRequest.getId())) {
			return Utils.badRequest(false, "Esta Atividade já existe!");
		}
		
		if (atividadeRepository.existsByDescricaoAndIdNot(atividadeRequest.getDescricao(), atividadeRequest.getId())) {
			return Utils.badRequest(false, "Esta Atividade já existe!");
		}

		atividadeService.updateAtividade(atividadeRequest);

		return Utils.created(true, "Atividade atualizada com sucesso.");
	}


	@DeleteMapping("/atividade/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteAtividade(@PathVariable("id") Long id) {
		atividadeService.deleteAtividade(id);

		return Utils.deleted(true, "Atividade deletada com sucesso.");
	}
	
	@GetMapping("/listaAtividades")
	public List<AtividadeResponse> getAllAtividades() {
		return atividadeService.getAllAtividades();
	}

}
