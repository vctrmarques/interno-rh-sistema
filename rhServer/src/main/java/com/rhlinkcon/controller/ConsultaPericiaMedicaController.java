package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.ConsultaPericiaMedicaFiltro;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.periciaMedica.PericiaMedicaResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.AgendaMedicaRelatorioDto;
import com.rhlinkcon.service.AgendaMedicaService;
import com.rhlinkcon.service.ConsultaPericiaMedicaService;
import com.rhlinkcon.service.MedicoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ConsultaPericiaMedicaController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ROLE_PERICIA_MEDICA_GESTAO')";
	private static final String AUTHORIZECADASTRAR = "hasAnyRole('ADMIN','ROLE_PERICIA_MEDICA_CADASTRAR')";
	private static final String AUTHORIZEATUALIZAR = "hasAnyRole('ADMIN','ROLE_PERICIA_MEDICA_ATUALIZAR')";
	private static final String AUTHORIZEEXCLUIR = "hasAnyRole('ADMIN','ROLE_PERICIA_MEDICA_EXCLUIR')";
	private static final String AUTHORIZEVISUALIZAR = "hasAnyRole('ADMIN','ROLE_PERICIA_MEDICA_VISUALIZAR')";
	
	@Autowired
	private AgendaMedicaService agendaMedicaService;
	
	@Autowired
	private ConsultaPericiaMedicaService service;
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping("/consultasPericiasMedicas")
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<PericiaMedicaResponse> getAllConsultaPericiaMedica(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			ConsultaPericiaMedicaFiltro consultaPericiaMedicaFiltro) {
		
		Medico medico = medicoService.getMedicoByUsuarioId(currentUser.getId());
		if(medico != null) {
			consultaPericiaMedicaFiltro.setMedicoId(medico.getId());
			consultaPericiaMedicaFiltro.setNomeMedico(medico.getNome());
		}
		
		return service.getAllConsultaPericiaMedica(page, size, consultaPericiaMedicaFiltro, order);
	}
	
	@GetMapping("/consultaPericiaMedica/agenda/filtro")
	@PreAuthorize(AUTHORIZEGESTAO)
	public List<AgendaMedicaRelatorioDto> getAllAgendaMedicaDataRelatorio(@RequestParam(value = "especialidadeMedicaId") Long especialidadeMedicaId,
			@RequestParam(value = "dataAgenda") String dataAgenda, @RequestParam(value = "isMaiorQue") Boolean isMaiorQue) {
		
		return agendaMedicaService.getAllAgendaMedicaDataPericiaMedica(especialidadeMedicaId, dataAgenda, isMaiorQue);
	}
	
	@PostMapping("/consultaPericiaMedica")
	@PreAuthorize(AUTHORIZECADASTRAR)
	public ResponseEntity<?> create(@Valid @RequestBody ConsultaPericiaMedicaDto dto) {
		service.create(dto);
		return Utils.created(true, "Consulta Perícia Médica criada com sucesso.");
	}
	
	@PutMapping("/consultaPericiaMedica/naoCompareceu")
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public ResponseEntity<?> updateNaoCompareceu(@Valid @RequestBody String cpf) {
		return service.updateNaoCompareceu(cpf);
	}
	
	@PutMapping("/consultaPericiaMedica/recusouPericia")
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public ResponseEntity<?> updateRecusouPericia(@Valid @RequestBody Long id) {
		return service.updateRecusouPericia(id);
	}
	
	@GetMapping("/consultaPericiaMedica/{id}")
	@PreAuthorize(AUTHORIZEVISUALIZAR)
	public ConsultaPericiaMedicaDto getById(@PathVariable("id") Long id) {
		return service.getByIdConsulta(id);
	}
}
