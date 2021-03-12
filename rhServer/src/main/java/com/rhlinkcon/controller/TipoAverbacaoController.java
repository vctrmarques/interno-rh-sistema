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
import com.rhlinkcon.payload.sefip.SefipResponse;
import com.rhlinkcon.payload.tipoAverbacao.TipoAverbacaoRequest;
import com.rhlinkcon.payload.tipoAverbacao.TipoAverbacaoResponse;
import com.rhlinkcon.repository.TipoAverbacaoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TipoAverbacaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TipoAverbacaoController {

	@Autowired
	private TipoAverbacaoRepository tipoAverbacaoRepository;

	@Autowired
	private TipoAverbacaoService tipoAverbacaoService;

	@GetMapping("/tiposAverbacoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TipoAverbacaoResponse> getAllTiposAverbacoes(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return tipoAverbacaoService.getAllTiposAverbacoes(page, size, descricao, order);
	}

	@GetMapping("/tipoAverbacao/{tipoAverbacaoId}")
	public TipoAverbacaoResponse getTipoAverbacaoById(@PathVariable Long tipoAverbacaoId) {
		return tipoAverbacaoService.getTipoAverbacaoById(tipoAverbacaoId);
	}

	@PostMapping("/tipoAverbacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTipoAverbacao(@Valid @RequestBody TipoAverbacaoRequest tipoAverbacaoRequest) {
		if (tipoAverbacaoRepository.existsByDescricao(tipoAverbacaoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Tipo de Averbação já existe!");
		}

		tipoAverbacaoService.createTipoAverbacao(tipoAverbacaoRequest);

		return Utils.created(true, "Tipo de Averbação criado com sucesso.");
	}

	@PutMapping("/tipoAverbacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTipoAverbacao(@Valid @RequestBody TipoAverbacaoRequest tipoAverbacaoRequest) {
		if (tipoAverbacaoRepository.existsByDescricaoAndIdNot(tipoAverbacaoRequest.getDescricao(), tipoAverbacaoRequest.getId())) {
			return Utils.badRequest(false, "Este Tipo de Averbação já existe!");
		}

		tipoAverbacaoService.updateTipoAverbacao(tipoAverbacaoRequest);

		return Utils.created(true, "Tipo de Averbação atualizado com sucesso.");
	}


	@DeleteMapping("/tipoAverbacao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTipoAverbacao(@PathVariable("id") Long id) {
		tipoAverbacaoService.deleteTipoAverbacao(id);

		return Utils.deleted(true, "Tipo de Averbação deletada com sucesso.");
	}
	
	@GetMapping("/listaTipoAverbacoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TipoAverbacaoResponse> getAllTiposAverbacoes() {
		return tipoAverbacaoService.getAllTiposAverbacoes();
	}

}
