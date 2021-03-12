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
import com.rhlinkcon.payload.habilidade.HabilidadeRequest;
import com.rhlinkcon.payload.habilidade.HabilidadeResponse;
import com.rhlinkcon.repository.HabilidadeRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.HabilidadeService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class HabilidadeController {

	@Autowired
	private HabilidadeRepository habilidadeRepository;

	@Autowired
	private HabilidadeService habilidadeService;

	@GetMapping("/habilidades")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<HabilidadeResponse> getAllHabilidades(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return habilidadeService.getAllHabilidades(page, size, descricao, order);
	}
	
	@GetMapping("/habilidade/{habilidadeId}")
	public HabilidadeResponse getHabilidadeById(@PathVariable Long habilidadeId) {
		return habilidadeService.getHabilidadeById(habilidadeId);
	}

	@PostMapping("/habilidade")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createHabilidade(@Valid @RequestBody HabilidadeRequest habilidadeRequest) {
		if (habilidadeRepository.existsByCodigo(habilidadeRequest.getCodigo())) {
			return Utils.badRequest(false, "Esta Habilidade já existe!");
		}
		
		if (habilidadeRepository.existsByDescricao(habilidadeRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Habilidade já existe!");
		}

		habilidadeService.createHabilidade(habilidadeRequest);

		return Utils.created(true, "Habilidade criada com sucesso.");
	}

	@PutMapping("/habilidade")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateHabilidade(@Valid @RequestBody HabilidadeRequest habilidadeRequest) {
		if (habilidadeRepository.existsByCodigo(habilidadeRequest.getCodigo())) {
			return Utils.badRequest(false, "Esta Habilidade já existe!");
		}
		
		if (habilidadeRepository.existsByDescricaoAndIdNot(habilidadeRequest.getDescricao(), habilidadeRequest.getId())) {
			return Utils.badRequest(false, "Esta Habilidade já existe!");
		}

		habilidadeService.updateHabilidade(habilidadeRequest);
		
		return Utils.created(true, "Habilidade atualizada com sucesso.");
	}


	@DeleteMapping("/habilidade/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteHabilidade(@PathVariable("id") Long id) {
		habilidadeService.deleteHabilidade(id);
		
		return Utils.deleted(true, "Habilidade deletada com sucesso.");
	}
	
	@GetMapping("/listaHabilidades")
	public List<HabilidadeResponse> getAllHabilidades(){
		return habilidadeService.getAllHabilidades();
	}

}
