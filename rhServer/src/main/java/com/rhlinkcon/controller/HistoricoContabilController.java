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

import com.rhlinkcon.payload.areaFormacao.AreaFormacaoRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.historicoContabil.HistoricoContabilRequest;
import com.rhlinkcon.payload.historicoContabil.HistoricoContabilResponse;
import com.rhlinkcon.repository.HistoricoContabilRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.HistoricoContabilService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class HistoricoContabilController {

	@Autowired
	private HistoricoContabilRepository historicoContabilRepository;

	@Autowired
	private HistoricoContabilService historicoContabilService;

	@GetMapping("/historicosContabeis")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<HistoricoContabilResponse> getHistoricosContabeis(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return historicoContabilService.getAllHistoricosContabeis(page, size, descricao, order);
	}
	
	@GetMapping("/historicoContabil/{historicoContabilId}")
	public HistoricoContabilResponse getHistoricoContabilById(@PathVariable Long historicoContabilId) {
		return historicoContabilService.getHistoricoContabilById(historicoContabilId);
	}

	@PostMapping("/historicoContabil")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createHistoricoContabil(@Valid @RequestBody HistoricoContabilRequest historicoContabilRequest) {
		if (historicoContabilRepository.existsByDescricao(historicoContabilRequest.getDescricao())) {
			return Utils.badRequest(false, "Historico Contábil já existe!");
		}

		historicoContabilService.createHistoricoContabil(historicoContabilRequest);;

		return Utils.created(true, "Histórico Contábil criado com sucesso.");
	}

	@PutMapping("/historicoContabil")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateHistoricoContabil(@Valid @RequestBody HistoricoContabilRequest historicoContabilRequest) {
		if (historicoContabilRepository.existsByDescricaoAndIdNot(historicoContabilRequest.getDescricao(), historicoContabilRequest.getId())) {
			return Utils.badRequest(false, "Historico Contábil já existe!");
		}

		historicoContabilService.updateHistoricoContabil(historicoContabilRequest);

		return Utils.created(true, "Histórico Contábil atualizado com sucesso.");
	}


	@DeleteMapping("/historicoContabil/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteHistoricoContabil(@PathVariable("id") Long id) {
		historicoContabilService.deleteHistoricoContabil(id);
		
		return Utils.deleted(true, "Histórico Contábil deletado com sucesso.");
	}

}
