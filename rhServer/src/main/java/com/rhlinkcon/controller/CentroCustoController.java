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

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.centroCusto.CentroCustoRequest;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.CentroCustoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CentroCustoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CentroCustoController {

	@Autowired
	private CentroCustoRepository centroCustoRepository;

	@Autowired
	private CentroCustoService centroCustoService;

	@GetMapping("/centroCusto/search")
	public List<DadoBasicoDto> search() {
		return centroCustoService.search();
	}

	@GetMapping("/centrosCustos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<CentroCustoResponse> getCentrosCustos(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return centroCustoService.getAllCentrosCustos(page, size, descricao, order);
	}
	
	@GetMapping("/listaCentroCustos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<CentroCustoResponse> getListCentrosCustos() {
		return centroCustoService.getAllCentrosCustos();
	}

	@GetMapping("/centroCusto/{centroCustoId}")
	public CentroCustoResponse getCentroCustoById(@PathVariable Long centroCustoId) {
		return centroCustoService.getCentroCustoById(centroCustoId);
	}

	@GetMapping("/centroCusto/numeroConta")
	public Integer getNumeroContaCreditoDebito(@RequestParam(value = "centroCustoId", defaultValue = AppConstants.DEFAULT_EMPTY) String centroCustoId,
												@RequestParam(value = "creditoDebito", defaultValue = AppConstants.DEFAULT_EMPTY) String creditoDebito) {
		return centroCustoService.getNumeroContaCreditoDebito(Long.parseLong(centroCustoId), creditoDebito);
	}

	
	@PostMapping("/centroCusto")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCentroCusto(@Valid @RequestBody CentroCustoRequest centroCustoRequest) {
		if (centroCustoRepository.existsByDescricao(centroCustoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Centro de Custo já existe!");
		}

		centroCustoService.createCentroCusto(centroCustoRequest);

		return Utils.created(true, "Centro de Custo criado com sucesso.");
	}

	@PutMapping("/centroCusto")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCentroCusto(@Valid @RequestBody CentroCustoRequest centroCustoRequest) {
		if (centroCustoRepository.existsByDescricaoAndIdNot(centroCustoRequest.getDescricao(), centroCustoRequest.getId())) {
			return Utils.badRequest(false, "Este Centro de Custo já existe!");
		}

		centroCustoService.updateCentroCusto(centroCustoRequest);
		
		return Utils.created(true, "Centro de Custo atualizada com sucesso.");
	}


	@DeleteMapping("/centroCusto/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteCentroCusto(@PathVariable("id") Long id) {
		centroCustoService.deleteCentroCusto(id);
		
		return Utils.deleted(true, "Centro de Custo deletada com sucesso.");
	}

}
