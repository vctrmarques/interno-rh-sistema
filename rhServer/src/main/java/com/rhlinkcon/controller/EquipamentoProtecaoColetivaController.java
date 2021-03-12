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

import com.rhlinkcon.payload.equipamentoProtecaoColetiva.EquipamentoProtecaoColetivaRequest;
import com.rhlinkcon.payload.equipamentoProtecaoColetiva.EquipamentoProtecaoColetivaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EquipamentoProtecaoColetivaRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.EquipamentoProtecaoColetivaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class EquipamentoProtecaoColetivaController {

	@Autowired
	private EquipamentoProtecaoColetivaRepository equipamentoProtecaoColetivaRepository;

	@Autowired
	private EquipamentoProtecaoColetivaService equipamentoProtecaoColetivaService;

	@GetMapping("/equipamentoProtecaoColetiva")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EquipamentoProtecaoColetivaResponse> getAllEquipamentosProtecaoColetiva(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return equipamentoProtecaoColetivaService.getAllEquipamentosProtecaoColetiva(page, size, descricao, order);
	}

	@GetMapping("/equipamentoProtecaoColetiva/{equipamentoProtecaoColetivaId}")
	public EquipamentoProtecaoColetivaResponse getEquipamentoProtecaoColetivaById(@PathVariable Long equipamentoProtecaoColetivaId) {
		return equipamentoProtecaoColetivaService.getEquipamentoProtecaoColetivaById(equipamentoProtecaoColetivaId);
	}

	@PostMapping("/equipamentoProtecaoColetiva")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createEquipamentoProtecaoColetiva(@Valid @RequestBody EquipamentoProtecaoColetivaRequest equipamentoProtecaoColetivaRequest) {
		
		equipamentoProtecaoColetivaService.createEquipamentoProtecaoColetiva(equipamentoProtecaoColetivaRequest);

		return Utils.created(true, "Equipamento de Proteção Coletiva criado com sucesso.");
	}

	@PutMapping("/equipamentoProtecaoColetiva")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateEquipamentoProtecaoColetiva(@Valid @RequestBody EquipamentoProtecaoColetivaRequest equipamentoProtecaoColetivaRequest) {
		if (equipamentoProtecaoColetivaRepository.existsByDescricaoAndIdNot(equipamentoProtecaoColetivaRequest.getDescricao(), equipamentoProtecaoColetivaRequest.getId())) {
			return Utils.badRequest(false, "Este Equipamento de Proteção Coletiva já existe!");
		}

		equipamentoProtecaoColetivaService.updateEquipamentoProtecaoColetiva(equipamentoProtecaoColetivaRequest);
		
		return Utils.created(true, "Equipamento de Proteção Coletiva atualizado com sucesso.");
	}


	@DeleteMapping("/equipamentoProtecaoColetiva/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteEquipamentoProtecaoColetiva(@PathVariable("id") Long id) {
		equipamentoProtecaoColetivaService.deleteEquipamentoProtecaoColetiva(id);
		
		return Utils.deleted(true, "Classificação de Equipamento de Proteção Individual deletada com sucesso.");
	}

}
