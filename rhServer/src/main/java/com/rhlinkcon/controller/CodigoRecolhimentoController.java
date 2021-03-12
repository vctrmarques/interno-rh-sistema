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
import com.rhlinkcon.payload.codigoRecolhimento.CodigoRecolhimentoRequest;
import com.rhlinkcon.payload.codigoRecolhimento.CodigoRecolhimentoResponse;
import com.rhlinkcon.repository.CodigoRecolhimentoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CodigoRecolhimentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CodigoRecolhimentoController {

	@Autowired
	private CodigoRecolhimentoRepository codigoRecolhimentoRepository;

	@Autowired
	private CodigoRecolhimentoService codigoRecolhimentoService;

	@GetMapping("/codigosRecolhimento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CodigoRecolhimentoResponse> getAllCodigosRecolhimento(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return codigoRecolhimentoService.getAllCodigosRecolhimento(page, size, descricao, order);
	}
	
	@GetMapping("/listaCodigosRecolhimento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CodigoRecolhimentoResponse> getAllCodigosRecolhimento() {
		return codigoRecolhimentoService.getAllCodigosRecolhimento();
	}
	
	@GetMapping("/codigoRecolhimento/{codigoRecolhimentoId}")
	public CodigoRecolhimentoResponse getCodigoRecolhimentoById(@PathVariable Long codigoRecolhimentoId) {
		return codigoRecolhimentoService.getCodigoRecolhimentoById(codigoRecolhimentoId);
	}

	@PostMapping("/codigoRecolhimento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCodigoRecolhimento(@Valid @RequestBody CodigoRecolhimentoRequest codigoRecolhimentoRequest) {
		if (codigoRecolhimentoRepository.existsByCodigo(codigoRecolhimentoRequest.getCodigo())) {
			return Utils.badRequest(false, "Este Código de Recolhimento já existe!");
		}
		
		if (codigoRecolhimentoRepository.existsByDescricao(codigoRecolhimentoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Código de Recolhimento já existe!");
		}

		codigoRecolhimentoService.createCodigoRecolhimento(codigoRecolhimentoRequest);

		return Utils.created(true, "Código de Recolhimento criado com sucesso.");
	}

	@PutMapping("/codigoRecolhimento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCodigoRecolhimento(@Valid @RequestBody CodigoRecolhimentoRequest codigoRecolhimentoRequest) {
		if (codigoRecolhimentoRepository.existsByDescricaoAndIdNot(codigoRecolhimentoRequest.getDescricao(), codigoRecolhimentoRequest.getId())) {
			return Utils.badRequest(false, "Este Codigo de Recolhimento já existe!");
		}

		codigoRecolhimentoService.updateCodigoRecolhimento(codigoRecolhimentoRequest);
		
		return Utils.created(true, "Código de Recolhimento atualizado com sucesso.");
	}


	@DeleteMapping("/codigoRecolhimento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCodigoRecolhimento(@PathVariable("id") Long id) {
		codigoRecolhimentoService.deleteCodigoRecolhimento(id);
		
		return Utils.deleted(true, "Código de Recolhimento deletado com sucesso.");
	}

}
