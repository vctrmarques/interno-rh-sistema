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
import com.rhlinkcon.payload.codigoPagamentoGps.CodigoPagamentoGpsRequest;
import com.rhlinkcon.payload.codigoPagamentoGps.CodigoPagamentoGpsResponse;
import com.rhlinkcon.repository.CodigoPagamentoGpsRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CodigoPagamentoGpsService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CodigoPagamentoGpsController {

	@Autowired
	private CodigoPagamentoGpsRepository codigoPagamentoGpsRepository;

	@Autowired
	private CodigoPagamentoGpsService codigoPagamentoGpsService;

	@GetMapping("/codigosPagamentosGps")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CodigoPagamentoGpsResponse> getAllCodigosPagamentosGps(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return codigoPagamentoGpsService.getAllCodigosPagamentosGps(page, size, descricao, order);
	}
	
	@GetMapping("/listaCodigosPagamentosGps")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CodigoPagamentoGpsResponse> getAllCodigosPagamentosGps() {
		return codigoPagamentoGpsService.getAllCodigosPagamentosGps();
	}
	
	@GetMapping("/codigoPagamentoGps/{codigoPagamentoGpsId}")
	public CodigoPagamentoGpsResponse getCodigoPagamentoGpsById(@PathVariable Long codigoPagamentoGpsId) {
		return codigoPagamentoGpsService.getCodigoPagamentoGpsById(codigoPagamentoGpsId);
	}

	@PostMapping("/codigoPagamentoGps")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCodigoPagamentoGps(@Valid @RequestBody CodigoPagamentoGpsRequest codigoPagamentoGpsRequest) {
		if (codigoPagamentoGpsRepository.existsByDescricao(codigoPagamentoGpsRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Código de Pagamento GPS já existe!");
		}

		codigoPagamentoGpsService.createCodigoPagamentoGps(codigoPagamentoGpsRequest);

		return Utils.created(true, "Código de Pagamento GPS criado com sucesso.");
	}

	@PutMapping("/codigoPagamentoGps")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCodigoPagamentoGps(@Valid @RequestBody CodigoPagamentoGpsRequest codigoPagamentoGpsRequest) {
		if (codigoPagamentoGpsRepository.existsByDescricaoAndIdNot(codigoPagamentoGpsRequest.getDescricao(), codigoPagamentoGpsRequest.getId())) {
			return Utils.badRequest(false, "Este Codigo de Pagamento GPS já existe!");
		}

		codigoPagamentoGpsService.updateCodigoPagamentoGps(codigoPagamentoGpsRequest);
		
		return Utils.created(true, "Código de Pagamento GPS atualizado com sucesso.");
	}


	@DeleteMapping("/codigoPagamentoGps/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCodigoPagamentoGps(@PathVariable("id") Long id) {
		codigoPagamentoGpsService.deleteCodigoPagamentoGps(id);
		
		return Utils.deleted(true, "Código de Pagamento GPS deletado com sucesso.");
	}

}
