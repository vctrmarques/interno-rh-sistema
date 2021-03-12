package com.rhlinkcon.model;

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

import com.rhlinkcon.filtro.AgendaMedicaFiltro;
import com.rhlinkcon.payload.agendaMedica.AgendaMedicaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.AgendaMedicaDto;
import com.rhlinkcon.service.AgendaMedicaRelatorioDto;
import com.rhlinkcon.service.AgendaMedicaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/agendaMedica")
public class AgendaMedicaController {

	private static final String AUTHORIZEGESTAO = "hasAnyRole('ADMIN','ROLE_AGENDA_MEDICA_GESTAO')";
	private static final String AUTHORIZECADASTRAR = "hasAnyRole('ADMIN','ROLE_AGENDA_MEDICA_CADASTRAR')";
	private static final String AUTHORIZEATUALIZAR = "hasAnyRole('ADMIN','ROLE_AGENDA_MEDICA_ATUALIZAR')";
	private static final String AUTHORIZEEXCLUIR = "hasAnyRole('ADMIN','ROLE_AGENDA_MEDICA_EXCLUIR')";
	private static final String AUTHORIZEVISUALIZAR = "hasAnyRole('ADMIN','ROLE_AGENDA_MEDICA_VISUALIZAR')";
	
	@Autowired
	private AgendaMedicaService service;
	
	@GetMapping
	@PreAuthorize(AUTHORIZEGESTAO)
	public PagedResponse<AgendaMedicaResponse> getAllAgendaMedica(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			AgendaMedicaFiltro filtro) {
		return service.getAllAgendaMedica(page, size, filtro, order);
	}
	
	@GetMapping("/relatorio/search")
	@PreAuthorize(AUTHORIZEGESTAO)
	public List<AgendaMedicaRelatorioDto> getAllAgendaMedicaRelatorio(AgendaMedicaFiltro filtro) {
		return service.getAllAgendaMedicaRelatorio(filtro);
	}
	
	@PostMapping
	@PreAuthorize(AUTHORIZECADASTRAR)
	public ResponseEntity<?> create(@Valid @RequestBody AgendaMedicaDto dto) {
		service.create(dto);
		return Utils.created(true, "Agenda Médica criada com sucesso.");
	}
	
	@GetMapping("/{id}")
	@PreAuthorize(AUTHORIZEVISUALIZAR)
	public AgendaMedicaDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@PutMapping
	@PreAuthorize(AUTHORIZEATUALIZAR)
	public ResponseEntity<?> update(@Valid @RequestBody AgendaMedicaDto dto) {
		service.update(dto);
		return Utils.created(true, "Agenda Médica atualizado com sucesso.");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(AUTHORIZEEXCLUIR)
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return Utils.deleted(true, "Agenda Médica deletado com sucesso.");
	}
	
	@GetMapping("/calendario/search")
	public List<AgendaMedicaResponse> calcularAgenda(@RequestParam(value = "dtInicial", required = true) String dtInicial,
			@RequestParam(value = "dtFinal", required = true) String dtFinal, @RequestParam(value = "semanal") boolean semanal,
			@RequestParam(value = "segunda", required = true) boolean segunda, @RequestParam(value = "terca", required = true) boolean terca,
			@RequestParam(value = "quarta", required = true) boolean quarta, @RequestParam(value = "quinta", required = true) boolean quinta,
			@RequestParam(value = "sexta", required = true) boolean sexta, @RequestParam(value = "sabado", required = true) boolean sabado,
			@RequestParam(value = "domingo", required = true) boolean domingo, @RequestParam(value = "horaInicial") String horaInicial, 
			 @RequestParam(value = "horaFinal", required = true) String horaFinal, @RequestParam(value = "intervalo", required = true) int intervalo) {
		
		return service.calcularAgenda(dtInicial, dtFinal, semanal, segunda, terca, quarta, quinta, sexta, sabado, domingo , horaInicial, horaFinal, intervalo);
		
	}	
	
}
