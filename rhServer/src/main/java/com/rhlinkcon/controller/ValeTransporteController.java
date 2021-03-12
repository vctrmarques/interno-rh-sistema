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
import com.rhlinkcon.payload.valeTransporte.ValeTransporteRequest;
import com.rhlinkcon.payload.valeTransporte.ValeTransporteResponse;
import com.rhlinkcon.repository.ValeTransporteRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ValeTransporteService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ValeTransporteController {

	@Autowired
	private ValeTransporteRepository valeTransporteRepository;

	@Autowired
	private ValeTransporteService valeTransporteService;

	@GetMapping("/valesTransporte")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ValeTransporteResponse> getAllValesTransporte(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "codigo", defaultValue = AppConstants.DEFAULT_EMPTY) String codigo) {
		return valeTransporteService.getAllValesTransporte(page, size, codigo, order);
	}
	
	@GetMapping("/valeTransporte/{valeTransporteId}")
	public ValeTransporteResponse getValeTransporteById(@PathVariable Long valeTransporteId) {
		return valeTransporteService.getValeTransporteById(valeTransporteId);
	}

	@PostMapping("/valeTransporte")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createValeTransporte(@Valid @RequestBody ValeTransporteRequest valeTransporteRequest) {
		if (valeTransporteRepository.existsByCodigo(valeTransporteRequest.getCodigo())) {
			return Utils.badRequest(false, "Este Vale Transporte já existe!");
		}

		valeTransporteService.createValeTransporte(valeTransporteRequest);

		return Utils.created(true, "Vale Transporte criado com sucesso.");
	}

	@PutMapping("/valeTransporte")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateValeTransporte(@Valid @RequestBody ValeTransporteRequest valeTransporteRequest) {
		if (valeTransporteRepository.existsByCodigoAndIdNot(valeTransporteRequest.getCodigo(), valeTransporteRequest.getId())) {
			return Utils.badRequest(false, "Este Vale Transporte já existe!");
		}

		valeTransporteService.updateValeTransporte(valeTransporteRequest);
		
		return Utils.created(true, "Vale Transporte atualizado com sucesso.");
	}


	@DeleteMapping("/valeTransporte/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteValeTransporte(@PathVariable("id") Long id) {
		valeTransporteService.deleteValeTransporte(id);
		
		return Utils.deleted(true, "Vale Transporte deletado com sucesso.");
	}
	
	@GetMapping("/listaValesTransportes")
	public List<ValeTransporteResponse> getAllValesTransportes(){
		return valeTransporteService.getAllvalesTransporte();
	}

}
