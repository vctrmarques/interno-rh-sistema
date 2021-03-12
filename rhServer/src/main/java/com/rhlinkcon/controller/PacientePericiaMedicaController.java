package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.ConsultaPericiaMedicaFiltro;
import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.periciaMedica.PericiaMedicaResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.PacientePericiaMedicaDto;
import com.rhlinkcon.service.PacientePericiaMedicaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class PacientePericiaMedicaController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ROLE_PERICIA_MEDICA_GESTAO')";
	private static final String AUTHORIZEATUALIZAR = "hasAnyRole('ADMIN','ROLE_PERICIA_MEDICA_ATUALIZAR')";
	
	@Autowired
	private PacientePericiaMedicaService pacientePericiaMedicaService;
	
	
	@GetMapping("/pacientesPericiasMedicas")
	@PreAuthorize(AUTHORIZEGESTAO)
	public List<PericiaMedicaResponse> getAllFuncionariosBySituacaoFuncional(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			FuncionarioFiltro funcionarioFiltro) {
		
			return pacientePericiaMedicaService.getAllFuncionariosBySituacaoFuncional(page, size, funcionarioFiltro, order);
	}
	
	@GetMapping("/pacientesPericiasMedicasAll")
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<PericiaMedicaResponse> getAllPacientePericiaMedica(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			ConsultaPericiaMedicaFiltro consultaPericiaMedicaFiltro) {
		return pacientePericiaMedicaService.getAllPacientePericiaMedica(page, size, consultaPericiaMedicaFiltro, order);
	}
	
	
	@GetMapping("/pacientePericiaMedica/{id}")
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public PacientePericiaMedicaDto getById(@PathVariable("id") Long id) {
		return pacientePericiaMedicaService.getById(id);
	}
	
	@PutMapping("/pacientePericiaMedica/recusouPericia")
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public ResponseEntity<?> getByCpf(@Valid @RequestBody String cpf) {
		pacientePericiaMedicaService.updateComparecimento(cpf);
		return Utils.created(true, "Paciente atualizado com sucesso.");
	}
	
	
	
}
