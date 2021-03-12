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
import com.rhlinkcon.payload.tomadorServico.TomadorServicoRequest;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoResponse;
import com.rhlinkcon.repository.TomadorServicoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.TomadorServicoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class TomadorServicoController {

	@Autowired
	private TomadorServicoRepository tomadorRepository;

	@Autowired
	private TomadorServicoService tomadorService;

	@GetMapping("/tomadorServico/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<TomadorServicoResponse> searchAllByCodigoOrDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search,
			@RequestParam(value = "essencial", defaultValue = AppConstants.DEFAULT_EMPTY) String essencial) {
		return tomadorService.searchAllByRazaoSocial(search, essencial);
	}

	@GetMapping("/tomadores")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<TomadorServicoResponse> getAllTomadoresServico(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "cnpj", defaultValue = AppConstants.DEFAULT_EMPTY) String cnpj) {
		return tomadorService.getAllTomadoresServico(page, size, cnpj, order);
	}

	@GetMapping("/tomadorServico/{tomadorServicoId}")
	public TomadorServicoResponse getTomadorServicoById(@PathVariable Long tomadorServicoId) {
		return tomadorService.getTomadorServicoById(tomadorServicoId);
	}

	@PostMapping("/tomadorServico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createTomadorServico(@Valid @RequestBody TomadorServicoRequest tomadorRequest) {
		if (tomadorRepository.existsByCnpj(tomadorRequest.getCnpj())) {
			return Utils.badRequest(false, "Já existe um Tomador de Serviço com este CNPJ");
		}

		tomadorService.createTomadorServico(tomadorRequest);

		return Utils.created(true, "Tomador de Serviço criado com sucesso.");
	}

	@PutMapping("/tomadorServico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateTomadorServico(@Valid @RequestBody TomadorServicoRequest tomadorRequest) {
		if (tomadorRepository.existsByCnpjAndIdNot(tomadorRequest.getCnpj(), tomadorRequest.getId())) {
			return Utils.badRequest(false, "Já existe um Tomador de Serviço com esta descrição!!");
		}

		tomadorService.updateTomadorServico(tomadorRequest);

		return Utils.created(true, "Tomador de Serviço atualizado com sucesso.");
	}

	@DeleteMapping("/tomadorServico/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteTomadorServico(@PathVariable("id") Long id) {
		tomadorService.deleteTomadorServico(id);

		return Utils.deleted(true, "Tomador de Serviço deletado com sucesso.");
	}

}
