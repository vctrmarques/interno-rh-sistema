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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.AgenciaFiltro;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.agencia.AgenciaRequest;
import com.rhlinkcon.payload.agencia.AgenciaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.service.AgenciaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AgenciaController {

	@Autowired
	private AgenciaRepository agenciaRepository;

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private AgenciaService agenciaService;

	@GetMapping("/agencias")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<AgenciaResponse> getAllAgencia(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "bancoId", defaultValue = AppConstants.DEFAULT_EMPTY) String bancoId) {

		AgenciaFiltro agenciaFiltro = new AgenciaFiltro();
		agenciaFiltro.setNome(nome);
		if (!bancoId.isEmpty()) {
			Long bancoIdLong = Long.parseLong(bancoId);
			Banco banco = bancoRepository.findById(bancoIdLong)
					.orElseThrow(() -> new ResourceNotFoundException("Banco", "id", bancoIdLong));
			agenciaFiltro.setBanco(banco);
		}
		return agenciaService.getAllAgencia(page, size, order, agenciaFiltro);
	}

	@GetMapping("/listaAgencias")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AgenciaResponse> getAllAgencia() {
		return agenciaService.getAllAgencia();
	}

	@GetMapping("/agencia/{agenciaId}")
	public AgenciaResponse getAgenciaById(@PathVariable Long agenciaId) {
		return agenciaService.getAgenciaById(agenciaId);
	}

	@PostMapping("/agencia")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createAgencia(@Valid @RequestBody AgenciaRequest agenciaRequest) {
		if (agenciaRepository.existsByNome(agenciaRequest.getNome())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		agenciaService.createAgencia(agenciaRequest);

		return Utils.created(true, "Agencia criada com sucesso.");
	}

	@PutMapping("/agencia")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateAgencia(@Valid @RequestBody AgenciaRequest agenciaRequest) {
		if (agenciaRepository.existsByNomeAndIdNot(agenciaRequest.getNome(), agenciaRequest.getId())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		agenciaService.updateAgencia(agenciaRequest);

		return Utils.created(true, "Agencia atualizada com sucesso.");
	}

	@DeleteMapping("/agencia/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteAgencia(@PathVariable("id") Long id) {
		agenciaService.deleteAgencia(id);

		return Utils.deleted(true, "Agencia deletada com sucesso.");
	}
	
	@GetMapping("/agencia/searchComBanco")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AgenciaResponse> searchByNomeAndBanco(@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) Integer search, 
			@RequestParam(value = "bancoId", defaultValue = AppConstants.DEFAULT_EMPTY) Long bancoId) {
		return agenciaService.findByNomeAndBanco(search, bancoId);
	}
	
	@GetMapping("/agencia/search")
	public List<DadoBasicoDto> getDadoBasicoList(@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "bancoId", required = false) String bancoId) {
		return agenciaService.getDadoBasicoList(search, bancoId);
	}

}
