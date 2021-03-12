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

import com.rhlinkcon.payload.equipamentoProtecaoIndividual.EquipamentoProtecaoIndividualRequest;
import com.rhlinkcon.payload.equipamentoProtecaoIndividual.EquipamentoProtecaoIndividualResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EquipamentoProtecaoIndividualRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.EquipamentoProtecaoIndividualService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class EquipamentoProtecaoIndividualController {

	@Autowired
	private EquipamentoProtecaoIndividualRepository equipamentoProtecaoIndividualRepository;

	@Autowired
	private EquipamentoProtecaoIndividualService equipamentoProtecaoIndividualService;

	@GetMapping("/equipamentoProtecaoIndividual")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EquipamentoProtecaoIndividualResponse> getAllEquipamentosProtecaoIndividual(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return equipamentoProtecaoIndividualService.getAllEquipamentosProtecaoIndividual(page, size, descricao, order);
	}

	@GetMapping("/equipamentoProtecaoIndividual/{equipamentoProtecaoIndividualId}")
	public EquipamentoProtecaoIndividualResponse getEquipamentoProtecaoIndividualById(@PathVariable Long equipamentoProtecaoIndividualId) {
		return equipamentoProtecaoIndividualService.getEquipamentoProtecaoIndividualById(equipamentoProtecaoIndividualId);
	}

	@PostMapping("/equipamentoProtecaoIndividual")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createEquipamentoProtecaoIndividual(@Valid @RequestBody EquipamentoProtecaoIndividualRequest equipamentoProtecaoIndividualRequest) {
		equipamentoProtecaoIndividualService.createEquipamentoProtecaoIndividual(equipamentoProtecaoIndividualRequest);
		return Utils.created(true, "Equipamento de Proteção Individual criado com sucesso.");
	}

	@PutMapping("/equipamentoProtecaoIndividual")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateEquipamentoProtecaoIndividual(@Valid @RequestBody EquipamentoProtecaoIndividualRequest equipamentoProtecaoIndividualRequest) {
		equipamentoProtecaoIndividualService.updateEquipamentoProtecaoIndividual(equipamentoProtecaoIndividualRequest);		
		return Utils.created(true, "Equipamento de Proteção Individual atualizado com sucesso.");
	}


	@DeleteMapping("/equipamentoProtecaoIndividual/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteEquipamentoProtecaoIndividual(@PathVariable("id") Long id) {
		equipamentoProtecaoIndividualService.deleteEquipamentoProtecaoIndividual(id);		
		return Utils.deleted(true, "Classificação de Equipamento de Proteção Coletiva deletada com sucesso.");
	}

}
