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

import com.rhlinkcon.payload.prestadorServico.PrestadorServicoRequest;
import com.rhlinkcon.payload.prestadorServico.PrestadorServicoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.PrestadorServicoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.PrestadorServicoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class PrestadorServicoController {

	@Autowired
	private PrestadorServicoRepository prestadorServicoRepository;

	@Autowired
	private PrestadorServicoService prestadorServicoService;

	@GetMapping("/prestadoresServicos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<PrestadorServicoResponse> getAllPrestadoresServicos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nomeCivil", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeCivil) {
		return prestadorServicoService.getAllPrestadoresServicos(page, size, nomeCivil, order);
	}
	
//	@GetMapping("/listaPrestadoresServicos")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	public List<PrestadorServicoResponse> getAllPrestadoresServicos() {
//		return prestadorServicoService.getAllPrestadoresServicos();
//	}
//
//	@GetMapping("/prestadorServico/searchComplete")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	public List<PrestadorServicoResponse> searchAllByNomeCivil(
//			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
//		return prestadorServicoService.searchAllByNomeCivil(search);
//	}

	@GetMapping("/prestadorServico/{prestadorServicoId}")
	public PrestadorServicoResponse getPrestadorServicoById(@PathVariable Long prestadorServicoId) {
		return prestadorServicoService.getPrestadorServicoById(prestadorServicoId);
	}

	@PostMapping("/prestadorServico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createPrestadorServico(@Valid @RequestBody PrestadorServicoRequest prestadorServicoRequest) {
		if (prestadorServicoRepository.existsByNomeCivil(prestadorServicoRequest.getNomeCivil())) {
			return Utils.badRequest(false, "Já existe um Prestador de Serviço com este nome!");
		}
		
		prestadorServicoService.createPrestadorServico(prestadorServicoRequest);

		return Utils.created(true, "Prestador de Serviço criado com sucesso.");
	}

	@PutMapping("/prestadorServico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updatePrestadorServico(@Valid @RequestBody PrestadorServicoRequest prestadorServicoRequest) {
		if (prestadorServicoRepository.existsByNomeCivilAndIdNot(prestadorServicoRequest.getNomeCivil(), prestadorServicoRequest.getId())) {
			return Utils.badRequest(false, "Já existe um Prestador de Serviço com este nome!!");
		}

		prestadorServicoService.updatePrestadorServico(prestadorServicoRequest);
		
		return Utils.created(true, "Prestador de Serviço atualizado com sucesso.");
	}


	@DeleteMapping("/prestadorServico/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deletePrestadorServico(@PathVariable("id") Long id) {
		prestadorServicoService.deletePrestadorServico(id);
		
		return Utils.deleted(true, "Prestador de Serviço deletado com sucesso.");
	}

}
